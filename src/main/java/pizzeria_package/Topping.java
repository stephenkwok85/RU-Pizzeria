package pizzeria_package;

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

    @Override
    public String toString() {
        String name = name().toLowerCase().replace("_", " ");
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
