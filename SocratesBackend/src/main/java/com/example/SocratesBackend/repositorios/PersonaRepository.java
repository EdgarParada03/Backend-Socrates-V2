package com.example.SocratesBackend.repositorios;

import com.example.SocratesBackend.modelos.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
}

