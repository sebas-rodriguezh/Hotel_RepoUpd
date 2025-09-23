package org.example.hotel_proyectoc3.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.hotel_proyectoc3.Domain.Model.Reservacion;

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
    }


    @FXML
    public void buscarReserva(ActionEvent actionEvent) {

    }
}
