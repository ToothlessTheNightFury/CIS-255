import java.time.LocalDateTime;

public class CreditCard {

    private final static int VALID_CARD_NUMBER_LENGTH = 16;

    private final static String VALID_CARD_NUMBER_PATTERN_SPACES =
            "(\\d)(\\d)(\\d)(\\d)(\\s)(\\d)(\\d)(\\d)(\\d)(\\s)(\\d)(\\d)(\\d)(\\d)(\\s)(\\d)(\\d)(\\d)(\\d)";
    private final static String VALID_CARD_NUMBER_PATTERN_NO_SPACES =
            "(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)";

    private final static int VALID_STARTING_DIGIT_4 = 4;
    private final static int VALID_STARTING_DIGIT_5 = 5;
    private final static int VALID_STARTING_DIGIT_6 = 6;

    private final static String VALID_EXPIRATION_PATTERN_M_YY = "(\\d)/(\\d)(\\d)";
    private final static String VALID_EXPIRATION_PATTERN_MM_YY = "(\\d)(\\d)/(\\d)(\\d)";
    private final static String VALID_EXPIRATION_PATTERN_M_YYYY = "(\\d)/(\\d)(\\d)(\\d)(\\d)";
    private final static String VALID_EXPIRATION_PATTERN_MM_YYYY = "(\\d)(\\d)/(\\d)(\\d)(\\d)(\\d)";

    private String cardNumber;
    private int cardExpirationMonth;
    private int cardExpirationYear;

    public static CreditCard promptCreditCard() {

        CreditCard newCard = new CreditCard();
        String cardExpirationDate;

        do {
            newCard.cardNumber = readCardNumberFromUser();
            cardExpirationDate = readExpirationDateFromUser();

        } while (cardExpirationDate.equalsIgnoreCase("back"));

        newCard.cardExpirationMonth = getCardExpirationMonth(cardExpirationDate);
        newCard.cardExpirationYear = getCardExpirationYear(cardExpirationDate);

        return newCard;
    }

    // In reality, if this was meant for a secure system, these functions would be
    // protected instead of public, and the values would be hashed / encrypted

    public void printFullInfo() {

        printCardNumber();
        printCardExpiration();
        System.out.println();
    }

    public void printCardNumber() {
        System.out.printf("Card Number: %s\n", cardNumber);
    }

