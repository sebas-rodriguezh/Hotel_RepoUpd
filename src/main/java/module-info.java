module org.example.hotel_proyectoc3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.xml.bind; //Nueva.

    exports org.example.hotel_proyectoc3;
    opens org.example.hotel_proyectoc3.Domain.Model to javafx.base;

    exports org.example.hotel_proyectoc3.Domain.Model;

    exports org.example.hotel_proyectoc3.Controllers to javafx.fxml;

    opens org.example.hotel_proyectoc3 to javafx.fxml;
    opens org.example.hotel_proyectoc3.Controllers to javafx.fxml;


    opens org.example.hotel_proyectoc3.Data to jakarta.xml.bind; //Nueva.
    exports org.example.hotel_proyectoc3.Data; //Nueva.
}

