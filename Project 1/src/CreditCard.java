import java.time.LocalDateTime;

public class CreditCard {

    private final static int VALID_CREDIT_CARD_NUMBER_LENGTH = 16;

    private final static String VALID_CREDIT_CARD_NUMBER_PATTERN_1 =
            "(\\d)(\\d)(\\d)(\\d)(\\s)(\\d)(\\d)(\\d)(\\d)(\\s)(\\d)(\\d)(\\d)(\\d)(\\s)(\\d)(\\d)(\\d)(\\d)";
    private final static String VALID_CREDIT_CARD_NUMBER_PATTERN_2 =
            "(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)";

    private final static int VALID_STARTING_DIGIT_1 = 4;
    private final static int VALID_STARTING_DIGIT_2 = 5;
    private final static int VALID_STARTING_DIGIT_3 = 6;

    private final static String VALID_EXPIRATION_DATE_PATTERN_1 = "(\\d)(\\d)/(\\d)(\\d)";
    private final static String VALID_EXPIRATION_DATE_PATTERN_2 = "(\\d)(\\d)/(\\d)(\\d)(\\d)(\\d)";

    private String creditCardNumber;
    private int creditCardExpirationMonth;
    private int creditCardExpirationYear;

    // TODO: Create get and set functions
    // TODO: Use printCreditCardNumber() for valid credit card inputs to reduce repetition
    // TODO: Use printCreditCardDate() for valid credit date inputs to reduce repetition
    // TODO: Constructor that takes in number, month, and year
    // TODO: Shrink names to smaller maybe
    // TODO: General cleanup

    public static CreditCard promptCreditCard() {

        CreditCard newCard = new CreditCard();
        String creditCardExpirationDate;

        do {
            newCard.creditCardNumber = readCreditCardNumberFromUser();
            creditCardExpirationDate = readExpirationDateFromUser();

        } while (creditCardExpirationDate.equalsIgnoreCase("back"));

        newCard.creditCardExpirationMonth = getCreditCardExpirationMonth(creditCardExpirationDate);
        newCard.creditCardExpirationYear = getCreditCardExpirationYear(creditCardExpirationDate);

        return newCard;
    }

    public void printFullInfo() {

        printCreditCardNumber();
        printCreditCardExpiration();
        System.out.println();
    }

    public void printCreditCardNumber() {
        System.out.printf("Card Number: %s\n", creditCardNumber);
    }


    public void printCreditCardExpiration() {
        System.out.printf("Expiration Date: %02d/%d\n", creditCardExpirationMonth, creditCardExpirationYear);
    }


    // ====================readCreditCardNumberFromUser()====================

    private static String readCreditCardNumberFromUser() {

        String creditCardNumber = promptCreditCardNumber(
                "Please input your 16-digit credit card number. You can type it with or without spaces " +
                        "(ex. \"5500 0000 0000 0004\" or \"5500000000000004\").\n");
        return creditCardNumber;
    }

    // ====================readCreditCardNumberFromUser() HELPER FUNCTIONS====================

    private static String promptCreditCardNumber (String message) {

        String creditCardNumberStr;
        boolean validInput;

        Utils.printMsg(message);

        do {

            creditCardNumberStr = Utils.promptStr();
            validInput = false;

            if (validCreditCardNumberPattern(creditCardNumberStr) &&
                    validCreditCardStartingDigit(creditCardNumberStr) && validCheckSum(creditCardNumberStr)) {
                validInput = true;
            }

        } while (!validInput);

        creditCardNumberStr = creditCardNumberStr.replaceAll("\\s", "");
        creditCardNumberStr = creditCardNumberStr.substring(0, 4) + " " + creditCardNumberStr.substring(4, 8) + " " +
                creditCardNumberStr.substring(8, 12) + " " + creditCardNumberStr.substring(12, 16);

        System.out.printf("\nYour valid credit card number is:\n%s", creditCardNumberStr);
        System.out.printf("\n\n");

        return creditCardNumberStr;
    }

    // ====================readCreditCardNumberFromUser() VERIFY FUNCTIONS====================

    private static boolean validCreditCardNumberPattern (String creditCardExpirationDateStr) {

        boolean validCreditCardNumberPattern = false;

        if (creditCardExpirationDateStr.matches(VALID_CREDIT_CARD_NUMBER_PATTERN_1) ||
                creditCardExpirationDateStr.matches(VALID_CREDIT_CARD_NUMBER_PATTERN_2)) {
            validCreditCardNumberPattern = true;
        }
        else {
            System.out.printf("\nPattern not recognized as valid credit card number. Please try again.\n");
        }
        return validCreditCardNumberPattern;
    }

    private static boolean validCreditCardStartingDigit (String input) {

        boolean isValidCreditCardStartingDigit = false;

        switch(Integer.parseInt(String.valueOf(input.charAt(0)))) {

            case VALID_STARTING_DIGIT_1:
            case VALID_STARTING_DIGIT_2:
            case VALID_STARTING_DIGIT_3:
                isValidCreditCardStartingDigit = true;
                break;
        }

        if (!isValidCreditCardStartingDigit) {
            System.out.printf("\nPlease input a credit card number whose starting digit is %d, %d, or %d.\n", VALID_STARTING_DIGIT_1, VALID_STARTING_DIGIT_2, VALID_STARTING_DIGIT_3);
        }

        return isValidCreditCardStartingDigit;
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
        for (int i = 0; i < VALID_CREDIT_CARD_NUMBER_LENGTH - 1; i += 2) {

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
            System.out.printf("\nYour credit card number does not pass the Check Sum test. Please input a new credit card.\n");
        }

        return validCheckSum;
    }




