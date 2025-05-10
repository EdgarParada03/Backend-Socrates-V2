package com.example.SocratesBackend.repositorios;


import com.example.SocratesBackend.modelos.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByCodigoEmpleado(String codigoEmpleado);
    Optional<Empleado> findByNumeroIdentificacion(String cedula);

    @Query("SELECT MAX(e.id) FROM Empleado e")
    Optional<Long> findMaxId();

}

