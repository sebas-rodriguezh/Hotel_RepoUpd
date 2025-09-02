package org.example.hotel_proyectoc3.Domain.Model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public abstract class Bitacora {
    protected List<Tarea> tareas;

    public Bitacora(List<Tarea> tareas) {
        this.tareas = new ArrayList<>(tareas);
    }

    public Bitacora() {
        this.tareas = new ArrayList<Tarea>();
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = new ArrayList<>(tareas);
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public List<Tarea> obtenerTareasPorIDDelTrabajador(int id) {
        List<Tarea> tareasResultado = new ArrayList<>();

        for (Tarea tarea : tareas) {
            if (tarea != null &&
                    tarea.getTrabajadorACargo() != null &&
                    tarea.getTrabajadorACargo().getId() == id) {
                tareasResultado.add(tarea);
            }
        }
        return tareasResultado;
    }

    public Boolean agregarTarea(Tarea tarea) {
        if (tarea == null) {
            return false;
        }
        tareas.add(tarea);
        return true;
    }

    public Boolean eliminarTarea(String idTarea) {
        for (Tarea tarea : tareas) {
            if (tarea != null && tarea.getIdTarea().equals(idTarea)) {
                tareas.remove(tarea);
                return true;
            }
        }
        return false;
    }

    public List<Tarea> obtenerTareasPorEstado(Boolean estado) {
        List<Tarea> resultado = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (tarea != null && tarea.getEstado().equals(estado)) {
                resultado.add(tarea);
            }
        }
        return resultado;
    }

    public List<Tarea> obtenerTareasPorFecha(LocalDate fecha) {
        List<Tarea> resultado = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (tarea != null && tarea.getFecha().equals(fecha)) {
                resultado.add(tarea);
            }
        }
        return resultado;
    }

    public Boolean hayTareasPendientes() {
        for (Tarea tarea : tareas) {
            if (tarea != null && !tarea.getEstado()) {
                return true;
            }
        }
        return false;
    }

    //M.V.P
    public abstract String tipo();
}