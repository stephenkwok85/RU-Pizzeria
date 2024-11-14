package pizzeria_package;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The BuildYourOwnTest class contains unit tests for the BuildYourOwn pizza class,
 * verifying correct price calculations based on pizza size and number of toppings.
 *
 */
class BuildYourOwnTest {
    private BuildYourOwn chicagoPizza;
    private BuildYourOwn nyPizza;

    /**
     * Sets up test instances for each test case.
     */
    @BeforeEach
    void setUp() {
        chicagoPizza = new BuildYourOwn("Chicago", Crust.PAN);
        nyPizza = new BuildYourOwn("NY", Crust.HAND_TOSSED);
    }

    /**
     * Tests the price calculation for a small Chicago pizza with no toppings.
     */
    @Test
    void testPriceSmallNoToppings() {
        chicagoPizza.setSize(Size.SMALL);
        assertEquals(8.99, chicagoPizza.price(), "Expected price for small Chicago pizza with no toppings is $8.99");
    }

    /**
     * * Tests the price calculation for a medium Chicago pizza with three toppings.
     */
    @Test
    void testPriceMediumWithThreeToppings() {
        chicagoPizza.setSize(Size.MEDIUM);
        chicagoPizza.addTopping(Topping.PEPPERONI);
        chicagoPizza.addTopping(Topping.SAUSAGE);
        chicagoPizza.addTopping(Topping.MUSHROOM);
        assertEquals(10.99 + (1.69 * 3), chicagoPizza.price(), "Expected price for medium Chicago pizza with 3 toppings");
    }

    /**
     * Tests the price calculation for a large New York pizza with the maximum allowed toppings (7).
     * */
    @Test
    void testPriceLargeWithMaxToppings() {
        nyPizza.setSize(Size.LARGE);
        for (int i = 0; i < 7; i++) {
            nyPizza.addTopping(Topping.PEPPERONI);
        }
        assertEquals(12.99 + (1.69 * 7), nyPizza.price(), "Expected price for large NY pizza with 7 toppings");
    }

    /**
     * Tests the price calculation for a medium New York pizza with one topping.
     */
    @Test
    void testPriceMediumOneTopping() {
        nyPizza.setSize(Size.MEDIUM);
        nyPizza.addTopping(Topping.ONION);
        assertEquals(10.99 + 1.69, nyPizza.price(), "Expected price for medium NY pizza with 1 topping");
    }

    /**
     * Tests the price calculation for a large Chicago pizza with no toppings.
     */
    @Test
    void testPriceLargeNoToppings() {
        chicagoPizza.setSize(Size.LARGE);
        assertEquals(12.99, chicagoPizza.price(), "Expected price for large Chicago pizza with no toppings is $12.99");
    }
}