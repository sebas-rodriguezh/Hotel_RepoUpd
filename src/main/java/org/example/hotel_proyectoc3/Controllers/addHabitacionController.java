package org.example.hotel_proyectoc3.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.hotel_proyectoc3.Domain.Model.Habitacion;

public class addHabitacionController {

    @FXML private Button btnGuardar;
    @FXML private TextField txtIDHabitacion;
    @FXML private TextField txtNumeroHabitacion;
    @FXML private TextField txtTipoHabitacion;
    @FXML private TextField txtEstadoHabitacion;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtCapacidad;

    private Habitacion habitacion;
    private Boolean modificacion = false;

    @FXML
    public void guardarHabitacion(ActionEvent actionEvent) {
        try {
            String idText = txtIDHabitacion.getText().trim();
            String numeroText = txtNumeroHabitacion.getText().trim();
            String tipoText = txtTipoHabitacion.getText().trim();
            String estadoText = txtEstadoHabitacion.getText().trim();
            String precioText = txtPrecio.getText().trim();
            String capacidadText = txtCapacidad.getText().trim();

            if (camposInvalidos(idText, numeroText, tipoText, estadoText, precioText, capacidadText)) {
                mostrarAlerta("Campos Incompletos", "Debe llenar todos los campos del formulario.");
                return;
            }

            // Convertir valores
            int id = Integer.parseInt(idText);
            int numero = Integer.parseInt(numeroText);
            int tipo = Integer.parseInt(tipoText);
            int estado = Integer.parseInt(estadoText);
            double precio = Double.parseDouble(precioText);
            int capacidad = Integer.parseInt(capacidadText);

            if (!modificacion) {
                habitacion = new Habitacion(id, numero, tipo, estado, precio, capacidad);
                // Asignar descripciones según los códigos
                asignarDescripciones(habitacion);
            } else {
                habitacion.setId(id);
                habitacion.setNumero(numero);
                habitacion.setTipo(tipo);
                habitacion.setEstado(estado);
                habitacion.setPrecio(precio);
                habitacion.setCapacidad(capacidad);
                asignarDescripciones(habitacion);
            }

            Stage stage = (Stage) btnGuardar.getScene().getWindow();
            stage.setUserData(habitacion);
            stage.close();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error en formato", "Verifique que todos los campos numéricos tengan valores válidos.");
        } catch (Exception e) {
            mostrarAlerta("Error guardando los datos", e.getMessage());
        }
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

    private Boolean camposInvalidos(String id, String numero, String tipo, String estado, String precio, String capacidad) {
        return id.isEmpty() || numero.isEmpty() || tipo.isEmpty() ||
                estado.isEmpty() || precio.isEmpty() || capacidad.isEmpty();
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
            // Llenar los campos con los datos existentes
            txtIDHabitacion.setText(String.valueOf(habitacion.getId()));
            txtNumeroHabitacion.setText(String.valueOf(habitacion.getNumero()));
            txtTipoHabitacion.setText(String.valueOf(habitacion.getTipo()));
            txtEstadoHabitacion.setText(String.valueOf(habitacion.getEstado()));
            txtPrecio.setText(String.valueOf(habitacion.getPrecio()));
            txtCapacidad.setText(String.valueOf(habitacion.getCapacidad()));
        }
    }

    public Boolean getModificacion() {
        return modificacion;
    }

    public void setModificacion(Boolean modificacion) {
        this.modificacion = modificacion;
    }
}