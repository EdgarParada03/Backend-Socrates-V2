package com.example.SocratesBackend.controladores;

import com.example.SocratesBackend.modelos.Soporte;
import com.example.SocratesBackend.repositorios.SoporteRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api/v1")
public class SoporteController {

    @Autowired
    private SoporteRepository soporteRepository;

    @GetMapping("/soportes/count")
    public long getSoporteCount() {
        return soporteRepository.count();
    }

    // Obtener todos los soportes
    @GetMapping("/soportes")
    public List<Soporte> getAllSoportes() {
        return soporteRepository.findAll();
    }

    // Crear soporte
    @PostMapping("/soportes")
    public Soporte createSoporte(@RequestBody Soporte soporte) {
        return soporteRepository.save(soporte);
    }

    // Obtener soporte por ID
    @GetMapping("/soportes/{id}")
    public ResponseEntity<Soporte> getSoporteById(@PathVariable Long id) {
        Soporte soporte = soporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Soporte no existe con id: " + id));
        return ResponseEntity.ok(soporte);
    }

    // Actualizar soporte
    @PutMapping("/soportes/{id}")
    public ResponseEntity<Soporte> updateSoporte(@PathVariable Long id, @RequestBody Soporte soporteDetails) {
        Soporte soporte = soporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Soporte no existe con id: " + id));

        soporte.setCliente(soporteDetails.getCliente());
        soporte.setDescripcion(soporteDetails.getDescripcion());
        soporte.setFechaSolicitud(soporteDetails.getFechaSolicitud());
        soporte.setEstado(soporteDetails.getEstado());
        soporte.setTecnico(soporteDetails.getTecnico());
        soporte.setFechaRegistro(soporteDetails.getFechaRegistro());

        Soporte updatedSoporte = soporteRepository.save(soporte);
        return ResponseEntity.ok(updatedSoporte);
    }

    // Eliminar soporte
    @DeleteMapping("/soportes/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteSoporte(@PathVariable Long id) {
        Soporte soporte = soporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Soporte no existe con id: " + id));

        soporteRepository.delete(soporte);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}

