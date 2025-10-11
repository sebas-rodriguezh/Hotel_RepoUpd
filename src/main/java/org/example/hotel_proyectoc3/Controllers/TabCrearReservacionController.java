package org.example.hotel_proyectoc3.Controllers;

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
import org.example.hotel_proyectoc3.Domain.Model.Habitacion;
import org.example.hotel_proyectoc3.Domain.Model.Reservacion;

import java.io.IOException;
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

    Cliente clienteSeleccionado = null;
    Habitacion habitacionSeleccionada = null;

    @FXML
    public void limpiarCamposReserva() {
        tbvResultadoBusquedaCliente.getItems().clear();
        tbvResultadoBusquedaHabitacion.getItems().clear();
        tbvResultadoBusquedaCliente.getSelectionModel().clearSelection();
        tbvResultadoBusquedaHabitacion.getSelectionModel().clearSelection();
        dtpFechaLlegada.setValue(null);
        dtpFechaSalida.setValue(null);
        spinnerCantidadNoches.getValueFactory().setValue(1);
        txtPrecioTotal.clear();
        txtOferta.clear();
        clienteSeleccionado = null;
        habitacionSeleccionada = null;
    }


    @FXML
    public void guardarReserva(ActionEvent actionEvent) {
        try
        {
            int cantidadNoches = spinnerCantidadNoches.getValue();
            LocalDate fechaLlegada = dtpFechaLlegada.getValue();
            LocalDate fechaSalida = fechaLlegada.plusDays(cantidadNoches);
            double oferta = 0.0;
            if (txtOferta.getText() != null && !txtOferta.getText().trim().isEmpty())
            {
                oferta = Double.parseDouble(txtOferta.getText());
            }

            if (camposValidos(cantidadNoches, fechaLlegada))
            {
                Reservacion reservacion = new Reservacion(clienteSeleccionado, habitacionSeleccionada, fechaLlegada, cantidadNoches, oferta);
                reservacion.setFechaSalida(fechaSalida);

                // ✅ 2. Actualizar el estado de la habitación a "Reservada"
                habitacionSeleccionada.setEstado(3); // 3 = Reservada

                // ✅ 3. Guardar la reservación en la base de datos
                // (Necesitarás ReservacionDatos similar a ClienteDatos)
                // ReservacionDatos reservacionStore = new ReservacionDatos();
                // reservacionStore.insert(reservacion);

                // ✅ 4. Actualizar la habitación en la base de datos
                // HabitacionDatos habitacionStore = new HabitacionDatos();
                // habitacionStore.update(habitacionSeleccionada);


                mostrarAlerta("Guardado", "Se guardó correctamente la reservación en el sistema.");
                limpiarCamposReserva();
            }
            else {
                mostrarAlerta("Error", "Todos los valores se requieren para poder guardar la reserva.");
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "No se puedo guardar la reserva" + e);
        }
    }

    private boolean camposValidos(int noches, LocalDate fechaLlegada) {
        return (noches > 0) && clienteSeleccionado != null && habitacionSeleccionada != null && fechaLlegada != null;
    }

    @FXML
    public void seleccionarCliente(ActionEvent actionEvent) {
        try {
            clienteSeleccionado = mostrarFormularioCliente();
            if (clienteSeleccionado != null)
            {
                tbvResultadoBusquedaCliente.getItems().clear();
                tbvResultadoBusquedaCliente.getItems().setAll(clienteSeleccionado);
            } else {
                mostrarAlerta("Error", "No se pudo seleccionar al cliente en el sistema.");
            }
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    private Cliente mostrarFormularioCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hotel_proyectoc3/UI/View/BuscarCliente.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Selección del cliente.");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            return (Cliente) stage.getUserData();
        } catch (IOException error) {
            mostrarAlerta("Error abriendo el formulario", error.getMessage());
            return null;
        }
    }

    @FXML
    public void seleccionarHabitacion() {
        try {
            habitacionSeleccionada = mostrarFormularioBusquedaHabitacion();
            if (habitacionSeleccionada != null)
            {
                tbvResultadoBusquedaHabitacion.getItems().clear();
                tbvResultadoBusquedaHabitacion.getItems().setAll(habitacionSeleccionada);
                calcularPrecioTotal();
            } else {
                mostrarAlerta("Error", "No se pudo seleccionar la habitación en el sistema.");
            }
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    private Habitacion mostrarFormularioBusquedaHabitacion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hotel_proyectoc3/UI/View/BuscarHabitacion.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Selección del cliente.");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            return (Habitacion) stage.getUserData();
        } catch (IOException error) {
            mostrarAlerta("Error abriendo el formulario", error.getMessage());
            return null;
        }
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
        dtpFechaSalida.setDisable(true);
        txtPrecioTotal.setDisable(true);
        configurarTablas();
        configurarListeners();
    }

    private void configurarListeners() {
        txtOferta.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.isEmpty()) {
                try {
                    Double.parseDouble(newVal);
                    calcularPrecioTotal();
                } catch (NumberFormatException e) {
                    txtOferta.setText(oldVal != null ? oldVal : "");
                }
            } else {
                calcularPrecioTotal();
            }
        });

        spinnerCantidadNoches.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                calcularPrecioTotal();
                calcularFechaSalida();
            }
        });

        dtpFechaLlegada.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                calcularPrecioTotal();
                calcularFechaSalida();

            }
        });
    }

    private void calcularFechaSalida() {
        if (dtpFechaLlegada.getValue() != null && spinnerCantidadNoches.getValue() != null)
        {
            dtpFechaSalida.setValue(dtpFechaLlegada.getValue().plusDays(spinnerCantidadNoches.getValue()));
        }
    }

    private void calcularPrecioTotal() {
        if (habitacionSeleccionada == null) return;

        try {
            double precio = habitacionSeleccionada.getPrecio() * spinnerCantidadNoches.getValue();
            if (!txtOferta.getText().trim().isEmpty()) {
                precio -= Double.parseDouble(txtOferta.getText());
            }
            txtPrecioTotal.setText(String.format("%.2f", Math.max(precio, 0)));
        } catch (Exception e) {
            txtPrecioTotal.setText("0.00");
        }
    }


    private void configurarTablas() {
        colIDCliente.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colFechaNacimientoCliente.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        colIdentificacionCliente.setCellValueFactory(new PropertyValueFactory<>("identificacion"));
        colTipoHabitacion.setCellValueFactory(new PropertyValueFactory<>("descripcionTipo"));
        colPrecioHabitacion.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colIDHabitacion.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEstadoHabitacion.setCellValueFactory(new PropertyValueFactory<>("descripcionEstado"));
        colCapacidadHabitacion.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        colDisponibleHabitacion.setCellValueFactory(new PropertyValueFactory<>("disponibleTexto"));
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

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}