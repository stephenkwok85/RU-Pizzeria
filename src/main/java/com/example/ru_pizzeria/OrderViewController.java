package com.example.ru_pizzeria;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pizzeria_package.Pizza;
import pizzeria_package.Topping;

import java.util.List;
import java.util.stream.Collectors;
public class OrderViewController {

    @FXML
    private TextField order_num_selection;
    @FXML
    private TableView<Pizza> current_order_table;
    @FXML
    private TableColumn<Pizza, Integer> pizzaNumberColumn;
    @FXML
    private TableColumn<Pizza, String> pizzaTypeColumn;
    @FXML
    private TableColumn<Pizza, String> pizzaSizeColumn;
    @FXML
    private TableColumn<Pizza, String> pizzaCrustColumn;
    @FXML
    private TableColumn<Pizza, String> pizzaToppingsColumn;
    @FXML
    private TableColumn<Pizza, Double> pizzaPriceColumn;
    @FXML
    private TextField subtotal_order;
    @FXML
    private TextField tax_order;
    @FXML
    private TextField order_total;
    @FXML
    private Button completeOrderButton;
    @FXML
    private Button clear_all_order;
    @FXML
    private Button remove_pizza;

    private ObservableList<Pizza> pizzaDetailsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        pizzaNumberColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(current_order_table.getItems().indexOf(cellData.getValue()) + 1));
        pizzaTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClass().getSimpleName()));
        pizzaSizeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSize().toString()));
        pizzaCrustColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCrust().toString()));
        pizzaToppingsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getToppings().stream().map(Topping::toString).collect(Collectors.joining(", "))));
        pizzaPriceColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().price()));

        current_order_table.setItems(pizzaDetailsList);
    }


    @FXML
    private void searchOrder() {
        try {
            int orderNum = Integer.parseInt(order_num_selection.getText());
            List<Pizza> pizzas = OrderManager.getOrder(orderNum);

            if (pizzas != null && !pizzas.isEmpty()) {
                pizzaDetailsList.clear();
                pizzaDetailsList.addAll(pizzas);

                double subtotal = pizzas.stream().mapToDouble(Pizza::price).sum();
                subtotal_order.setText(String.format("%.2f", subtotal));

                double tax = subtotal * 0.06625;
                tax_order.setText(String.format("%.2f", tax));

                double total = subtotal + tax;
                order_total.setText(String.format("%.2f", total));
            } else {
                showAlert("Error", "Order number not found!");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid order number!");
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
        current_order_table.getItems().clear(); // Clear the order table view (if necessary)
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void clearAllOrders() {
        try {
            // Get the order number from the search field
            int orderNum = Integer.parseInt(order_num_selection.getText());

            // Delete the order from OrderManager
            boolean isDeleted = OrderManager.deleteOrder(orderNum);

            if (isDeleted) {
                showAlert("Order Cleared", "Order #" + orderNum + " has been cleared.");
                // Clear the UI components
                current_order_table.getItems().clear(); // Clear the order table view (if necessary)
                subtotal_order.clear();
                tax_order.clear();
                order_total.clear();
            } else {
                showAlert("Error", "Order #" + orderNum + " not found.");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid order number to clear.");
        }
    }

    @FXML
    private void removePizza() {
        Pizza selectedPizza = current_order_table.getSelectionModel().getSelectedItem();
        if (selectedPizza == null) {
            showAlert("No Selection", "Please select a pizza to remove.");
            return;
        }

        int orderNum = Integer.parseInt(order_num_selection.getText());
        List<Pizza> pizzas = OrderManager.getOrder(orderNum);

        if (pizzas != null && !pizzas.isEmpty()) {
            pizzas.remove(selectedPizza);
            OrderManager.updateOrder(orderNum, pizzas);
            searchOrder(); // Update the TableView with the new list
        } else {
            showAlert("Error", "Order not found.");
        }
    }

}