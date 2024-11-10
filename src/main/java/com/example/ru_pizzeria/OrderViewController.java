package com.example.ru_pizzeria;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import pizzeria_package.Pizza;
import pizzeria_package.Topping;

import java.util.List;

public class OrderViewController {

    @FXML
    private TextField order_num_selection;  // TextField to input order number
    @FXML
    private ListView<String> current_order_list;  // ListView to display order details
    @FXML
    private Button completeOrderButton;  // Button to complete the current order

    private ObservableList<String> pizzaDetailsList = FXCollections.observableArrayList();  // List to hold pizza details

    @FXML
    private void searchOrder() {
        try {
            int orderNum = Integer.parseInt(order_num_selection.getText());  // Get order number from TextField
            List<Pizza> pizzas = OrderManager.getOrder(orderNum);  // Retrieve list of pizzas for the given order number

            // Print to the console to see if the order is retrieved
            System.out.println("Searching for Order #" + orderNum);
            if (pizzas != null && !pizzas.isEmpty()) {
                System.out.println("Order found: " + pizzas.size() + " pizzas");  // Print the number of pizzas in the order

                pizzaDetailsList.clear();  // Clear previous details
                pizzaDetailsList.add("Order #" + orderNum);  // Add order number at the top

                // Loop through each pizza in the order and add its details
                for (Pizza pizza : pizzas) {
                    // Add pizza details to the list
                    pizzaDetailsList.add("Type: " + pizza.getClass().getSimpleName());  // Add pizza type
                    pizzaDetailsList.add("Size: " + pizza.getSize());  // Add pizza size
                    pizzaDetailsList.add("Crust: " + pizza.getCrust().toString());  // Add crust type
                    pizzaDetailsList.add("Price: $" + pizza.price());  // Add pizza price

                    // Add toppings details
                    StringBuilder toppings = new StringBuilder();
                    for (Topping topping : pizza.getToppings()) {
                        toppings.append(topping.toString()).append(", ");
                    }
                    if (toppings.length() > 0) {
                        toppings.setLength(toppings.length() - 2);  // Remove trailing comma
                    }
                    pizzaDetailsList.add("Toppings: " + toppings);
                }

                // Update ListView to show the pizza details
                current_order_list.setItems(pizzaDetailsList);
            } else {
                System.out.println("Order #" + orderNum + " not found.");  // Print when order is not found
                showAlert("Error", "Order number not found!");  // Show error if order not found
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + order_num_selection.getText());  // Print invalid input value
            showAlert("Invalid Input", "Please enter a valid order number!");  // Handle invalid input
        }
    }

    @FXML
    private void completeOrder() {
        // Complete the current order in OrderManager
        OrderManager.completeCurrentOrder();

        // Get the next order number (which should be the new order number)
        int nextOrderNumber = OrderManager.getNextOrderNumber();
        int currentOrderNumber = OrderManager.getCurrentOrderNumber()-1;

        // Display the updated order number in the alert
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Order Completed");
        alert.setHeaderText("Order #" + currentOrderNumber + " Completed");
        alert.setContentText("Your current order is now complete.\nThe next order number will be: " + nextOrderNumber);
        alert.showAndWait();

        // Optionally, reset any other UI components related to the order
        order_num_selection.clear(); // Clear the order number text field
        current_order_list.getItems().clear(); // Clear the order list view (if necessary)
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}