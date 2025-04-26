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

    // @ManyToOne
    // @JoinColumn(name = "soporte_id")
    // private Sopor soporte; // 🔧 Comentado porque aún no existe

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_programada")
    private Date fechaProgramada;

    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Empleado tecnico;

    // @OneToMany
    // @JoinColumn(name = "mantenimiento_id")
    // private List<Producto> productos; // 🔧 Comentado porque aún no existe

    public Mantenimiento() {
    }

    // 🔧 Comentado el constructor con `soporte` y `productos`, para evitar errores por clases inexistentes
    // public Mantenimiento(long id, Sopor soporte, String descripcion, Date fechaProgramada, String estado, Empleado tecnico, List<Producto> productos) {
    public Mantenimiento(long id, String descripcion, Date fechaProgramada, String estado, Empleado tecnico) {
        this.id = id;
        // this.soporte = soporte;
        this.descripcion = descripcion;
        this.fechaProgramada = fechaProgramada;
        this.estado = estado;
        this.tecnico = tecnico;
        // this.productos = productos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // public Sopor getSoporte() {
    //     return soporte;
    // }

    // public void setSoporte(Sopor soporte) {
    //     this.soporte = soporte;
    // }

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

    // public List<Producto> getProductos() {
    //     return productos;
    // }

    // public void setProductos(List<Producto> productos) {
    //     this.productos = productos;
    // }
}