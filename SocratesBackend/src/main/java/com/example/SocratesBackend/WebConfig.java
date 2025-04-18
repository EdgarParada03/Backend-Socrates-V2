package com.example.SocratesBackend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Permitir CORS para todas las rutas
                .allowedOrigins("http://localhost:4200")  // Permitir solicitudes solo desde este origen
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Permitir estos métodos HTTP
                .allowedHeaders("*")  // Permitir cualquier tipo de cabeceras
                .allowCredentials(true);  // Permitir el uso de credenciales (si es necesario)
    }
}
