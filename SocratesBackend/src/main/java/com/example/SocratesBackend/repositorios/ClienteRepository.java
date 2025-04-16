package com.example.SocratesBackend.repositorios;

import com.example.SocratesBackend.modelos.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}

