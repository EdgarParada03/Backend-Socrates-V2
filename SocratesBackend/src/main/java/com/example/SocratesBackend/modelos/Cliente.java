package com.example.SocratesBackend.modelos;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@DiscriminatorValue("CLIENTE")
public class Cliente extends Persona {

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_registro")
    private LocalDate  fechaRegistro;


    @Column(name = "tipo_cliente")
    private String tipoCliente;

    public Cliente() {}

    public Cliente(Long id, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String tipoIdentificacion, String numeroIdentificacion, String sexo, String correoElectronico, String telefono, Date fechaNacimiento, String lugarResidencia, String direccionCasa, String barrio, boolean estado, LocalDate fechaRegistro, String tipoCliente) {
        super(id, primerNombre, segundoNombre, primerApellido, segundoApellido, tipoIdentificacion, numeroIdentificacion, sexo, correoElectronico, telefono, fechaNacimiento, lugarResidencia, direccionCasa, barrio, estado);
        this.fechaRegistro = fechaRegistro;
        this.tipoCliente = tipoCliente;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
}
