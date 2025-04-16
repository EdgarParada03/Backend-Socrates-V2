package com.example.SocratesBackend.modelos;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue("CLIENTE")
public class Cliente extends Persona {

    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;

    private String tipoCliente;

    public Cliente() {}

    public Cliente(Long id, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String tipoIdentificacion, String numeroIdentificacion, String sexo, String correoElectronico, String telefono, Date fechaNacimiento, String lugarResidencia, String direccionCasa, String barrio, boolean estado, Date fechaRegistro, String tipoCliente) {
        super(id, primerNombre, segundoNombre, primerApellido, segundoApellido, tipoIdentificacion, numeroIdentificacion, sexo, correoElectronico, telefono, fechaNacimiento, lugarResidencia, direccionCasa, barrio, estado);
        this.fechaRegistro = fechaRegistro;
        this.tipoCliente = tipoCliente;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
}