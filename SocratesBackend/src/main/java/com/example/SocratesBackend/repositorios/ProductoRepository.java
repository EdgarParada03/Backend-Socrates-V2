package com.example.SocratesBackend.repositorios;

import com.example.SocratesBackend.modelos.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Puedes agregar consultas personalizadas aquí si es necesario
}
