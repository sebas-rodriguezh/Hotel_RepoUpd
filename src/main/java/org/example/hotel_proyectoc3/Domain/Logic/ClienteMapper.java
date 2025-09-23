package org.example.hotel_proyectoc3.Domain.Logic;

import org.example.hotel_proyectoc3.Data.ClienteEntity;
import org.example.hotel_proyectoc3.Domain.Model.Cliente;

public class ClienteMapper {

    public static ClienteEntity toXML(Cliente c)
    {
        if (c == null) {
            return null;
        }
        ClienteEntity cli = new ClienteEntity();
        cli.setId(c.getId());
        cli.setNombre(c.getNombre());
        cli.setPrimerApellido(c.getPrimerApellido());
        cli.setSegundoApellido(c.getSegundoApellido());
        cli.setIdentificacion(  c.getIdentificacion());
        cli.setFechaNacimiento(c.getFechaNacimiento());
        return cli;
    }

    public static Cliente toModel (ClienteEntity c) {
        if (c == null) {
            return null;
        }
        Cliente cli = new Cliente(0,c.getIdentificacion(),c.getNombre(),c.getPrimerApellido(),c.getFechaNacimiento());
        return cli;
    }

}
