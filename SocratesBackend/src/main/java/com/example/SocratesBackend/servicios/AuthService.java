package com.example.SocratesBackend.servicios;


import com.example.SocratesBackend.modelos.Empleado;
import com.example.SocratesBackend.modelos.Persona;
import com.example.SocratesBackend.repositorios.EmpleadoRepository;
import com.example.SocratesBackend.repositorios.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public Map<String, String> authenticate(String codigoEmpleado, String password) {
        Map<String, String> response = new HashMap<>();
        Optional<Empleado> empleado = empleadoRepository.findByCodigoEmpleado(codigoEmpleado);

        if (empleado.isPresent() && empleado.get().getCodigoEmpleado().equals(password)) {
            Empleado usuario = empleado.get();
            response.put("message", "Login successful");
            response.put("userType", usuario.getCargo().toLowerCase()); // Por ejemplo, "secretaria" o "admin"
            response.put("primerNombre", usuario.getPrimerNombre());    // Se envía el primer nombre
            response.put("primerApellido", usuario.getPrimerApellido());// Se envía el primer apellido
        } else {
            response.put("message", "Invalid credentials");
        }

        return response;
    }


}
