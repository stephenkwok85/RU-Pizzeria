package com.example.ru_pizzeria;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ChoiceBox;
import pizzeria_package.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NyPizzaViewController {

    private static final double TOPPING_PRICE = 1.69;
    private static final double DELUXE_S_PRICE = 16.99;
    private static final double DELUXE_M_PRICE = 18.99;
    private static final double DELUXE_L_PRICE = 20.99;

    private static final double BBQ_CHICKEN_S_PRICE = 14.99;
    private static final double BBQ_CHICKEN_M_PRICE = 16.99;
    private static final double BBQ_CHICKEN_L_PRICE = 19.99;

    private static final double MEATZZA_S_PRICE = 17.99;
    private static final double MEATZZA_M_PRICE = 19.99;
    private static final double MEATZZA_L_PRICE = 21.99;

    private static final double BUILD_YOUR_OWN_S_PRICE = 8.99;
    private static final double BUILD_YOUR_OWN_M_PRICE = 10.99;
    private static final double BUILD_YOUR_OWN_L_PRICE = 12.99;

    @FXML
    private ChoiceBox<String> choose_type;
    @FXML
    private TextField crustField;
    @FXML
    private RadioButton s_size;
    @FXML
    private RadioButton m_size;
    @FXML
    private RadioButton l_size;
    @FXML
    private ToggleGroup sizeGroup;
    @FXML
    private RadioButton SAUSAGE, PEPPERONI, GREEN_PEPPER, ONION, MUSHROOM, BBQ_CHICKEN, BEEF, HAM, PROVOLONE, CHEDDAR, OLIVES, SPINACH, PINEAPPLE, BACON;
    @FXML
    private TextField pizza_price;
    @FXML
    private Button add_order_button;
    @FXML
    private ImageView ny_pic;

    private boolean isCustomizable = false;
    private int selectedToppingsCount = 0;
    private final int MAX_TOPPINGS = 7;

    @FXML
    public void initialize() {
        updatePizzaImage("Build Your Own");
        choose_type.setItems(FXCollections.observableArrayList("Deluxe", "BBQ Chicken", "Meatzza", "Build Your Own"));

        choose_type.getSelectionModel().select("Build Your Own");
        crustField.setText("Hand-tossed");
        crustField.setEditable(false);
        s_size.setSelected(true);

        isCustomizable = choose_type.getValue().equals("Build Your Own");

        choose_type.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setPizzaOptions(newValue);
            isCustomizable = newValue.equals("Build Your Own");
            lockToppings(!isCustomizable);
            updatePizzaPrice();
            updatePizzaImage(newValue);
        });

        sizeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> updatePizzaPrice());
        setupToppingListeners();
        updatePizzaPrice();
    }

    private void setPizzaOptions(String pizzaType) {
        SAUSAGE.setSelected(false);
        PEPPERONI.setSelected(false);
        GREEN_PEPPER.setSelected(false);
        ONION.setSelected(false);
        MUSHROOM.setSelected(false);
        BBQ_CHICKEN.setSelected(false);
        BEEF.setSelected(false);
        HAM.setSelected(false);
        PROVOLONE.setSelected(false);
        CHEDDAR.setSelected(false);
        OLIVES.setSelected(false);
        SPINACH.setSelected(false);
        PINEAPPLE.setSelected(false);
        BACON.setSelected(false);

        switch (pizzaType) {
            case "Deluxe":
                crustField.setText("Brooklyn");
                SAUSAGE.setSelected(true);
                PEPPERONI.setSelected(true);
                GREEN_PEPPER.setSelected(true);
                ONION.setSelected(true);
                MUSHROOM.setSelected(true);
                break;
            case "BBQ Chicken":
                crustField.setText("Thin");
                BBQ_CHICKEN.setSelected(true);
                GREEN_PEPPER.setSelected(true);
                PROVOLONE.setSelected(true);
                CHEDDAR.setSelected(true);
                break;
            case "Meatzza":
                crustField.setText("Hand-tossed");
                SAUSAGE.setSelected(true);
                PEPPERONI.setSelected(true);
                BEEF.setSelected(true);
                HAM.setSelected(true);
                break;
            case "Build Your Own":
                crustField.setText("Hand-tossed");
                selectedToppingsCount = 0;
                break;
        }
    }

    private void lockToppings(boolean lock) {
        SAUSAGE.setDisable(lock);
        PEPPERONI.setDisable(lock);
        GREEN_PEPPER.setDisable(lock);
        ONION.setDisable(lock);
        MUSHROOM.setDisable(lock);
        BBQ_CHICKEN.setDisable(lock);
        BEEF.setDisable(lock);
        HAM.setDisable(lock);
        PROVOLONE.setDisable(lock);
        CHEDDAR.setDisable(lock);
        OLIVES.setDisable(lock);
        SPINACH.setDisable(lock);
        PINEAPPLE.setDisable(lock);
        BACON.setDisable(lock);
    }

    private void setupToppingListeners() {
        setupToppingListener(SAUSAGE);
        setupToppingListener(PEPPERONI);
        setupToppingListener(GREEN_PEPPER);
        setupToppingListener(ONION);
        setupToppingListener(MUSHROOM);
        setupToppingListener(BBQ_CHICKEN);
        setupToppingListener(BEEF);
        setupToppingListener(HAM);
        setupToppingListener(PROVOLONE);
        setupToppingListener(CHEDDAR);
        setupToppingListener(OLIVES);
        setupToppingListener(SPINACH);
        setupToppingListener(PINEAPPLE);
        setupToppingListener(BACON);
    }

    private void setupToppingListener(RadioButton toppingButton) {
        toppingButton.setOnAction(event -> {
            if (toppingButton.isSelected()) {
                if (selectedToppingsCount < MAX_TOPPINGS) {
                    selectedToppingsCount++;
                    updatePizzaPrice();
                } else {
                    toppingButton.setSelected(false);
                    showAlert("Error!", "Max " + MAX_TOPPINGS + " toppings are allowed.");
                }
            } else {
                selectedToppingsCount--;
                updatePizzaPrice();
            }
        });
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void updatePizzaPrice() {
        String selectedType = choose_type.getValue();
        RadioButton selectedSizeButton = (RadioButton) sizeGroup.getSelectedToggle();
        String selectedSize = selectedSizeButton.getText();

        double basePrice = calculateBasePrice(selectedType, selectedSize);
        int toppingCount = isCustomizable ? selectedToppingsCount : 0;

        double totalPrice = basePrice + (TOPPING_PRICE * toppingCount);
        pizza_price.setText(String.format("%.2f", totalPrice));
    }


    private double calculateBasePrice(String type, String size) {
        switch (type) {
            case "Deluxe":
                return size.equals("S") ? DELUXE_S_PRICE : size.equals("M") ? DELUXE_M_PRICE : DELUXE_L_PRICE;
            case "BBQ Chicken":
                return size.equals("S") ? BBQ_CHICKEN_S_PRICE : size.equals("M") ? BBQ_CHICKEN_M_PRICE : BBQ_CHICKEN_L_PRICE;
            case "Meatzza":
                return size.equals("S") ? MEATZZA_S_PRICE : size.equals("M") ? MEATZZA_M_PRICE : MEATZZA_L_PRICE;
            case "Build Your Own":
                return size.equals("S") ? BUILD_YOUR_OWN_S_PRICE : size.equals("M") ? BUILD_YOUR_OWN_M_PRICE : BUILD_YOUR_OWN_L_PRICE;
            default:
                return 0.0;
        }
    }


    @FXML
    private void addOrder() {
        Pizza pizza = null;
        PizzaFactory pizzaFactory = new NYPizza();

        switch (choose_type.getValue()) {
            case "Deluxe":
                pizza = pizzaFactory.createDeluxe();
                break;
            case "BBQ Chicken":
                pizza = pizzaFactory.createBBQChicken();
                break;
            case "Meatzza":
                pizza = pizzaFactory.createMeatzza();
                break;
            case "Build Your Own":
                pizza = pizzaFactory.createBuildYourOwn();
                addCustomToppings((BuildYourOwn) pizza);
                break;
        }

        String selectedSize = ((RadioButton) sizeGroup.getSelectedToggle()).getText();

        switch (selectedSize) {
            case "S":
            case "Small":
                pizza.setSize(Size.SMALL);
                break;
            case "M":
            case "Medium":
                pizza.setSize(Size.MEDIUM);
                break;
            case "L":
            case "Large":
                pizza.setSize(Size.LARGE);
                break;
            default:
                throw new IllegalStateException("Unexpected size: " + selectedSize);
        }

        OrderManager.addOrderToCurrentOrder(pizza);

        int orderNumber = OrderManager.getCurrentOrderNumber();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Order Confirmation");
        alert.setHeaderText("Order #" + orderNumber);
        alert.setContentText("Your pizza is added.");
        alert.showAndWait();
    }

    private void addCustomToppings(BuildYourOwn pizza) {
        if (SAUSAGE.isSelected()) pizza.addTopping(Topping.SAUSAGE);
        if (PEPPERONI.isSelected()) pizza.addTopping(Topping.PEPPERONI);
        if (GREEN_PEPPER.isSelected()) pizza.addTopping(Topping.GREEN_PEPPER);
        if (ONION.isSelected()) pizza.addTopping(Topping.ONION);
        if (MUSHROOM.isSelected()) pizza.addTopping(Topping.MUSHROOM);
        if (BBQ_CHICKEN.isSelected()) pizza.addTopping(Topping.BBQ_CHICKEN);
        if (BEEF.isSelected()) pizza.addTopping(Topping.BEEF);
        if (HAM.isSelected()) pizza.addTopping(Topping.HAM);
        if (PROVOLONE.isSelected()) pizza.addTopping(Topping.PROVOLONE);
        if (CHEDDAR.isSelected()) pizza.addTopping(Topping.CHEDDAR);
        if (OLIVES.isSelected()) pizza.addTopping(Topping.OLIVES);
        if (SPINACH.isSelected()) pizza.addTopping(Topping.SPINACH);
        if (PINEAPPLE.isSelected()) pizza.addTopping(Topping.PINEAPPLE);
        if (BACON.isSelected()) pizza.addTopping(Topping.BACON);

        if (pizza.getToppings().size() > 7) {
            pizza.removeTopping(pizza.getToppings().get(7));
        }
    }

    private void updatePizzaImage(String pizzaType) {
        Image image = null;
        switch (pizzaType) {
            case "Deluxe":
                image = new Image(getClass().getResourceAsStream("/images/ny_deluxe.png"));
                break;
            case "BBQ Chicken":
                image = new Image(getClass().getResourceAsStream("/images/ny_bbq.png"));
                break;
            case "Meatzza":
                image = new Image(getClass().getResourceAsStream("/images/ny_meat.png"));
                break;
            case "Build Your Own":
                image = new Image(getClass().getResourceAsStream("/images/ny_build.png"));
                break;
        }

        if (image != null) {
            ny_pic.setImage(image);
        } else {
        }
    }

}
