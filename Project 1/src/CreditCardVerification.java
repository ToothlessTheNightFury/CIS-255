import java.util.StringTokenizer;
import java.time.LocalDateTime;

public class CreditCardVerification {

    public static void main (String[] args) {

        Utils.printTitle("Credit Card Verification", "Benson Yee");

        CreditCard card = new CreditCard();
        card = CreditCard.promptCreditCard();
        card.printFullInfo();

        Utils.printVar("cardNumber", card.getCardNumber());
        Utils.printVar("getCardNumberNum()", card.getCardNumberNum());
        System.out.println();

        Utils.printVar("cardExpirationMonth", card.getCardExpirationMonth());
        Utils.printVar("cardExpirationYear", card.getCardExpirationYear());
        Utils.printVar("getCardExpirationDate()", card.getCardExpirationDate());
        System.out.println();

        Utils.printCredits("Benson Yee");
    }
}
