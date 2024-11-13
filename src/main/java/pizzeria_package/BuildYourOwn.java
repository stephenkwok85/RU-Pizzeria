package pizzeria_package;

import java.util.ArrayList;

public class BuildYourOwn extends Pizza {

    private static final double BASE_PRICE_SMALL = 8.99;
    private static final double BASE_PRICE_MEDIUM = 10.99;
    private static final double BASE_PRICE_LARGE = 12.99;
    private static final double TOPPING_PRICE = 1.69;
    private static final int MAX_TOPPINGS = 7;

    public BuildYourOwn(String style, Crust crust) {
        super(style);
        setCrust(crust);
        setToppings(new ArrayList<>());
    }

    public void addTopping(Topping topping) {
        if (getToppings().size() < MAX_TOPPINGS) {
            getToppings().add(topping);
        } else {
            throw new IllegalStateException("Maximum of " + MAX_TOPPINGS + " toppings allowed");
        }
    }

    public void removeTopping(Topping topping) {
        getToppings().remove(topping);
    }

    @Override
    public double price() {
        double basePrice;
        switch (getSize()) {
            case SMALL:
                basePrice = BASE_PRICE_SMALL;
                break;
            case MEDIUM:
                basePrice = BASE_PRICE_MEDIUM;
                break;
            case LARGE:
                basePrice = BASE_PRICE_LARGE;
                break;
            default:
                throw new IllegalStateException("Unexpected size: " + getSize());
        }
        return basePrice + (TOPPING_PRICE * getToppings().size());
    }
}