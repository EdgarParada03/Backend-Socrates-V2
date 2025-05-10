package com.example.SocratesBackend.servicios;

import com.example.SocratesBackend.modelos.*;
import com.example.SocratesBackend.repositorios.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class ContratoService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private TipoPlanRepository tipoPlanRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public void importarContratosDesdeExcel(MultipartFile file) throws Exception {
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String cedulaCliente = getCellValue(row.getCell(0));
                Cliente cliente = clienteRepository.findByNumeroIdentificacion(cedulaCliente);
                if (cliente == null) {
                    System.out.println("Cliente con cédula " + cedulaCliente + " no encontrado.");
                    continue;
                }

                // Verificar si el cliente ya tiene un contrato activo
                Optional<Contrato> contratoExistente = contratoRepository.findByClienteAndEstado(cliente, true);
                if (contratoExistente.isPresent()) {
                    System.out.println("El cliente con cédula " + cedulaCliente + " ya tiene un contrato activo. Omitiendo creación.");
                    continue;  // Omitir este cliente y continuar con el siguiente contrato
                }

                Servicio servicio = new Servicio();
                servicio.setDescripcion(getCellValue(row.getCell(5)));
                servicio.setFechaServicio(getLocalDateValue(row.getCell(6)));
                servicio.setHoraServicio(getLocalTimeValue(row.getCell(7)));
                servicio.setEstado(getCellValue(row.getCell(8)));
                servicio.setFechaRegistro(getLocalDateValue(row.getCell(11)));

                // Buscar tipo de plan
                String nombreTipoPlan = getCellValue(row.getCell(9));
                Optional<TipoPlan> tipoPlan = tipoPlanRepository.findByNombre(nombreTipoPlan);
                if (tipoPlan.isEmpty()) {
                    System.out.println("Tipo de plan '" + nombreTipoPlan + "' no encontrado.");
                    continue;
                }
                servicio.setTipoPlan(tipoPlan.get());

                // Buscar técnico por cédula
                String cedulaTecnico = getCellValue(row.getCell(10));
                Optional<Empleado> tecnico = empleadoRepository.findByNumeroIdentificacion(cedulaTecnico);
                if (tecnico.isEmpty()) {
                    System.out.println("Técnico con cédula " + cedulaTecnico + " no encontrado.");
                    continue;
                }
                servicio.setTecnico(tecnico.get());

                servicioRepository.save(servicio);

                Contrato contrato = new Contrato();
                contrato.setCliente(cliente);
                contrato.setServicio(servicio);
                contrato.setFechaInicio(getDateValue(row.getCell(1)));
                contrato.setFechaFin(getDateValue(row.getCell(2)));
                contrato.setEstado(Boolean.parseBoolean(getCellValue(row.getCell(3))));
                contrato.setDuracion(getCellValue(row.getCell(4)));

                contratoRepository.save(contrato);
            }
        }
    }


    private String getCellValue(Cell cell) {
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    yield cell.getDateCellValue().toString();
                } else {
                    yield String.valueOf((long) cell.getNumericCellValue());
                }
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> null;
        };
    }

    private LocalDate getLocalDateValue(Cell cell) {
        if (cell == null) return null;
        try {
            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } else if (cell.getCellType() == CellType.STRING) {
                return LocalDate.parse(cell.getStringCellValue().trim());
            }
        } catch (Exception e) {
            System.out.println("Error parsing LocalDate: " + e.getMessage());
        }
        return null;
    }

    private LocalTime getLocalTimeValue(Cell cell) {
        if (cell == null) return null;
        try {
            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
            } else if (cell.getCellType() == CellType.STRING) {
                return LocalTime.parse(cell.getStringCellValue().trim());
            }
        } catch (Exception e) {
            System.out.println("Error parsing LocalTime: " + e.getMessage());
        }
        return null;
    }

    private Date getDateValue(Cell cell) {
        if (cell == null) return null;
        try {
            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            } else if (cell.getCellType() == CellType.STRING) {
                return Date.from(LocalDate.parse(cell.getStringCellValue().trim())
                        .atStartOfDay(ZoneId.systemDefault()).toInstant());
            }
        } catch (Exception e) {
            System.out.println("Error parsing Date: " + e.getMessage());
        }
        return null;
    }

    public Servicio guardarServicioDesdeContrato(Servicio servicio) {
        return servicioRepository.save(servicio);
    }
}
