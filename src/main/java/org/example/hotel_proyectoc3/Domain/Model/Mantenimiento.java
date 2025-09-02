package org.example.hotel_proyectoc3.Domain.Model;


import java.time.LocalDate;

public class Mantenimiento extends Personal {

    public Mantenimiento() {
    }

    public Mantenimiento(int id, String identificacion, double salario, String nombre, String primerApellido, LocalDate fechaNacimiento) {
        super(id, identificacion, salario, nombre, primerApellido, fechaNacimiento);
    }

    @Override
    public String mostrarInformacion() {
        StringBuilder s = new StringBuilder();
        s.append("Informacion personal de mantenimiento " + "\n");
        s.append("  Nombre: " + this.getNombre() + " " +this.getPrimerApellido() + "\n");
        s.append("  Edad: " + this.getEdad() + " años. \n");
        s.append("  Dirrecion: " + this.getDireccion() + "\n");
        s.append("  Salario: " + this.getSalario() + "\n");
        return s.toString();
    }

    @Override
    public String tipo() {
        return "Mantenimiento";
    }
}
