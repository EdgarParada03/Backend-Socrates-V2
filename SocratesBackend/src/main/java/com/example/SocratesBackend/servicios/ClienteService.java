package com.example.SocratesBackend.servicios;

import com.example.SocratesBackend.modelos.Cliente;
import com.example.SocratesBackend.repositorios.ClienteRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public void importarClientes(MultipartFile file) throws Exception {
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String numeroIdentificacion = getCellValue(row.getCell(5));

                if (numeroIdentificacion != null && !clienteRepository.existsByNumeroIdentificacion(numeroIdentificacion)) {
                    Cliente cliente = new Cliente();
                    cliente.setPrimerNombre(getCellValue(row.getCell(0)));
                    cliente.setSegundoNombre(getCellValue(row.getCell(1)));
                    cliente.setPrimerApellido(getCellValue(row.getCell(2)));
                    cliente.setSegundoApellido(getCellValue(row.getCell(3)));
                    cliente.setTipoIdentificacion(getCellValue(row.getCell(4)));
                    cliente.setNumeroIdentificacion(numeroIdentificacion);
                    cliente.setSexo(getCellValue(row.getCell(6)));
                    cliente.setCorreoElectronico(getCellValue(row.getCell(7)));
                    cliente.setTelefono(getCellValue(row.getCell(8)));
                    // cliente.setFechaNacimiento(getLocalDateValue(row.getCell(9)));  --> Eliminado
                    cliente.setLugarResidencia(getCellValue(row.getCell(9))); // Ajustamos la posición
                    cliente.setDireccionCasa(getCellValue(row.getCell(10)));
                    cliente.setBarrio(getCellValue(row.getCell(11)));
                    cliente.setEstado(getBooleanValue(row.getCell(12)));
                    cliente.setFechaRegistro(getLocalDateValue(row.getCell(13)));
                    cliente.setTipoCliente(getCellValue(row.getCell(14)));

                    clienteRepository.save(cliente);
                }
            }
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().trim();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((long) cell.getNumericCellValue());
        }
        return null;
    }

    private Boolean getBooleanValue(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.BOOLEAN) {
            return cell.getBooleanCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            return Boolean.parseBoolean(cell.getStringCellValue());
        }
        return null;
    }

    private LocalDate getLocalDateValue(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            Instant instant = cell.getDateCellValue().toInstant();
            return instant.atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }
}
