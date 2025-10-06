package org.example.hotel_proyectoc3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/org/example/hotel_proyectoc3/UI/View/TabConsultaHabitaciones.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 650);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();


        //Probando si es el repo correcto y actualizado.
                    //ESTE ES EL REPO, BUSCAR SIEMRPE ESTA LINEA PARA SABER DONDE ES QUE SE DEBEN DE GESTIONAR TODOS LOS CAMBIOS.
    }

    public static void main(String[] args) {
        launch();
    }
}