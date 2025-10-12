package org.example.hotel_proyectoc3.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.hotel_proyectoc3.Domain.Logic.HabitacionLogica;
import org.example.hotel_proyectoc3.Domain.Logic.Hotel;
import org.example.hotel_proyectoc3.Domain.Model.Habitacion;

import java.net.URL;
import java.util.ResourceBundle;

import static org.example.hotel_proyectoc3.Domain.Model.Habitacion.convertirEstadoACodigo;
import static org.example.hotel_proyectoc3.Domain.Model.Habitacion.convertirTipoACodigo;

public class addHabitacionController implements Initializable {

    @FXML private Button btnGuardarHabitacion;
    @FXML private Spinner <Integer> spinnerCapacidadHabitacion;
    @FXML private ComboBox <String> comboBoxEstadoHabitacion;
    @FXML private ComboBox <String> comboBoxTipoHabitacion;
    @FXML private Button btnGuardar;
    @FXML private TextField txtNumeroHabitacion;
    @FXML private TextField txtPrecio;

    private Habitacion habitacion;
    private Boolean modificacion = false;

    private final HabitacionLogica  habitacionLogica = Hotel.getInstance().getHabitaciones();

    @FXML
    public void guardarHabitacion(ActionEvent actionEvent) {
        try {
            String numeroText = txtNumeroHabitacion.getText().trim();
            String precioText = txtPrecio.getText().trim();
            String tipoSeleccionado = comboBoxTipoHabitacion.getValue();
            String estadoSeleccionado = comboBoxEstadoHabitacion.getValue();

            if (camposInvalidos(numeroText, precioText, tipoSeleccionado, estadoSeleccionado)) {
                mostrarAlerta("Campos Incompletos", "Debe llenar todos los campos del formulario.");
                return;
            }

            int numero = Integer.parseInt(numeroText);
            double precio = Double.parseDouble(precioText);
            int capacidad = spinnerCapacidadHabitacion.getValue();

            int tipo = convertirTipoACodigo(tipoSeleccionado);
            int estado = convertirEstadoACodigo(estadoSeleccionado);

            Habitacion resultado = null;

            if (!modificacion) {
                // Crear nueva habitación
                Habitacion nuevaHabitacion = new Habitacion(numero, tipo, estado, precio, capacidad);
                resultado = habitacionLogica.create(nuevaHabitacion);

                if (resultado != null) {
                    mostrarAlerta("Éxito", "Habitación creada correctamente con ID: " + resultado.getId());
                } else {
                    mostrarAlerta("Error", "No se pudo crear la habitación");
                    return;
                }
            } else {
                // Modificar habitación existente
                habitacion.setNumero(numero);
                habitacion.setTipo(tipo);
                habitacion.setEstado(estado);
                habitacion.setPrecio(precio);
                habitacion.setCapacidad(capacidad);

                resultado = habitacionLogica.update(habitacion);

                if (resultado != null) {
                    mostrarAlerta("Éxito", "Habitación actualizada correctamente");
                } else {
                    mostrarAlerta("Error", "No se pudo actualizar la habitación");
                    return;
                }
            }
            Stage stage = (Stage) btnGuardarHabitacion.getScene().getWindow();
            stage.setUserData(resultado);
            stage.close();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error en formato", "Verifique que los campos numéricos tengan valores válidos.");
        } catch (Exception e) {
            mostrarAlerta("Error guardando los datos", e.getMessage());
        }
    }

    private Boolean camposInvalidos(String numero, String precio, String tipo, String estado) {
        return numero.isEmpty() || precio.isEmpty() || tipo == null || estado == null;
    }


    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion, Boolean editar) {
        this.habitacion = habitacion;
        this.modificacion = editar;

        if (editar && habitacion != null) {
            txtNumeroHabitacion.setDisable(true);
            txtNumeroHabitacion.setText(String.valueOf(habitacion.getNumero()));
            txtPrecio.setText(String.valueOf(habitacion.getPrecio()));
            spinnerCapacidadHabitacion.getValueFactory().setValue(habitacion.getCapacidad());
            comboBoxTipoHabitacion.setValue(Habitacion.convertirCodigoATipo(habitacion.getTipo()));
            comboBoxEstadoHabitacion.setValue(Habitacion.convertirCodigoAEstado(habitacion.getEstado()));
            if (habitacion.getEstado() == 3)
            {
                comboBoxEstadoHabitacion.setDisable(true);
            } else {
                comboBoxEstadoHabitacion.setDisable(false);

            }
        }
    }

    public Boolean getModificacion() {
        return modificacion;
    }

    public void setModificacion(Boolean modificacion) {
        this.modificacion = modificacion;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxTipoHabitacion.getItems().addAll("Standard", "Suite");
        comboBoxEstadoHabitacion.getItems().addAll("Perfecto", "Mantenimiento", "Reservada", "Clausurada", "Ocupada");

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        spinnerCapacidadHabitacion.setValueFactory(valueFactory);
    }
}