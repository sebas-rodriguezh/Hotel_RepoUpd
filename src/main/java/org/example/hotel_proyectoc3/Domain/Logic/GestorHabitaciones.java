package org.example.hotel_proyectoc3.Domain.Logic;
import org.example.hotel_proyectoc3.Domain.Model.Habitacion;

import java.util.ArrayList;
import java.util.List;

public class GestorHabitaciones {
    private List<Habitacion> listaHabitaciones;

    public GestorHabitaciones() {
        listaHabitaciones = new ArrayList<Habitacion>();
    }

    public GestorHabitaciones(List<Habitacion> listaHabitaciones) {
        this.listaHabitaciones = new ArrayList<Habitacion>(listaHabitaciones);
    }

    public List<Habitacion> getListaHabitaciones() {
        return listaHabitaciones;
    }

    public void setListaHabitaciones(List<Habitacion> listaHabitaciones) {
        this.listaHabitaciones = new ArrayList<Habitacion>(listaHabitaciones);
    }

    public Boolean insertarHabitacion(Habitacion habitacion) {
        if (habitacion == null) {
            return false;
        }

        if (existeHabitacionConNumero(habitacion.getNumero())) {
            return false;
        }

        if (habitacion.getNumero() <= 0) {
            return false;
        }

        if (habitacion.getCapacidad() <= 0) {
            return false;
        }

        if (habitacion.getPrecio() <= 0) {
            return false;
        }

        listaHabitaciones.add(habitacion);
        return true;
    }

    public String mostrarHabitaciones() {
        StringBuilder sb = new StringBuilder();
        if (listaHabitaciones.isEmpty()) {
            return "No hay habitaciones registradas.";
        }

        for (Habitacion habitacion : listaHabitaciones) {
            if (habitacion != null) {
                sb.append(habitacion.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    public String mostrarHabitacionesDisponibles() {
        StringBuilder sb = new StringBuilder();
        boolean encontradas = false;

        for (Habitacion habitacion : listaHabitaciones) {
            if (habitacion != null && habitacion.getDisponible()) {
                sb.append(habitacion.toString()).append("\n");
                encontradas = true;
            }
        }

        if (!encontradas) {
            return "No hay habitaciones disponibles.";
        }
        return sb.toString();
    }

    public Boolean eliminarHabitacion(int numeroHabitacion) {
        if (numeroHabitacion <= 0) {
            return false;
        }

        for (int i = 0; i < listaHabitaciones.size(); i++) {
            Habitacion habitacion = listaHabitaciones.get(i);
            if (habitacion != null && habitacion.getNumero() == numeroHabitacion) {
                // Validar que la habitación no tenga reservas activas
                if (tieneReservasActivas(habitacion)) {
                    return false;
                }
                listaHabitaciones.remove(i);
                return true;
            }
        }
        return false;
    }

    public Boolean existeHabitacionConNumero(int numero) {
        for (Habitacion habitacion : listaHabitaciones) {
            if (habitacion != null && habitacion.getNumero() == numero) {
                return true;
            }
        }
        return false;
    }

    private Boolean tieneReservasActivas(Habitacion habitacion) {
        // Aquí deberías implementar la lógica para verificar reservas activas
        // Esto depende de cómo tengas implementado el sistema de reservas
        // Por ahora retornamos false como placeholder
        return false;
    }

    public Habitacion buscarHabitacionPorNumero(int numero) {
        for (Habitacion habitacion : listaHabitaciones) {
            if (habitacion != null && habitacion.getNumero() == numero) {
                return habitacion;
            }
        }
        return null;
    }

    public List<Habitacion> obtenerHabitacionesPorCapacidad(int capacidadMinima) {
        List<Habitacion> resultado = new ArrayList<>();
        for (Habitacion habitacion : listaHabitaciones) {
            if (habitacion != null && habitacion.getCapacidad() >= capacidadMinima) {
                resultado.add(habitacion);
            }
        }
        return resultado;
    }

    public boolean estaVacia() {
        return listaHabitaciones.isEmpty();
    }

    public int tamaño() {
        return listaHabitaciones.size();
    }

    public Boolean actualizarDisponibilidad(int numeroHabitacion, boolean disponible) {
        Habitacion habitacion = buscarHabitacionPorNumero(numeroHabitacion);
        if (habitacion != null) {
            habitacion.setDisponible(disponible);
            return true;
        }
        return false;
    }
}