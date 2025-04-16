package com.example.SocratesBackend.controladores;


import com.example.SocratesBackend.modelos.Contrato;
import com.example.SocratesBackend.repositorios.ContratoRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1")
public class ContratoController {

    @Autowired
    private ContratoRepository contratoRepository;

    // Get all contratos
    @GetMapping("/contratos")
    public List<Contrato> getAllContratos() {
        return contratoRepository.findAll();
    }

    // Create contrato
    @PostMapping("/contratos")
    public Contrato createContrato(@RequestBody Contrato contrato) {
        return contratoRepository.save(contrato);
    }

    // Get contrato by id
    @GetMapping("/contratos/{id}")
    public ResponseEntity<Contrato> getContratoById(@PathVariable Long id) {
        Contrato contrato = contratoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrato no existe con id: " + id));
        return ResponseEntity.ok(contrato);
    }

    // Update contrato
    @PutMapping("/contratos/{id}")
    public ResponseEntity<Contrato> updateContrato(@PathVariable Long id, @RequestBody Contrato contratoDetails) {
        Contrato contrato = contratoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrato no existe con id: " + id));

        contrato.setCliente(contratoDetails.getCliente());
        contrato.setServicio(contratoDetails.getServicio());
        contrato.setFechaInicio(contratoDetails.getFechaInicio());
        contrato.setFechaFin(contratoDetails.getFechaFin());
        contrato.setEstado(contratoDetails.isEstado());
        contrato.setDuracion(contratoDetails.getDuracion());

        Contrato updatedContrato = contratoRepository.save(contrato);
        return ResponseEntity.ok(updatedContrato);
    }

    // Delete contrato
    @DeleteMapping("/contratos/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteContrato(@PathVariable Long id) {
        Contrato contrato = contratoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrato no existe con id: " + id));

        contratoRepository.delete(contrato);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}

