package org.example.hotel_proyectoc3.Data;

import org.example.hotel_proyectoc3.Domain.Model.Habitacion;
import org.example.hotel_proyectoc3.Domain.Model.Habitacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class HabitacionDatos {
    
    public List<Habitacion > findAll() throws SQLException
    {
        String sql = "SELECT * FROM habitacion ORDER BY id";

        try (Connection cn = DB.getConnection(); //1. Conexión.
             PreparedStatement ps = cn.prepareStatement(sql); //2. Preparar la sentencia SQL.
             ResultSet rs = ps.executeQuery())  //3. Ejecutar la setencia.
        {
            List<Habitacion> list = new ArrayList<>();
            while (rs.next())
            {
                list.add(crearHabitacionDesdeResultSet(rs));
            }
            return list;
        }
    }

    public Habitacion findById(int id) throws SQLException {
        String sql = "SELECT * FROM habitacion WHERE id = ?";

        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return crearHabitacionDesdeResultSet(rs);
                }
                return null;
            }
        }
    }

    public Habitacion findByNumero(int numero) throws SQLException {
        String sql = "SELECT * FROM habitacion WHERE numero = ?";

        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, numero);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return crearHabitacionDesdeResultSet(rs);
                }
                return null;
            }
        }
    }


    public Habitacion insert (Habitacion habitacion) throws SQLException {
        String sql = "INSERT INTO habitacion (numero, tipo, estado, precio, capacidad) VALUES (?, ?, ?, ?, ?)";
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, habitacion.getNumero());
            ps.setInt(2, habitacion.getTipo());
            ps.setInt(3, habitacion.getEstado());
            ps.setDouble(4, habitacion.getPrecio());
            ps.setInt(5, habitacion.getCapacidad());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    habitacion.setId(keys.getInt(1));
                    return habitacion;
                }
            }
            return null;
        }
    }

    public Habitacion update(Habitacion habitacion) throws SQLException {
        String sql = "UPDATE habitacion SET numero = ?, tipo = ?, estado = ?, precio = ?, capacidad = ? WHERE id = ?";

        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, habitacion.getNumero());
            ps.setInt(2, habitacion.getTipo());
            ps.setInt(3, habitacion.getEstado());
            ps.setDouble(4, habitacion.getPrecio());
            ps.setInt(5, habitacion.getCapacidad());
            ps.setInt(6, habitacion.getId());

            if (ps.executeUpdate() > 0) {
                return habitacion;
            }
            return null;
        }
    }

    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM habitacion WHERE id = ?";

        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }




    private Habitacion crearHabitacionDesdeResultSet(ResultSet rs) throws SQLException {
        Habitacion habitacion = new Habitacion(
                rs.getInt("id"),
                rs.getInt("numero"),
                rs.getInt("tipo"),
                rs.getInt("estado"),
                rs.getDouble("precio"),
                rs.getInt("capacidad")
        );

        return habitacion;
    }

    public List<Habitacion> findAvailableRooms() throws SQLException {
        String sql = "SELECT * FROM habitacion WHERE estado = 1 ORDER BY numero";

        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Habitacion> habitaciones = new ArrayList<>();
            while (rs.next()) {
                habitaciones.add(crearHabitacionDesdeResultSet(rs));
            }
            return habitaciones;
        }
    }
}
