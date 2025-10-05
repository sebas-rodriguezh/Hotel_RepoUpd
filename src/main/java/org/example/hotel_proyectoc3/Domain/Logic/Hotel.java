package org.example.hotel_proyectoc3.Domain.Logic;

import org.example.hotel_proyectoc3.Domain.Model.BitacoraLimpieza;
import org.example.hotel_proyectoc3.Domain.Model.BitacoraMantenimiento;
import org.example.hotel_proyectoc3.Domain.Model.Parqueo;

//El hotel es el controlador que se encarga de algunas interacciones del sistema. Sin generar dependencias.
//Importante que el hotel mantenga referencias a todas las grandes colecciones, y que tenga un control generalizado de todo lo que lo compone.
public class Hotel {
    // Instancia única del Singleton
    private static Hotel instance;

    //Listas (Es como si fueran las bases de datos que controla el Hotel).
    private GestorClientes clientes;
    private GestorPersonal trabajadores;
    private GestorReservaciones reservaciones;
    private GestorHabitaciones habitaciones;

    //En este caso el hotel solo cuenta con un elemento de los siguientes atributos, por eso mantiene una referencia simple a una instancia.
    private Parqueo parqueo;
    private BitacoraLimpieza bitacoraLimpieza;
    private BitacoraMantenimiento bitacoraMantenimiento;

    // Constructor privado para prevenir instanciación externa
    private Hotel() {
        this.clientes = new GestorClientes();
        this.trabajadores = new GestorPersonal();
        this.reservaciones = new GestorReservaciones();
        this.habitaciones = new GestorHabitaciones();

        this.parqueo = new Parqueo();
        this.bitacoraLimpieza = new BitacoraLimpieza();
        this.bitacoraMantenimiento = new BitacoraMantenimiento();
    }

    private Hotel(GestorClientes clientes, GestorPersonal trabajadores, GestorReservaciones reservaciones, GestorHabitaciones habitaciones) {
        this.clientes = clientes;
        this.trabajadores = trabajadores;
        this.reservaciones = reservaciones;
        this.habitaciones = habitaciones;

        this.parqueo = new Parqueo();
        this.bitacoraLimpieza = new BitacoraLimpieza();
        this.bitacoraMantenimiento = new BitacoraMantenimiento();
    }

    // Método estático para obtener la instancia única (versión básica)
    public static Hotel getInstance() {
        if (instance == null) {
            instance = new Hotel();
        }
        return instance;
    }

    // Método estático para obtener la instancia única con parámetros (opcional)
    public static Hotel getInstance(GestorClientes clientes, GestorPersonal trabajadores,
                                    GestorReservaciones reservaciones, GestorHabitaciones habitaciones) {
        if (instance == null) {
            instance = new Hotel(clientes, trabajadores, reservaciones, habitaciones);
        }
        return instance;
    }

    // Método para reiniciar la instancia (útil para testing)
    public static void resetInstance() {
        instance = null;
    }

    // Getters y Setters (se mantienen igual)
    public GestorClientes getClientes() {
        return clientes;
    }

    public void setClientes(GestorClientes clientes) {
        this.clientes = clientes;
    }

    public GestorPersonal getTrabajadores() {
        return trabajadores;
    }

    public void setTrabajadores(GestorPersonal trabajadores) {
        this.trabajadores = trabajadores;
    }

    public GestorReservaciones getReservaciones() {
        return reservaciones;
    }

    public void setReservaciones(GestorReservaciones reservaciones) {
        this.reservaciones = reservaciones;
    }

    public GestorHabitaciones getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(GestorHabitaciones habitaciones) {
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