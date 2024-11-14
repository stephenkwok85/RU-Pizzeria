package pizzeria_package;

/**
 * The Topping enum represents the various toppings that can be added to a pizza.
 *
 */
public enum Topping {
    SAUSAGE,
    PEPPERONI,
    GREEN_PEPPER,
    ONION,
    MUSHROOM,
    BBQ_CHICKEN,
    BEEF,
    HAM,
    PROVOLONE,
    CHEDDAR,
    OLIVES,
    SPINACH,
    PINEAPPLE,
    BACON;

    /**
     * Overrides the default toString() method to provide a user-friendly format for each enum value.
     *
     * @return A formatted string representation of the enum constant name.
     */
    @Override
    public String toString() {
        String name = name().toLowerCase().replace("_", " ");
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
