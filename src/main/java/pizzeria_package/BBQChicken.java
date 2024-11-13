package pizzeria_package;

import java.util.List;

public class BBQChicken extends Pizza {

    private static final double SMALL_PRICE = 14.99;
    private static final double MEDIUM_PRICE = 16.99;
    private static final double LARGE_PRICE = 19.99;

    public BBQChicken(String style, Crust crust) {
        super(style);
        setCrust(crust);
        setToppings(List.of(Topping.BBQ_CHICKEN, Topping.GREEN_PEPPER, Topping.PROVOLONE, Topping.CHEDDAR));
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
