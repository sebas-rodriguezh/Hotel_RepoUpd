package org.example.hotel_proyectoc3.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.hotel_proyectoc3.Domain.Model.Cliente;

import java.time.LocalDate;


public class addClienteController {


    @FXML private Button btnGuardar;
    @FXML private TextField txtEdad;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtSegundoApellido;
    @FXML private DatePicker dtpFechaNacimiento;
    @FXML private TextField txtPrimerApellido;
    @FXML private TextField txtNumeroIdentificacion;
    @FXML private TextField txtNombre;

    private Cliente cliente;
    private Boolean modificacion = (Boolean) false;

    @FXML
    public void guardarCliente(ActionEvent actionEvent) {
        try {
            String identificacion = txtNumeroIdentificacion.getText().trim();
            String nombre = txtNombre.getText().trim();
            String direccion = txtDireccion.getText().trim();
            String segundoApellido = txtSegundoApellido.getText().trim();
            String primerApellido = txtPrimerApellido.getText().trim();
            LocalDate fechaNacimiento = dtpFechaNacimiento.getValue();

            if (camposInvalidos(identificacion, nombre, direccion, segundoApellido, primerApellido, fechaNacimiento)) {
                mostrarAlerta("Campos Incompletos ó incorrectos", "Debe llenar todos los campos del formulario.");
                return;
            }

            if (!modificacion) {
                cliente =  new Cliente(0, identificacion, nombre, primerApellido, fechaNacimiento);
                cliente.setDireccion(direccion);
                cliente.setSegundoApellido(segundoApellido);
            } else {
                cliente.setIdentificacion(identificacion);
                cliente.setNombre(nombre);
                cliente.setPrimerApellido(primerApellido);
                cliente.setFechaNacimiento();
                cliente.setDireccion(direccion);
                cliente.setSegundoApellido(segundoApellido);
            }

            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.setUserData(cliente);
            stage.close();
        } catch (Exception e) {
            mostrarAlerta("Error guardando los datos.", e.getMessage());

        }
    }

    public Boolean camposInvalidos(String identificacion, String nombre, String direccion, String segundoApellido, String primerApellido,  LocalDate fechaNacimiento) {
        return identificacion.isEmpty() || nombre.isEmpty() || primerApellido.isEmpty() || fechaNacimiento == null || direccion.isEmpty() || segundoApellido.isEmpty();
    }


    private void mostrarAlerta(String titulo, String mensaje) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente, Boolean editar) {
        this.cliente = cliente;
        this.modificacion = editar;
        if (editar && cliente != null) {
            txtNumeroIdentificacion.setText(cliente.getIdentificacion());
            txtNombre.setText(cliente.getNombre());
            txtPrimerApellido.setText(cliente.getPrimerApellido());
            dtpFechaNacimiento.setValue(cliente.getFechaNacimiento());
            txtSegundoApellido.setText(cliente.getSegundoApellido());
            txtDireccion.setText(cliente.getDireccion());
        }
    }

    public Boolean getModificacion() {
        return modificacion;
    }

    public void setModificacion(Boolean modificacion) {
        this.modificacion = modificacion;
    }

}
