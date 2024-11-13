package pizzeria_package;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int orderCounter = 1;
    private int number;
    private List<Pizza> pizzas;
    private boolean isPlaced;

    public Order() {
        this.number = orderCounter++;
        this.pizzas = new ArrayList<>();
        this.isPlaced = false;
    }

    public int getNumber() {
        return number;
    }

    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    public void removePizza(Pizza pizza) {
        pizzas.remove(pizza);
    }

    public List<Pizza> getPizzas() {
        return new ArrayList<>(pizzas);
    }

    public void clearOrder() {
        pizzas.clear();
    }

    public double calculateTotal(double taxRate) {
        double subtotal = pizzas.stream().mapToDouble(Pizza::price).sum();
        return subtotal + (subtotal * taxRate);
    }

    public double calculateSubtotal() {
        return pizzas.stream().mapToDouble(Pizza::price).sum();
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public void placeOrder() {
        this.isPlaced = true;
    }

    @Override
    public String toString() {
        return "Order Number: " + number + "\n" +
                "Pizzas: " + pizzas + "\n" +
                "Subtotal: $" + String.format("%.2f", calculateSubtotal()) + "\n" +
                "Status: " + (isPlaced ? "Placed" : "In Progress") + "\n";
    }
}
