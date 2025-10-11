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
import org.example.hotel_proyectoc3.Domain.Logic.ClienteLogica;
import org.example.hotel_proyectoc3.Domain.Logic.HabitacionLogica;
import org.example.hotel_proyectoc3.Domain.Logic.Hotel;
import org.example.hotel_proyectoc3.Domain.Model.Cliente;
import org.example.hotel_proyectoc3.Domain.Model.Habitacion;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class

TabConsultaHabitacionesController implements Initializable {
    @FXML private ComboBox <String> comboBoxCriteroFiltro;
    @FXML private Button btnInsertarHabitacion;
    @FXML private Button btnModificarHabitacion;
    @FXML private Button btnEliminarHabitacion;
    @FXML private TextField txtFiltroHabitaciones;
    @FXML private TableColumn<Habitacion, Integer> colNumeroHabitacion;
    @FXML private TableColumn <Habitacion, Integer> colIDHabitacion;
    @FXML private TableColumn<Habitacion, String> colTipoHabitacion;
    @FXML private TableColumn<Habitacion, Double> colPrecioHabitacion;
    @FXML private TableView<Habitacion> tblHabitaciones;

    private final ObservableList<Habitacion> listaHabitaciones = FXCollections.observableArrayList();
    private final HabitacionLogica habitacionLogica = Hotel.getInstance().getHabitaciones();


    @FXML
    public void insertarHabitacion(ActionEvent actionEvent) {
        try {
            Habitacion nuevaHabitacion = mostrarFormulario(null, false);
            if (nuevaHabitacion != null) {
                // Recargar datos desde la base de datos
                cargarHabitacionesDesdeBD();
                mostrarAlerta("Éxito", "Habitación creada correctamente");
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo crear la habitación: " + e.getMessage());
        }
    }

    @FXML
    public void modificarHabitacion(ActionEvent actionEvent) {
        try {
            Habitacion habitacionSeleccionada = tblHabitaciones.getSelectionModel().getSelectedItem();

            if (habitacionSeleccionada == null) {
                mostrarAlerta("Seleccione una habitación", "Debe seleccionar una habitación.");
                return;
            }

            Habitacion modificada = mostrarFormulario(habitacionSeleccionada, true);

            if (modificada != null) {
                // Recargar datos desde la base de datos
                cargarHabitacionesDesdeBD();
                mostrarAlerta("Éxito", "Habitación actualizada correctamente");
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo modificar la habitación: " + e.getMessage());
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
            boolean eliminado = habitacionLogica.deleteById(habitacionSeleccionada.getId());
            if (eliminado) {
                cargarHabitacionesDesdeBD();
                mostrarAlerta("Éxito", "Habitación eliminada correctamente");
            } else {
                mostrarAlerta("Error", "No se pudo eliminar la habitación");
            }

        } catch (Exception error) {
            mostrarAlerta("Error eliminando la habitación", error.getMessage());
        }
    }

    private void cargarHabitacionesDesdeBD() {
        try {
            listaHabitaciones.clear();
            listaHabitaciones.addAll(habitacionLogica.findAll());
            tblHabitaciones.setItems(listaHabitaciones);
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudieron cargar las habitaciones: " + e.getMessage());
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
        configurarColumnasTabla();
        cargarHabitacionesDesdeBD();
    }

    private void configurarColumnasTabla() {
        colIDHabitacion.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNumeroHabitacion.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colTipoHabitacion.setCellValueFactory(new PropertyValueFactory<>("descripcionTipo"));
        colPrecioHabitacion.setCellValueFactory(new PropertyValueFactory<>("precio"));
    }

    @FXML
    public void buscarHabitacion(ActionEvent actionEvent) {
        try {
            String textoBusqueda = txtFiltroHabitaciones.getText().trim();

            if (textoBusqueda.isEmpty()) {
                cargarHabitacionesDesdeBD();
                return;
            }
            listaHabitaciones.clear();
            listaHabitaciones.addAll(habitacionLogica.findAllByParameters(textoBusqueda));
            tblHabitaciones.setItems(listaHabitaciones);

        } catch (Exception error) {
            mostrarAlerta("Error buscando la habitación", error.getMessage());
        }
    }
}