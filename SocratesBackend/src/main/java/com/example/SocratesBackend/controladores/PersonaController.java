package com.example.SocratesBackend.controladores;

import com.example.SocratesBackend.modelos.Persona;
import com.example.SocratesBackend.repositorios.PersonaRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1")
public class PersonaController {

    @Autowired
    private PersonaRepository personaRepository;

    // Obtener todas las personas
    @GetMapping("/personas")
    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }

    // Crear una persona
    @PostMapping("/personas")
    public Persona createPersona(@RequestBody Persona persona) {
        return personaRepository.save(persona);
    }

    // Obtener persona por ID
    @GetMapping("/personas/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable Long id) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no existe con el id: " + id));
        return ResponseEntity.ok(persona);
    }

    // Actualizar persona
    @PutMapping("/personas/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable Long id, @RequestBody Persona personaDetails) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no existe con el id: " + id));

        persona.setPrimerNombre(personaDetails.getPrimerNombre());
        persona.setSegundoNombre(personaDetails.getSegundoNombre());
        persona.setPrimerApellido(personaDetails.getPrimerApellido());
        persona.setSegundoApellido(personaDetails.getSegundoApellido());
        persona.setTipoIdentificacion(personaDetails.getTipoIdentificacion());
        persona.setNumeroIdentificacion(personaDetails.getNumeroIdentificacion());
        persona.setSexo(personaDetails.getSexo());
        persona.setCorreoElectronico(personaDetails.getCorreoElectronico());
        persona.setTelefono(personaDetails.getTelefono());
        persona.setFechaNacimiento(personaDetails.getFechaNacimiento());
        persona.setLugarResidencia(personaDetails.getLugarResidencia());
        persona.setDireccionCasa(personaDetails.getDireccionCasa());
        persona.setBarrio(personaDetails.getBarrio());
        persona.setEstado(personaDetails.isEstado());

        Persona updatedPersona = personaRepository.save(persona);
        return ResponseEntity.ok(updatedPersona);
    }

    // Eliminar persona
    @DeleteMapping("/personas/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePersona(@PathVariable Long id) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no existe con el id: " + id));

        personaRepository.delete(persona);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
