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

    private static final double TAX_RATE = 0.06625;
    private static final String ERROR_TITLE = "Error";
    private static final String INVALID_INPUT_TITLE = "Invalid Input";
    private static final String ORDER_COMPLETED_TITLE = "Order Completed";
    private static final String ORDER_CLEARED_TITLE = "Order Cleared";
    private static final String NO_SELECTION_TITLE = "No Selection";
    private static final String ERROR_HEADER = null;

    @FXML
    private TextField order_num_selection;
    @FXML
    private TableView<Pizza> current_order_table;
    @FXML
    private TableColumn<Pizza, Integer> pizzaNumberColumn;
    @FXML
    private TableColumn<Pizza, String> pizzaCategoryColumn;
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
        pizzaNumberColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(current_order_table.getItems().indexOf(cellData.getValue()) + 1)
        );

        pizzaCategoryColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getClass().getSimpleName())
        );

        pizzaTypeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStyle())
        );

        pizzaSizeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSize().toString())
        );
        pizzaCrustColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCrust().toString())
        );
        pizzaToppingsColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getToppings().stream()
                                .map(Topping::toString)
                                .collect(Collectors.joining(", "))
                )
        );
        pizzaPriceColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().price())
        );

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

                double tax = subtotal * TAX_RATE;
                tax_order.setText(String.format("%.2f", tax));

                double total = subtotal + tax;
                order_total.setText(String.format("%.2f", total));
            } else {
                showAlert(ERROR_TITLE, "Order number not found or it has already been placed!");
            }
        } catch (NumberFormatException e) {
            showAlert(INVALID_INPUT_TITLE, "Please enter a valid order number!");
        }
    }

    @FXML
    private void completeOrder() {
        if (order_num_selection.getText().isEmpty()) {
            showAlert(INVALID_INPUT_TITLE, "Please enter a valid order number before completing the order.");
            return;
        }

        try {
            int orderNum = Integer.parseInt(order_num_selection.getText());
            List<Pizza> pizzas = OrderManager.getOrder(orderNum);

            if (pizzas == null || pizzas.isEmpty()) {
                showAlert(ERROR_TITLE, "Order number not found!");
                return;
            }

            OrderManager.completeCurrentOrder();

            order_num_selection.clear();
            current_order_table.getItems().clear();
            subtotal_order.clear();
            tax_order.clear();
            order_total.clear();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(ORDER_COMPLETED_TITLE);
            alert.setHeaderText("Order #" + orderNum + " Completed");
            alert.setContentText("The order has been placed. You can view it in the Orders Placed menu.");
            alert.showAndWait();

        } catch (NumberFormatException e) {
            showAlert(INVALID_INPUT_TITLE, "Please enter a valid order number!");
        }
    }



    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(ERROR_HEADER);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void clearAllOrders() {
        try {
            int orderNum = Integer.parseInt(order_num_selection.getText());
            List<Pizza> pizzas = OrderManager.getOrder(orderNum); // 주문을 가져옴

            if (pizzas == null || pizzas.isEmpty()) {
                // 주문이 없거나 피자가 없으면 경고 표시
                showAlert(ERROR_TITLE, "Order #" + orderNum + " not found or it has no pizzas.");
                return;
            }

            // 피자 목록을 비움
            pizzas.clear(); // OrderManager에서 삭제할 수 없으면 이 방법을 사용할 수 있음
            OrderManager.updateOrder(orderNum, pizzas); // 피자 목록을 업데이트

            // UI 업데이트
            pizzaDetailsList.clear();
            current_order_table.setItems(pizzaDetailsList);
            order_num_selection.clear();
            subtotal_order.clear();
            tax_order.clear();
            order_total.clear();

            showAlert(ORDER_CLEARED_TITLE, "Order #" + orderNum + " has been cleared.");

        } catch (NumberFormatException e) {
            showAlert(INVALID_INPUT_TITLE, "Please enter a valid order number to clear.");
        }
    }




    @FXML
    private void removePizza() {
        Pizza selectedPizza = current_order_table.getSelectionModel().getSelectedItem();
        if (selectedPizza == null) {
            showAlert(NO_SELECTION_TITLE, "Please select a pizza to remove.");
            return;
        }

        try {
            int orderNum = Integer.parseInt(order_num_selection.getText());
            List<Pizza> pizzas = OrderManager.getOrder(orderNum);

            if (pizzas != null && pizzas.contains(selectedPizza)) {
                pizzas.remove(selectedPizza);
                OrderManager.updateOrder(orderNum, pizzas);
                pizzaDetailsList.remove(selectedPizza);
                updateOrderSummary();
            } else {
                showAlert(ERROR_TITLE, "Pizza could not be found in the order.");
            }
        } catch (NumberFormatException e) {
            showAlert(INVALID_INPUT_TITLE, "Please enter a valid order number!");
        }
    }

    private void updateOrderSummary() {
        double subtotal = pizzaDetailsList.stream().mapToDouble(Pizza::price).sum();
        subtotal_order.setText(String.format("%.2f", subtotal));

        double tax = subtotal * TAX_RATE;
        tax_order.setText(String.format("%.2f", tax));

        double total = subtotal + tax;
        order_total.setText(String.format("%.2f", total));
    }

}
