package pizzeria_package;

import java.util.ArrayList;
import java.util.List;

public abstract class Pizza {
    private ArrayList<Topping> toppings;
    private Crust crust;
    private Size size;

    // Constructor to initialize the toppings list
    public Pizza() {
        this.toppings = new ArrayList<>();
    }

    // Abstract method to calculate the price, implemented by subclasses
    public abstract double price();

    // Getters and Setters for size, crust, and toppings

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Crust getCrust() {
        return crust;
    }

    public void setCrust(Crust crust) {
        this.crust = crust;
    }

    public List<Topping> getToppings() {
        return new ArrayList<>(toppings);
    }

    public void setToppings(List<Topping> toppings) {
        this.toppings = new ArrayList<>(toppings);
    }

    // manage toppings for BuildYourOwn
    public void addTopping(Topping topping) {
        if (toppings.size() < 7) {
            toppings.add(topping);
        } else {
            throw new IllegalStateException("Maximum of 7 toppings allowed");
        }
    }

    public void removeTopping(Topping topping) {
        toppings.remove(topping);
    }

    // temporary if needed later
    public void clearToppings() {
        toppings.clear();
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "toppings=" + toppings +
                ", crust=" + crust +
                ", size=" + size +
                ", price=" + String.format("%.2f", price()) +
                '}';
    }
}