import java.io.IOException;
import java.time.LocalDateTime;

public class CreditCard {

    /**
     * Number of digits in a valid credit card number.
     */
    private final static int VALID_CARD_NUMBER_LENGTH = 16;

    /**
     * Regex expression for valid credit card numbers.
     * Format is DDDD DDDD DDDD DDDD
     *
     * Example:
     * 0000 0000 0000 0000
     */
    private final static String VALID_CARD_NUMBER_PATTERN_SPACES =
            "(\\d)(\\d)(\\d)(\\d)(\\s)(\\d)(\\d)(\\d)(\\d)(\\s)(\\d)(\\d)(\\d)(\\d)(\\s)(\\d)(\\d)(\\d)(\\d)";

    /**
     * Regex expression for valid credit card numbers.
     * Format is DDDDDDDDDDDDDDDD
     *
     * Example:
     * 0000000000000000
     */
    private final static String VALID_CARD_NUMBER_PATTERN_NO_SPACES =
            "(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)";

    /**
     * Valid starting digits for credit card numbers.
     */
    private final static int VALID_STARTING_DIGIT_4 = 4;
    private final static int VALID_STARTING_DIGIT_5 = 5;
    private final static int VALID_STARTING_DIGIT_6 = 6;

    /**
     * Regex expression for valid credit card expiration dates.
     * Format is M/YY
     *
     * Example:
     * 3/23
     */
    private final static String VALID_EXPIRATION_PATTERN_M_YY = "(\\d)/(\\d)(\\d)";

    /**
     * Regex expression for valid credit card expiration dates.
     * Format is MM/YY
     *
     * Example:
     * 03/23
     */
    private final static String VALID_EXPIRATION_PATTERN_MM_YY = "(\\d)(\\d)/(\\d)(\\d)";

    /**
     * Regex expression for valid credit card expiration dates.
     * Format is M/YYYY
     *
     * Example:
     * 3/2023
     */
    private final static String VALID_EXPIRATION_PATTERN_M_YYYY = "(\\d)/(\\d)(\\d)(\\d)(\\d)";

    /**
     * Regex expression for valid credit card expiration dates.
     * Format is MM/YYYY
     *
     * Example:
     * 03/2023
     */
    private final static String VALID_EXPIRATION_PATTERN_MM_YYYY = "(\\d)(\\d)/(\\d)(\\d)(\\d)(\\d)";

    /**
     * NOTE: If meant for a secure system, values would be hashed.
     */
    private String cardNumber;
    private int cardExpirationMonth;
    private int cardExpirationYear;

    /**
     * Prompts user to input data for a new credit card.
     * Data is written to a new CreditCard object and
     * returned.
     *
     * @return the new CreditCard object
     */
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

    /**
     * Prints the full information about a credit card.
     * Calls the printCardNumber() and printCardExpiration()
     * methods.
     *
     * NOTE: If meant for a secure system, these functions
     * would be PROTECTED instead of PUBLIC.
     */
    public void printFullInfo() {

        printCardNumber();
        printCardExpiration();
        System.out.println();
    }

    /**
     * Prints the credit card number.
     */
    public void printCardNumber() {
        System.out.printf("Card Number: %s\n", cardNumber);
    }

    /**
     * Prints the credit card expiration date.
     */
    public void printCardExpiration() {
        System.out.printf("Expiration Date: %s\n", getCardExpirationDate());
    }


    /**
     * Gets the credit card number.
     * Returns it as a string.
     *
     * @return the credit card number
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Gets the credit card number.
     * Returns it as a double.
     *
     * @return the credit card number
     */
    public double getCardNumberNum() {
        return Double.parseDouble(cardNumber.replaceAll("\\s", ""));
    }

    /**
     * Gets the credit card expiration month.
     * Returns it as an int.
     *
     * @return the credit card expiration month
     */
    public int getCardExpirationMonth() {
        return cardExpirationMonth;
    }

    /**
     * Gets the credit card expiration year.
     * Returns it as an int.
     *
     * @return the credit card expiration year
     */
    public int getCardExpirationYear() {
        return cardExpirationYear;
    }

    /**
     * Gets the credit card expiration date.
     * Returns it as a string.
     *
     * @return the credit card expiration date
     */
    public String getCardExpirationDate() {
        return String.format("%02d/%d", cardExpirationMonth, cardExpirationYear);
    }

    // ====================readCardNumberFromUser()====================

    /**
     * Prompts the user with a message to type in
     * their credit card number and returns a valid
     * input. Message is preset.
     *
     * The following tests are done on the credit
     * card number to validate it:
     * - a valid credit card number pattern
     *   (ex. 0000 0000 0000 0000 or 0000000000000000)
     * - a valid starting digit (ex. 4, 5, or 6)
     * - passes the CheckSum test (deviation of Luhn algorithm,
     *   https://en.wikipedia.org/wiki/Luhn_algorithm)
     *
     * @return the valid credit card number
     */
    private static String readCardNumberFromUser() {
        return promptCardNumber("Please type in your 16-digit credit card number. You can enter it with or without spaces " +
                        "(ex. 5500 0000 0000 0004 or 5500000000000004).\n");
    }

