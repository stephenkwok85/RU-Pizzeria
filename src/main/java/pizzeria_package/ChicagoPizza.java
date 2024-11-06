package pizzeria_package;
import java.util.List;

public class ChicagoPizza implements PizzaFactory{
    @Override
    public Pizza createDeluxe() {
        Pizza deluxe = new Deluxe();
        deluxe.setCrust(Crust.DEEP_DISH);
        deluxe.setToppings(List.of(Topping.SAUSAGE, Topping.PEPPERONI, Topping.GREEN_PEPPER, Topping.ONION, Topping.MUSHROOM));
        return deluxe;
    }

    @Override
    public Pizza createMeatzza() {
        Pizza meatzza = new Meatzza();
        meatzza.setCrust(Crust.STUFFED);
        meatzza.setToppings(List.of(Topping.SAUSAGE, Topping.PEPPERONI, Topping.BEEF, Topping.HAM));
        return meatzza;
    }

    @Override
    public Pizza createBBQChicken() {
        Pizza bbqChicken = new BBQChicken();
        bbqChicken.setCrust(Crust.PAN);
        bbqChicken.setToppings(List.of(Topping.BBQ_CHICKEN, Topping.GREEN_PEPPER, Topping.PROVOLONE, Topping.CHEDDAR));
        return bbqChicken;
    }

    @Override
    public Pizza createBuildYourOwn() {
        Pizza buildYourOwn = new BuildYourOwn();
        buildYourOwn.setCrust(Crust.PAN);
        // Empty list when initializing b/c topping customizable
        return buildYourOwn;
    }
}
