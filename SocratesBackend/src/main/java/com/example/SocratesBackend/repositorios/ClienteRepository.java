package com.example.SocratesBackend.repositorios;

import com.example.SocratesBackend.modelos.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Método para verificar si existe un cliente con un número de identificación específico
    boolean existsByNumeroIdentificacion(String numeroIdentificacion);
}
