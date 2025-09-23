package org.example.hotel_proyectoc3.Data;

import jakarta.xml.bind.*;
import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@XmlRootElement(name = "clientesData") //Raíz de mi archivo.
@XmlAccessorType(XmlAccessType.FIELD) //Convertir lo que venga del XML para que Java lo pueda entender.
public class ClienteConector {
    @XmlElementWrapper (name = "clientes") //Todo va a estar contenido en una etiqueta llamada cliente.
    @XmlElement(name = "Cliente") //Traer datos para convertirlos en datos de Java.

    private List<ClienteEntity> clientes = new ArrayList<>();

    public List<ClienteEntity> getClientes()
    {
        return clientes;
    }

    public void setClientes(List<ClienteEntity> clientes)
    {
        this.clientes = clientes;
    }
}
