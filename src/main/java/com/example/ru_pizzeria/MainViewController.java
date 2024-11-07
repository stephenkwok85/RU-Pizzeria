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

    private void openChicagoPizzaView() {   // Pizza order menu for Chicago style
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/chicago_pizza_view.fxml"));
            Stage stage = (Stage) chicago_button.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openNyPizzaView() {   // Pizza order menu for NY style
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ny_pizza_view.fxml"));
            Stage stage = (Stage) ny_button.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openOrderView() {  // Menu to check placed order
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/current_order_view.fxml"));
            Stage stage = (Stage) order_placed_button.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openManageOrdersView() {   // Current order menu to place order
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/chicago_pizza_view.fxml"));
            Stage stage = (Stage) current_order_button.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
