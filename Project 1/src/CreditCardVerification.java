import java.util.StringTokenizer;
import java.time.LocalDateTime;

public class CreditCardVerification {

    public static void main (String[] args) {

        Utils.printTitle("Credit Card Verification", "Benson Yee");

        CreditCard card = new CreditCard();
        card = CreditCard.promptCreditCard();
        card.printFullInfo();

        Utils.printCredits("Benson Yee");
    }
}
