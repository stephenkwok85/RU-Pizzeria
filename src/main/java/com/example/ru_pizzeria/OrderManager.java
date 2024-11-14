package com.example.ru_pizzeria;

import pizzeria_package.Order;
import pizzeria_package.Pizza;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the orders in the pizza ordering application. This class allows
 * for adding pizzas to an order, managing order completion, updating orders,
 * and retrieving placed and unplaced orders. Orders are stored in a map with
 * a unique order number for each.
 *
 * @author Stephen Kwok and Jeongtae Kim
 */
public class OrderManager {

    private static final Map<Integer, Order> orders = new HashMap<>();
    private static int nextOrderNumber = 1;
    private static int currentOrderNumber = 0;

    /**
     * Adds a pizza to the current order. If no current order exists,
     * initializes a new order with the next order number.
     *
     * @param pizza The pizza to add to the current order.
     */
    public static void addOrderToCurrentOrder(Pizza pizza) {
        if (currentOrderNumber == 0) {
            currentOrderNumber = nextOrderNumber;
            orders.put(currentOrderNumber, new Order());
        }
        orders.get(currentOrderNumber).addPizza(pizza);
    }

    /**
     * Retrieves the pizzas in an unplaced order by order number.
     *
     * @param orderNumber The order number to retrieve.
     * @return A list of pizzas in the order, or null if the order is placed or doesn't exist.
     */
    public static List<Pizza> getOrder(int orderNumber) {
        Order order = orders.get(orderNumber);
        return (order != null && !order.isPlaced()) ? order.getPizzas() : null;
    }

    /**
     * Retrieves the pizzas in a placed order by order number.
     *
     * @param orderNumber The order number to retrieve.
     * @return A list of pizzas in the placed order, or null if the order is unplaced or doesn't exist.
     */
    public static List<Pizza> getPlacedOrder(int orderNumber) {
        Order order = orders.get(orderNumber);
        return (order != null && order.isPlaced()) ? order.getPizzas() : null;
    }

    /**
     * Gets the current order number.
     *
     * @return The current order number.
     */
    public static int getCurrentOrderNumber() {
        return currentOrderNumber;
    }

    /**
     * Completes the current order by marking it as placed.
     * Increments the next order number and resets the current order.
     */
    public static void completeCurrentOrder() {
        if (currentOrderNumber != 0 && orders.containsKey(currentOrderNumber)) {
            orders.get(currentOrderNumber).placeOrder();
            currentOrderNumber = 0;
            nextOrderNumber++;
        }
    }

    /**
     * Deletes a placed order by order number.
     *
     * @param orderNum The order number to delete.
     * @return True if the placed order was deleted, false otherwise.
     */
    public static boolean deletePlacedOrder(int orderNum) {
        if (orders.containsKey(orderNum)) {
            orders.remove(orderNum);
            return true;
        }
        return false;
    }

    /**
     * Updates an unplaced order with a new list of pizzas.
     *
     * @param orderNumber   The order number to update.
     * @param updatedPizzas The new list of pizzas for the order.
     */
    public static void updateOrder(int orderNumber, List<Pizza> updatedPizzas) {
        Order order = orders.get(orderNumber);
        if (order != null && !order.isPlaced()) {
            order.clearOrder();
            for (Pizza pizza : updatedPizzas) {
                order.addPizza(pizza);
            }
        }
    }

    /**
     * Gets a list of all placed order numbers.
     *
     * @return A list of placed order numbers.
     */
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