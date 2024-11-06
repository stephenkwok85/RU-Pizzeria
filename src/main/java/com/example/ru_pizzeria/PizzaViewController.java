package com.example.ru_pizzeria;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class PizzaViewController {

    @FXML
    private ComboBox<String> styleComboBox;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private ComboBox<String> sizeComboBox;

    @FXML
    private VBox toppingCheckBoxes;

    @FXML
    private Label priceLabel;

    @FXML
    private Button addPizzaButton;

    @FXML
    private Button cancelButton;

    private ObservableList<CheckBox> toppings;

    @FXML
    public void initialize() {
        styleComboBox.setItems(FXCollections.observableArrayList("Chicago", "New York"));
        typeComboBox.setItems(FXCollections.observableArrayList("Deluxe", "BBQ Chicken", "Meatzza", "Build Your Own"));
        sizeComboBox.setItems(FXCollections.observableArrayList("Small", "Medium", "Large"));

        toppings = FXCollections.observableArrayList(
                new CheckBox("Sausage"),
                new CheckBox("Pepperoni"),
                new CheckBox("Green Pepper"),
                new CheckBox("Onion"),
                new CheckBox("Mushroom"),
                new CheckBox("BBQ Chicken"),
                new CheckBox("Cheddar"),
                new CheckBox("Provolone")
        );

        toppingCheckBoxes.getChildren().addAll(toppings);

        addPizzaButton.setOnAction(event -> addPizza());
        cancelButton.setOnAction(event -> cancel());
    }

    private void addPizza() {
        double basePrice = 8.99;
        int selectedToppings = (int) toppings.stream().filter(CheckBox::isSelected).count();
        double totalPrice = basePrice + selectedToppings * 1.69;
        priceLabel.setText(String.format("$%.2f", totalPrice));

        // Order logic here

        System.out.println("Pizza Added!");
    }

    private void cancel() {
        // cancel logic here
        System.out.println("Order Canceled.");
    }
}
