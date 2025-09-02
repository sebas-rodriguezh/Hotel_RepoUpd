package org.example.hotel_proyectoc3.Domain.Model;

import java.util.List;

public class BitacoraLimpieza extends Bitacora {

    public BitacoraLimpieza(List<Tarea> tareas) {
        super(tareas);
    }

    public BitacoraLimpieza() {
        super();
    }

    @Override
    public String tipo() {
        return "Limpieza";
    }
}