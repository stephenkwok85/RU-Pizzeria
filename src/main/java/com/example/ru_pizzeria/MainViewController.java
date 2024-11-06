package com.example.ru_pizzeria;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class MainViewController {

    @FXML
    private Button newOrderButton;

    @FXML
    private Button manageOrdersButton;

    @FXML
    public void initialize() {
        newOrderButton.setOnAction(event -> openOrderView());
        manageOrdersButton.setOnAction(event -> openManageOrdersView());
    }

    private void openOrderView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/order_view.fxml"));
            Stage stage = (Stage) newOrderButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openManageOrdersView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/pizza_view.fxml"));
            Stage stage = (Stage) manageOrdersButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
