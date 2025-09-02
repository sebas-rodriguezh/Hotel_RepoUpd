package org.example.hotel_proyectoc3.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TabConsultaClientesController {

    @FXML private Button btnEliminarClienteSeleccionado;
    @FXML private Button btnModificarClienteSeleccionado;
    @FXML private Button btnInsertarCliente;
    @FXML private TextField txtFiltroCliente;
    @FXML private TableColumn colNombreCliente;
    @FXML private TableColumn colIdentificacionCliente;
    @FXML private TableColumn colIdCliente;
    @FXML private TableView tblClientes;

    @FXML
    public void insertarCliente(ActionEvent actionEvent) {
    }


    @FXML
    public void modificarClienteSeleccionado(ActionEvent actionEvent) {
    }


    @FXML
    public void eliminarClienteSeleccionado(ActionEvent actionEvent) {

    }
}
