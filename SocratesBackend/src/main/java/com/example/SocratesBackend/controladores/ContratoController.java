package com.example.SocratesBackend.controladores;

import com.example.SocratesBackend.modelos.Contrato;
import com.example.SocratesBackend.modelos.Servicio;
import com.example.SocratesBackend.repositorios.ContratoRepository;
import com.example.SocratesBackend.servicios.ContratoService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"}, allowCredentials = "true")
@RequestMapping("/api/v1")
public class ContratoController {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private ContratoService contratoService;

    // Obtener todos los contratos
    @GetMapping("/contratos")
    public List<Contrato> getAllContratos() {
        return contratoRepository.findAll();
    }

    // Crear contrato (y servicio si viene completo)
    @PostMapping("/contratos")
    public ResponseEntity<Contrato> createContrato(@RequestBody Contrato contrato) {
        try {
            Servicio servicio = contrato.getServicio();

            // Validar si el servicio viene completo y no tiene ID aún (es nuevo)
            if (servicio != null && servicio.getId() == 0) {
                servicio = contratoService.guardarServicioDesdeContrato(servicio);
                contrato.setServicio(servicio);
            }

            Contrato contratoGuardado = contratoRepository.save(contrato);
            return ResponseEntity.ok(contratoGuardado);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }


    // Obtener contrato por ID
    @GetMapping("/contratos/{id}")
    public ResponseEntity<Contrato> getContratoById(@PathVariable Long id) {
        Contrato contrato = contratoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrato no existe con el id: " + id));
        return ResponseEntity.ok(contrato);
    }

    // Actualizar contrato
    @PutMapping("/contratos/{id}")
    public ResponseEntity<?> updateContrato(@PathVariable Long id, @RequestBody Contrato contratoDetails) {
        try {
            Contrato contrato = contratoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Contrato no existe con el id: " + id));

            contrato.setCliente(contratoDetails.getCliente());
            contrato.setServicio(contratoDetails.getServicio());
            contrato.setFechaInicio(contratoDetails.getFechaInicio());
            contrato.setFechaFin(contratoDetails.getFechaFin());
            contrato.setEstado(contratoDetails.isEstado());
            contrato.setDuracion(contratoDetails.getDuracion());

            Contrato updatedContrato = contratoRepository.save(contrato);
            return ResponseEntity.ok(updatedContrato);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al actualizar el contrato: " + e.getMessage());
        }
    }

    // Eliminar contrato
    @DeleteMapping("/contratos/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteContrato(@PathVariable Long id) {
        Contrato contrato = contratoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrato no existe con el id: " + id));

        contratoRepository.delete(contrato);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    // Importar contratos desde archivo Excel
    @PostMapping("/contratos/importar-excel")
    public ResponseEntity<String> importarContratosDesdeExcel(@RequestParam("file") MultipartFile file) {
        try {
            contratoService.importarContratosDesdeExcel(file);
            return ResponseEntity.ok("Contratos importados correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al importar contratos: " + e.getMessage());
        }
    }
}
