package org.example.hotel_proyectoc3.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.hotel_proyectoc3.Domain.Model.Cliente;
import org.example.hotel_proyectoc3.Domain.Model.Reservacion;

import java.io.IOException;
import java.time.LocalDate;

public class TabConsultaReservacionesController {
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


    @FXML
    public void borrarReserva(ActionEvent actionEvent) {
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

            //return (Cliente) stage.getUserData(); //De este Stage, voy a traerme lo que trajo el llenado de la otra pestaña y construyo los datos.
        } catch (IOException error) {
            mostrarAlerta("Error abriendo el formulario", error.getMessage());
            //return null;
        }
    }

    @FXML
    public void buscarReserva(ActionEvent actionEvent) {

    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
