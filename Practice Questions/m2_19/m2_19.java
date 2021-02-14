import java.util.Random;
import java.util.Scanner;

public class m2_19 {

    static double promptNum (String message) {

        System.out.printf("%s", message);

        double num;
        Scanner scanner = new Scanner(System.in);

        while (!scanner.hasNextDouble()) {

            // If user does not input a number
            System.out.print("Please input a number: ");
            scanner = new Scanner(System.in);
        }

        num = scanner.nextDouble();
        return num;
    }

    public static int flipCoin (int numOfFlips) {

        if (numOfFlips <= 0) {
            System.out.printf("You must input a number greater than 0!\n\n");
            return -1;
        }

        Random generator = new Random();
        boolean headSideUp;
        int numOfHeads = 0;

        for (int i = 1; i <= numOfFlips; i++) {

            headSideUp = generator.nextBoolean();

            if (headSideUp)
                numOfHeads++;

            System.out.printf("%d. %s\n", i, headSideUp ? "Heads" : "Tails");
        }

        System.out.printf("\nYou flipped %d coins that landed on heads!\n\n", numOfHeads);
        return numOfHeads;
    }

    public static void main(String[] args) {

        double input = promptNum("Enter the number of coin flips, or 0 to exit: ");

        while (input != 0) {

            flipCoin((int) input);
            input = promptNum("Enter the number of coin flips, or 0 to exit: ");
        }
    }
}
