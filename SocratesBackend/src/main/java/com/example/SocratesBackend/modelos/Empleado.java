package com.example.SocratesBackend.modelos;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@DiscriminatorValue("EMPLEADO")
public class Empleado extends Persona{



    @Column(name = "codigo_empleado")
    private String codigoEmpleado;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "tipo_contrato")
    private String tipoContrato;

    @Column(name = "hoja_de_vida")
    private String hojaDeVida;

    @Column(name = "referencia_laboral")
    private String referenciaLaboral;

    @Column(name = "contacto_emergencia_nombre")
    private String contactoEmergenciaNombre;

    @Column(name = "contacto_emergencia_parentesco")
    private String contactoEmergenciaParentesco;

    @Column(name = "contacto_emergencia_telefono")
    private String contactoEmergenciaTelefono;

    @Column(name = "fecha_ingreso")
    private String fechaIngreso;

    @Column(name = "fecha_retiro")
    private String fechaRetiro;

    @Column(name = "sueldo")
    private String sueldo;


    public Empleado() {
    }

    public Empleado(Long id, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String tipoIdentificacion, String numeroIdentificacion, String sexo, String correoElectronico, String telefono, Date fechaNacimiento, String lugarResidencia, String direccionCasa, String barrio, boolean estado, String codigoEmpleado, String cargo, String tipoContrato, String hojaDeVida, String referenciaLaboral, String contactoEmergenciaNombre, String contactoEmergenciaParentesco, String contactoEmergenciaTelefono, String fechaIngreso, String fechaRetiro, String sueldo) {
        super(id, primerNombre, segundoNombre, primerApellido, segundoApellido, tipoIdentificacion, numeroIdentificacion, sexo, correoElectronico, telefono, fechaNacimiento, lugarResidencia, direccionCasa, barrio, estado);
        this.codigoEmpleado = codigoEmpleado;
        this.cargo = cargo;
        this.tipoContrato = tipoContrato;
        this.hojaDeVida = hojaDeVida;
        this.referenciaLaboral = referenciaLaboral;
        this.contactoEmergenciaNombre = contactoEmergenciaNombre;
        this.contactoEmergenciaParentesco = contactoEmergenciaParentesco;
        this.contactoEmergenciaTelefono = contactoEmergenciaTelefono;
        this.fechaIngreso = fechaIngreso;
        this.fechaRetiro = fechaRetiro;
        this.sueldo = sueldo;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public String getHojaDeVida() {
        return hojaDeVida;
    }

    public void setHojaDeVida(String hojaDeVida) {
        this.hojaDeVida = hojaDeVida;
    }

    public String getReferenciaLaboral() {
        return referenciaLaboral;
    }

    public void setReferenciaLaboral(String referenciaLaboral) {
        this.referenciaLaboral = referenciaLaboral;
    }

    public String getContactoEmergenciaNombre() {
        return contactoEmergenciaNombre;
    }

    public void setContactoEmergenciaNombre(String contactoEmergenciaNombre) {
        this.contactoEmergenciaNombre = contactoEmergenciaNombre;
    }

    public String getContactoEmergenciaParentesco() {
        return contactoEmergenciaParentesco;
    }

    public void setContactoEmergenciaParentesco(String contactoEmergenciaParentesco) {
        this.contactoEmergenciaParentesco = contactoEmergenciaParentesco;
    }

    public String getContactoEmergenciaTelefono() {
        return contactoEmergenciaTelefono;
    }

    public void setContactoEmergenciaTelefono(String contactoEmergenciaTelefono) {
        this.contactoEmergenciaTelefono = contactoEmergenciaTelefono;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(String fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public String getSueldo() {
        return sueldo;
    }

    public void setSueldo(String sueldo) {
        this.sueldo = sueldo;
    }
}

