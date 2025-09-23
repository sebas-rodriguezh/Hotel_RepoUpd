package org.example.hotel_proyectoc3.Data;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

//Se necesitan las 3 clases para cada Entidad que querramos guardar.
//Esta clase es la que gestiona la Base de Datos (El archivo XML) para la entidad Cliente.
public class ClienteDatos {

    private final Path xmlPath;
    private JAXBContext ctx; //Contexto del archivo.
    private ClienteConector cache;

    public ClienteDatos(String filePath) {
        try {
            this.xmlPath = Path.of(Objects.requireNonNull(filePath));
            this.ctx = JAXBContext.newInstance(ClienteConector.class, ClienteEntity.class);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //Abre el archivo.
    public synchronized ClienteConector load() { //Cada vez que haya un upd, lo pueda sync y llevarlo a presentación.
        try {
            if (cache != null) {
                return cache;
            }

            if (!Files.exists(xmlPath)) {
                cache = new ClienteConector();
                save(cache); //Crea un archivo vacío.
                return cache;
            }

            //Convierte XML a Java.
            Unmarshaller u = ctx.createUnmarshaller(); //Convertidor.

            //Gestiona la información convertida del archivo XML.
            cache = (ClienteConector) u.unmarshal(xmlPath.toFile()); //Tiene que convertir.

            //Esto es si el archivo está vacío.
            if (cache.getClientes() == null)
            {
                //Aquí creamos una primera instancia de clientes dentro del archivo.
                //Esto se aplicaría la primera vez que se corre el sistema.
                //o cuando se limpia la información de BD.
                cache.setClientes(new java.util.ArrayList<>());

            }
            return cache;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //Cargar los datos.
    public synchronized void save(ClienteConector data) {
        try {
            Marshaller m = ctx.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE); //Convertimos todas las propiedas sí o sí.
            m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            File out = xmlPath.toFile(); //Tome el XML path y hagalo archivo.
            File parent = out.getParentFile();

            if (parent != null) parent.mkdirs();

            java.io.StringWriter sw = new java.io.StringWriter();
            m.marshal(data, sw); //Pasa los datos a escritura.
            m.marshal(data, out); //Escribe los datos en el archivo.

            cache = data;
        }

        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public  Path getXmlPath() {
        return xmlPath;
    }
}
