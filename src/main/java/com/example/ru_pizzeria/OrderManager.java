package com.example.ru_pizzeria;

import pizzeria_package.Order;
import pizzeria_package.Pizza;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private static final Map<Integer, Order> orders = new HashMap<>();
    private static int nextOrderNumber = 1;
    private static int currentOrderNumber = 0;

    public static void addOrderToCurrentOrder(Pizza pizza) {
        if (currentOrderNumber == 0) {
            currentOrderNumber = nextOrderNumber;
            orders.put(currentOrderNumber, new Order());
        }
        orders.get(currentOrderNumber).addPizza(pizza);
    }

    public static List<Pizza> getOrder(int orderNumber) {
        Order order = orders.get(orderNumber);
        return (order != null && !order.isPlaced()) ? order.getPizzas() : null;
    }

    public static List<Pizza> getPlacedOrder(int orderNumber) {
        Order order = orders.get(orderNumber);
        return (order != null && order.isPlaced()) ? order.getPizzas() : null;
    }

    public static int getCurrentOrderNumber() {
        return currentOrderNumber;
    }

    public static void completeCurrentOrder() {
        if (currentOrderNumber != 0 && orders.containsKey(currentOrderNumber)) {
            orders.get(currentOrderNumber).placeOrder();
            currentOrderNumber = 0;
            nextOrderNumber++;
        }
    }

    public static int getNextOrderNumber() {
        return nextOrderNumber;
    }

    public static boolean deleteOrder(int orderNumber) {
        Order order = orders.get(orderNumber);
        if (order != null && order.isPlaced()) {
            orders.remove(orderNumber);
            return true;
        }
        return false;
    }

    public static void updateOrder(int orderNumber, List<Pizza> updatedPizzas) {
        Order order = orders.get(orderNumber);
        if (order != null && !order.isPlaced()) {
            order.clearOrder();
            for (Pizza pizza : updatedPizzas) {
                order.addPizza(pizza);
            }
        }
    }

    public static List<Integer> getAllOrderNumbers() {
        return new ArrayList<>(orders.keySet());
    }

    public static List<Integer> getPlacedOrderNumbers() {
        List<Integer> placedOrderNumbers = new ArrayList<>();
        for (Order order : orders.values()) {
            if (order.isPlaced()) {
                placedOrderNumbers.add(order.getNumber());
            }
        }
        return placedOrderNumbers;
    }
}
