package org.example.hotel_proyectoc3.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {


    @FXML private Button btnIngresar;
    @FXML private Button btnVolver;
    @FXML private Button btnSalir;
    @FXML private PasswordField pwdPassword;
    @FXML private TextField txtIdUsuario;
    @FXML private ProgressIndicator progress;

    @FXML
    public void salirDelaApp(ActionEvent actionEvent) {
    }

    @FXML
    public void volverAlMenu(ActionEvent actionEvent) {
    }

    @FXML
    public void iniciarSesion(ActionEvent actionEvent) {
        String user = txtIdUsuario.getText();
        String password = pwdPassword.getText();

        btnIngresar.setVisible(false);
        btnVolver.setVisible(false);
        btnSalir.setVisible(false);
        progress.setVisible(true);

        new Thread(() -> {
            try {
                Thread.sleep(1500); // Simula procesamiento
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                btnIngresar.setVisible(true);
                btnVolver.setVisible(true);
                btnSalir.setVisible(true);
                progress.setVisible(false);

                if (user.equals("admin") && password.equals("1234")) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hotel_proyectoc3/UI/View/VistaInicio.fxml"));
                        Parent root = loader.load();
                        Stage stage = (Stage) txtIdUsuario.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Pantalla de Inicio");
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error de sistema");
                        alert.setHeaderText(e.getStackTrace()[0].getClassName());
                        alert.setContentText("No fue posible iniciar sesión, debido a un error de sistema: " + e.getMessage());
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error de autenticación");
                    alert.setHeaderText(null);
                    alert.setContentText("Usuario o contraseña incorrectos.");
                    alert.showAndWait();
                }
            });
        }).start();
    }
}
