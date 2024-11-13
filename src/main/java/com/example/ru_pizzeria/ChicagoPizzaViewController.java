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
import pizzeria_package.ChicagoPizza;
import pizzeria_package.Pizza;
import pizzeria_package.PizzaFactory;
import pizzeria_package.BuildYourOwn;
import pizzeria_package.Topping;
import pizzeria_package.Size;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ChicagoPizzaViewController {

    private static final double TOPPING_PRICE = 1.69;
    private static final int MAX_TOPPINGS = 7;
    private static final double DELUXE_S_PRICE = 16.99;
    private static final double DELUXE_M_PRICE = 18.99;
    private static final double DELUXE_L_PRICE = 20.99;
    private static final double BBQ_S_PRICE = 14.99;
    private static final double BBQ_M_PRICE = 16.99;
    private static final double BBQ_L_PRICE = 19.99;
    private static final double MEATZZA_S_PRICE = 17.99;
    private static final double MEATZZA_M_PRICE = 19.99;
    private static final double MEATZZA_L_PRICE = 21.99;
    private static final double BYO_S_PRICE = 8.99;
    private static final double BYO_M_PRICE = 10.99;
    private static final double BYO_L_PRICE = 12.99;

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
    private ImageView ch_pic;

    private boolean isCustomizable = false;
    private int selectedToppingsCount = 0;

    @FXML
    public void initialize() {
        updatePizzaImage("Build Your Own");
        choose_type.setItems(FXCollections.observableArrayList("Deluxe", "BBQ Chicken", "Meatzza", "Build Your Own"));
        choose_type.getSelectionModel().select("Build Your Own");
        crustField.setText("Pan");
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
        crustField.clear();
        resetToppingSelections();

        switch (pizzaType) {
            case "Deluxe":
                crustField.setText("Deep Dish");
                selectToppings(SAUSAGE, PEPPERONI, GREEN_PEPPER, ONION, MUSHROOM);
                break;
            case "BBQ Chicken":
                crustField.setText("Pan");
                selectToppings(BBQ_CHICKEN, GREEN_PEPPER, PROVOLONE, CHEDDAR);
                break;
            case "Meatzza":
                crustField.setText("Stuffed");
                selectToppings(SAUSAGE, PEPPERONI, BEEF, HAM);
                break;
            case "Build Your Own":
                crustField.setText("Pan");
                selectedToppingsCount = 0;
                break;
        }
    }

    private void selectToppings(RadioButton... toppings) {
        for (RadioButton topping : toppings) {
            topping.setSelected(true);
        }
    }

    private void resetToppingSelections() {
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
                return size.equals("S") ? BBQ_S_PRICE : size.equals("M") ? BBQ_M_PRICE : BBQ_L_PRICE;
            case "Meatzza":
                return size.equals("S") ? MEATZZA_S_PRICE : size.equals("M") ? MEATZZA_M_PRICE : MEATZZA_L_PRICE;
            case "Build Your Own":
                return size.equals("S") ? BYO_S_PRICE : size.equals("M") ? BYO_M_PRICE : BYO_L_PRICE;
            default:
                return 0.0;
        }
    }

    @FXML
    private void addOrder() {
        Pizza pizza = null;
        PizzaFactory pizzaFactory = new ChicagoPizza();

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
        alert.setContentText("Your pizza has been added.");
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

        if (pizza.getToppings().size() > MAX_TOPPINGS) {
            pizza.removeTopping(pizza.getToppings().get(MAX_TOPPINGS));
        }
    }

    private void updatePizzaImage(String pizzaType) {
        Image image = null;
        switch (pizzaType) {
            case "Deluxe":
                image = new Image(getClass().getResourceAsStream("/images/ch_deluxe.png"));
                break;
            case "BBQ Chicken":
                image = new Image(getClass().getResourceAsStream("/images/ch_bbq.png"));
                break;
            case "Meatzza":
                image = new Image(getClass().getResourceAsStream("/images/ch_meat.png"));
                break;
            case "Build Your Own":
                image = new Image(getClass().getResourceAsStream("/images/ch_build.png"));
                break;
        }

        if (image != null) {
            ch_pic.setImage(image);
        } else {
        }
    }
}
