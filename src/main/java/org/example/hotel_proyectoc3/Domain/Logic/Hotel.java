package org.example.hotel_proyectoc3.Domain.Logic;
import org.example.hotel_proyectoc3.Domain.Model.BitacoraLimpieza;
import org.example.hotel_proyectoc3.Domain.Model.BitacoraMantenimiento;
import org.example.hotel_proyectoc3.Domain.Model.Parqueo;


//El hotel es el controlador que se encarga de algunas interacciones del sistema. Sin generar dependencias.
//Importante que el hotel mantenga referencias a todas las grandes colecciones, y que tenga un control generalizado de todo lo que lo compone.
public class Hotel {
    //Listas (Es como si fueran las bases de datos que controla el Hotel).
    private ListaCliente clientes;
    private ListaPersonal trabajadores;
    private ListaReservacion reservaciones;
    private ListaHabitaciones habitaciones;

    //En este caso el hotel solo cuenta con un elemento de los siguientes atributos, por eso mantiene una referencia simple a una instancia.
    private Parqueo parqueo;
    private BitacoraLimpieza bitacoraLimpieza;
    private BitacoraMantenimiento bitacoraMantenimiento;


    public Hotel() {
        this.clientes = new ListaCliente();
        this.trabajadores = new ListaPersonal();
        this.reservaciones = new ListaReservacion();
        this.habitaciones = new ListaHabitaciones();

        this.parqueo = new Parqueo();
        this.bitacoraLimpieza = new BitacoraLimpieza();
        this.bitacoraMantenimiento = new BitacoraMantenimiento();
    }

    public Hotel(ListaCliente clientes, ListaPersonal trabajadores, ListaReservacion reservaciones, ListaHabitaciones habitaciones) {
        this.clientes = clientes;
        this.trabajadores = trabajadores;
        this.reservaciones = reservaciones;
        this.habitaciones = habitaciones;

        this.parqueo = new Parqueo();
        this.bitacoraLimpieza = new BitacoraLimpieza();
        this.bitacoraMantenimiento = new BitacoraMantenimiento();
    }


    public ListaCliente getClientes() {
        return clientes;
    }

    public void setClientes(ListaCliente clientes) {
        this.clientes = clientes;
    }

    public ListaPersonal getTrabajadores() {
        return trabajadores;
    }

    public void setTrabajadores(ListaPersonal trabajadores) {
        this.trabajadores = trabajadores;
    }

    public ListaReservacion getReservaciones() {
        return reservaciones;
    }

    public void setReservaciones(ListaReservacion reservaciones) {
        this.reservaciones = reservaciones;
    }

    public ListaHabitaciones getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(ListaHabitaciones habitaciones) {
        this.habitaciones = habitaciones;
    }

    public Parqueo getParqueo() {
        return parqueo;
    }

    public void setParqueo(Parqueo parqueo) {
        this.parqueo = parqueo;
    }

    public BitacoraLimpieza getBitacoraLimpieza() {
        return bitacoraLimpieza;
    }

    public void setBitacoraLimpieza(BitacoraLimpieza bitacoraLimpieza) {
        this.bitacoraLimpieza = bitacoraLimpieza;
    }

    public BitacoraMantenimiento getBitacoraMantenimiento() {
        return bitacoraMantenimiento;
    }

    public void setBitacoraMantenimiento(BitacoraMantenimiento bitacoraMantenimiento) {
        this.bitacoraMantenimiento = bitacoraMantenimiento;
    }
}
