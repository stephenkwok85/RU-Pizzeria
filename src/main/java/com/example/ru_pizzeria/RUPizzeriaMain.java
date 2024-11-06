package com.example.ru_pizzeria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RUPizzeriaMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RUPizzeriaMain.class.getResource("main_view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        stage.setTitle("RU Pizzeria");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
