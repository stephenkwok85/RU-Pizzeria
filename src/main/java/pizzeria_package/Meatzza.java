package pizzeria_package;
import java.util.List;

public class Meatzza extends Pizza {

    public Meatzza() {
        setToppings(List.of(Topping.SAUSAGE, Topping.PEPPERONI, Topping.BEEF, Topping.HAM));
    }

    @Override
    public double price() {
        switch (getSize()) {
            case SMALL:
                return 17.99;
            case MEDIUM:
                return 19.99;
            case LARGE:
                return 21.99;
            default:
                throw new IllegalStateException("Unexpected size: " + getSize());
        }
    }
}