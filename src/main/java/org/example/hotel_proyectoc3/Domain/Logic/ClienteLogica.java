package org.example.hotel_proyectoc3.Domain.Logic;

import org.example.hotel_proyectoc3.Data.ClienteConector;
import org.example.hotel_proyectoc3.Data.ClienteDatos;
import org.example.hotel_proyectoc3.Data.ClienteEntity;
import org.example.hotel_proyectoc3.Domain.Model.Cliente;

import java.util.List;
import java.util.stream.Collectors;

//Este hace el CRUD haciendo el archivo.
public class ClienteLogica {
    //Referenciar a la fuente de datos.
    private final ClienteDatos store; //Almacén de datos.

    public ClienteLogica(String rutaArchivo) {
        this.store = new ClienteDatos(rutaArchivo);
    }

    //Funciones de lectura.
    public List<Cliente> findAll () {
        ClienteConector data = store.load(); //Obtenemos el XML.
        return data.getClientes().stream().map(ClienteMapper :: toModel).collect(Collectors.toList()); //Copiamos el XML al modelo y lo convertimos a lista.
    }

    public List<Cliente> findAllByParameters (String text) {
        ClienteConector data = store.load();
        return data.getClientes().stream().filter(c -> c.getNombre() == text || c.getIdentificacion() == text
                || c.getPrimerApellido() == text
                || c.getSegundoApellido() == text).map(ClienteMapper :: toModel).collect(Collectors.toList());
    }

    public Cliente create (Cliente nuevo) {
        //Todo este código debería estar dentro de un try-catch.
        ClienteConector data = store.load();

        //Validaciones principales, como una lista.
        //Validar que el cliene no se repita.
        //La edad.
        //La longitud de la ID.
        //Cualquier validación debería ir en la capa de Lógica.

        ClienteEntity clienteDatos = ClienteMapper.toXML(nuevo);
        data.getClientes().add(clienteDatos);
        store.save(data); //Escribir en el XML.
        return ClienteMapper.toModel(clienteDatos);
    }

    public Cliente update (Cliente actualizado) {
        //Todo este código debería estar dentro de un try-catch.
        //Validaciones.
        //Validar que no se eliminen datoos como fecha de nacimiento y otros.
        //La edad.
        //La longitud de la ID.

        ClienteConector data = store.load();
        for (int i = 0; i < data.getClientes().size(); i++) {
            ClienteEntity actual = data.getClientes().get(i);
            if (actual.getId() == actualizado.getId()) {
                //Encontramos le cliente a modificar y aplicamos los cambios.
                data.getClientes().set(i, ClienteMapper.toXML(actualizado));
                store.save(data);
                break;
            }
        }
        return actualizado;
    }

    public Boolean deleteById (int id) {
        //Todo este código debería estar dentro de un try-catch.
        //Que no sea 0 o número negativo.
        ClienteConector data = store.load();
        boolean eliminado = data.getClientes().removeIf(cliente -> cliente.getId() == id);
        if (eliminado) {
            store.save(data);
        }
        return eliminado;
    }


}
