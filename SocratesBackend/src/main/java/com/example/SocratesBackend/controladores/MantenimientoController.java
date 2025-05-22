package com.example.SocratesBackend.controladores;

import com.example.SocratesBackend.modelos.Mantenimiento;
import com.example.SocratesBackend.modelos.Producto;
import com.example.SocratesBackend.repositorios.MantenimientoRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.SocratesBackend.repositorios.ProductoRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api/v1")
public class MantenimientoController {

    @Autowired
    private MantenimientoRepository mantenimientoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // 🔹 Obtener todos los mantenimientos
    @GetMapping("/mantenimientos")
    public List<Mantenimiento> getAllMantenimientos() {
        return mantenimientoRepository.findAll();
    }

    // 🔹 Crear un nuevo mantenimiento
    @PostMapping("/mantenimientos")
    public Mantenimiento createMantenimiento(@RequestBody Mantenimiento mantenimiento) {
        return crearMantenimiento(mantenimiento);
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
        Mantenimiento mantenimientoExistente = mantenimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mantenimiento no existe con el ID: " + id));

        // 🟠 1. Devolver productos antiguos al inventario
        for (Producto productoAntiguo : mantenimientoExistente.getProductos()) {
            Producto productoDB = productoRepository.findById(productoAntiguo.getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoAntiguo.getId()));
            productoDB.setCantidad(productoDB.getCantidad() + 1); // devolver 1 unidad
            productoRepository.save(productoDB);
        }

        // 🟢 2. Descontar productos nuevos del inventario
        for (Producto productoNuevo : mantenimientoDetails.getProductos()) {
            Producto productoDB = productoRepository.findById(productoNuevo.getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoNuevo.getId()));
            if (productoDB.getCantidad() < 1) {
                throw new RuntimeException("Producto sin stock: " + productoDB.getNombre());
            }
            productoDB.setCantidad(productoDB.getCantidad() - 1); // descontar 1 unidad
            productoRepository.save(productoDB);
        }

        // 🧩 3. Actualizar datos
        mantenimientoExistente.setDescripcion(mantenimientoDetails.getDescripcion());
        mantenimientoExistente.setFechaProgramada(mantenimientoDetails.getFechaProgramada());
        mantenimientoExistente.setEstado(mantenimientoDetails.getEstado());
        mantenimientoExistente.setTecnico(mantenimientoDetails.getTecnico());
        mantenimientoExistente.setProductos(mantenimientoDetails.getProductos()); // actualizar productos

        // 💾 4. Guardar
        Mantenimiento mantenimientoActualizado = mantenimientoRepository.save(mantenimientoExistente);
        return ResponseEntity.ok(mantenimientoActualizado);
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

    public Mantenimiento crearMantenimiento(Mantenimiento mantenimiento) {
        // Por cada producto en el mantenimiento
        for (Producto producto : mantenimiento.getProductos()) {
            Producto productoExistente = productoRepository.findById(producto.getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + producto.getId()));

            // Verificar si hay al menos 1 en inventario
            if (productoExistente.getCantidad() < 1) {
                throw new RuntimeException("Producto sin stock: " + productoExistente.getNombre());
            }

            // Restar 1 unidad
            productoExistente.setCantidad(productoExistente.getCantidad() - 1);
            productoRepository.save(productoExistente);
        }

        // Guardar el mantenimiento
        return mantenimientoRepository.save(mantenimiento);
    }



}
