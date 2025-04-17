package com.example.SocratesBackend.modelos;


import jakarta.persistence.*;

@Entity(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    public Empleado() {
    }

    public Empleado(long id, String codigoEmpleado, String cargo, String tipoContrato, String hojaDeVida,
                    String referenciaLaboral, String contactoEmergenciaNombre,
                    String contactoEmergenciaParentesco, String contactoEmergenciaTelefono) {
        this.id = id;
        this.codigoEmpleado = codigoEmpleado;
        this.cargo = cargo;
        this.tipoContrato = tipoContrato;
        this.hojaDeVida = hojaDeVida;
        this.referenciaLaboral = referenciaLaboral;
        this.contactoEmergenciaNombre = contactoEmergenciaNombre;
        this.contactoEmergenciaParentesco = contactoEmergenciaParentesco;
        this.contactoEmergenciaTelefono = contactoEmergenciaTelefono;
    }

    // Getters y Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}

