package org.example.hotel_proyectoc3.Controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import org.example.hotel_proyectoc3.Domain.Logic.ReservacionLogica;
import org.example.hotel_proyectoc3.Domain.Model.Cliente;
import org.example.hotel_proyectoc3.Domain.Model.Habitacion;
import org.example.hotel_proyectoc3.Domain.Model.Reservacion;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TabConsultaReservacionesController implements Initializable {
    @FXML private Button btnBorrarReserva;
    @FXML private Button btnModificarReserva;
    @FXML private Button btnInsertarReserva;
    @FXML private TableColumn <Reservacion, Double> colPrecioTotalReservacion;
    @FXML private TableColumn <Reservacion, LocalDate> colFechaSalida;
    @FXML private TableColumn <Reservacion, LocalDate> colFechaLlegada;
    @FXML private TableColumn <Reservacion, Integer> colNumeroHabitacion;
    @FXML private TableColumn <Reservacion, String> colNombreCliente;
    @FXML private TableColumn <Reservacion, Integer> idReservacion;
    @FXML private TableView <Reservacion> tbvResultadoBusquedaReservacion;
    @FXML private Button btnBuscarReserva;
    @FXML private TextField txtBusquedaReserva;
    @FXML private ComboBox <String> comboBoxCriteroFiltro;

    private final ObservableList<Reservacion> listaReservaciones = FXCollections.observableArrayList();
    private final ReservacionLogica reservacionLogica = Hotel.getInstance().getReservaciones();

    @FXML
    public void borrarReserva(ActionEvent actionEvent) {
        try {
            Reservacion reservacionSeleccionada = tbvResultadoBusquedaReservacion.getSelectionModel().getSelectedItem();
            if (reservacionSeleccionada == null) {
                mostrarAlerta("Error", "Seleccione una reservación para eliminar");
                return;
            }
            reservacionSeleccionada.getHabitacion().setEstado(1);
            Hotel.getInstance().getHabitaciones().update(reservacionSeleccionada.getHabitacion());
            boolean eliminado = reservacionLogica.deleteById(reservacionSeleccionada.getIdReservacion());
            if (eliminado) {
                cargarReservaciones();
                mostrarAlerta("Éxito", "Reservación eliminada");
            } else {
                mostrarAlerta("Error", "No se pudo eliminar la reservación");
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al eliminar: " + e.getMessage());
        }
    }



    @FXML
    public void modificarReserva(ActionEvent actionEvent) {
    }


    @FXML
    public void insertarReserva(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hotel_proyectoc3/UI/View/TabCrearReservacion.fxml"));
            Parent root = loader.load(); //Se levanta la ventana y se dice ser padre.


            //Vamos a llamar a la clase del FormularioClienteController, desde ahí seteamos la info recolectada del cliente que vamos a agregar.
            //Para eso necesitamos el método respectivo en FormularioClienteController.
//            addClienteController controller = loader.getController();
//            controller.setCliente(client, editar);
            Stage stage = new Stage(); //Voy a presentar la pestaña...
            //stage.setTitle(editar ? "Modificar Cliente" : "Agregar Cliente");
            stage.setScene(new Scene(root)); //Es inicio-view.fxml
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            cargarReservaciones();
            //return (Cliente) stage.getUserData(); //De este Stage, voy a traerme lo que trajo el llenado de la otra pestaña y construyo los datos.
        } catch (IOException error) {
            mostrarAlerta("Error abriendo el formulario", error.getMessage());
            //return null;
        }
    }

    @FXML
    public void buscarReserva(ActionEvent actionEvent) {
        try {
            String texto = txtBusquedaReserva.getText().trim();
            if (texto.isEmpty()) {
                cargarReservaciones();
            } else {
                listaReservaciones.setAll(reservacionLogica.findAllByParameters(texto));
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error en búsqueda: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cargarReservaciones() {
        try {
            listaReservaciones.setAll(reservacionLogica.findAll());
        } catch (Exception e) {
            mostrarAlerta("Error", "Error cargando reservaciones: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarTabla();
        cargarReservaciones();

        comboBoxCriteroFiltro.getItems().addAll("Todos", "Por Cliente", "Por Habitación");
        comboBoxCriteroFiltro.setValue("Todos");

        tbvResultadoBusquedaReservacion.setItems(listaReservaciones);
    }


    private void configurarTabla() {
        idReservacion.setCellValueFactory(new PropertyValueFactory<>("idReservacion"));
        colNombreCliente.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCliente().getNombre()));
        colNumeroHabitacion.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getHabitacion().getNumero()).asObject());
        colFechaLlegada.setCellValueFactory(new PropertyValueFactory<>("fechaLlegada"));
        colFechaSalida.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));
        colPrecioTotalReservacion.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
    }
}
