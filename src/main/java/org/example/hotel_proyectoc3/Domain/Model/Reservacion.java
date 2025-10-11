package org.example.hotel_proyectoc3.Domain.Model;

import java.time.LocalDate;

public class Reservacion {
    private Habitacion habitacion;
    private Cliente cliente;
    private int idReservacion;
    private LocalDate fechaReservacion;
    private LocalDate fechaLlegada;
    private int cantidadDeNoches;
    private LocalDate fechaSalida;
    private double precioTotal;
    private double descuento;


    public Reservacion () {
    }

    public Reservacion(int idReservacion, Cliente cliente, Habitacion habitacion, LocalDate fechaLlegada, int cantidadDeNoches) {
        this.idReservacion = idReservacion;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaLlegada = fechaLlegada;
        this.cantidadDeNoches = cantidadDeNoches;

        this.setPrecioTotal();

    }

    public Reservacion(Cliente cliente, Habitacion habitacion, LocalDate fechaLlegada, int cantidadDeNoches, double descuento) {
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaLlegada = fechaLlegada;
        this.cantidadDeNoches = cantidadDeNoches;
        this.descuento = descuento;
        this.setPrecioTotal();
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFechaReservacion() {
        return fechaReservacion;
    }

    public void setFechaReservacion(LocalDate fechaReservacion) {
        this.fechaReservacion = fechaReservacion;
    }

    public int getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(int idReservacion) {
        this.idReservacion = idReservacion;
    }

    public LocalDate getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(LocalDate fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public int getCantidadDeNoches() {
        return cantidadDeNoches;
    }

    public void setCantidadDeNoches(int cantidadDeNoches) {
        this.cantidadDeNoches = cantidadDeNoches;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }


    public void setPrecioTotal() {
        if ((habitacion != null) && (cantidadDeNoches > 0)) {
            double precioBase = habitacion.getPrecio() * cantidadDeNoches;
            if (descuento > precioBase) {
                throw new IllegalArgumentException("Error: El descuento no puede ser mayor al precio total de la reservación.");
            }
            this.precioTotal = precioBase - descuento;
        } else {
            throw new IllegalArgumentException("Error: Cantidad de noches menor que 0 o no existe habitacion asignada. ");
        }
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
        this.setPrecioTotal(); //Lo llamo para setear el descuento apenas fijemos un descuento. Asi lo tenemos siempre calculado.
    }
}
