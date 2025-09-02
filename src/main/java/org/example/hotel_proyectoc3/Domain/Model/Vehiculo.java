package org.example.hotel_proyectoc3.Domain.Model;

public class Vehiculo
{
    private String matricula;
    private String modelo;
    private Cliente duennio;

    public Vehiculo () {

    }

    public Vehiculo(Cliente duennio, String modelo, String matricula) {
        this.duennio = duennio;
        this.modelo = modelo;
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Cliente getDuennio() {
        return duennio;
    }

    public void setDuennio(Cliente duennio) {
        this.duennio = duennio;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String mostrarInformacion() {
        StringBuilder s = new StringBuilder();
        s.append("Informacion del Vehiculo " + "\n");
        s.append("  Matricula: " + this.getMatricula() + "\n");
        s.append("  Modelo: " + this.getModelo() + "\n");
        s.append("  ID del duennio: " + this.getDuennio().getId() + "\n");

        return s.toString();
    }


}
