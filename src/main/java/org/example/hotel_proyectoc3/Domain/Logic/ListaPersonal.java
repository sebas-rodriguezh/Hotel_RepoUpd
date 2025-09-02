package org.example.hotel_proyectoc3.Domain.Logic;
import org.example.hotel_proyectoc3.Domain.Model.Personal;

import java.util.ArrayList;
import java.util.List;

public class ListaPersonal {
    private List<Personal> listaPersonal;

    public ListaPersonal() {
        this.listaPersonal = new ArrayList<Personal>();
    }

    public ListaPersonal(List<Personal> listaPersonal) {
        this.listaPersonal = new ArrayList<Personal>(listaPersonal);
    }

    public List<Personal> getListaPersonal() {
        return listaPersonal;
    }

    public void setListaPersonal(List<Personal> listaPersonal) {
        this.listaPersonal = new ArrayList<Personal>(listaPersonal);
    }

    public Boolean insertarPersonal(Personal personal, Boolean respuestaLista) {
        if (personal == null) {
            return false;
        }

        int id = personal.getId();
        String identificacion = personal.getIdentificacion();

        // Validar que no exista alguien con los mismos credenciales
        if (respuestaLista ||
                existeAlguienConEsosCredenciales(id, identificacion)) {
            return false;
        }

        // Validaciones adicionales
        if (id <= 0) {
            return false;
        }

        if (identificacion == null || identificacion.trim().isEmpty()) {
            return false;
        }

        if (personal.getNombre() == null || personal.getNombre().trim().isEmpty()) {
            return false;
        }

        listaPersonal.add(personal);
        return true;
    }

    public Boolean existeAlguienConEsosCredenciales(int id, String numIdentificacion) {
        for (Personal personal : listaPersonal) {
            if (personal != null &&
                    (personal.getId() == id || personal.getIdentificacion().equals(numIdentificacion))) {
                return true;
            }
        }
        return false;
    }

    public String mostrarListaPersonal() {
        StringBuilder s = new StringBuilder();
        if (listaPersonal.isEmpty()) {
            return "No hay personal registrado.";
        }

        for (Personal personal : listaPersonal) {
            if (personal != null) {
                s.append(personal.mostrarInformacion()).append("\n");
            }
        }
        return s.toString();
    }

    public String mostrarPersonalPorTipo(String tipo) {
        StringBuilder s = new StringBuilder();
        if (tipo == null || tipo.trim().isEmpty()) {
            return "Tipo de personal no válido.";
        }

        boolean encontrados = false;
        for (Personal personal : listaPersonal) {
            if (personal != null && personal.tipo().equalsIgnoreCase(tipo.trim())) {
                s.append(personal.mostrarInformacion()).append("\n");
                encontrados = true;
            }
        }

        if (!encontrados) {
            return "No se encontró personal del tipo: " + tipo;
        }
        return s.toString();
    }

    public Boolean eliminarPersonal(int id, String identificacion) {
        if (id <= 0 || identificacion == null || identificacion.trim().isEmpty()) {
            return false;
        }

        for (int i = 0; i < listaPersonal.size(); i++) {
            Personal personal = listaPersonal.get(i);
            if (personal != null &&
                    personal.getId() == id &&
                    personal.getIdentificacion().equals(identificacion)) {

                // Validaciones adicionales antes de eliminar
                // (por ejemplo, verificar que no tenga responsabilidades activas)
                if (puedeEliminarPersonal(personal)) {
                    listaPersonal.remove(i);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    private Boolean puedeEliminarPersonal(Personal personal) {
        // Aquí puedes implementar validaciones específicas
        // Por ejemplo, verificar que no tenga turnos activos, responsabilidades, etc.
        // Por ahora retornamos true como placeholder
        return true;
    }

    public Personal buscarPersonalPorId(int id) {
        for (Personal personal : listaPersonal) {
            if (personal != null && personal.getId() == id) {
                return personal;
            }
        }
        return null;
    }

    public Personal buscarPersonalPorIdentificacion(String identificacion) {
        if (identificacion == null || identificacion.trim().isEmpty()) {
            return null;
        }

        for (Personal personal : listaPersonal) {
            if (personal != null && personal.getIdentificacion().equals(identificacion.trim())) {
                return personal;
            }
        }
        return null;
    }

    public boolean estaVacia() {
        return listaPersonal.isEmpty();
    }

    public int tamaño() {
        return listaPersonal.size();
    }

    public Boolean actualizarPersonal(Personal personalActualizado) {
        if (personalActualizado == null) {
            return false;
        }

        for (int i = 0; i < listaPersonal.size(); i++) {
            Personal personal = listaPersonal.get(i);
            if (personal != null && personal.getId() == personalActualizado.getId()) {
                listaPersonal.set(i, personalActualizado);
                return true;
            }
        }
        return false;
    }

    public List<Personal> obtenerPersonalPorTipo(String tipo) {
        List<Personal> resultado = new ArrayList<>();
        if (tipo == null || tipo.trim().isEmpty()) {
            return resultado;
        }

        for (Personal personal : listaPersonal) {
            if (personal != null && personal.tipo().equalsIgnoreCase(tipo.trim())) {
                resultado.add(personal);
            }
        }
        return resultado;
    }
}