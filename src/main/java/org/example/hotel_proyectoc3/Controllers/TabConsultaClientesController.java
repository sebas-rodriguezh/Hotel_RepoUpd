package org.example.hotel_proyectoc3.Controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.hotel_proyectoc3.Domain.Model.Cliente;
import org.example.hotel_proyectoc3.Domain.Logic.ClienteLogica;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TabConsultaClientesController implements Initializable {

    @FXML private ComboBox <String> comboBoxCriteroFiltro;
    @FXML private Button btnBuscar;
    @FXML private Button btnEliminarClienteSeleccionado;
    @FXML private Button btnModificarClienteSeleccionado;
    @FXML private Button btnInsertarCliente;
    @FXML private TextField txtFiltroCliente;
    @FXML private TableColumn<Cliente, String> colNombreCliente;
    @FXML private TableColumn <Cliente, String> colIdentificacionCliente;
    @FXML private TableColumn <Cliente, Integer> colIdCliente;
    @FXML private TableView <Cliente> tblClientes;

    private final ObservableList<Cliente> listaClientes = FXCollections.observableArrayList(); //Esta es la que debe de veni  de logica y repo.

    private static final String RUTA_CLIENTE = java.nio.file.Paths.get(System.getProperty("user.dir"),"bd","clientes.xml").toString();
    private final ClienteLogica clienteLogica = new ClienteLogica(RUTA_CLIENTE);





    @FXML
    public void insertarCliente(ActionEvent actionEvent) {
        Cliente nuevoCliente = mostrarFormulario(null, Boolean.valueOf(false)); //Llamamos los datos de la nueva pestaña formulario-cliente-view.fxml
        if (nuevoCliente != null) {
            nuevoCliente.setId(listaClientes.getLast().getId() + 1);

            for (Cliente c: listaClientes)
            {
                if (c.getIdentificacion().equals(nuevoCliente.getIdentificacion())) {
                    mostrarAlerta("El cliente ya existe", "Digite un ID valido.");
                    return;
                }
            }
            //Primero enviamos a escribir a la base de datos.
            clienteLogica.create(nuevoCliente);
            //Luego cargamos la información en memoria.
            listaClientes.add(nuevoCliente);
        }
    }

    @FXML
    private Cliente mostrarFormulario(Cliente client, Boolean editar) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hotel_proyectoc3/UI/View/ClienteFXML.fxml"));
            Parent root = loader.load(); //Se levanta la ventana y se dice ser padre.


            //Vamos a llamar a la clase del FormularioClienteController, desde ahí seteamos la info recolectada del cliente que vamos a agregar.
            //Para eso necesitamos el método respectivo en FormularioClienteController.
            addClienteController controller = loader.getController();
            controller.setCliente(client, editar);
            Stage stage = new Stage(); //Voy a presentar la pestaña...
            stage.setTitle(editar ? "Modificar Cliente" : "Agregar Cliente");
            stage.setScene(new Scene(root)); //Es inicio-view.fxml
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            return (Cliente) stage.getUserData(); //De este Stage, voy a traerme lo que trajo el llenado de la otra pestaña y construyo los datos.
        } catch (IOException error) {
            mostrarAlerta("Error abriendo el formulario", error.getMessage());
            return null;
        }
    }



    private void mostrarAlerta(String titulo, String mensaje) { //Todas las alertas que dropiemos, se van a lanzar con este método.

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void modificarClienteSeleccionado(ActionEvent actionEvent) {
        Cliente clienteSeleccionado = tblClientes.getSelectionModel().getSelectedItem();

        if (clienteSeleccionado == null) {
            mostrarAlerta("Seleccione un cliente", "Debe seleccionar un cliente.");
            return;
        }

        Cliente modificado = mostrarFormulario(clienteSeleccionado, Boolean.valueOf(true));

        if (modificado != null) {
            clienteLogica.update(clienteSeleccionado);
            tblClientes.refresh();
        }
    }


    @FXML
    public void eliminarClienteSeleccionado(ActionEvent actionEvent) {
        try {
            Cliente clienteSeleccionado = tblClientes.getSelectionModel().getSelectedItem();
            if (clienteSeleccionado == null) {
                mostrarAlerta("Seleccione un cliente", "Seleccione un cliente.");
                return;
            }

            clienteLogica.deleteById(clienteSeleccionado.getId());
            listaClientes.remove(clienteSeleccionado);
        }

        catch (Error error) {
            mostrarAlerta("Error eliminando el cliente", error.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colIdCliente.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colIdentificacionCliente.setCellValueFactory(new PropertyValueFactory<>("identificacion"));

        listaClientes.addAll(
                new Cliente(1111, "12345", "Sebas", "Rodriguez", LocalDate.of(2001,12,18)),
                new Cliente(2222, "54332", "Celeste", "Chinchilla", LocalDate.of(2002,11,20)),
                new Cliente(3333, "224354", "Leticia", "Hernandez", LocalDate.of(1997,7,6)),
                new Cliente(4444, "454434", "Minor", "Rodriguez", LocalDate.of(1992,6,15))
        );

        listaClientes.addAll(clienteLogica.findAll());
        tblClientes.setItems(listaClientes);

    }

    @FXML
    public void buscarCliente(ActionEvent actionEvent) {
        try {
            String criterio = txtFiltroCliente.getText().trim().toLowerCase();
            if (criterio.isEmpty()) {
                tblClientes.setItems(listaClientes);
                return;
            }

//            ObservableList<Cliente> filtrados = listaClientes.stream()
//                    .filter(c -> c.getIdentificacion().toLowerCase().contains(criterio)
//                            || c.getNombre().toLowerCase().contains(criterio)
//                            || c.getPrimerApellido().toLowerCase().contains(criterio))
//                    .collect(Collectors.toCollection(FXCollections::observableArrayList));


            listaClientes.addAll(clienteLogica.findAllByParameters(criterio));
            tblClientes.setItems(listaClientes);

        } catch (Exception error) {
            mostrarAlerta("Error buscando el cliente", error.getMessage());
        }
    }
}
