package com.example.SocratesBackend.modelos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "soportes")
public class Soporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "fecha_solicitud", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "UTC")
    private Date fechaSolicitud;


    @Column(name = "fecha_registro", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "UTC")
    private Date fechaRegistro;

    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Empleado tecnico;

    public Soporte() {
    }

    public Soporte(long id, Cliente cliente, String descripcion, Date fechaSolicitud, Date fechaRegistro, String estado, Empleado tecnico) {
        this.id = id;
        this.cliente = cliente;
        this.descripcion = descripcion;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.tecnico = tecnico;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Empleado getTecnico() {
        return tecnico;
    }

    public void setTecnico(Empleado tecnico) {
        this.tecnico = tecnico;
    }
}

