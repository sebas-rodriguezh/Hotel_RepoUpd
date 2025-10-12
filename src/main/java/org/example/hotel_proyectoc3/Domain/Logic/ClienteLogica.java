package org.example.hotel_proyectoc3.Domain.Logic;

import org.example.hotel_proyectoc3.Data.ClienteDatos;
import org.example.hotel_proyectoc3.Domain.Model.Cliente;

import java.sql.SQLException;
import java.util.List;

public class ClienteLogica {
    private ClienteDatos store = new ClienteDatos();

    public List<Cliente> findAll () throws SQLException {
        return store.findAll();
    }

    public Cliente findById (int id) throws SQLException {
        return store.findById(id);
    }


    public List<Cliente> findAllByParameters(String text) throws SQLException {
        return store.findAllByParameters(text);
    }


    public Cliente create (Cliente nuevo) throws SQLException {
        validarNuevoCliente(nuevo);
        return store.insert(nuevo);
    }

    public Cliente update (Cliente cliente) throws SQLException {
        if (cliente == null || cliente.getId() <= 0) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }
        validarNuevoCliente(cliente);
        return store.update(cliente);
    }

    public Boolean deleteById (int id) throws SQLException {
        if (id <= 0) return false;

        return store.delete(id) > 0;
    }

    private void validarNuevoCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }

        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente es requerido");
        }
        if (cliente.getNombre().length() > 50) {
            throw new IllegalArgumentException("El nombre no puede tener más de 50 caracteres");
        }

        if (cliente.getPrimerApellido() == null || cliente.getPrimerApellido().trim().isEmpty()) {
            throw new IllegalArgumentException("El primer apellido del cliente es requerido");
        }
        if (cliente.getPrimerApellido().length() > 50) {
            throw new IllegalArgumentException("El primer apellido no puede tener más de 50 caracteres");
        }

        if (cliente.getIdentificacion() == null || cliente.getIdentificacion().trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación del cliente es requerida");
        }
        if (cliente.getIdentificacion().length() > 20) {
            throw new IllegalArgumentException("La identificación no puede tener más de 20 caracteres");
        }

        if (cliente.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es requerida");
        }
        if (cliente.getFechaNacimiento().isAfter(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura");
        }
    }

}
