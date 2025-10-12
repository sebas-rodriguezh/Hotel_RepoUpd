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
import org.example.hotel_proyectoc3.Domain.Logic.Hotel;
import org.example.hotel_proyectoc3.Domain.Model.Cliente;
import org.example.hotel_proyectoc3.Domain.Logic.ClienteLogica;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
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

    private final ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
    private final ClienteLogica clienteLogica = Hotel.getInstance().getClientes();



    @FXML
    public void insertarCliente(ActionEvent actionEvent) throws SQLException {
        Cliente nuevoCliente = mostrarFormulario(null, Boolean.valueOf(false));
        if (nuevoCliente != null) {

            for (Cliente c: listaClientes)
            {
                if (c.getIdentificacion().equals(nuevoCliente.getIdentificacion())) {
                    mostrarAlerta("El cliente ya existe", "Digite un ID valido.");
                    return;
                }
            }

            Cliente clienteConId = clienteLogica.create(nuevoCliente);
            if (clienteConId != null) {
                listaClientes.add(clienteConId);
            }
        }
    }

    @FXML
    private Cliente mostrarFormulario(Cliente client, Boolean editar) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hotel_proyectoc3/UI/View/ClienteFXML.fxml"));
            Parent root = loader.load();
            addClienteController controller = loader.getController();
            controller.setCliente(client, editar);
            Stage stage = new Stage();
            stage.setTitle(editar ? "Modificar Cliente" : "Agregar Cliente");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            return (Cliente) stage.getUserData();
        } catch (IOException error) {
            mostrarAlerta("Error abriendo el formulario", error.getMessage());
            return null;
        }
    }



    private void mostrarAlerta(String titulo, String mensaje) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void modificarClienteSeleccionado(ActionEvent actionEvent) throws SQLException {
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

        catch (Error | SQLException error) {
            mostrarAlerta("Error eliminando el cliente", error.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colIdCliente.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colIdentificacionCliente.setCellValueFactory(new PropertyValueFactory<>("identificacion"));

        try {
            listaClientes.addAll(clienteLogica.findAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tblClientes.setItems(listaClientes);
        comboBoxCriteroFiltro.setItems(FXCollections.observableArrayList("ID", "Nombre", "Identificación"));
        comboBoxCriteroFiltro.setValue("ID");
    }

    @FXML
    public void buscarCliente(ActionEvent actionEvent) throws SQLException {
        listaClientes.clear();
        if (txtFiltroCliente == null || txtFiltroCliente.getText().isEmpty()) {
            listaClientes.addAll(clienteLogica.findAll());
            return;
        }

        try {
            String filtro = txtFiltroCliente.getText().toLowerCase().trim();
            String tipoFiltro = comboBoxCriteroFiltro.getValue();

            List<Cliente> todosLosClientes = clienteLogica.findAll();
            List<Cliente> filtrados = todosLosClientes.stream()
                    .filter(p -> {
                        switch (tipoFiltro) {
                            case "ID":
                                return String.valueOf(p.getId()).contains(filtro);
                            case "Nombre":
                                return p.getNombre().toLowerCase().contains(filtro);
                            case "Identificación":
                                return String.valueOf(p.getIdentificacion()).contains(filtro);
                            default:
                                return p.getNombre().toLowerCase().contains(filtro) ||
                                        String.valueOf(p.getId()).contains(filtro) ||
                                        String.valueOf(p.getIdentificacion()).contains(filtro);
                        }
                    })
                    .collect(Collectors.toList());
            listaClientes.setAll(filtrados);
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al filtrar Clientes: " + e.getMessage());
        }
    }
}