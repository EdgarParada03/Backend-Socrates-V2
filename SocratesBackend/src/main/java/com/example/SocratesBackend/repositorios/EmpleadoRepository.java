package com.example.SocratesBackend.repositorios;


import com.example.SocratesBackend.modelos.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByCodigoEmpleado(String codigoEmpleado);
}

