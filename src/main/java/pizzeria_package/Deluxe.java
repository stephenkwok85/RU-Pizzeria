package pizzeria_package;
import java.util.List;

public class Deluxe extends Pizza {

    public Deluxe() {
        setToppings(List.of(Topping.SAUSAGE, Topping.PEPPERONI, Topping.GREEN_PEPPER, Topping.ONION, Topping.MUSHROOM));
    }

    @Override
    public double price() {
        switch (getSize()) {
            case SMALL:
                return 16.99;
            case MEDIUM:
                return 18.99;
            case LARGE:
                return 20.99;
            default:
                throw new IllegalStateException("Unexpected size: " + getSize());
        }
    }
}