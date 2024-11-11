package com.example.ru_pizzeria;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import pizzeria_package.Pizza;
import pizzeria_package.Topping;

import java.util.List;

public class PlacedOrderViewController {

    @FXML
    private ComboBox<Integer> placed_order_number_selection;
    @FXML
    private ListView<String> placed_order_list;
    @FXML
    private TextField order_total;

    private ObservableList<String> pizzaDetailsList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        placed_order_number_selection.setItems(FXCollections.observableArrayList(OrderManager.getAllOrderNumbers()));

        placed_order_number_selection.setOnAction(event -> showOrderDetails());
    }

    private void showOrderDetails() {
        Integer orderNum = placed_order_number_selection.getValue();
        if (orderNum != null) {
            List<Pizza> pizzas = OrderManager.getOrder(orderNum);

            if (pizzas != null && !pizzas.isEmpty()) {
                pizzaDetailsList.clear();
                double subtotal = 0.0;
                StringBuilder toppings = new StringBuilder();

                int pizzaNumber = 1;
                for (Pizza pizza : pizzas) {
                    pizzaDetailsList.add("Pizza " + pizzaNumber++);
                    pizzaDetailsList.add("Type: " + pizza.getClass().getSimpleName());
                    pizzaDetailsList.add("Size: " + pizza.getSize());
                    pizzaDetailsList.add("Crust: " + pizza.getCrust().toString());
                    pizzaDetailsList.add("Price: $" + pizza.price());

                    toppings.setLength(0);
                    for (Topping topping : pizza.getToppings()) {
                        toppings.append(topping.toString()).append(", ");
                    }
                    if (toppings.length() > 0) {
                        toppings.setLength(toppings.length() - 2);
                    }
                    pizzaDetailsList.add("Toppings: " + toppings.toString());
                    pizzaDetailsList.add("");

                    subtotal += pizza.price();
                }

                placed_order_list.setItems(pizzaDetailsList);

                double tax = subtotal * 0.06625;
                double total = subtotal + tax;
                order_total.setText(String.format("%.2f", total));
            } else {
                showAlert("Error", "Order not found.");
            }
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}