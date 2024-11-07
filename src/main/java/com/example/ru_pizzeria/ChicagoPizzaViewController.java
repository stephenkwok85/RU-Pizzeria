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

public class ChicagoPizzaViewController {

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

    private boolean isCustomizable = false;
    private int selectedToppingsCount = 0;
    private final int MAX_TOPPINGS = 7;

    @FXML
    public void initialize() {
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
        });

        sizeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> updatePizzaPrice());
        setupToppingListeners();
        updatePizzaPrice();
    }

    private void setPizzaOptions(String pizzaType) {
        crustField.clear();
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
                crustField.setText("Deep Dish");
                SAUSAGE.setSelected(true);
                PEPPERONI.setSelected(true);
                GREEN_PEPPER.setSelected(true);
                ONION.setSelected(true);
                MUSHROOM.setSelected(true);
                break;
            case "BBQ Chicken":
                crustField.setText("Pan");
                BBQ_CHICKEN.setSelected(true);
                GREEN_PEPPER.setSelected(true);
                PROVOLONE.setSelected(true);
                CHEDDAR.setSelected(true);
                break;
            case "Meatzza":
                crustField.setText("Stuffed");
                SAUSAGE.setSelected(true);
                PEPPERONI.setSelected(true);
                BEEF.setSelected(true);
                HAM.setSelected(true);
                break;
            case "Build Your Own":
                crustField.setText("Pan");
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

        double totalPrice = basePrice + (1.69 * toppingCount);
        pizza_price.setText(String.format("%.2f", totalPrice));
    }

    private double calculateBasePrice(String type, String size) {
        switch (type) {
            case "Deluxe":
                return size.equals("S") ? 16.99 : size.equals("M") ? 18.99 : 20.99;
            case "BBQ Chicken":
                return size.equals("S") ? 14.99 : size.equals("M") ? 16.99 : 19.99;
            case "Meatzza":
                return size.equals("S") ? 17.99 : size.equals("M") ? 19.99 : 21.99;
            case "Build Your Own":
                return size.equals("S") ? 8.99 : size.equals("M") ? 10.99 : 12.99;
            default:
                return 0.0;
        }
    }

    /*
    This is Just example to see if order button is working. This should be changed using classes.
     */
    @FXML
    private void addOrder() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Order Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Your pizza is added");
        alert.showAndWait();

        String selectedType = choose_type.getValue();
        String selectedSize = ((RadioButton) sizeGroup.getSelectedToggle()).getText();
        String selectedCrust = crustField.getText();
        double totalPrice = Double.parseDouble(pizza_price.getText());

        StringBuilder selectedToppings = new StringBuilder();
        if (SAUSAGE.isSelected()) selectedToppings.append("Sausage, ");
        if (PEPPERONI.isSelected()) selectedToppings.append("Pepperoni, ");
        if (GREEN_PEPPER.isSelected()) selectedToppings.append("Green Pepper, ");
        if (ONION.isSelected()) selectedToppings.append("Onion, ");
        if (MUSHROOM.isSelected()) selectedToppings.append("Mushroom, ");
        if (BBQ_CHICKEN.isSelected()) selectedToppings.append("BBQ Chicken, ");
        if (BEEF.isSelected()) selectedToppings.append("Beef, ");
        if (HAM.isSelected()) selectedToppings.append("Ham, ");
        if (PROVOLONE.isSelected()) selectedToppings.append("Provolone, ");
        if (CHEDDAR.isSelected()) selectedToppings.append("Cheddar, ");
        if (OLIVES.isSelected()) selectedToppings.append("Olives, ");
        if (SPINACH.isSelected()) selectedToppings.append("Spinach, ");
        if (PINEAPPLE.isSelected()) selectedToppings.append("Pineapple, ");
        if (BACON.isSelected()) selectedToppings.append("Bacon, ");
        if (selectedToppings.length() > 0) {
            selectedToppings.setLength(selectedToppings.length() - 2);
        }

        //int orderNumber = generateOrderNumber();

        System.out.println("Chicago Order Details:");
        System.out.println("Type: " + selectedType);
        System.out.println("Size: " + selectedSize);
        System.out.println("Crust: " + selectedCrust);
        System.out.println("Toppings: " + selectedToppings);
        System.out.println("Price: $" + totalPrice);
        //System.out.println("Order Number: " + orderNumber);
        System.out.println("================================");
    }
}
