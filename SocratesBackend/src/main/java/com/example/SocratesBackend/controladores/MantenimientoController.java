package com.example.SocratesBackend.controladores;

import com.example.SocratesBackend.modelos.Mantenimiento;
import com.example.SocratesBackend.repositorios.MantenimientoRepository;
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
public class MantenimientoController {

    @Autowired
    private MantenimientoRepository mantenimientoRepository;

    // 🔹 Obtener todos los mantenimientos
    @GetMapping("/mantenimientos")
    public List<Mantenimiento> getAllMantenimientos() {
        return mantenimientoRepository.findAll();
    }

    // 🔹 Crear un nuevo mantenimiento
    @PostMapping("/mantenimientos")
    public Mantenimiento createMantenimiento(@RequestBody Mantenimiento mantenimiento) {
        return mantenimientoRepository.save(mantenimiento);
    }

    // 🔹 Obtener un mantenimiento por ID
    @GetMapping("/mantenimientos/{id}")
    public ResponseEntity<Mantenimiento> getMantenimientoById(@PathVariable Long id) {
        Mantenimiento mantenimiento = mantenimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mantenimiento no existe con el ID: " + id));
        return ResponseEntity.ok(mantenimiento);
    }

    // 🔹 Actualizar un mantenimiento
    @PutMapping("/mantenimientos/{id}")
    public ResponseEntity<Mantenimiento> updateMantenimiento(@PathVariable Long id, @RequestBody Mantenimiento mantenimientoDetails) {
        Mantenimiento mantenimiento = mantenimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mantenimiento no existe con el ID: " + id));

        mantenimiento.setDescripcion(mantenimientoDetails.getDescripcion());
        mantenimiento.setFechaProgramada(mantenimientoDetails.getFechaProgramada());
        mantenimiento.setEstado(mantenimientoDetails.getEstado());
        mantenimiento.setTecnico(mantenimientoDetails.getTecnico());

        Mantenimiento updatedMantenimiento = mantenimientoRepository.save(mantenimiento);
        return ResponseEntity.ok(updatedMantenimiento);
    }

    // 🔹 Eliminar un mantenimiento
    @DeleteMapping("/mantenimientos/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteMantenimiento(@PathVariable Long id) {
        Mantenimiento mantenimiento = mantenimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mantenimiento no existe con el ID: " + id));

        mantenimientoRepository.delete(mantenimiento);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
