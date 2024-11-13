package pizzeria_package;
import java.util.List;

public class NYPizza implements PizzaFactory {

    @Override
    public Pizza createDeluxe() {
        Pizza nyDeluxe = new Deluxe("NY", Crust.BROOKLYN);
        nyDeluxe.setStyle("NY");
        nyDeluxe.setCrust(Crust.BROOKLYN);
        nyDeluxe.setToppings(List.of(Topping.SAUSAGE, Topping.PEPPERONI, Topping.GREEN_PEPPER, Topping.ONION, Topping.MUSHROOM));
        return nyDeluxe;
    }

    @Override
    public Pizza createMeatzza() {
        Pizza nyMeatzza = new Meatzza("NY", Crust.HAND_TOSSED);
        nyMeatzza.setStyle("NY");
        nyMeatzza.setCrust(Crust.HAND_TOSSED);
        nyMeatzza.setToppings(List.of(Topping.SAUSAGE, Topping.PEPPERONI, Topping.BEEF, Topping.HAM));
        return nyMeatzza;
    }

    @Override
    public Pizza createBBQChicken() {
        Pizza nyBBQ = new BBQChicken("NY", Crust.THIN);
        nyBBQ.setStyle("NY");
        nyBBQ.setCrust(Crust.THIN);
        nyBBQ.setToppings(List.of(Topping.BBQ_CHICKEN, Topping.GREEN_PEPPER, Topping.PROVOLONE, Topping.CHEDDAR));
        return nyBBQ;
    }

    @Override
    public Pizza createBuildYourOwn() {
        Pizza nyBuildYourOwn = new BuildYourOwn("NY", Crust.HAND_TOSSED);
        nyBuildYourOwn.setStyle("NY");
        nyBuildYourOwn.setCrust(Crust.HAND_TOSSED);
        return nyBuildYourOwn;
    }
}