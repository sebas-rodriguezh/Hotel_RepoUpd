package org.example.hotel_proyectoc3.Domain.Model;


import java.time.LocalDate;
import java.time.Period;

public class Cliente {
    private int id; //ID para control interno de la persona. Para consultas más sencillas.
    private String identificacion;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String direccion;
    private LocalDate fechaNacimiento;
    private int edad;

    //Uso de Clase Miselanea. (Es como una especie de clase de servicio).

    public Cliente() {

    }

    public Cliente(int id, String identificacion, String nombre, String primerApellido, LocalDate fechaNacimiento) {
        this.id = id;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.fechaNacimiento = fechaNacimiento;

        setFechaNacimiento();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad() {
        LocalDate fechaActual = LocalDate.now();
        this.edad = Period.between(this.fechaNacimiento, fechaActual).getYears();
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento() {
        //Entregable: Calcular la fecha para que la persona registrada
        //solo pueda tener 18 años cumplidos ó más.

        LocalDate fechaActual = LocalDate.now();
        int edadAux = Period.between(this.fechaNacimiento, fechaActual).getYears();
        if (edadAux < 18) {
            throw new IllegalArgumentException("La edad no puede ser menor que 18.");
        }
        this.edad = edadAux;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Información del cliente:\n");
        s.append("  ID interno: ").append(id).append("\n");
        s.append("  Identificacion: ").append(identificacion).append("\n");
        s.append("  Nombre completo: ").append(nombre).append(" ").append(primerApellido).append("\n");
        s.append("  Direccion: ").append(direccion).append("\n");
        s.append("  Fecha de nacimiento: ").append(fechaNacimiento).append("\n");
        s.append("  Edad: ").append(edad).append(" años\n");
        return s.toString();
    }

}
