package com.example.SocratesBackend.controladores;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.MalformedURLException;


@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1")
public class ArchivoController {

    @PostMapping("/subir-pdf")
    public ResponseEntity<Map<String, Object>> subirArchivo(@RequestParam("archivo") MultipartFile archivo) {
        Map<String, Object> respuesta = new HashMap<>();

        if (archivo.isEmpty()) {
            respuesta.put("mensaje", "El archivo está vacío.");
            return ResponseEntity.badRequest().body(respuesta);
        }

        try {
            String carpetaDestino = System.getProperty("user.dir") + File.separator + "uploads";
            Path pathCarpeta = Paths.get(carpetaDestino);

            if (!Files.exists(pathCarpeta)) {
                Files.createDirectories(pathCarpeta);
            }

            Path rutaArchivo = pathCarpeta.resolve(archivo.getOriginalFilename());
            archivo.transferTo(rutaArchivo.toFile());

            respuesta.put("mensaje", "Archivo subido exitosamente.");
            respuesta.put("ruta", rutaArchivo.toString());
            respuesta.put("nombreArchivo", archivo.getOriginalFilename());
            System.out.println(archivo.getOriginalFilename());

            return ResponseEntity.ok(respuesta);

        } catch (IOException e) {
            e.printStackTrace();
            respuesta.put("mensaje", "Error al guardar el archivo.");
            return ResponseEntity.status(500).body(respuesta);
        }
    }


    @GetMapping("/ver-pdf/{nombreArchivo}")
    public ResponseEntity<Resource> verArchivo(@PathVariable String nombreArchivo) {
        try {
            String carpetaDestino = System.getProperty("user.dir") + File.separator + "uploads";
            Path rutaArchivo = Paths.get(carpetaDestino).resolve(nombreArchivo).normalize();

            Resource recurso = new UrlResource(rutaArchivo.toUri());

            if (!recurso.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + recurso.getFilename() + "\"")
                    .body(recurso);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}