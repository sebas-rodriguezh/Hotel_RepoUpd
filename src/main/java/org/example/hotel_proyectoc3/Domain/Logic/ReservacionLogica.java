package org.example.hotel_proyectoc3.Domain.Logic;

import org.example.hotel_proyectoc3.Data.ReservacionDatos;
import org.example.hotel_proyectoc3.Domain.Model.Reservacion;

import java.sql.SQLException;
import java.util.List;

public class ReservacionLogica {
    private ReservacionDatos store = new ReservacionDatos();

    public List<Reservacion> findAll() throws SQLException {
        return store.findAll();
    }

    public Reservacion create(Reservacion reservacion) throws SQLException {
        validarReservacion(reservacion);
        return store.insert(reservacion);
    }

    public Boolean deleteById(int id) throws SQLException {
        if (id <= 0) return false;
        return store.delete(id) > 0;
    }

    public List<Reservacion> findAllByParameters(String texto) throws SQLException {
        return store.findAllByParameters(texto);
    }

    private void validarReservacion(Reservacion reservacion) {
        if (reservacion == null) {
            throw new IllegalArgumentException("La reservación no puede ser nula");
        }

        if (reservacion.getCliente() == null || reservacion.getCliente().getId() <= 0) {
            throw new IllegalArgumentException("Cliente no válido");
        }

        if (reservacion.getHabitacion() == null || reservacion.getHabitacion().getId() <= 0) {
            throw new IllegalArgumentException("Habitación no válida");
        }

        if (reservacion.getFechaLlegada() == null) {
            throw new IllegalArgumentException("Fecha de llegada es requerida");
        }

        if (reservacion.getCantidadDeNoches() <= 0) {
            throw new IllegalArgumentException("La cantidad de noches debe ser mayor a 0");
        }

        if (reservacion.getDescuento() < 0) {
            throw new IllegalArgumentException("El descuento no puede ser negativo");
        }
    }
}