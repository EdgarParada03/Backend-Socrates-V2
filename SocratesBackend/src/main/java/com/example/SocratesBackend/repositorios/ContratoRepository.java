package com.example.SocratesBackend.repositorios;

import com.example.SocratesBackend.modelos.Cliente;
import com.example.SocratesBackend.modelos.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    // Buscar contrato por cliente y estado (activo)
    Optional<Contrato> findByClienteAndEstado(Cliente cliente, boolean estado);
}
