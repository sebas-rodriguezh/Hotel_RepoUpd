package org.example.hotel_proyectoc3.Domain.Model;

public class Parqueo {
    private Vehiculo[][] vehiculos;
    private int filas;
    private int columnas;

    public Parqueo () {
        filas = columnas = 10;
        vehiculos = new Vehiculo[filas][columnas]; //Si no se definen las filas y columnas en el constructor, la creamos de 10 x 10.
    }

    public Parqueo(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        vehiculos = new Vehiculo[filas][columnas];
    }

    public Vehiculo[][] getVehiculos() {
        return vehiculos;
    }

    public Boolean espacioDisponible(int fila, int columna) {
        if ((fila >= 0 && fila < filas && columna >= 0 && columna < columnas) && vehiculos[fila][columna] == null) {
            return true;
        }
        return false;
    }

    public Boolean matriculaYaExistente(String matricula) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Vehiculo v = vehiculos[i][j];
                if (v != null && v.getMatricula() == matricula) {
                    return true;
                }
            }
        }
        return false;
    }


    public Vehiculo getVehiculoEnPosicion(int fila, int columna) {
        if (fila >= 0 && fila < filas && columna >= 0 && columna < columnas) {
            return vehiculos[fila][columna];
        }
        return null;
    }

    public Boolean insertarVehiculo(int fila, int columna, Vehiculo vehiculo) {
        if (espacioDisponible(fila, columna) && (!matriculaYaExistente(vehiculo.getMatricula()))) {
            vehiculos[fila][columna] = vehiculo;
            return true;
        }
        return false;
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Estado del Parqueo:\n");

        sb.append("     ");
        for (int j = 0; j < columnas; j++) {
            sb.append(j).append("     ");
        }
        sb.append("\n");

        for (int i = 0; i < filas; i++) {
            sb.append(i).append("   ");
            for (int j = 0; j < columnas; j++) {
                if (vehiculos[i][j] != null) {
                    sb.append("[*]   ");
                } else {
                    sb.append("[D]   ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void mostrarVehiculosPorCliente(int idCliente) {
        boolean encontrado = false;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Vehiculo v = vehiculos[i][j];
                if (v != null && v.getDuennio().getId() == idCliente) {
                    System.out.println(v.mostrarInformacion());
                    encontrado = true;
                }
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron vehículos para este cliente.");
        }
    }

    public Boolean puedeSacarVehiculo(int idCliente, String matricula){
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Vehiculo v = vehiculos[i][j];
                if (v != null && v.getMatricula() == matricula && v.getDuennio().getId() == idCliente) {
                    return true;
                }
            }
        }
        return false;
    }


    public Boolean retirarVehiculoDelParqueo(int idCliente, String matricula){
        if (puedeSacarVehiculo(idCliente, matricula)) {
            int pos[] = this.getIndicesVehiculo(idCliente,matricula);
            vehiculos[pos[0]][pos[1]] = null;
            return true;
        }
        return false;

    }

    public int[] getIndicesVehiculo(int idCliente, String matricula) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Vehiculo v = vehiculos[i][j];
                if (v != null &&
                        v.getDuennio().getId() == idCliente &&
                        v.getMatricula().equalsIgnoreCase(matricula)) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }



}
