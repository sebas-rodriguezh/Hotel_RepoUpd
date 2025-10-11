package org.example.hotel_proyectoc3.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.hotel_proyectoc3.Domain.Logic.Hotel;
import org. example.hotel_proyectoc3.Domain.Model.Cliente;
import org.example.hotel_proyectoc3.Domain.Logic.GestorClientes;



import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BuscarClienteController implements Initializable {
    @FXML private Button btnSalir;
    @FXML private Button btnSeleccionar;
    @FXML private TextField txtValorBuscado;
    @FXML private ComboBox<String> comboBoxFiltro;
    @FXML private TableColumn<Cliente, String> colIdentificacionCliente;
    @FXML private TableColumn<Cliente, LocalDate> colFechaNacimientoCliente;
    @FXML private TableColumn<Cliente, String> colNombreCliente;
    @FXML private TableColumn<Cliente, String> colIDCliente;
    @FXML private TableView<Cliente> tbvResultadoBCliente;

    private final GestorClientes gestorClientes = Hotel.getInstance().getClientes();
    private Cliente ClienteSeleccionado;
    //private TabPrescibirController controllerPadre;

    public BuscarClienteController() {
        Cliente client1 = new Cliente(1001, "1234", "Juan", "Herrera", LocalDate.of(1990, 5, 15));
        Cliente client2 = new Cliente(1002, "2345", "Maria", "Gonzalez", LocalDate.of(1985, 8, 22));
        Cliente client3 = new Cliente(1003, "3456", "Carlos", "Martinez", LocalDate.of(1998, 3, 10));
        Cliente client4 = new Cliente(1004, "4567", "Ana", "Lopez", LocalDate.of(1992, 11, 30));
        Cliente client5 = new Cliente(1005, "5678", "Luis", "Ramirez", LocalDate.of(1987, 7, 18));

        gestorClientes.insertarCliente(client1,false);
        gestorClientes.insertarCliente(client2,false);
        gestorClientes.insertarCliente(client3,false);
        gestorClientes.insertarCliente(client4,false);
        gestorClientes.insertarCliente(client5,false);
    }

//    public void setControllerPadre(TabPrescibirController controller) {
//        this.controllerPadre = controller;
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colIDCliente.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colFechaNacimientoCliente.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        colIdentificacionCliente.setCellValueFactory(new PropertyValueFactory<>("identificacion"));

        comboBoxFiltro.setItems(FXCollections.observableArrayList("ID", "Nombre", "Identificación"));
        comboBoxFiltro.setValue("Nombre");

        cargarTodosLosClientes();

        txtValorBuscado.textProperty().addListener((obs, oldVal, newVal) -> {
            filtrarClientes(newVal);
        });

        tbvResultadoBCliente.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    ClienteSeleccionado = newValue;
                    btnSeleccionar.setDisable(newValue == null);
                }
        );

        btnSeleccionar.setDisable(true);
    }

    private void cargarTodosLosClientes() {
        try {
            List<Cliente> Clientes = gestorClientes.getClientes(); //Cae del bd por ahora vacío.
            tbvResultadoBCliente.setItems(FXCollections.observableArrayList(Clientes));
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar los Clientes: " + e.getMessage());
        }
    }

    private void filtrarClientes(String texto) {
        if (texto == null || texto.isBlank()) {
            cargarTodosLosClientes();
            return;
        }

        try {
            String filtro = texto.toLowerCase().trim();
            String tipoFiltro = comboBoxFiltro.getValue();

            List<Cliente> todosLosClientes = gestorClientes.getClientes();
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

            tbvResultadoBCliente.setItems(FXCollections.observableArrayList(filtrados));
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al filtrar Clientes: " + e.getMessage());
        }
    }

    @FXML
    private void volverAAnterior(ActionEvent actionEvent) {
        cerrarVentana();
    }

    @FXML
    private void seleccionarCliente(ActionEvent actionEvent) {
        if (ClienteSeleccionado == null) {
            mostrarAlerta("Seleccione un Cliente", "Debe seleccionar un Cliente.");
            return;
        }

        try {
            Stage stage = (Stage) comboBoxFiltro.getScene().getWindow();
            stage.setUserData(ClienteSeleccionado);
            cerrarVentana();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al seleccionar cliente: " + e.getMessage());
        }
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}