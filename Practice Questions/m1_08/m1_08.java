public class m1_08 {

    public static void printPizzaPerPerson (int numberOfPizzas, int numberOfPeople) {

        double pizzasPerPerson = (double) numberOfPizzas / (double) numberOfPeople;
        System.out.printf("Each person gets %s pizzas.\n", String.valueOf(pizzasPerPerson).replaceFirst("\\.0*$|(\\.\\d*?)0+$", "$1"));
    }

    public static void main (String[] args) {

        printPizzaPerPerson(3, 5);
        printPizzaPerPerson(4, 3);
        printPizzaPerPerson(4, 2);
    }
}
