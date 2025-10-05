package org.example.hotel_proyectoc3.Domain.Logic;
import org.example.hotel_proyectoc3.Domain.Model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class GestorClientes {
    private List<Cliente> clientes;

    public GestorClientes() {
        clientes = new ArrayList<Cliente>();
    }

    public GestorClientes(List<Cliente> clientes) {
        this.clientes = new ArrayList<Cliente>(clientes);
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = new ArrayList<Cliente>(clientes);
    }

    public String mostrarClientes() {
        StringBuilder s = new StringBuilder();
        for (Cliente cliente : clientes) {
            if (cliente != null) {
                s.append(cliente.toString());
            }
        }
        return s.toString();
    }

    public Boolean insertarCliente(Cliente cliente, Boolean valorBool) {
        int id = cliente.getId();
        String identificacion = cliente.getIdentificacion();

        if (valorBool ||
                existeAlguienConEsosCredenciales(id, identificacion)) {
            return false;
        }
        clientes.add(cliente);
        return true;
    }

    public String mostrarClientesPorID(int id) {
        StringBuilder s = new StringBuilder();
        for (Cliente cliente : clientes) {
            if (cliente != null && cliente.getId() == id) {
                s.append(cliente.toString());
            }
        }
        return s.toString();
    }

    public Boolean existeAlguienConEsosCredenciales(int id, String numIdentificacion) {
        for (Cliente cliente : clientes) {
            if (cliente != null &&
                    (cliente.getId() == id || cliente.getIdentificacion().equals(numIdentificacion))) {
                return true;
            }
        }
        return false;
    }

    public Boolean eliminarCliente(int id, String identificacion) {
        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            if (cliente != null &&
                    cliente.getId() == id &&
                    cliente.getIdentificacion().equals(identificacion)) {
                clientes.remove(i);
                return true;
            }
        }
        return false;
    }

    public Cliente buscarClientePorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente != null && cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

    public boolean estaVacia() {
        return clientes.isEmpty();
    }

    public int tamaño() {
        return clientes.size();
    }
}