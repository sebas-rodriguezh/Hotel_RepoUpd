package org.example.hotel_proyectoc3.Domain.Model;

import java.util.List;

public class BitacoraMantenimiento extends Bitacora {

    public BitacoraMantenimiento(List<Tarea> tareas) {
        super(tareas);
    }

    public BitacoraMantenimiento() {
        super();
    }

    @Override
    public String tipo() {
        return "Mantenimiento";
    }
}