package org.example.hotel_proyectoc3.Domain.Model;

import java.time.LocalDate;

public class Tarea {
    private Personal trabajadorACargo;
    private Habitacion habitacionDeTarea;
    private String idTarea;
    private String tipo;
    private String descripcion;
    private LocalDate fecha;
    private Boolean estado;

    public Tarea() {

    }

    public Tarea(Habitacion habitacionDeTarea, Personal trabajadorACargo, String idTarea, String tipo, String descripcion, Boolean estado, LocalDate fecha) {
        this.habitacionDeTarea = habitacionDeTarea;
        this.trabajadorACargo = trabajadorACargo;
        this.idTarea = idTarea;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fecha = fecha;
    }

    public Habitacion getHabitacionDeTarea() {
        return habitacionDeTarea;
    }

    public void setHabitacionDeTarea(Habitacion habitacionDeTarea) {
        this.habitacionDeTarea = habitacionDeTarea;
    }

    public Personal getTrabajadorACargo() {
        return trabajadorACargo;
    }

    public void setTrabajadorACargo(Personal trabajadorACargo) {
        this.trabajadorACargo = trabajadorACargo;
    }

    public String getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(String idTarea) {
        this.idTarea = idTarea;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}

