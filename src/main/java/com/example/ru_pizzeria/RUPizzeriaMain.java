package com.example.ru_pizzeria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main entry point for the RU Pizzeria application. This class initializes
 * the primary stage and loads the main view for the application.
 *
 * @author Stephen Kwok and Jeongtae Kim
 */
public class RUPizzeriaMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RUPizzeriaMain.class.getResource("main_view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 700, 700);

        stage.setTitle("RU Pizzeria");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
