package com.example.SocratesBackend.repositorios;

import com.example.SocratesBackend.modelos.Mantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Long> {
}
