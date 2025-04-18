package com.example.SocratesBackend.controladores;

import com.example.SocratesBackend.servicios.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String codigoEmpleado, @RequestParam String password) {
        return authService.authenticate(codigoEmpleado, password);
    }

}
