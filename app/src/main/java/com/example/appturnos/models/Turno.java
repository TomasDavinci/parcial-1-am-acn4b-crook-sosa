package com.example.appturnos.models;

import com.google.type.DateTime;

import com.google.firebase.Timestamp;

public class Turno {
    private Long idUsuario;
    private String nombreCliente;
    private String dniCliente;
    private String detalle;
    private String direccion;
    private Timestamp fechaTurno;
    private String horario;

    public Turno(Long idUsuario, String nombreCliente, String dniCliente, String detalle, String direccion, Timestamp fechaTurno, String titulo, String horario) {
        this.idUsuario = idUsuario;
        this.nombreCliente = nombreCliente;
        this.dniCliente = dniCliente;
        this.detalle = detalle;
        this.direccion = direccion;
        this.fechaTurno = fechaTurno;
        this.horario = horario;
    }

    public Long getidUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
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

    public Timestamp getFechaTurno() {
        return fechaTurno;
    }

    public void setFechaTurno(Timestamp fechaTurno) {
        this.fechaTurno = fechaTurno;
    }


    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
