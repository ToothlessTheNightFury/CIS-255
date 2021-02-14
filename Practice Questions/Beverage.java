import java.util.*;

public class Beverage {

    enum TypeBeverage {
        DEFAULT(0.0), MILK(0.0), SODA(0.05), BEER(0.1);

        private double tax;
        private static final double MIN_TAX = 0.0;

        private TypeBeverage (double tax) {
            this.tax = Math.max(tax, MIN_TAX);
        }

        public double getTax() {
            return tax;
        }
    }

    private String name;
    private TypeBeverage type;
    private double cost;

    private static final double MIN_COST = 0.0;

    public Beverage (String name, TypeBeverage type, double cost) {
        this.name = name;
        this.type = type;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public TypeBeverage getType() {
        return type;
    }

    public double getCost() {
        return cost;
    }

    public double getTax() {
        return type.getTax();
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setType (TypeBeverage type) {
        this.type = type;
    }

    public void setCost (double cost) {
        this.cost = Math.max(cost, MIN_COST);
    }

    public String toString() {
        return String.format("%s\nType:       %s\nCost:       $%.2f\nTax:        %.0f%%\nTotal Cost: $%.2f\n", name, type, cost, getTax() * 100, calculateTotalCost());
    }

    public double calculateTotalCost() {
        return cost + cost * getTax();
    }

    public static void main (String[] args) {
        Beverage mooMilk = new Beverage("Moo Milk", TypeBeverage.MILK, 6.90);
        System.out.println(mooMilk);

        Beverage sprunk = new Beverage("Sprunk", TypeBeverage.SODA, 4.20);
        System.out.println(sprunk);

        Beverage citrusMistress = new Beverage("Citrus Mistress", TypeBeverage.BEER, 2.99);
        System.out.println(citrusMistress);

        Beverage water = new Beverage ("Water", TypeBeverage.DEFAULT, 0.99);
        System.out.println(water);
    }
}
