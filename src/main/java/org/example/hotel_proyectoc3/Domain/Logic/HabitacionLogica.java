package org.example.hotel_proyectoc3.Domain.Logic;

import org.example.hotel_proyectoc3.Data.HabitacionDatos;
import org.example.hotel_proyectoc3.Domain.Model.Habitacion;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class HabitacionLogica {
    private HabitacionDatos store = new HabitacionDatos();

    // Funciones de lectura
    public List<Habitacion> findAll() throws SQLException {
        return store.findAll();
    }

    public Habitacion findById(int id) throws SQLException {
        return store.findById(id);
    }

    public Habitacion findByNumero(int numero) throws SQLException {
        return store.findByNumero(numero);
    }


    public List<Habitacion> findAvailableRooms() throws SQLException {
        return store.findAvailableRooms();
    }

    public Habitacion create(Habitacion nueva) throws SQLException {
        validarNuevaHabitacion(nueva);

        Habitacion existente = store.findByNumero(nueva.getNumero());
        if (existente != null) {
            throw new IllegalArgumentException("Ya existe una habitación con el número: " + nueva.getNumero());
        }

        return store.insert(nueva);
    }

    public Habitacion update(Habitacion habitacion) throws SQLException {
        if (habitacion == null || habitacion.getId() <= 0) {
            throw new IllegalArgumentException("La habitación no puede ser nula o tener ID inválido");
        }

        validarNuevaHabitacion(habitacion);

        Habitacion existente = store.findByNumero(habitacion.getNumero());
        if (existente != null && existente.getId() != habitacion.getId()) {
            throw new IllegalArgumentException("Ya existe otra habitación con el número: " + habitacion.getNumero());
        }

        return store.update(habitacion);
    }

    public Boolean deleteById(int id) throws SQLException {
        if (id <= 0) return false;
        return store.delete(id) > 0;
    }

    private void validarNuevaHabitacion(Habitacion habitacion) {
        if (habitacion == null) {
            throw new IllegalArgumentException("La habitación no puede ser nula");
        }

        if (habitacion.getNumero() <= 0) {
            throw new IllegalArgumentException("El número de habitación debe ser mayor a 0");
        }

        if (habitacion.getTipo() <= 0 || habitacion.getTipo() > 2) {
            throw new IllegalArgumentException("El tipo de habitación no es válido");
        }

        if (habitacion.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }

        if (habitacion.getCapacidad() <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0");
        }
    }

    public List<Habitacion> findAllByParameters(String textoBusqueda) throws SQLException {
        List<Habitacion> todasLasHabitaciones = store.findAll();

        if (textoBusqueda == null || textoBusqueda.trim().isEmpty()) {
            return todasLasHabitaciones;
        }

        String textoLower = textoBusqueda.toLowerCase().trim();

        return todasLasHabitaciones.stream()
                .filter(habitacion ->
                        String.valueOf(habitacion.getNumero()).toLowerCase().contains(textoLower) ||
                                habitacion.getDescripcionTipo().toLowerCase().contains(textoLower) ||
                                habitacion.getDescripcionEstado().toLowerCase().contains(textoLower) ||
                                String.valueOf(habitacion.getPrecio()).toLowerCase().contains(textoLower) ||
                                String.valueOf(habitacion.getCapacidad()).toLowerCase().contains(textoLower) ||
                                habitacion.getDisponibleTexto().toLowerCase().contains(textoLower)
                )
                .collect(Collectors.toList());
    }

}