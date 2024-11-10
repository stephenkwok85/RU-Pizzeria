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
    @FXML
    private TextField subtotal_order;
    @FXML
    private TextField tax_order;
    @FXML
    private TextField order_total;

    private ObservableList<String> pizzaDetailsList = FXCollections.observableArrayList();  // List to hold pizza details

    @FXML
    private void searchOrder() {
        try {
            int orderNum = Integer.parseInt(order_num_selection.getText());  // Get order number from TextField
            List<Pizza> pizzas = OrderManager.getOrder(orderNum);  // Retrieve list of pizzas for this order from OrderManager

            // Print to the console to see if the order is retrieved
            System.out.println("Searching for Order #" + orderNum);

            if (pizzas != null && !pizzas.isEmpty()) {
                System.out.println("Order found: " + pizzas.size() + " pizzas");

                pizzaDetailsList.clear();  // Clear previous details
                pizzaDetailsList.add("Order #" + orderNum);  // Add order number

                // Variable to store the subtotal (total cost)
                double subtotal = 0.0;
                StringBuilder toppings = new StringBuilder();

                // Iterate through each pizza in the order
                for (Pizza pizza : pizzas) {
                    pizzaDetailsList.add("Type: " + pizza.getClass().getSimpleName());  // Add pizza type
                    pizzaDetailsList.add("Size: " + pizza.getSize());  // Add pizza size
                    pizzaDetailsList.add("Crust: " + pizza.getCrust().toString());  // Add crust
                    pizzaDetailsList.add("Price: $" + pizza.price());  // Add price for this pizza

                    // Add toppings
                    toppings.setLength(0);  // Reset toppings string builder
                    for (Topping topping : pizza.getToppings()) {
                        toppings.append(topping.toString()).append(", ");
                    }
                    if (toppings.length() > 0) {
                        toppings.setLength(toppings.length() - 2);  // Remove trailing comma
                    }
                    pizzaDetailsList.add("Toppings: " + toppings.toString());

                    // Add the price of this pizza to the subtotal
                    subtotal += pizza.price();
                }

                // Update the ListView with the order details
                current_order_list.setItems(pizzaDetailsList);

                // Set the subtotal in the TextField (formatted to 2 decimal places)
                subtotal_order.setText(String.format("%.2f", subtotal));

                // Calculate the tax (6.625%) and set it in the tax_order TextField
                double tax = subtotal * 0.06625;  // 6.625% tax
                tax_order.setText(String.format("%.2f", tax));

                // Calculate the total (subtotal + tax) and set it in the order_total TextField
                double total = subtotal + tax;
                order_total.setText(String.format("%.2f", total));

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