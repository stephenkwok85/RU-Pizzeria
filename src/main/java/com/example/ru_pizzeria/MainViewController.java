package com.example.ru_pizzeria;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.image.ImageView;

/**
 * Controller class for the main view of the pizza ordering application.
 * This class manages button interactions and image initialization for
 * different types of pizza orders and order management screens.
 * It provides methods to open the views for Chicago-style, NY-style pizzas,
 * current orders, and placed orders.
 *
 * @author Stephen Kwok and Jeongtae Kim
 */
public class MainViewController {

    @FXML
    private Button current_order_button;

    @FXML
    private Button order_placed_button;

    @FXML
    private Button ny_button;

    @FXML
    private Button chicago_button;

    @FXML
    private ImageView nyPizzaImage;
    @FXML
    private ImageView nyPizzaButtonImage;

    @FXML
    private ImageView chicagoPizzaButtonImage;

    @FXML
    private ImageView ordersPlacedImage;
    @FXML
    private ImageView currentOrderImage;

    /**
     * Initializes the main view with images for each button
     * and sets up action handlers to navigate to other views.
     */
    @FXML
    public void initialize() {
        Image chicagoImage = new Image(getClass().getResourceAsStream("/images/chicago_pizza.png"));
        chicagoPizzaButtonImage.setImage(chicagoImage);
        chicago_button.setGraphic(chicagoPizzaButtonImage);

        Image nyImage = new Image(getClass().getResourceAsStream("/images/ny_pizza.png"));
        nyPizzaButtonImage.setImage(nyImage);
        ny_button.setGraphic(nyPizzaButtonImage);

        Image ordersPlaced = new Image(getClass().getResourceAsStream("/images/orders_placed.png"));
        ImageView ordersPlacedView = new ImageView(ordersPlaced);
        ordersPlacedView.setFitWidth(278.0);
        ordersPlacedView.setFitHeight(250.0);
        ordersPlacedView.setPreserveRatio(false);
        order_placed_button.setGraphic(ordersPlacedView);

        Image currentOrder = new Image(getClass().getResourceAsStream("/images/current_order.png"));
        ImageView currentOrderView = new ImageView(currentOrder);
        currentOrderView.setFitWidth(278.0);
        currentOrderView.setFitHeight(250.0);
        currentOrderView.setPreserveRatio(false);
        current_order_button.setGraphic(currentOrderView);

        chicago_button.setOnAction(event -> openChicagoPizzaView());
        ny_button.setOnAction(event -> openNyPizzaView());
        order_placed_button.setOnAction(event -> openOrderView());
        current_order_button.setOnAction(event -> openManageOrdersView());
    }

    /**
     * Opens the Chicago-style pizza ordering view.
     * Loads the FXML layout for the Chicago-style pizza interface.
     */
    private void openChicagoPizzaView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chicago_pizza_view.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Chicago Style Pizza Order");
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the NY-style pizza ordering view.
     * Loads the FXML layout for the NY-style pizza interface.
     */
    private void openNyPizzaView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ny_pizza_view.fxml"));
            Stage stage = new Stage();
            stage.setTitle("NY Style Pizza Order");
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the view to manage current orders.
     * Loads the FXML layout for the current order interface.
     */
    private void openManageOrdersView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("current_order_view.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Current Order");
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the view to display placed orders.
     * Loads the FXML layout for the placed orders interface.
     */
    private void openOrderView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("placed_order_view.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Manage Orders");
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}