    public void printCardExpiration() {
        System.out.printf("Expiration Date: %s\n", getCardExpirationDate());
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public double getCardNumberNum() {
        return Double.parseDouble(cardNumber.replaceAll("\\s", ""));
    }

    public int getCardExpirationMonth() {
        return cardExpirationMonth;
    }

    public int getCardExpirationYear() {
        return cardExpirationYear;
    }

    public String getCardExpirationDate() {
        return String.format("%02d/%d", cardExpirationMonth, cardExpirationYear);
    }

    // ====================readCardNumberFromUser()====================

    private static String readCardNumberFromUser() {
        return promptCardNumber("Please type in your 16-digit credit card number. You can enter it with or without spaces " +
                        "(ex. \"5500 0000 0000 0004\" or \"5500000000000004\").\n");
    }

    // ====================readCardNumberFromUser() HELPER FUNCTIONS====================

    private static String promptCardNumber (String message) {

        String cardNumberStr;
        boolean validInput;

        Utils.printMsg(message);

        do {

            cardNumberStr = Utils.promptStr();
            validInput = false;

            if (!validCardNumberPattern(cardNumberStr)) continue;
            if (!validCardStartingDigit(cardNumberStr)) continue;
            if (!validCheckSum(cardNumberStr)) continue;

            validInput = true;

        } while (!validInput);

        cardNumberStr = cardNumberStr.replaceAll("\\s", "");
        cardNumberStr = String.format("%s %s %s %s", cardNumberStr.substring(0, 4), cardNumberStr.substring(4, 8),
                cardNumberStr.substring(8, 12), cardNumberStr.substring(12, 16));

        System.out.printf("\nYour valid credit card number is:\n%s\n\n", cardNumberStr);

        return cardNumberStr;
    }

    // ====================readCardNumberFromUser() VERIFY FUNCTIONS====================

    private static boolean validCardNumberPattern (String cardExpirationDate) {

        boolean validCardNumberPattern = false;

        if (cardExpirationDate.matches(VALID_CARD_NUMBER_PATTERN_SPACES) ||
                cardExpirationDate.matches(VALID_CARD_NUMBER_PATTERN_NO_SPACES)) {
            validCardNumberPattern = true;
        }
        else {
            System.out.print("\nInput not recognized as valid credit card number. Please try again.\n");
        }
        return validCardNumberPattern;
    }

    private static boolean validCardStartingDigit (String input) {

        boolean isValidCardStartingDigit = switch (Integer.parseInt(String.valueOf(input.charAt(0)))) {
            case VALID_STARTING_DIGIT_4, VALID_STARTING_DIGIT_5, VALID_STARTING_DIGIT_6 -> true;
            default -> false;
        };

        if (!isValidCardStartingDigit) {
            System.out.printf("\nStarting digit of credit card number is invalid. Please try again.\n");
        }

        return isValidCardStartingDigit;
    }

    private static boolean validCheckSum (String input) {

        boolean validCheckSum = false;
        int checkSum = 0;
        int newDigit;

        /* Runs checkSum algorithm by
         * 1) Starting with the first digit, multiplies every other digit by 2 (1st, 3rd, 5th, etc.)
         *      a) If this multiplication gives a two digit number, add digits of those numbers together
         *      b) Use this result to replace original digit
         * 2) Add up the 15 digits AND the check digit (16 digits total)
         * 3) If this sum is divisible by 10, valid. If not, invalid
         */
        for (int i = 0; i < VALID_CARD_NUMBER_LENGTH - 1; i += 2) {

            // Multiplies every other digit by 2 and replace original digit
            // If result is two-digit, add digits together and replace
            newDigit = (2 * Character.getNumericValue(input.charAt(i)));
            newDigit = newDigit < 9 ? newDigit : 1 + (newDigit % 10);

            // Add newDigit and following digit in checkSum
            checkSum += newDigit + Character.getNumericValue(input.charAt(i + 1));
        }

        if (checkSum % 10 == 0) {
            validCheckSum = true;
        }
        else {
            System.out.print("\nYour credit card number is invalid (failed check sum). Please try again.\n");
        }

        return validCheckSum;
    }




    // ====================readExpirationDateFromUser()====================

    private static String readExpirationDateFromUser() {

        return promptCardExpirationDate(
                "Please type in your expiration date (ex. 1/23, 01/23, 1/2023, or 01/2023), or BACK to input a new credit card.\n");
    }

    // ====================readExpirationDateFromUser() HELPER FUNCTIONS====================

    private static String promptCardExpirationDate (String message) {

        String cardExpirationDate;
        int cardExpirationMonth;
        int cardExpirationYear;
        boolean validInput;

        Utils.printMsg(message);

        do {

            validInput = false;
            cardExpirationDate = Utils.promptStr();

            if (cardExpirationDate.equalsIgnoreCase("back")) {
                System.out.println();
                break;
            }

            if (!validCardExpirationDatePattern(cardExpirationDate)) continue;

            cardExpirationMonth = getCardExpirationMonth(cardExpirationDate);
            cardExpirationYear = getCardExpirationYear(cardExpirationDate);

            if (!validMonthRange(cardExpirationMonth)) continue;
            if (!validExpiration(cardExpirationMonth, cardExpirationYear)) continue;

            validInput = true;

        } while (!validInput);

        if (!cardExpirationDate.equalsIgnoreCase("back")) {
            System.out.printf("\nYour valid credit card expiration date is:\n%02d/%d\n\n",
                    getCardExpirationMonth(cardExpirationDate), getCardExpirationYear(cardExpirationDate));
        }

        return cardExpirationDate;
    }

    private static int getCardExpirationMonth (String cardExpirationDate) {

        final int MONTH_START_INDEX = 0;
        final int MONTH_END_INDEX = cardExpirationDate.indexOf("/");

        return Integer.parseInt(cardExpirationDate.substring(MONTH_START_INDEX, MONTH_END_INDEX));
    }

    private static int getCardExpirationYear (String cardExpirationDate) {

        final LocalDateTime CURRENT_TIME = LocalDateTime.now();
        final int CURRENT_YEAR = CURRENT_TIME.getYear();
        final int YEARS_IN_CENTURY = 1000;
        final int CURRENT_MILLENNIUM = (CURRENT_YEAR / YEARS_IN_CENTURY) * YEARS_IN_CENTURY;
        final int YEAR_START_INDEX = cardExpirationDate.indexOf("/") + 1;

        int cardExpirationYear = 0;

        // If user types in D/DD
        if (cardExpirationDate.matches(VALID_EXPIRATION_PATTERN_M_YY)) {
            cardExpirationYear = Integer.parseInt(cardExpirationDate.substring(YEAR_START_INDEX)) + CURRENT_MILLENNIUM;
        }
        // If user types in DD/DD
        else if (cardExpirationDate.matches(VALID_EXPIRATION_PATTERN_MM_YY)) {
            cardExpirationYear = Integer.parseInt(cardExpirationDate.substring(YEAR_START_INDEX)) + CURRENT_MILLENNIUM;
        }
        // If user types in D/DDDD
        else if (cardExpirationDate.matches(VALID_EXPIRATION_PATTERN_M_YYYY)) {
            cardExpirationYear = Integer.parseInt(cardExpirationDate.substring(YEAR_START_INDEX));
        }
        // If user types in DD/DDDD
        else if (cardExpirationDate.matches(VALID_EXPIRATION_PATTERN_MM_YYYY)) {
            cardExpirationYear = Integer.parseInt(cardExpirationDate.substring(YEAR_START_INDEX));
        }

        return cardExpirationYear;
    }

    // ====================readExpirationDateFromUser() VERIFY FUNCTIONS====================

    private static boolean validCardExpirationDatePattern (String cardExpirationDate) {

        boolean validForm = false;

        if (cardExpirationDate.matches(VALID_EXPIRATION_PATTERN_M_YY) ||
                cardExpirationDate.matches(VALID_EXPIRATION_PATTERN_MM_YY) ||
                cardExpirationDate.matches(VALID_EXPIRATION_PATTERN_M_YYYY) ||
                cardExpirationDate.matches(VALID_EXPIRATION_PATTERN_MM_YYYY)) {
            validForm = true;
        }
        else {
            System.out.print("\nInput not recognized as valid credit card expiration date. Please try again.\n");
        }
        return validForm;
    }

    private static boolean validMonthRange (int cardExpirationMonth) {

        final int MIN_MONTH = 1;
        final int MAX_MONTH = 12;

        boolean validMonthRange = (cardExpirationMonth >= MIN_MONTH) && (cardExpirationMonth <= MAX_MONTH);

        if (!validMonthRange) {
            System.out.print("\nPlease input a month number between 01 and 12.\n");
        }

        return validMonthRange;
    }

    private static boolean validExpiration (int cardExpirationMonth, int cardExpirationYear) {

        boolean validMonth = false;

        final LocalDateTime CURRENT_TIME = LocalDateTime.now();
        final int CURRENT_MONTH = CURRENT_TIME.getMonthValue();
        final int CURRENT_YEAR = CURRENT_TIME.getYear();

        if (cardExpirationYear > CURRENT_YEAR) {
            validMonth = true;
        }
        else if ((cardExpirationYear == CURRENT_YEAR) && (cardExpirationMonth > CURRENT_MONTH)) {
            validMonth = true;
        }
        else if ((cardExpirationYear == CURRENT_YEAR) && (cardExpirationMonth == CURRENT_MONTH)) {
            System.out.printf("WARNING! Your credit card will expire this month. Purchases with this card after %02d/%d will be invalid.\n",
                    cardExpirationMonth, cardExpirationYear);
            validMonth = true;
        }

        if (!validMonth) {
            System.out.print("\nYour credit card is expired. Please check the date or type BACK to input a new credit card.\n");
        }

        return validMonth;
    }
}
