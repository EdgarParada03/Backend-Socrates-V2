package com.example.SocratesBackend.servicios;

import com.example.SocratesBackend.modelos.*;
import com.example.SocratesBackend.repositorios.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

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

                String cedula = getCellValue(row.getCell(0));
                Cliente cliente = clienteRepository.findByNumeroIdentificacion(cedula);
                if (cliente == null) continue;

                Servicio servicio = new Servicio();
                servicio.setDescripcion(getCellValue(row.getCell(5)));
                servicio.setFechaServicio(getLocalDateValue(row.getCell(6)));
                servicio.setHoraServicio(getLocalTimeValue(row.getCell(7)));
                servicio.setEstado(getCellValue(row.getCell(8)));
                servicio.setFechaRegistro(getLocalDateValue(row.getCell(11)));

                Long tipoPlanId = Long.parseLong(getCellValue(row.getCell(9)));
                servicio.setTipoPlan(tipoPlanRepository.findById(tipoPlanId).orElse(null));

                Long tecnicoId = Long.parseLong(getCellValue(row.getCell(10)));
                servicio.setTecnico(empleadoRepository.findById(tecnicoId).orElse(null));

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
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
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
