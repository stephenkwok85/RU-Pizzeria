package com.example.ru_pizzeria;

import pizzeria_package.Pizza;
import java.util.HashMap;
import java.util.Map;

public class OrderManager {
    private static final Map<Integer, Pizza> orders = new HashMap<>();
    private static int nextOrderNumber = 1;

    // Method to add an order
    public static void addOrder(Pizza pizza) {
        orders.put(nextOrderNumber, pizza);
        nextOrderNumber++;
    }

    // Method to get an order by order number
    public static Pizza getOrder(int orderNumber) {
        return orders.get(orderNumber);
    }

    // Method to get the next order number
    public static int getNextOrderNumber() {
        return nextOrderNumber;
    }
}