    // ====================readCardNumberFromUser() HELPER FUNCTIONS====================

    /**
     * Prompts the user with a message to type in
     * their credit card number and returns a valid
     * input. Message must be specified in parameter.
     *
     * The following tests are done on the credit
     * card number to validate it:
     * - a valid card number pattern
     *   (ex. 0000 0000 0000 0000 or 0000000000000000)
     * - a valid starting digit (ex. 4, 5, or 6)
     * - passes the CheckSum test (deviation of Luhn algorithm,
     *   https://en.wikipedia.org/wiki/Luhn_algorithm)
     *
     * @param message the message to prompt the user
     * @return the valid credit card number
     */
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

    /**
     * Checks if a cardNumberStr String has a valid credit
     * card number pattern. A pattern, in this case, is an
     * expression where a certain character (or range of
     * characters) are defined to be valid.
     *
     * The following patterns are accepted:
     * - DDDD DDDD DDDD DDDD
     * - DDDDDDDDDDDDDDDD
     *
     * In the above, every D is a digit from 0 - 9.
     * Any string that matches one of these patterns is
     * valid. All other strings are invalid.
     *
     * Example:
     * (empty)                     INVALID
     * a                           INVALID
     * this is a string            INVALID
     * 0 00 0000 000 0             INVALID
     * 0                           INVALID
     * 0000000000000000000000000   INVALID
     * 0000 0000 0000 0000         VALID
     * 0000000000000000            VALID
     *
     * Valid credit card number patterns are defined by
     * regular expressions (regex). To view the valid credit
     * card number patterns, see the constants near the top
     * of this class.
     *
     * @param cardNumberStr the credit card number to check
     * @return true if valid, false otherwise
     */
    private static boolean validCardNumberPattern (String cardNumberStr) {

        boolean validCardNumberPattern = false;

        if (cardNumberStr.matches(VALID_CARD_NUMBER_PATTERN_SPACES) ||
                cardNumberStr.matches(VALID_CARD_NUMBER_PATTERN_NO_SPACES)) {
            validCardNumberPattern = true;
        }
        else {
            System.out.print("\nInput not recognized as valid credit card number. Please try again.\n");
        }
        return validCardNumberPattern;
    }

    /**
     * Checks if a cardNumberStr String has a valid starting
     * digit. Valid starting digits are predefined. If a
     * credit card number does not start with one of the
     * predefined starting digits, it is invalid.
     *
     * The following starting digits are accepted:
     * - 4
     * - 5
     * - 6
     *
     * Example:
     * 0000 0000 0000 0000 INVALID
     * 4000 0000 0000 0000 VALID
     * 5000 0000 0000 0000 VALID
     * 6000 0000 0000 0000 VALID
     *
     * @param cardNumberStr the credit card number to check
     * @return true if valid, false otherwise
     */
    private static boolean validCardStartingDigit (String cardNumberStr) {

        boolean isValidCardStartingDigit = switch (Integer.parseInt(String.valueOf(cardNumberStr.charAt(0)))) {
            case VALID_STARTING_DIGIT_4, VALID_STARTING_DIGIT_5, VALID_STARTING_DIGIT_6 -> true;
            default -> false;
        };

        if (!isValidCardStartingDigit) {
            System.out.printf("\nStarting digit of credit card number is invalid. Please try again.\n");
        }

        return isValidCardStartingDigit;
    }

    /**
     * Checks if a cardNumberStr String passes the CheckSum test.
     * CheckSum tests are done using a derivation of the
     * Luhn algorithm (https://en.wikipedia.org/wiki/Luhn_algorithm).
     *
     * This derivation takes a 16-digit credit card number
     * and checks if it's valid. It does the following:
     *
     * 1. Starting with the 1st digit, multiply every other
     * digit by 2.
     *      a. If this multiplication gives a 2-digit number,
     *         add the digits of those numbers together.
     *      b. Use this result to replace the original digit.
     * 2. Add up the 15 digits AND the check digit (16 digits total).
     * 3. If the sum is divisible by 10, valid, Otherwise, invalid.
     *
     *
     * Example:
     * 1. Original                      4111 1111 1111 1111
     * 2. Multiply every other          8121 2121 2121 2121
     *    digit, adding digits
     *    of any two-digit numbers
     *    together
     * 3. Add up the digits             12 + 6 + 6 + 6
     *                                  18 + 12
     *                                  30
     * 4. If divisible by 10, valid.    VALID
     *    Otherwise, invalid
     *
     * @param cardNumberStr the credit card number to check
     * @return true if valid, false otherwise
     */
    private static boolean validCheckSum (String cardNumberStr) {

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
            newDigit = (2 * Character.getNumericValue(cardNumberStr.charAt(i)));
            newDigit = newDigit < 9 ? newDigit : 1 + (newDigit % 10);

            // Add newDigit and following digit in checkSum
            checkSum += newDigit + Character.getNumericValue(cardNumberStr.charAt(i + 1));
        }

