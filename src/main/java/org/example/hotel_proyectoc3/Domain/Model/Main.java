package org.example.hotel_proyectoc3.Domain.Model;
import org.example.hotel_proyectoc3.Domain.Logic.GestorClientes;
import org.example.hotel_proyectoc3.Domain.Logic.GestorPersonal;

import java.time.LocalDate;

//Main-local: Con la finalidad de probar ciertas clases.

public class Main {
    public static void main(String[] args) {
        Cliente client1 = new Cliente(1001, "1234", "Juan", "Herrera", LocalDate.of(1990, 5, 15));
        Cliente client2 = new Cliente(1002, "2345", "Maria", "Gonzalez", LocalDate.of(1985, 8, 22));
        Cliente client3 = new Cliente(1003, "3456", "Carlos", "Martinez", LocalDate.of(1998, 3, 10));
        Cliente client4 = new Cliente(1004, "4567", "Ana", "Lopez", LocalDate.of(1992, 11, 30));
        Cliente client5 = new Cliente(1005, "5678", "Luis", "Ramirez", LocalDate.of(1987, 7, 18));
        Cliente clienteRepe = new Cliente(5003, "4567", "Repetido", "Repetido", LocalDate.of(1999, 12, 25));

        Personal perso1 = new Limpieza(5001, "87654321", 34000, "Roberto", "Mendoza", LocalDate.of(1995, 10, 14));
        Personal perso2 = new Limpieza(5002, "98765432", 35000, "Gabriela", "Rojas", LocalDate.of(1988, 7, 3));
        Personal perso3 = new Limpieza(5003, "56789012", 36000, "Fernando", "Silva", LocalDate.of(1993, 4, 19));
        Personal perso4 = new Limpieza(5004, "67890123", 34000, "Patricia", "Castro", LocalDate.of(1990, 1, 30));
        Personal perso5 = new Limpieza(5005, "78901234", 37000, "Ricardo", "Navarro", LocalDate.of(1987, 9, 22));


        GestorClientes listaClientes = new GestorClientes();
        GestorPersonal listaPesonales = new GestorPersonal();

        listaClientes.insertarCliente(client1, false);
        listaClientes.insertarCliente(client2, false);
        listaClientes.insertarCliente(client3, false);
        listaClientes.insertarCliente(client4, false);
        listaClientes.insertarCliente(client5, false);

        listaPesonales.insertarPersonal(perso1, false);
        listaPesonales.insertarPersonal(perso2, false);
        listaPesonales.insertarPersonal(perso3, false);
        listaPesonales.insertarPersonal(perso4, false);
        listaPesonales.insertarPersonal(perso5, false);

        if (listaClientes.insertarCliente(clienteRepe, false)) {
            System.out.println("SI se metio");
        } else {
            System.out.println("NO se metio CLIENTE");
        }

        System.out.println(listaClientes.mostrarClientes());


    //HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA CHANGES

    }
}