package com.example.SocratesBackend.modelos;

import jakarta.persistence.*;
import java.util.Date;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "mantenimientos")
public class Mantenimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "soporte_id")
    private Soporte soporte;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_programada")
    private Date fechaProgramada;

    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Empleado tecnico;

    @OneToMany
    @JoinColumn(name = "producto_id")
    private List<Producto> productos;

    public Mantenimiento() {
    }

    public Mantenimiento(long id, Soporte soporte, String descripcion, Date fechaProgramada, String estado, Empleado tecnico, List<Producto> productos) {
        this.id = id;
        this.soporte = soporte;
        this.descripcion = descripcion;
        this.fechaProgramada = fechaProgramada;
        this.estado = estado;
        this.tecnico = tecnico;
        this.productos = productos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

     public Soporte getSoporte() {
         return soporte;
    }

    public void setSoporte(Soporte soporte) {
        this.soporte = soporte;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
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

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}