        if (checkSum % 10 == 0) {
            validCheckSum = true;
        }
        else {
            System.out.print("\nYour credit card number is invalid (failed CheckSum). Please try again.\n");
        }

        return validCheckSum;
    }




    // ====================readExpirationDateFromUser()====================

    /**
     * Prompts the user with a message to type in
     * their credit card expiration date and returns a
     * valid input. Message must be specified in parameter.
     *
     * The following tests are done on the credit
     * card expiration date to validate it:
     * - a valid credit card expiration date pattern
     *   (ex. 3/23, 03/23, 3/2023, or 03/2023)
     * - month given is within certain range (1 - 12)
     * - credit card is not expired (current date has not passed
     *   card expiration date)
     *
     * User can also type BACK to return to credit card
     * number input, if credit card is expired or user wants
     * to otherwise input a new credit card.
     *
     * @return the valid credit card expiration date, or BACK
     *         if user wants to return
     */
    private static String readExpirationDateFromUser() {

        return promptCardExpirationDate(
                "Please type in your expiration date (ex. 1/23, 01/23, 1/2023, or 01/2023), or BACK to input a new credit card.\n");
    }

    // ====================readExpirationDateFromUser() HELPER FUNCTIONS====================

    /**
     * Prompts the user with a message to type in
     * their credit card expiration date and returns a
     * valid input. Message must be specified in parameter.
     *
     * The following tests are done on the credit
     * card expiration date to validate it:
     * - a valid credit card expiration date pattern
     *   (ex. 3/23, 03/23, 3/2023, or 03/2023)
     * - month given is within certain range (1 - 12)
     * - credit card is not expired (current date has not passed
     *   card expiration date)
     *
     * User can also type BACK to return to credit card
     * number input, if credit card is expired or user wants
     * to otherwise input a new credit card.
     *
     * @return the valid credit card expiration date, or BACK
     *         if user wants to return
     */
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

    /**
     * Takes a cardExpirationDate String and returns
     * the credit card expiration month as an int.
     *
     * @return the credit card expiration month
     */
    private static int getCardExpirationMonth (String cardExpirationDate) {

        final int MONTH_START_INDEX = 0;
        final int MONTH_END_INDEX = cardExpirationDate.indexOf("/");

        return Integer.parseInt(cardExpirationDate.substring(MONTH_START_INDEX, MONTH_END_INDEX));
    }

    /**
     * Takes a cardExpirationDate String and returns
     * the credit card expiration year as an int.
     *
     * Uses regular expressions (regex) to test
     * which pattern user inputted and handle it
     * accordingly.
     *
     * The following patterns are accepted:
     * - M/YY
     * - MM/YY
     * - M/YYYY
     * - MM/YYYY
     *
     * Valid credit card expiration date patterns are
     * defined by regular expressions (regex). To view
     * the valid credit card expiration date patterns,
     * see the constants near the top of this class.
     *
     * @return the credit card expiration year
     */
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

    /**
     * Checks if a cardExpirationDate String has a valid credit card
     * expiration date pattern. A pattern, in this case, is an
     * expression where a certain character (or range of
     * characters) are defined to be valid.
     *
     * The following patterns are accepted:
     * - M/YY
     * - MM/YY
     * - M/YYYY
     * - MM/YYYY
     *
     * In the above, every M and Y is a digit from 0 - 9.
     * Any string that matches one of these patterns is
     * valid. All other strings are invalid.
     *
     * Example:
     * (empty)                     INVALID
     * a                           INVALID
     * this is a string            INVALID
     * 323                         INVALID
     * 3-23                        INVALID
     * 3/23                        VALID
     * 03/23                       VALID
     * 3/2023                      VALID
     * 03/2023                     VALID
     *
     * Valid credit card number patterns are defined by regular
     * expressions (regex). To view the valid credit card number
     * patterns, see the constants near the top of this class.
     *
     * @param cardExpirationDate the credit card expiration date to check
     * @return true if valid, false otherwise
     */
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

    /**
     * Takes a cardExpirationMonth int and checks if it
     * ranges between MIN_MONTH and MAX_MONTH.
     *
     * @return true if valid, false otherwise
     */
    private static boolean validMonthRange (int cardExpirationMonth) {

        final int MIN_MONTH = 1;
        final int MAX_MONTH = 12;

        boolean validMonthRange = (cardExpirationMonth >= MIN_MONTH) && (cardExpirationMonth <= MAX_MONTH);

        if (!validMonthRange) {
            System.out.print("\nPlease input a month number between 01 and 12.\n");
        }

        return validMonthRange;
    }

    /**
     * Takes a cardExpirationMonth int and a
     * cardExpirationYear int and verifies if the credit
     * card is expired or not.
     *
     * The given expiration date is compared to the current
     * time to verify its expiration. If the credit card will
     * expire this month, the method will warn the user.
     *
     * @return true if valid, false otherwise
     */
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
