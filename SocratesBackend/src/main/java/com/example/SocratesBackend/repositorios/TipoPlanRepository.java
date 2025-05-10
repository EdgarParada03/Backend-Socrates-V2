package com.example.SocratesBackend.repositorios;

import com.example.SocratesBackend.modelos.TipoPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoPlanRepository extends JpaRepository<TipoPlan, Long> {
    Optional<TipoPlan> findByNombre(String nombre);
}
