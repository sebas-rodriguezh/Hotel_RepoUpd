package org.example.hotel_proyectoc3.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class addClienteController {


    @FXML private Button btnGuardar;
    @FXML private TextField txtEdad;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtSegundoApellido;
    @FXML private DatePicker dtpFechaNacimiento;
    @FXML private TextField txtPrimerApellido;
    @FXML private TextField txtNumeroIdentificacion;
    @FXML private TextField txtNombre;


    @FXML
    public void guardarCliente(ActionEvent actionEvent) {
    }
}
