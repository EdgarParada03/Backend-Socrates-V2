package com.example.SocratesBackend.repositorios;


import com.example.SocratesBackend.modelos.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
}