    // ====================readExpirationDateFromUser()====================

    private static String readExpirationDateFromUser() {

        String creditCardExpirationDate = promptCreditCardExpirationDate(
                "Please input your expiration date (ex. \"12/23\" or \"12/2023\"), or \"back\" to input a new credit card.\n");
        return creditCardExpirationDate;
    }

    // ====================readExpirationDateFromUser() HELPER FUNCTIONS====================

    private static String promptCreditCardExpirationDate (String message) {

        String creditCardExpirationDateStr;
        int creditCardExpirationMonth = 0;
        int creditCardExpirationYear = 0;
        boolean validInput;

        Utils.printMsg(message);

        do {

            validInput = false;
            creditCardExpirationDateStr = Utils.promptStr();

            if (creditCardExpirationDateStr.equalsIgnoreCase("back")) {
                System.out.println();
                break;
            }

            if (!validCreditCardExpirationDatePattern(creditCardExpirationDateStr)) continue;

            creditCardExpirationMonth = getCreditCardExpirationMonth(creditCardExpirationDateStr);
            creditCardExpirationYear = getCreditCardExpirationYear(creditCardExpirationDateStr);

            // TODO: Check month within 1 - 12

            if ((validMonth(creditCardExpirationMonth, creditCardExpirationYear) && validYear(creditCardExpirationYear))) {
                validInput = true;
            }
            else {
                System.out.printf("\nYour credit card is expired or has an invalid date. Please check the date or type \"back\" to input a new credit card.\n");
            }

        } while (!validInput);

        if (!creditCardExpirationDateStr.equalsIgnoreCase("back")) {
            System.out.printf("\nYour valid credit card expiration date is:\n%02d/%d\n\n",
                    getCreditCardExpirationMonth(creditCardExpirationDateStr), getCreditCardExpirationYear(creditCardExpirationDateStr));
        }

        return creditCardExpirationDateStr;
    }

    private static int getCreditCardExpirationMonth (String creditCardExpirationDate) {

        final int MONTH_START_INDEX = 0;
        final int MONTH_END_INDEX = 2;

        int creditCardExpirationMonth = Integer.parseInt(creditCardExpirationDate.substring(MONTH_START_INDEX, MONTH_END_INDEX));

        return creditCardExpirationMonth;
    }

    private static int getCreditCardExpirationYear (String creditCardExpirationDate) {

        final int YEAR_START_INDEX = 3;
        final LocalDateTime CURRENT_TIME = LocalDateTime.now();
        final int CURRENT_YEAR = CURRENT_TIME.getYear();
        final int YEARS_IN_CENTURY = 1000;
        final int CURRENT_MILLENNUM = (CURRENT_YEAR / YEARS_IN_CENTURY) * YEARS_IN_CENTURY;

        int creditCardExpirationYear = 0;

        // If user types in DD/DD
        if (creditCardExpirationDate.matches(VALID_EXPIRATION_DATE_PATTERN_1)) {

            creditCardExpirationYear = Integer.parseInt(creditCardExpirationDate.substring(YEAR_START_INDEX)) + CURRENT_MILLENNUM;
        }
        // If user types in DD/DDDD
        else if (creditCardExpirationDate.matches(VALID_EXPIRATION_DATE_PATTERN_2)) {
            creditCardExpirationYear = Integer.parseInt(creditCardExpirationDate.substring(YEAR_START_INDEX));
        }

        return creditCardExpirationYear;
    }

    // ====================readExpirationDateFromUser() VERIFY FUNCTIONS====================

    private static boolean validCreditCardExpirationDatePattern (String creditCardExpirationDateStr) {

        boolean validForm = false;

        if (creditCardExpirationDateStr.matches(VALID_EXPIRATION_DATE_PATTERN_1) ||
                creditCardExpirationDateStr.matches(VALID_EXPIRATION_DATE_PATTERN_2)) {
            validForm = true;
        }
        else {
            System.out.printf("\nPattern not recognized as valid credit card expiration date. Please try again.\n");
        }
        return validForm;
    }

    private static boolean validMonth (int creditCardExpirationMonth, int creditCardExpirationYear) {

        boolean validMonth = true;

        final LocalDateTime CURRENT_TIME = LocalDateTime.now();
        final int CURRENT_MONTH = CURRENT_TIME.getMonthValue();
        final int CURRENT_YEAR = CURRENT_TIME.getYear();

        if ((creditCardExpirationYear == CURRENT_YEAR) && (creditCardExpirationMonth > CURRENT_MONTH)) {
            validMonth = false;
        }
        else if ((creditCardExpirationYear == CURRENT_YEAR) && (creditCardExpirationMonth == CURRENT_MONTH)) {
            System.out.printf("WARNING! Your credit card will expire this month. Purchases with this card after %d/%d will be invalid.\n",
                    creditCardExpirationMonth, creditCardExpirationYear);
        }

        return validMonth;
    }

    private static boolean validYear (int creditCardExpirationYear) {

        boolean validYear = true;

        final LocalDateTime CURRENT_TIME = LocalDateTime.now();
        final int CURRENT_YEAR = CURRENT_TIME.getYear();

        if (creditCardExpirationYear < CURRENT_YEAR) {
            validYear = false;
        }
        return validYear;
    }
}
