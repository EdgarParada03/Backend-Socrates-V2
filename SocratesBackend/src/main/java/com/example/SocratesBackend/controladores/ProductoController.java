package com.example.SocratesBackend.controladores;

import com.example.SocratesBackend.modelos.Producto;
import com.example.SocratesBackend.repositorios.ProductoRepository;
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
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;


    // Obtener todos los productos
    @GetMapping("/productos")
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    // Crear un nuevo producto
    @PostMapping("/productos")
    public Producto createProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    // Obtener un producto por su ID
    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no existe con el id: " + id));
        return ResponseEntity.ok(producto);
    }

    // Actualizar un producto
    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @RequestBody Producto productoDetails) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no existe con el id: " + id));

        producto.setNombre(productoDetails.getNombre());
        producto.setDescripcion(productoDetails.getDescripcion());
        producto.setCantidad(productoDetails.getCantidad());
        producto.setPrecio(productoDetails.getPrecio());
        producto.setFechaIngreso(productoDetails.getFechaIngreso());

        Producto updatedProducto = productoRepository.save(producto);
        return ResponseEntity.ok(updatedProducto);
    }

    // Eliminar un producto
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProducto(@PathVariable Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no existe con el id: " + id));

        productoRepository.delete(producto);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
