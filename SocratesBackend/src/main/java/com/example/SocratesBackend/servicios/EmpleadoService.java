package com.example.SocratesBackend.servicios;

import com.example.SocratesBackend.repositorios.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public String generarCodigoEmpleado() {
        Long ultimoId = empleadoRepository.findMaxId().orElse(0L); // Suponiendo que usas ID numérico
        Long siguienteId = ultimoId + 1;

        // Formatea el número a 4 dígitos, ej. 0001
        String numeroFormateado = String.format("%04d", siguienteId);

        return "CUC-" + numeroFormateado;
    }

}
