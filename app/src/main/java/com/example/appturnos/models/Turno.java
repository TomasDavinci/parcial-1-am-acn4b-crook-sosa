package com.example.appturnos.models;

public class Turno {
    private String titulo;
    private String horario;
    private String direccion;

    public Turno(String titulo, String horario, String direccion) {
        this.titulo = titulo;
        this.horario = horario;
        this.direccion = direccion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getHorario() {
        return horario;
    }

    public String getDireccion() {
        return direccion;
    }
}
