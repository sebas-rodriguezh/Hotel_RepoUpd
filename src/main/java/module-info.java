module org.example.hotel_proyectoc3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.xml.bind; //Nueva.
    requires java.sql; //Nueva para SQL.
    requires com.zaxxer.hikari; //Nueva para zaxxer.
    requires com.mysql.cj;
    //requires mysql.connector.j;

    exports org.example.hotel_proyectoc3;
    opens org.example.hotel_proyectoc3.Domain.Model to javafx.base;

    exports org.example.hotel_proyectoc3.Domain.Model;

    exports org.example.hotel_proyectoc3.Controllers to javafx.fxml;

    opens org.example.hotel_proyectoc3 to javafx.fxml;
    opens org.example.hotel_proyectoc3.Controllers to javafx.fxml;


    opens org.example.hotel_proyectoc3.Data to jakarta.xml.bind; //Nueva.
    exports org.example.hotel_proyectoc3.Data; //Nueva.
}

