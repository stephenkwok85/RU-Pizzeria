package pizzeria_package;
import model.project1.List;

public abstract class Pizza {
    private List<Topping> toppings; //Topping is a Enum class
    private Crust crust; //Crust is a Enum class
    private Size size; //Size is a Enum class
    public abstract double price();
}