package org.example.hotel_proyectoc3.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.hotel_proyectoc3.Domain.Logic.Hotel;
import org.example.hotel_proyectoc3.Domain.Model.Habitacion;
import org.example.hotel_proyectoc3.Domain.Logic.GestorHabitaciones;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BuscarHabitacionController implements Initializable {
    @FXML private Button btnSalir;
    @FXML private Button btnSeleccionar;
    @FXML private TextField txtValorBuscado;
    @FXML private ComboBox<String> comboBoxFiltro;
    @FXML private TableColumn<Habitacion, String> colDisponibleHabitacion;
    @FXML private TableColumn<Habitacion, Double> colPrecioHabitacion;
    @FXML private TableColumn<Habitacion, Integer> colCapacidadHabitacion;
    @FXML private TableColumn<Habitacion, String> colEstadoHabitacion;
    @FXML private TableColumn<Habitacion, String> colTipoHabitacion;
    @FXML private TableColumn<Habitacion, Integer> colNumeroHabitacion;
    @FXML private TableColumn<Habitacion, Integer> colIDHabitacion;
    @FXML private TableView<Habitacion> tbvResultadoBusquedaHabitacion;

    private final GestorHabitaciones gestorHabitaciones = Hotel.getInstance().getHabitaciones();
    private Habitacion habitacionSeleccionada;

    public BuscarHabitacionController() {
        Habitacion hab1 = new Habitacion(1, 101, 1, 1, 150000.0, 2);
        Habitacion hab2 = new Habitacion(2, 102, 2, 1, 250000.0, 4);
        Habitacion hab3 = new Habitacion(3, 201, 1, 2, 150000.0, 2);
        Habitacion hab4 = new Habitacion(4, 202, 2, 3, 250000.0, 3);
        Habitacion hab5 = new Habitacion(5, 301, 1, 4, 180000.0, 2);
        Habitacion hab6 = new Habitacion(6, 302, 2, 5, 300000.0, 5);

        gestorHabitaciones.insertarHabitacion(hab1);
        gestorHabitaciones.insertarHabitacion(hab2);
        gestorHabitaciones.insertarHabitacion(hab3);
        gestorHabitaciones.insertarHabitacion(hab4);
        gestorHabitaciones.insertarHabitacion(hab5);
        gestorHabitaciones.insertarHabitacion(hab6);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colIDHabitacion.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNumeroHabitacion.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colTipoHabitacion.setCellValueFactory(new PropertyValueFactory<>("descripcionTipo"));
        colEstadoHabitacion.setCellValueFactory(new PropertyValueFactory<>("descripcionEstado"));
        colPrecioHabitacion.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCapacidadHabitacion.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        colDisponibleHabitacion.setCellValueFactory(new PropertyValueFactory<>("disponibleTexto"));

        comboBoxFiltro.setItems(FXCollections.observableArrayList("Número", "ID", "Capacidad", "Tipo", "Estado", "Disponible"));
        comboBoxFiltro.setValue("Número");

        cargarTodasLasHabitaciones();

        txtValorBuscado.textProperty().addListener((obs, oldVal, newVal) -> {
            filtrarHabitaciones(newVal);
        });

        tbvResultadoBusquedaHabitacion.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    habitacionSeleccionada = newValue;
                    btnSeleccionar.setDisable(newValue == null);
                }
        );

        btnSeleccionar.setDisable(true);
    }

    private void cargarTodasLasHabitaciones() {
        try {
            List<Habitacion> habitaciones = gestorHabitaciones.getHabitaciones();
            tbvResultadoBusquedaHabitacion.setItems(FXCollections.observableArrayList(habitaciones));
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar las habitaciones: " + e.getMessage());
        }
    }

    private void filtrarHabitaciones(String texto) {
        if (texto == null || texto.isBlank()) {
            cargarTodasLasHabitaciones();
            return;
        }

        try {
            String filtro = texto.toLowerCase().trim();
            String tipo = comboBoxFiltro.getValue();

            List<Habitacion> filtradas = gestorHabitaciones.getHabitaciones().stream()
                    .filter(h -> switch (tipo) {
                        case "ID" -> String.valueOf(h.getId()).contains(filtro);
                        case "Número" -> String.valueOf(h.getNumero()).contains(filtro);
                        case "Capacidad" -> String.valueOf(h.getCapacidad()).contains(filtro);
                        case "Tipo" -> h.getDescripcionTipo().toLowerCase().contains(filtro);
                        case "Estado" -> h.getDescripcionEstado().toLowerCase().contains(filtro);
                        case "Disponible" -> (h.getDisponible() && filtro.matches(".*(sí|si).*")) ||
                                (!h.getDisponible() && filtro.contains("no"));
                        default -> String.valueOf(h.getNumero()).contains(filtro) ||
                                String.valueOf(h.getId()).contains(filtro) ||
                                h.getDescripcionTipo().toLowerCase().contains(filtro);
                    })
                    .collect(Collectors.toList());

            tbvResultadoBusquedaHabitacion.setItems(FXCollections.observableArrayList(filtradas));
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al filtrar habitaciones: " + e.getMessage());
        }
    }

    @FXML
    private void volverAAnterior(ActionEvent actionEvent) {
        cerrarVentana();
    }

    @FXML
    private void seleccionarHabitacion(ActionEvent actionEvent) {
        if (habitacionSeleccionada == null) {
            mostrarAlerta("Seleccione Habitación", "Debe seleccionar una habitación.");
            return;
        }

        try {
            // Aquí puedes agregar la lógica para pasar la habitación seleccionada al controlador padre
            // if (controllerPadre != null) {
            //     controllerPadre.setHabitacionSeleccionada(habitacionSeleccionada);
            // }
            cerrarVentana();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al seleccionar habitación: " + e.getMessage());
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

    public Habitacion getHabitacionSeleccionada() {
        return habitacionSeleccionada;
    }
}