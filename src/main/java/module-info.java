module com.example.ru_pizzeria {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.ru_pizzeria to javafx.fxml;
    exports com.example.ru_pizzeria;
}