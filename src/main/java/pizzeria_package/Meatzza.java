package pizzeria_package;

import java.util.List;

public class Meatzza extends Pizza {

    private static final double SMALL_PRICE = 17.99;
    private static final double MEDIUM_PRICE = 19.99;
    private static final double LARGE_PRICE = 21.99;

    public Meatzza(String style, Crust crust) {
        super(style);
        setCrust(crust);
        setToppings(List.of(Topping.SAUSAGE, Topping.PEPPERONI, Topping.BEEF, Topping.HAM));
    }

    @Override
    public double price() {
        switch (getSize()) {
            case SMALL:
                return SMALL_PRICE;
            case MEDIUM:
                return MEDIUM_PRICE;
            case LARGE:
                return LARGE_PRICE;
            default:
                throw new IllegalStateException("Unexpected size: " + getSize());
        }
    }
}
