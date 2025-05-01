package com.example.SocratesBackend.repositorios;

import com.example.SocratesBackend.modelos.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByNumeroIdentificacion(String numeroIdentificacion);

    Cliente findByNumeroIdentificacion(String numeroIdentificacion);
}
