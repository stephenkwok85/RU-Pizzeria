package pizzeria_package;
import java.util.List;

public class ChicagoPizza implements PizzaFactory{
    @Override
    public Pizza createDeluxe() {
        Pizza chicagoDeluxe = new Deluxe("Chicago", Crust.DEEP_DISH);
        chicagoDeluxe.setStyle("Chicago");
        chicagoDeluxe.setCrust(Crust.DEEP_DISH);
        chicagoDeluxe.setToppings(List.of(Topping.SAUSAGE, Topping.PEPPERONI, Topping.GREEN_PEPPER, Topping.ONION, Topping.MUSHROOM));
        return chicagoDeluxe;
    }

    @Override
    public Pizza createMeatzza() {
        Pizza chicagoMeatzza = new Meatzza("Chicago", Crust.STUFFED);
        chicagoMeatzza.setStyle("Chicago");
        chicagoMeatzza.setCrust(Crust.STUFFED);
        chicagoMeatzza.setToppings(List.of(Topping.SAUSAGE, Topping.PEPPERONI, Topping.BEEF, Topping.HAM));
        return chicagoMeatzza;
    }

    @Override
    public Pizza createBBQChicken() {
        Pizza chicagoBBQ = new BBQChicken("Chicago", Crust.PAN);
        chicagoBBQ.setStyle("Chicago");
        chicagoBBQ.setCrust(Crust.PAN);
        chicagoBBQ.setToppings(List.of(Topping.BBQ_CHICKEN, Topping.GREEN_PEPPER, Topping.PROVOLONE, Topping.CHEDDAR));
        return chicagoBBQ;
    }

    @Override
    public Pizza createBuildYourOwn() {
        Pizza chicagoBuildYourOwn = new BuildYourOwn("Chicago", Crust.PAN);
        chicagoBuildYourOwn.setStyle("Chicago");
        chicagoBuildYourOwn.setCrust(Crust.PAN);
        return chicagoBuildYourOwn;
    }
}
