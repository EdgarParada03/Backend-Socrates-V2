package com.example.SocratesBackend.repositorios;

import com.example.SocratesBackend.modelos.Servicio;
import com.example.SocratesBackend.modelos.TipoPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
}
