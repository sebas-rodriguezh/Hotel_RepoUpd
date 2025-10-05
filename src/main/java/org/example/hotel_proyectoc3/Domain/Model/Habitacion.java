package org.example.hotel_proyectoc3.Domain.Model;

public class Habitacion {
    private int id; //Identificador para la habitación. En este caso es a nivel de sistema. Control interno.
    private int numero; //Control de forma visual.
    private int tipo; //Suite - Standard. (base de datos)
    private String descripcionTipo; //Suite - Standard. ( Aplicación.)
    private int estado; //Clausurado - Reservado - En Mantenimiento - Perfectas condiciones. (base de datos)
    private String descripcionEstado; //Aplicación.
    private double precio;
    private int capacidad;
    private Boolean disponible;

    public Habitacion() {

    }

    public Habitacion(int id, int numero, int tipo, int estado, double precio, int capacidad) {
        this.id = id;
        this.numero = numero;
        this.tipo = tipo;
        this.estado = estado;
        this.precio = precio;
        this.capacidad = capacidad;
        actualizarDescripciones();
        this.disponible = (estado == 1);
    }

    private void actualizarDescripciones() {
        switch (this.tipo) {
            case 1: this.descripcionTipo = "Standard"; break;
            case 2: this.descripcionTipo = "Suite"; break;
            default: this.descripcionTipo = "Desconocido";
        }

        switch (this.estado) {
            case 1: this.descripcionEstado = "Perfecto"; break;
            case 2: this.descripcionEstado = "Mantenimiento"; break;
            case 3: this.descripcionEstado = "Reservada"; break;
            case 4: this.descripcionEstado = "Clausurada"; break;
            case 5: this.descripcionEstado = "Ocupada"; break;
            default: this.descripcionEstado = "Desconocido";
        }
    }

    public String getDisponibleTexto() {
        return disponible ? "Sí" : "No";
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
        if (disponible && this.estado != 1) {
            this.estado = 1;
            actualizarDescripciones();
        } else if (!disponible && this.estado == 1) {
            this.estado = 2;
            actualizarDescripciones();
        }
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getDescripcionTipo() {
        return descripcionTipo;
    }

    public void setDescripcionTipo(String descripcionTipo) {
        this.descripcionTipo = descripcionTipo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
        actualizarDescripciones();
        this.disponible = (estado == 1);
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
        actualizarDescripciones();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}