package org.example.hotel_proyectoc3.Domain.Model;

import java.time.LocalDate;
import java.time.Period;

public abstract class Personal {
    protected int id; //Similar al del cliente. Para identificarlo dentro del sistema.
    protected String identificacion;
    protected String nombre;
    protected String primerApellido;
    protected String direccion;
    protected double salario;
    protected LocalDate fechaNacimiento;
    protected int edad;


    public Personal () {

    }

    public Personal(int id, String identificacion, double salario, String nombre, String primerApellido, LocalDate fechaNacimiento) {
        this.id = id;
        this.identificacion = identificacion;
        this.salario = salario;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.fechaNacimiento = fechaNacimiento;

        setFechaNacimiento(); //Mejor de una vez, realizamos la conversion para la edad.
    }


    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento() {
        LocalDate fechaActual = LocalDate.now();
        int edadAux = Period.between(this.fechaNacimiento, fechaActual).getYears();
        if (edadAux < 18) {
            throw new IllegalArgumentException("La edad no puede ser menor que 18.");
        }
        this.edad = edadAux;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad() {
        LocalDate fechaActual = LocalDate.now();
        this.edad = Period.between(this.fechaNacimiento, fechaActual).getYears();
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //M.V.P
    public abstract String mostrarInformacion();
    public abstract String tipo();

}
