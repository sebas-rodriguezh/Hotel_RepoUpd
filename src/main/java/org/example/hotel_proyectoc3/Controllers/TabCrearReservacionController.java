package org.example.hotel_proyectoc3.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.hotel_proyectoc3.Domain.Model.Cliente;
import org.example.hotel_proyectoc3.Domain.Model.Habitacion;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TabCrearReservacionController implements Initializable {
    @FXML private TextField txtPrecioTotal;
    @FXML private Button btnLimpiarCampos;
    @FXML private Button btnGuardarReserva;
    @FXML private TableColumn <Habitacion, String> colDisponibleHabitacion;
    @FXML private TableColumn <Habitacion, Double> colPrecioHabitacion;
    @FXML private TableColumn <Habitacion, Integer> colCapacidadHabitacion;
    @FXML private TableColumn <Habitacion, String> colEstadoHabitacion;
    @FXML private TableColumn <Habitacion, String> colTipoHabitacion;
    @FXML private TableColumn <Habitacion, Integer> colIDHabitacion;
    @FXML private TableView <Habitacion> tbvResultadoBusquedaHabitacion;
    @FXML private TableView <Cliente> tbvResultadoBusquedaCliente;
    @FXML private TableColumn<Cliente, String> colIdentificacionCliente;
    @FXML private TableColumn<Cliente, LocalDate> colFechaNacimientoCliente;
    @FXML private TableColumn<Cliente, String> colNombreCliente;
    @FXML private TableColumn<Cliente, String> colIDCliente;
    @FXML private DatePicker dtpFechaSalida;
    @FXML private TextField txtOferta;
    @FXML private Button btnSeleccionarHabitacion;
    @FXML private Button btnSeleccionarCliente;
    @FXML private DatePicker dtpFechaLlegada;
    @FXML private Spinner <Integer> spinnerCantidadNoches;

    @FXML
    public void limpiarCamposReserva(ActionEvent actionEvent) {
        tbvResultadoBusquedaCliente.getSelectionModel().clearSelection();
        tbvResultadoBusquedaHabitacion.getSelectionModel().clearSelection();
        dtpFechaLlegada.setValue(null);
        dtpFechaSalida.setValue(null);
        spinnerCantidadNoches.getValueFactory().setValue(1);
        txtPrecioTotal.clear();
        txtOferta.clear();
    }

    @FXML
    public void guardarReserva(ActionEvent actionEvent) {
    }

    @FXML
    public void seleccionarCliente(ActionEvent actionEvent) {
    }

    @FXML
    public void seleccionarHabitacion(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarSpinnerNoches();

        txtOferta.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.isEmpty()) {
                try {
                    Double.parseDouble(newVal);
                } catch (NumberFormatException e) {
                    txtOferta.setText(oldVal != null ? oldVal : "");
                }
            }
        });
    }

    private void configurarSpinnerNoches() {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 1);
        spinnerCantidadNoches.setValueFactory(valueFactory);
        spinnerCantidadNoches.setEditable(true);
        spinnerCantidadNoches.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue != null && !newValue.isEmpty()) {
                    int value = Integer.parseInt(newValue);
                    if (value < 1) {
                        spinnerCantidadNoches.getValueFactory().setValue(1);
                    } else if (value > 30) {
                        spinnerCantidadNoches.getValueFactory().setValue(30);
                    }
                }
            } catch (NumberFormatException e) {
                spinnerCantidadNoches.getEditor().setText(oldValue);
            }
        });
    }
}