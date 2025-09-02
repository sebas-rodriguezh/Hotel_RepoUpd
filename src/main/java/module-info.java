module org.example.hotel_proyectoc3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.hotel_proyectoc3 to javafx.fxml;
    exports org.example.hotel_proyectoc3;
}