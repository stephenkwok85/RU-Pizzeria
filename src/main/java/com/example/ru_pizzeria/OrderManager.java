package com.example.ru_pizzeria;

import pizzeria_package.Pizza;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private static final Map<Integer, List<Pizza>> orders = new HashMap<>();
    private static int nextOrderNumber = 1;  // Tracks the next available order number, starting at 1
    private static int currentOrderNumber = 1;  // Start the current order number at 1

    public static void addOrderToCurrentOrder(Pizza pizza) {
        // If there is no active order, start a new one with the next order number
        if (currentOrderNumber == 0) {
            currentOrderNumber = nextOrderNumber;  // Set the current order number to the next available order number
            orders.put(currentOrderNumber, new ArrayList<>()); // Create a new list for this order
        }

        // Check if the list for the current order exists, if not, create it
        if (orders.get(currentOrderNumber) == null) {
            orders.put(currentOrderNumber, new ArrayList<>());  // Initialize the list if not present
        }

        // Add pizza to the current order's list
        orders.get(currentOrderNumber).add(pizza);
    }

    // Method to get an order by order number
    public static List<Pizza> getOrder(int orderNumber) {
        return orders.get(orderNumber);
    }

    // Method to get the current order number
    public static int getCurrentOrderNumber() {
        return currentOrderNumber;
    }

    // Method to complete the current order and increment the order number for the next order
    public static void completeCurrentOrder() {
        nextOrderNumber++;  // Increment the next order number
        currentOrderNumber = nextOrderNumber;  // Set the current order number to the next order number
    }

    // Method to get the next order number
    public static int getNextOrderNumber() {
        return nextOrderNumber;
    }

    // Method to delete an order
    public static boolean deleteOrder(int orderNumber) {
        System.out.println("Deleting Order #" + orderNumber);
        if (orders.containsKey(orderNumber)) {
            orders.remove(orderNumber);  // Remove the order from the map
            System.out.println("Order #" + orderNumber + " has been deleted.");
            return true;
        }
        System.out.println("Order #" + orderNumber + " not found in the orders.");
        return false;
    }
}