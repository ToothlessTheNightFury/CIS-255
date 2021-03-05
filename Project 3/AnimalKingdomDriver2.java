public class AnimalKingdomDriver2 {

    public static void main (String[] args) {

        Animal spirit = new Horse(0, "Spirit");
        Animal freeWilly = new BlueWhale(1, "Free Willy");
        Animal geeBilly = new BelugaWhale(2, "Gee Billy");

        System.out.println(spirit);
        System.out.println(freeWilly);
        System.out.println(geeBilly);
    }
}