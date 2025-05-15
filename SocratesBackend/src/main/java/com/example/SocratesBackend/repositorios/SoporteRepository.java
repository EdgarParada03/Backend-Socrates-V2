package com.example.SocratesBackend.repositorios;

import com.example.SocratesBackend.modelos.Soporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoporteRepository extends JpaRepository<Soporte, Long> {
}

