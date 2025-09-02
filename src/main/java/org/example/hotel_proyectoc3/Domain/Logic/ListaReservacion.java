package org.example.hotel_proyectoc3.Domain.Logic;

import org.example.hotel_proyectoc3.Domain.Model.Reservacion;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class ListaReservacion {
    private List<Reservacion> reservaciones;

    public ListaReservacion() {
        this.reservaciones = new ArrayList<Reservacion>();
    }

    public ListaReservacion(List<Reservacion> reservaciones) {
        this.reservaciones = new ArrayList<Reservacion>(reservaciones);
    }

    public List<Reservacion> getReservaciones() {
        return reservaciones;
    }

    public void setReservaciones(List<Reservacion> reservaciones) {
        this.reservaciones = new ArrayList<Reservacion>(reservaciones);
    }

    public Boolean insertarReservacion(Reservacion reservacion) {
        if (reservacion == null) {
            return false;
        }

        if (reservacion.getIdReservacion() <= 0 ||
                reservacion.getCantidadDeNoches() <= 0) {
            return false;
        }

        if (existeReservacionConId(reservacion.getIdReservacion())) {
            return false;
        }

        reservaciones.add(reservacion);
        return true;
    }

    private Boolean existeReservacionConId(int idReservacion) {
        for (Reservacion reservacion : reservaciones) {
            if (reservacion != null && reservacion.getIdReservacion() == idReservacion) {
                return true;
            }
        }
        return false;
    }

    public String mostrarReservaciones() {
        StringBuilder sb = new StringBuilder();
        if (reservaciones.isEmpty()) {
            return "No hay reservaciones registradas.";
        }

        for (Reservacion reservacion : reservaciones) {
            if (reservacion != null) {
                sb.append(reservacion.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    public String mostrarReservacionesPorCliente(int idCliente) {
        StringBuilder sb = new StringBuilder();
        boolean encontradas = false;

        for (Reservacion reservacion : reservaciones) {
            if (reservacion != null &&
                    reservacion.getCliente() != null &&
                    reservacion.getCliente().getId() == idCliente) {
                sb.append(reservacion.toString()).append("\n");
                encontradas = true;
            }
        }

        if (!encontradas) {
            return "No se encontraron reservaciones para el cliente con ID: " + idCliente;
        }
        return sb.toString();
    }

    public String mostrarReservacionesPorHabitacion(int numeroHabitacion) {
        StringBuilder sb = new StringBuilder();
        boolean encontradas = false;

        for (Reservacion reservacion : reservaciones) {
            if (reservacion != null &&
                    reservacion.getHabitacion() != null &&
                    reservacion.getHabitacion().getNumero() == numeroHabitacion) {
                sb.append(reservacion.toString()).append("\n");
                encontradas = true;
            }
        }

        if (!encontradas) {
            return "No se encontraron reservaciones para la habitación: " + numeroHabitacion;
        }
        return sb.toString();
    }

    public String mostrarReservacionesPorFechaLlegada(LocalDate fecha) {
        StringBuilder sb = new StringBuilder();
        boolean encontradas = false;

        for (Reservacion reservacion : reservaciones) {
            if (reservacion != null &&
                    reservacion.getFechaLlegada() != null &&
                    reservacion.getFechaLlegada().equals(fecha)) {
                sb.append(reservacion.toString()).append("\n");
                encontradas = true;
            }
        }

        if (!encontradas) {
            return "No se encontraron reservaciones para la fecha: " + fecha;
        }
        return sb.toString();
    }

    public Boolean eliminarReservacion(int idReservacion) {
        if (idReservacion <= 0) {
            return false;
        }

        for (int i = 0; i < reservaciones.size(); i++) {
            Reservacion reservacion = reservaciones.get(i);
            if (reservacion != null && reservacion.getIdReservacion() == idReservacion) {
                reservaciones.remove(i);
                return true;
            }
        }
        return false;
    }

    public Reservacion buscarReservacionPorId(int id) {
        for (Reservacion reservacion : reservaciones) {
            if (reservacion != null && reservacion.getIdReservacion() == id) {
                return reservacion;
            }
        }
        return null;
    }

    public List<Reservacion> buscarReservacionesPorCliente(int idCliente) {
        List<Reservacion> resultado = new ArrayList<>();
        for (Reservacion reservacion : reservaciones) {
            if (reservacion != null &&
                    reservacion.getCliente() != null &&
                    reservacion.getCliente().getId() == idCliente) {
                resultado.add(reservacion);
            }
        }
        return resultado;
    }

    public List<Reservacion> buscarReservacionesPorHabitacion(int numeroHabitacion) {
        List<Reservacion> resultado = new ArrayList<>();
        for (Reservacion reservacion : reservaciones) {
            if (reservacion != null &&
                    reservacion.getHabitacion() != null &&
                    reservacion.getHabitacion().getNumero() == numeroHabitacion) {
                resultado.add(reservacion);
            }
        }
        return resultado;
    }

    public Boolean actualizarReservacion(Reservacion reservacionActualizada) {
        if (reservacionActualizada == null) {
            return false;
        }

        for (Reservacion reservacion : reservaciones) {
            if (reservacion != null && reservacion.getIdReservacion() == reservacionActualizada.getIdReservacion()) {
                if (reservacionActualizada.getCantidadDeNoches() > 0) {
                    reservacion.setCliente(reservacionActualizada.getCliente());
                    reservacion.setHabitacion(reservacionActualizada.getHabitacion());
                    reservacion.setFechaLlegada(reservacionActualizada.getFechaLlegada());
                    reservacion.setCantidadDeNoches(reservacionActualizada.getCantidadDeNoches());
                    reservacion.setFechaReservacion(reservacionActualizada.getFechaReservacion());
                    reservacion.setDescuento(reservacionActualizada.getDescuento());

                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public boolean estaVacia() {
        return reservaciones.isEmpty();
    }

    public int tamaño() {
        return reservaciones.size();
    }

    public void limpiarReservaciones() {
        reservaciones.clear();
    }

    public List<Reservacion> obtenerReservacionesConDescuento() {
        List<Reservacion> resultado = new ArrayList<>();
        for (Reservacion reservacion : reservaciones) {
            if (reservacion != null && reservacion.getDescuento() > 0) {
                resultado.add(reservacion);
            }
        }
        return resultado;
    }

    public List<Reservacion> obtenerReservacionesPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Reservacion> resultado = new ArrayList<>();
        if (fechaInicio == null || fechaFin == null || fechaInicio.isAfter(fechaFin)) {
            return resultado;
        }

        for (Reservacion reservacion : reservaciones) {
            if (reservacion != null &&
                    reservacion.getFechaLlegada() != null &&
                    !reservacion.getFechaLlegada().isBefore(fechaInicio) &&
                    !reservacion.getFechaLlegada().isAfter(fechaFin)) {
                resultado.add(reservacion);
            }
        }
        return resultado;
    }

    public double calcularTotalIngresos() {
        double total = 0.0;
        for (Reservacion reservacion : reservaciones) {
            if (reservacion != null) {
                total += reservacion.getPrecioTotal();
            }
        }
        return total;
    }

    public List<Reservacion> obtenerReservacionesPorNochesMinimas(int nochesMinimas) {
        List<Reservacion> resultado = new ArrayList<>();
        for (Reservacion reservacion : reservaciones) {
            if (reservacion != null && reservacion.getCantidadDeNoches() >= nochesMinimas) {
                resultado.add(reservacion);
            }
        }
        return resultado;
    }

    public Reservacion obtenerReservacionMasReciente() {
        Reservacion masReciente = null;
        for (Reservacion reservacion : reservaciones) {
            if (reservacion != null && reservacion.getFechaReservacion() != null) {
                if (masReciente == null || reservacion.getFechaReservacion().isAfter(masReciente.getFechaReservacion())) {
                    masReciente = reservacion;
                }
            }
        }
        return masReciente;
    }

    public Reservacion obtenerReservacionMayorPrecio() {
        Reservacion mayorPrecio = null;
        for (Reservacion reservacion : reservaciones) {
            if (reservacion != null) {
                if (mayorPrecio == null || reservacion.getPrecioTotal() > mayorPrecio.getPrecioTotal()) {
                    mayorPrecio = reservacion;
                }
            }
        }
        return mayorPrecio;
    }
}