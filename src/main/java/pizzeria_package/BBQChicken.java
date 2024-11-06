package pizzeria_package;
import java.util.List;

public class BBQChicken extends Pizza {

    public BBQChicken() {
        setToppings(List.of(Topping.BBQ_CHICKEN, Topping.GREEN_PEPPER, Topping.PROVOLONE, Topping.CHEDDAR));
    }

    @Override
    public double price() {
        switch (getSize()) {
            case SMALL:
                return 14.99;
            case MEDIUM:
                return 16.99;
            case LARGE:
                return 19.99;
            default:
                throw new IllegalStateException("Unexpected size: " + getSize());
        }
    }
}
