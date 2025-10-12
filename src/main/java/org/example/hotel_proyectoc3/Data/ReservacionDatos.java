package org.example.hotel_proyectoc3.Data;

import org.example.hotel_proyectoc3.Domain.Model.Cliente;
import org.example.hotel_proyectoc3.Domain.Model.Habitacion;
import org.example.hotel_proyectoc3.Domain.Model.Reservacion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservacionDatos {

    public List<Reservacion> findAll() throws SQLException {
        String sql = "SELECT r.*, c.nombre, c.identificacion, " +
                "h.numero, h.precio, h.tipo, h.estado, h.capacidad " +
                "FROM reservacion r " +
                "JOIN cliente c ON r.cliente_id = c.id " +
                "JOIN habitacion h ON r.habitacion_id = h.id " +
                "ORDER BY r.id";

        List<Reservacion> reservaciones = new ArrayList<>();

        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reservacion reservacion = mapResultSetToReservacion(rs);
                reservaciones.add(reservacion);
            }
        }
        return reservaciones;
    }

    public Reservacion insert(Reservacion reservacion) throws SQLException {
        String sql = "INSERT INTO reservacion (cliente_id, habitacion_id, fecha_llegada, fecha_salida, " +
                "cantidad_noches, precio_total, descuento) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, reservacion.getCliente().getId());
            ps.setInt(2, reservacion.getHabitacion().getId());
            ps.setDate(3, Date.valueOf(reservacion.getFechaLlegada()));
            ps.setDate(4, Date.valueOf(reservacion.getFechaSalida()));
            ps.setInt(5, reservacion.getCantidadDeNoches());
            ps.setDouble(6, reservacion.getPrecioTotal());
            ps.setDouble(7, reservacion.getDescuento());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    reservacion.setIdReservacion(keys.getInt(1));
                }
            }
        }
        return reservacion;
    }

    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM reservacion WHERE id = ?";

        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }

    public List<Reservacion> findAllByParameters(String texto) throws SQLException {
        String sql = "SELECT r.*, c.nombre, c.identificacion, " +
                "h.numero, h.precio, h.tipo, h.estado, h.capacidad " +
                "FROM reservacion r " +
                "JOIN cliente c ON r.cliente_id = c.id " +
                "JOIN habitacion h ON r.habitacion_id = h.id " +
                "WHERE c.nombre LIKE ? OR c.identificacion LIKE ? OR CAST(h.numero AS CHAR) LIKE ? " +
                "ORDER BY r.id";

        List<Reservacion> reservaciones = new ArrayList<>();

        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            String buscar = "%" + texto + "%";
            ps.setString(1, buscar);
            ps.setString(2, buscar);
            ps.setString(3, buscar);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Reservacion reservacion = mapResultSetToReservacion(rs);
                    reservaciones.add(reservacion);
                }
            }
        }
        return reservaciones;
    }

    private Reservacion mapResultSetToReservacion(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("cliente_id"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setIdentificacion(rs.getString("identificacion"));

        Habitacion habitacion = new Habitacion();
        habitacion.setId(rs.getInt("habitacion_id"));
        habitacion.setNumero(rs.getInt("numero"));
        habitacion.setPrecio(rs.getDouble("precio"));
        habitacion.setTipo(rs.getInt("tipo"));
        habitacion.setEstado(rs.getInt("estado"));
        habitacion.setCapacidad(rs.getInt("capacidad"));

        int id = rs.getInt("id");
        LocalDate fechaLlegada = rs.getDate("fecha_llegada").toLocalDate();
        LocalDate fechaSalida = rs.getDate("fecha_salida").toLocalDate();
        int cantidadNoches = rs.getInt("cantidad_noches");
        double descuento = rs.getDouble("descuento");
        double precioTotal = rs.getDouble("precio_total");

        Reservacion reservacion = new Reservacion(cliente, habitacion, fechaLlegada, cantidadNoches, descuento);
        reservacion.setIdReservacion(id);
        reservacion.setFechaSalida(fechaSalida);

        return reservacion;
    }
}