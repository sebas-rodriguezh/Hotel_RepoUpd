package org.example.hotel_proyectoc3.Data;

import com.mysql.cj.xdevapi.Client;
import org.example.hotel_proyectoc3.Domain.Model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDatos {
    public List<Cliente> findAll() throws SQLException {
        String sql = "Select * from cliente ORDER BY id";

        try (Connection cn = DB.getConnection(); //1. Conexión.
        PreparedStatement ps = cn.prepareStatement(sql); //2. Preparar la sentencia SQL.
        ResultSet rs = ps.executeQuery())  //3. Ejecutar la setencia.
        {
            List<Cliente> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Cliente(
                      rs.getInt("id"),
                        rs.getString("identificacion"),
                        rs.getString("nombre"),
                        rs.getString("primerApellido"),
                        rs.getDate("fechaNacimiento").toLocalDate()
                ));
            }
            return list;
        }
    }

    public Cliente findById(int id) throws SQLException {
        String sql = "Select * from Cliente where id = "+ id;

        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            Cliente encontrado = null;
            if (rs.next()) {
                encontrado = new Cliente(
                        rs.getInt("id"),
                        rs.getString("identificacion"),
                        rs.getString("nombre"),
                        rs.getString("primerApellido"),
                        rs.getDate("fechaNacimiento").toLocalDate()
                );
            }
            return encontrado;
        }
    }

    public Cliente insert(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (nombre, primerApellido, segundoApellido, identificacion, fechaNacimiento) VALUES (?, ?, ?, ?, ?)";
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getPrimerApellido());
            ps.setString(3, cliente.getSegundoApellido());
            ps.setString(4, cliente.getIdentificacion());
            ps.setDate(5, Date.valueOf(cliente.getFechaNacimiento()));
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    cliente.setId(keys.getInt(1));
                    return cliente;
                }
            }
            return null;
        }
    }

    public Cliente update(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente set nombre = ?, primerApellido = ?, segundoApellido = ?, identificacion = ? WHERE id = ?";
        try (Connection cn = DB.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getPrimerApellido());
            ps.setString(3, cliente.getSegundoApellido());
            ps.setString(4, cliente.getIdentificacion());
            ps.setInt(5, cliente.getId());
            if (ps.executeUpdate() > 0)
            {
                return cliente; 
            }
            return null;
        }
    }

    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id = " + id;
        try (Connection cn = DB.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql)) {
            return ps.executeUpdate();
        }
    }

    public List<Cliente> findAllByParameters(String text) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE nombre LIKE ? OR identificacion LIKE ? OR primerApellido LIKE ? OR segundoApellido LIKE ?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String buscar = "%" + text + "%";
            stmt.setString(1, buscar);
            stmt.setString(2, buscar);
            stmt.setString(3, buscar);
            stmt.setString(4, buscar);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Cliente> clientes = new ArrayList<>();
                while (rs.next()) {
                    clientes.add(new Cliente(
                            rs.getInt("id"),
                            rs.getString("identificacion"),
                            rs.getString("nombre"),
                            rs.getString("primerApellido"),
                            rs.getDate("fechaNacimiento").toLocalDate()
                    ));
                }
                return clientes;
            }
        }
    }

}
