package com.example.appturnos.models;

import com.google.type.DateTime;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.Date;

public class Turno implements Serializable {
    private String id;
    private String nombreCliente;
    private String dniCliente;
    private String detalle;
    private String direccion;
    private Date fechaTurno;
    private String emailUsuario;
    private String emailCliente;
    private String telefono;

    public Turno(String id, String nombreCliente, String dniCliente, String detalle, String direccion, Date fechaTurno, String titulo, String horario, String emailUsuario, String emailCliente, String telefono) {
        this.id = id;
        this.nombreCliente = nombreCliente;
        this.dniCliente = dniCliente;
        this.detalle = detalle;
        this.direccion = direccion;
        this.fechaTurno = fechaTurno;
        this.emailUsuario = emailUsuario;
        this.emailCliente = emailCliente;
        this.telefono = telefono;
    }



    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaTurno() {
        return fechaTurno;
    }

    public void setFechaTurno(Date fechaTurno) {
        this.fechaTurno = fechaTurno;
    }



    @Override
    public String toString() {
        return "Turno{" +
                ", nombreCliente='" + nombreCliente + '\'' +
                ", dniCliente='" + dniCliente + '\'' +
                ", detalle='" + detalle + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fechaTurno=" + fechaTurno +
                '}';
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
