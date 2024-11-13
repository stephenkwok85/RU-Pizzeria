package pizzeria_package;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BuildYourOwnTest {
    private BuildYourOwn chicagoPizza;
    private BuildYourOwn nyPizza;

    @BeforeEach
    void setUp() {
        chicagoPizza = new BuildYourOwn("Chicago", Crust.PAN);
        nyPizza = new BuildYourOwn("NY", Crust.HAND_TOSSED);
    }

    @Test
    void testPriceSmallNoToppings() {
        chicagoPizza.setSize(Size.SMALL);
        assertEquals(8.99, chicagoPizza.price(), "Expected price for small Chicago pizza with no toppings is $8.99");
    }

    @Test
    void testPriceMediumWithThreeToppings() {
        chicagoPizza.setSize(Size.MEDIUM);
        chicagoPizza.addTopping(Topping.PEPPERONI);
        chicagoPizza.addTopping(Topping.SAUSAGE);
        chicagoPizza.addTopping(Topping.MUSHROOM);
        assertEquals(10.99 + (1.69 * 3), chicagoPizza.price(), "Expected price for medium Chicago pizza with 3 toppings");
    }

    @Test
    void testPriceLargeWithMaxToppings() {
        nyPizza.setSize(Size.LARGE);
        for (int i = 0; i < 7; i++) {
            nyPizza.addTopping(Topping.PEPPERONI);
        }
        assertEquals(12.99 + (1.69 * 7), nyPizza.price(), "Expected price for large NY pizza with 7 toppings");
    }

    @Test
    void testPriceMediumOneTopping() {
        nyPizza.setSize(Size.MEDIUM);
        nyPizza.addTopping(Topping.ONION);
        assertEquals(10.99 + 1.69, nyPizza.price(), "Expected price for medium NY pizza with 1 topping");
    }

    @Test
    void testPriceLargeNoToppings() {
        chicagoPizza.setSize(Size.LARGE);
        assertEquals(12.99, chicagoPizza.price(), "Expected price for large Chicago pizza with no toppings is $12.99");
    }
}