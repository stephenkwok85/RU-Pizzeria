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

/**
 * Controller class for managing the view of placed orders. This class allows
 * users to view details of placed orders, cancel orders, and export order details
 * to a text file. It also displays the total cost of an order including tax.
 *
 * @author Stephen Kwok and Jeongtae Kim
 */
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

    /**
     * Initializes the placed order view and sets up event handlers for buttons.
     */
    @FXML
    private void initialize() {
        cancel_order_button.setOnAction(event -> cancelOrder());
        export_order_button.setOnAction(event -> exportOrders());

        refreshPlacedOrders();
        placed_order_number_selection.setOnAction(event -> showOrderDetails());
    }

    /**
     * Refreshes the list of placed orders in the ComboBox.
     */
    private void refreshPlacedOrders() {
        placed_order_number_selection.getItems().clear();
        List<Integer> placedOrderNumbers = OrderManager.getPlacedOrderNumbers();
        placed_order_number_selection.getItems().addAll(placedOrderNumbers);
    }

    /**
     * Displays the details of the selected order, including pizza details
     * and the total order cost.
     */
    private void showOrderDetails() {
        Integer orderNum = placed_order_number_selection.getValue();
        if (orderNum != null) {
            List<Pizza> pizzas = OrderManager.getPlacedOrder(orderNum);

            if (pizzas != null && !pizzas.isEmpty()) {
                displayPizzaDetails(pizzas);
                calculateAndDisplayTotal(pizzas);
            } else {
                showAlert(ERROR_TITLE, "Order not found.");
            }
        }
    }

    /**
     * Displays the list of pizzas and their details for a given order.
     *
     * @param pizzas List of pizzas in the selected order.
     */
    private void displayPizzaDetails(List<Pizza> pizzas) {
        pizzaDetailsList.clear();
        int pizzaNumber = 1;

        for (Pizza pizza : pizzas) {
            pizzaDetailsList.add("Pizza " + pizzaNumber++);
            pizzaDetailsList.add("Category: " + pizza.getClass().getSimpleName());
            pizzaDetailsList.add("Style: " + pizza.getStyle());
            pizzaDetailsList.add("Size: " + pizza.getSize());
            pizzaDetailsList.add("Crust: " + pizza.getCrust().toString());
            pizzaDetailsList.add("Price: $" + pizza.price());
            pizzaDetailsList.add("Toppings: " + getToppingsString(pizza));
            pizzaDetailsList.add("");
        }

        placed_order_list.setItems(pizzaDetailsList);
    }

    /**
     * Retrieves the toppings of a pizza as a formatted string.
     *
     * @param pizza The pizza whose toppings are being formatted.
     * @return A comma-separated string of toppings.
     */
    private String getToppingsString(Pizza pizza) {
        StringBuilder toppings = new StringBuilder();
        for (Topping topping : pizza.getToppings()) {
            toppings.append(topping.toString()).append(", ");
        }
        if (toppings.length() > 0) {
            toppings.setLength(toppings.length() - 2);
        }
        return toppings.toString();
    }

    /**
     * Calculates and displays the total cost of the order, including tax.
     *
     * @param pizzas List of pizzas in the selected order.
     */
    private void calculateAndDisplayTotal(List<Pizza> pizzas) {
        double subtotal = 0.0;
        for (Pizza pizza : pizzas) {
            subtotal += pizza.price();
        }
        double tax = subtotal * TAX_RATE;
        double total = subtotal + tax;
        order_total.setText(String.format("%.2f", total));
    }

    /**
     * Cancels the selected placed order and removes it from the list of placed orders.
     */
    private void cancelOrder() {
        Integer orderNum = placed_order_number_selection.getValue();
        if (orderNum != null) {
            boolean isDeleted = OrderManager.deletePlacedOrder(orderNum);
            if (isDeleted) {
                showAlert(ORDER_CANCELED_TITLE, "Order #" + orderNum + " has been successfully canceled.");
                refreshPlacedOrders();
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

    /**
     * Exports the details of all placed orders to a text file.
     */
    private void exportOrders() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Integer orderNum : OrderManager.getPlacedOrderNumbers()) {
                List<Pizza> pizzas = OrderManager.getPlacedOrder(orderNum);
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

    /**
     * Displays an alert with the specified title and content.
     *
     * @param title   The title of the alert dialog.
     * @param content The message content of the alert dialog.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}