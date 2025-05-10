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
import java.util.Optional;

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

    @PostMapping("/contratos")
    public ResponseEntity<Contrato> createContrato(@RequestBody Contrato contrato) {
        try {
            // Verificar si ya existe un contrato activo para el cliente
            Optional<Contrato> contratoExistente = contratoRepository.findByClienteAndEstado(contrato.getCliente(), true);

            // Si ya existe un contrato activo para este cliente, omitir la creación
            if (contratoExistente.isPresent()) {
                return ResponseEntity.noContent().build(); // Retorna un 204 No Content si ya existe un contrato activo
            }

            // Si no existe un contrato activo, proceder con la creación
            Servicio servicio = contrato.getServicio();
            if (servicio != null && servicio.getId() == 0) {
                // Si el servicio no existe, guardarlo
                servicio = contratoService.guardarServicioDesdeContrato(servicio);
                contrato.setServicio(servicio);
            }

            // Guardar el contrato
            Contrato contratoGuardado = contratoRepository.save(contrato);
            return ResponseEntity.ok(contratoGuardado); // Retorna el contrato creado si no existía uno activo

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null); // Retorna un 500 en caso de error
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
