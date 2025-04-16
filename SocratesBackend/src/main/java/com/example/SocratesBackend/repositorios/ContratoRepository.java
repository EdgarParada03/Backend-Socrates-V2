package com.example.SocratesBackend.repositorios;

import com.example.SocratesBackend.modelos.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
}

