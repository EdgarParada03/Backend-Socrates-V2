package com.example.SocratesBackend.controladores;

import com.example.SocratesBackend.modelos.Empleado;
import com.example.SocratesBackend.repositorios.EmpleadoRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    // Get all empleados
    @GetMapping("/empleados")
    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

    // Create empleado
    @PostMapping("/empleados")
    public Empleado createEmpleado(@RequestBody Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    // Get empleado by id
    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no existe con id: " + id));
        return ResponseEntity.ok(empleado);
    }

    // Update empleado
    @PutMapping("/empleados/{id}")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable Long id, @RequestBody Empleado empleadoDetails) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no existe con id: " + id));


        empleado.setPrimerNombre(empleadoDetails.getPrimerNombre());
        empleado.setSegundoNombre(empleadoDetails.getSegundoNombre());
        empleado.setPrimerApellido(empleadoDetails.getPrimerApellido());
        empleado.setSegundoApellido(empleadoDetails.getSegundoApellido());
        empleado.setTipoIdentificacion(empleadoDetails.getTipoIdentificacion());
        empleado.setNumeroIdentificacion(empleadoDetails.getNumeroIdentificacion());
        empleado.setSexo(empleadoDetails.getSexo());
        empleado.setCorreoElectronico(empleadoDetails.getCorreoElectronico());
        empleado.setTelefono(empleadoDetails.getTelefono());
        empleado.setFechaNacimiento(empleadoDetails.getFechaNacimiento());
        empleado.setLugarResidencia(empleadoDetails.getLugarResidencia());
        empleado.setDireccionCasa(empleadoDetails.getDireccionCasa());
        empleado.setBarrio(empleadoDetails.getBarrio());
        empleado.setEstado(empleadoDetails.isEstado());
        empleado.setCodigoEmpleado(empleadoDetails.getCodigoEmpleado());
        empleado.setCargo(empleadoDetails.getCargo());
        empleado.setTipoContrato(empleadoDetails.getTipoContrato());
        empleado.setHojaDeVida(empleadoDetails.getHojaDeVida());
        empleado.setReferenciaLaboral(empleadoDetails.getReferenciaLaboral());
        empleado.setContactoEmergenciaNombre(empleadoDetails.getContactoEmergenciaNombre());
        empleado.setContactoEmergenciaParentesco(empleadoDetails.getContactoEmergenciaParentesco());
        empleado.setContactoEmergenciaTelefono(empleadoDetails.getContactoEmergenciaTelefono());

        Empleado updatedEmpleado = empleadoRepository.save(empleado);
        return ResponseEntity.ok(updatedEmpleado);
    }

    // Delete empleado
    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmpleado(@PathVariable Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no existe con id: " + id));

        empleadoRepository.delete(empleado);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}

