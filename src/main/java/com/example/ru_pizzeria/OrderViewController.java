package com.example.ru_pizzeria;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import pizzeria_package.Pizza;
import pizzeria_package.Topping;

public class OrderViewController {

    @FXML
    private TextField order_num_selection;  // TextField to input order number
    @FXML
    private ListView<String> current_order_list;  // ListView to display order details

    private ObservableList<String> pizzaDetailsList = FXCollections.observableArrayList();  // List to hold pizza details

    @FXML
    private void searchOrder() {
        try {
            int orderNum = Integer.parseInt(order_num_selection.getText());  // Get order number from TextField
            Pizza pizza = OrderManager.getOrder(orderNum);  // Retrieve order from OrderManager

            // Print to the console to see if the order is retrieved
            System.out.println("Searching for Order #" + orderNum);
            if (pizza != null) {
                System.out.println("Order found: " + pizza.getClass().getSimpleName());  // Print the type of pizza found

                pizzaDetailsList.clear();  // Clear previous details
                pizzaDetailsList.add("Order #" + orderNum);  // Add order number
                pizzaDetailsList.add("Type: " + pizza.getClass().getSimpleName());  // Add pizza type
                pizzaDetailsList.add("Size: " + pizza.getSize());  // Add pizza size
                pizzaDetailsList.add("Crust: " + pizza.getCrust().toString());  // Add crust
                pizzaDetailsList.add("Price: $" + pizza.price());  // Add price

                // Add toppings
                StringBuilder toppings = new StringBuilder();
                for (Topping topping : pizza.getToppings()) {
                    toppings.append(topping.toString()).append(", ");
                }
                if (toppings.length() > 0) {
                    toppings.setLength(toppings.length() - 2);  // Remove trailing comma
                }
                pizzaDetailsList.add("Toppings: " + toppings);

                current_order_list.setItems(pizzaDetailsList);  // Update ListView
            } else {
                System.out.println("Order #" + orderNum + " not found.");  // Print when order is not found
                showAlert("Error", "Order number not found!");  // Show error if order not found
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + order_num_selection.getText());  // Print invalid input value
            showAlert("Invalid Input", "Please enter a valid order number!");  // Handle invalid input
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
