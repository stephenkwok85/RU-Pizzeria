package com.example.ru_pizzeria;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class ChicagoPizzaViewController {

    @FXML
    private ComboBox<String> typeComboBox; // 피자 종류 선택 (Deluxe, BBQ Chicken, Meatzza, Build Your Own)

    @FXML
    private ComboBox<String> sizeComboBox; // 피자 크기 선택 (Small, Medium, Large)

    @FXML
    private RadioButton deepDishRadio; // Deep Dish 크러스트 선택
    @FXML
    private RadioButton panRadio; // Pan 크러스트 선택
    @FXML
    private ToggleGroup crustGroup; // 라디오 버튼 그룹

    @FXML
    private ListView<String> toppingsListView; // 토핑 선택 리스트

    // 선택된 피자의 type, size, crust, toppings을 가져오는 메서드
    public void submitPizzaSelection() {
        // Type 선택
        String selectedType = typeComboBox.getValue();

        // Size 선택
        String selectedSize = sizeComboBox.getValue();

        // Crust 선택
        RadioButton selectedCrustButton = (RadioButton) crustGroup.getSelectedToggle();
        String selectedCrust = selectedCrustButton.getText();

        // Toppings 선택
        ObservableList<String> selectedToppings = toppingsListView.getSelectionModel().getSelectedItems();

        // 결과를 출력하거나 다른 처리를 할 수 있습니다
        System.out.println("Selected Type: " + selectedType);
        System.out.println("Selected Size: " + selectedSize);
        System.out.println("Selected Crust: " + selectedCrust);
        System.out.println("Selected Toppings: " + selectedToppings);
    }
}
