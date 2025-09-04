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
import org.example.hotel_proyectoc3.Domain.Model.Habitacion;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TabConsultaHabitacionesController implements Initializable {

    @FXML private Button btnInsertarHabitacion;
    @FXML private Button btnModificarHabitacion;
    @FXML private Button btnEliminarHabitacion;
    @FXML private TextField txtFiltroHabitaciones;
    @FXML private TableColumn<Habitacion, Integer> colNumeroHabitacion;
    @FXML private TableColumn<Habitacion, String> colTipoHabitacion;
    @FXML private TableColumn<Habitacion, Double> colPrecioHabitacion;
    @FXML private TableView<Habitacion> tblHabitaciones;

    private final ObservableList<Habitacion> listaHabitaciones = FXCollections.observableArrayList();

    @FXML
    public void insertarHabitacion(ActionEvent actionEvent) {
        Habitacion nuevaHabitacion = mostrarFormulario(null, false);
        if (nuevaHabitacion != null) {
            // Verificar si ya existe una habitación con el mismo ID o número
            for (Habitacion h : listaHabitaciones) {
                if (h.getId() == nuevaHabitacion.getId() || h.getNumero() == nuevaHabitacion.getNumero()) {
                    mostrarAlerta("Habitación ya existe", "Ya existe una habitación con ese ID o número.");
                    return;
                }
            }
            listaHabitaciones.add(nuevaHabitacion);
        }
    }

    @FXML
    public void modificarHabitacion(ActionEvent actionEvent) {
        Habitacion habitacionSeleccionada = tblHabitaciones.getSelectionModel().getSelectedItem();

        if (habitacionSeleccionada == null) {
            mostrarAlerta("Seleccione una habitación", "Debe seleccionar una habitación.");
            return;
        }

        Habitacion modificada = mostrarFormulario(habitacionSeleccionada, true);

        if (modificada != null) {
            tblHabitaciones.refresh();
        }
    }

    @FXML
    public void eliminarHabitacion(ActionEvent actionEvent) {
        try {
            Habitacion habitacionSeleccionada = tblHabitaciones.getSelectionModel().getSelectedItem();
            if (habitacionSeleccionada == null) {
                mostrarAlerta("Seleccione una habitación", "Seleccione una habitación.");
                return;
            }
            listaHabitaciones.remove(habitacionSeleccionada);
        } catch (Exception error) {
            mostrarAlerta("Error eliminando la habitación", error.getMessage());
        }
    }

    private Habitacion mostrarFormulario(Habitacion habitacion, Boolean editar) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hotel_proyectoc3/UI/View/HabitacionFXML.fxml"));
            Parent root = loader.load();

            addHabitacionController controller = loader.getController();
            controller.setHabitacion(habitacion, editar);

            Stage stage = new Stage();
            stage.setTitle(editar ? "Modificar Habitación" : "Agregar Habitación");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            return (Habitacion) stage.getUserData();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            // Configurar las columnas de la tabla
            colNumeroHabitacion.setCellValueFactory(new PropertyValueFactory<>("numero"));
            colTipoHabitacion.setCellValueFactory(new PropertyValueFactory<>("descripcionTipo"));
            colPrecioHabitacion.setCellValueFactory(new PropertyValueFactory<>("precio"));

            // Agregar datos de ejemplo
            listaHabitaciones.addAll(
                    new Habitacion(1, 101, 1, 1, 150.0, 2),
                    new Habitacion(2, 102, 2, 1, 100.0, 2),
                    new Habitacion(3, 201, 1, 1, 200.0, 4),
                    new Habitacion(4, 202, 2, 1, 120.0, 3)
            );

            // Asignar descripciones a las habitaciones de ejemplo
            for (Habitacion habitacion : listaHabitaciones) {
                asignarDescripciones(habitacion);
            }

            tblHabitaciones.setItems(listaHabitaciones);
        });
    }

    private void asignarDescripciones(Habitacion habitacion) {
        // Asignar descripción del tipo
        switch (habitacion.getTipo()) {
            case 1 -> habitacion.setDescripcionTipo("Suite");
            case 2 -> habitacion.setDescripcionTipo("Standard");
            default -> habitacion.setDescripcionTipo("Desconocido");
        }

        // Asignar descripción del estado
        switch (habitacion.getEstado()) {
            case 1 -> habitacion.setDescripcionEstado("Libre");
            case 2 -> habitacion.setDescripcionEstado("Clausurado");
            case 3 -> habitacion.setDescripcionEstado("Reservado");
            case 4 -> habitacion.setDescripcionEstado("Mantenimiento");
            default -> habitacion.setDescripcionEstado("Desconocido");
        }
    }

    @FXML
    public void buscarHabitacion(ActionEvent actionEvent) {
        try {
            String criterio = txtFiltroHabitaciones.getText().trim().toLowerCase();
            if (criterio.isEmpty()) {
                tblHabitaciones.setItems(listaHabitaciones);
                return;
            }

            ObservableList<Habitacion> filtradas = listaHabitaciones.stream()
                    .filter(h -> String.valueOf(h.getNumero()).toLowerCase().contains(criterio) ||
                            h.getDescripcionTipo().toLowerCase().contains(criterio) ||
                            String.valueOf(h.getPrecio()).toLowerCase().contains(criterio))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));

            tblHabitaciones.setItems(filtradas);
        } catch (Exception error) {
            mostrarAlerta("Error buscando la habitación", error.getMessage());
        }
    }
}