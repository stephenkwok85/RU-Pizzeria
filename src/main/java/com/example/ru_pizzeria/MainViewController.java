package com.example.ru_pizzeria;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class MainViewController {

    @FXML
    private Button current_order_button;

    @FXML
    private Button order_placed_button;

    @FXML
    private Button ny_button;

    @FXML
    private Button chicago_button;

    @FXML
    public void initialize() {
        order_placed_button.setOnAction(event -> openOrderView());
        current_order_button.setOnAction(event -> openManageOrdersView());
        chicago_button.setOnAction(event -> openChicagoPizzaView());
        ny_button.setOnAction(event -> openNyPizzaView());

    }

    private void openChicagoPizzaView() {   // Chicago style pizza order menu
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chicago_pizza_view.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Chicago Style Pizza Order");
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openNyPizzaView() {   // NY style pizza order menu
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ny_pizza_view.fxml"));
            Stage stage = new Stage();
            stage.setTitle("NY Style Pizza Order");
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openManageOrdersView() {  // Menu to check placed order
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("current_order_view.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Current Order");
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openOrderView() {   // Current order menu to place order
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("placed_order_view.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Manage Orders");
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
