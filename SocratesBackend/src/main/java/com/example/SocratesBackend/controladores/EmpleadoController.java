package com.example.SocratesBackend.controladores;

import com.example.SocratesBackend.modelos.Empleado;
import com.example.SocratesBackend.repositorios.EmpleadoRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @GetMapping("/empleados/count")
    public long getEmpleadoCount() {
        return empleadoRepository.count();
    }

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

    /*@PostMapping("/{id}/hoja-vida")
    public ResponseEntity<String> subirHojaDeVida(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            if (!file.getContentType().equals("application/pdf")) {
                return ResponseEntity.badRequest().body("Solo se permiten archivos PDF.");
            }

            // Crear carpeta si no existe
            Path uploadPath = Paths.get("uploads");
            Files.createDirectories(uploadPath);

            // Nombre seguro
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            // Guardar archivo
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Buscar empleado y asociar
            Empleado empleado = empleadoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

            empleado.setHojaDeVida("/uploads/" + fileName);
            empleadoRepository.save(empleado);

            return ResponseEntity.ok("/uploads/" + fileName);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la hoja de vida.");
        }
    }


    @PostMapping("/hoja-vida-temporal")
    public ResponseEntity<String> uploadHojaDeVida(@RequestParam("file") MultipartFile file) {
        try {
            if (!file.getContentType().equals("application/pdf")) {
                return ResponseEntity.badRequest().body("Solo se permiten archivos PDF.");
            }

            // Crear carpeta si no existe
            Path uploadPath = Paths.get("uploads");
            Files.createDirectories(uploadPath);

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok("/uploads/" + fileName);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir el archivo.");
        }
    }


    @PostMapping("/hoja-vida")
    public ResponseEntity<String> subirHojaDeVidaTemporal(@RequestParam("file") MultipartFile file) {
        try {
            if (!file.getContentType().equals("application/pdf")) {
                return ResponseEntity.badRequest().body("Solo se permiten archivos PDF.");
            }

            Path uploadPath = Paths.get("uploads");
            Files.createDirectories(uploadPath);

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok("/uploads/" + fileName);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir el archivo temporal.");
        }
    }*/



}

