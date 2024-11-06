package com.example.ru_pizzeria;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class OrderViewController {

    @FXML
    private ListView<String> pizzaListView;

    @FXML
    private Label totalLabel;

    @FXML
    private Button addPizzaButton;

    @FXML
    private Button submitOrderButton;

    @FXML
    private Button clearOrderButton;

    private ObservableList<String> pizzaList; // change into Pizza List

    @FXML
    public void initialize() {
        pizzaList = FXCollections.observableArrayList();
        pizzaListView.setItems(pizzaList);

        addPizzaButton.setOnAction(event -> addPizza());
        submitOrderButton.setOnAction(event -> submitOrder());
        clearOrderButton.setOnAction(event -> clearOrder());
    }

    private void addPizza() {
        pizzaList.add("New Pizza");
        updateTotal();
    }

    private void submitOrder() {
        System.out.println("Order Submitted!");
        pizzaList.clear();
        updateTotal();
    }

    private void clearOrder() {
        pizzaList.clear();
        updateTotal();
    }

    private void updateTotal() {
        double total = pizzaList.size() * 10.0; // $10 for example
        totalLabel.setText(String.format("$%.2f", total));
    }
}
