package com.example.ru_pizzeria;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import pizzeria_package.Pizza;
import pizzeria_package.Topping;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PlacedOrderViewController {

    private static final double TAX_RATE = 0.06625;
    private static final String FILE_PATH = System.getProperty("user.dir") + "/PlacedOrders.txt";

    private static final String ERROR_TITLE = "Error";
    private static final String ORDER_CANCELED_TITLE = "Order Canceled";
    private static final String NO_ORDER_SELECTED_TITLE = "No Order Selected";
    private static final String EXPORT_SUCCESS_TITLE = "Export Successful";
    private static final String EXPORT_FAILED_TITLE = "Export Failed";

    @FXML
    private ComboBox<Integer> placed_order_number_selection;
    @FXML
    private ListView<String> placed_order_list;
    @FXML
    private TextField order_total;
    @FXML
    private Button cancel_order_button;
    @FXML
    private Button export_order_button;

    private ObservableList<String> pizzaDetailsList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        placed_order_number_selection.setItems(FXCollections.observableArrayList(OrderManager.getAllOrderNumbers()));
        placed_order_number_selection.setOnAction(event -> showOrderDetails());
        cancel_order_button.setOnAction(event -> cancelOrder());
        export_order_button.setOnAction(event -> exportOrders());
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
                    pizzaDetailsList.add("Category: " + pizza.getClass().getSimpleName());
                    pizzaDetailsList.add("Style: " + pizza.getStyle());
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

                double tax = subtotal * TAX_RATE;
                double total = subtotal + tax;
                order_total.setText(String.format("%.2f", total));
            } else {
                showAlert(ERROR_TITLE, "Order not found.");
            }
        }
    }

    private void cancelOrder() {
        Integer orderNum = placed_order_number_selection.getValue();
        if (orderNum != null) {
            boolean isDeleted = OrderManager.deleteOrder(orderNum);
            if (isDeleted) {
                showAlert(ORDER_CANCELED_TITLE, "Order #" + orderNum + " has been successfully canceled.");
                placed_order_number_selection.getItems().remove(orderNum);
                placed_order_number_selection.setValue(null);
                placed_order_list.getItems().clear();
                order_total.clear();
            } else {
                showAlert(ERROR_TITLE, "Order #" + orderNum + " could not be found or deleted.");
            }
        } else {
            showAlert(NO_ORDER_SELECTED_TITLE, "Please select an order to cancel.");
        }
    }

    private void exportOrders() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Integer orderNum : OrderManager.getAllOrderNumbers()) {
                List<Pizza> pizzas = OrderManager.getOrder(orderNum);
                if (pizzas != null && !pizzas.isEmpty()) {
                    writer.write("Order #" + orderNum + "\n");
                    double subtotal = 0.0;
                    for (Pizza pizza : pizzas) {
                        writer.write("  - Pizza: " + pizza.getClass().getSimpleName() + "\n");
                        writer.write("    Size: " + pizza.getSize() + "\n");
                        writer.write("    Crust: " + pizza.getCrust().toString() + "\n");
                        writer.write("    Price: $" + pizza.price() + "\n");

                        writer.write("    Toppings: ");
                        StringBuilder toppings = new StringBuilder();
                        for (Topping topping : pizza.getToppings()) {
                            toppings.append(topping.toString()).append(", ");
                        }
                        if (toppings.length() > 0) {
                            toppings.setLength(toppings.length() - 2);
                        }
                        writer.write(toppings.toString() + "\n\n");

                        subtotal += pizza.price();
                    }
                    double tax = subtotal * TAX_RATE;
                    double total = subtotal + tax;
                    writer.write("Order Total (including tax): $" + String.format("%.2f", total) + "\n\n");
                }
            }
            showAlert(EXPORT_SUCCESS_TITLE, "All placed orders have been successfully exported to: " + FILE_PATH);
        } catch (IOException e) {
            showAlert(EXPORT_FAILED_TITLE, "An error occurred while exporting orders.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
