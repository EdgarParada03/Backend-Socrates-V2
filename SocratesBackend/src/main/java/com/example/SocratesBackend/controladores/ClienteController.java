package com.example.SocratesBackend.controladores;

import com.example.SocratesBackend.modelos.Cliente;
import com.example.SocratesBackend.repositorios.ClienteRepository;
import com.example.SocratesBackend.servicios.ClienteService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Se aplica a TODO el controlador
@RequestMapping("/api/v1")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/clientes/count")
    public long getClienteCount() {
        return clienteRepository.count();
    }

    @Autowired
    private ClienteService clienteService;

    // Obtener todos los clientes
    @GetMapping("/clientes")
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    // Crear un cliente
    @PostMapping("/clientes")
    public Cliente createCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Obtener cliente por ID
    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no existe con el id: " + id));
        return ResponseEntity.ok(cliente);
    }

    // Actualizar cliente
    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        try {
            Cliente cliente = clienteRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente no existe con el id: " + id));

            cliente.setPrimerNombre(clienteDetails.getPrimerNombre());
            cliente.setSegundoNombre(clienteDetails.getSegundoNombre());
            cliente.setPrimerApellido(clienteDetails.getPrimerApellido());
            cliente.setSegundoApellido(clienteDetails.getSegundoApellido());
            cliente.setTipoIdentificacion(clienteDetails.getTipoIdentificacion());
            cliente.setNumeroIdentificacion(clienteDetails.getNumeroIdentificacion());
            cliente.setSexo(clienteDetails.getSexo());
            cliente.setCorreoElectronico(clienteDetails.getCorreoElectronico());
            cliente.setTelefono(clienteDetails.getTelefono());
            cliente.setFechaNacimiento(clienteDetails.getFechaNacimiento());
            cliente.setLugarResidencia(clienteDetails.getLugarResidencia());
            cliente.setDireccionCasa(clienteDetails.getDireccionCasa());
            cliente.setBarrio(clienteDetails.getBarrio());
            cliente.setEstado(clienteDetails.isEstado());
            cliente.setFechaRegistro(clienteDetails.getFechaRegistro());
            cliente.setTipoCliente(clienteDetails.getTipoCliente());

            Cliente updatedCliente = clienteRepository.save(cliente);
            return ResponseEntity.ok(updatedCliente);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al actualizar el cliente: " + e.getMessage());
        }
    }

    // Eliminar cliente
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteCliente(@PathVariable Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no existe con el id: " + id));

        clienteRepository.delete(cliente);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    // Verificar si la cédula ya está registrada
    @GetMapping("/clientes/verificar-cedula/{cedula}")
    public ResponseEntity<Boolean> verificarCedulaExistente(@PathVariable String cedula) {
        boolean existe = clienteRepository.existsByNumeroIdentificacion(cedula);
        return ResponseEntity.ok(existe);
    }

    // Importar clientes desde archivo Excel
    @PostMapping("/clientes/importar-excel")
    public ResponseEntity<String> importarClientesDesdeExcel(@RequestParam("file") MultipartFile file) {
        try {
            clienteService.importarClientes(file);
            return ResponseEntity.ok("Clientes importados correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al importar clientes: " + e.getMessage());
        }
    }
}
