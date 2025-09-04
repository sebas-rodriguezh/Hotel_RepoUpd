package org.example.hotel_proyectoc3.Domain.Model;

public class Habitacion {
    private int id; //Identificador para la habitación. En este caso es a nivel de sistema. Control interno.
    private int numero; //Control de forma visual.
    private int tipo; //Suite - Standard. (base de datos)
    private String descripcionTipo; //Suite - Standard. ( Aplicación.)
    private int estado; //Clausurado - Reservado - En Mantenimiento. (base de datos)
    private String descripcionEstado; //Aplicación.
    private double precio;
    private int capacidad;
    private Boolean disponible;

    public Habitacion() {

    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Habitacion(int id, int numero, int tipo, int estado, double precio, int capacidad) {
        this.id = id;
        this.numero = numero;
        this.tipo = tipo;
        this.estado = estado;
        this.precio = precio;
        this.capacidad = capacidad;
        this.disponible = true;
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
