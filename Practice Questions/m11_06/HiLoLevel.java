import java.util.Random;

public class HiLoLevel {

    private int minNum;
    private int maxNum;
    private int randNum;

    private boolean beatLevel = false;

    public HiLoLevel (int minNum, int maxNum) {
        this.minNum = minNum;
        this.maxNum = maxNum;
        generateRandNum();
    }

    private void generateRandNum() {
        Random rand = new Random();
        randNum = rand.nextInt(maxNum) + minNum;
    }

    public GuessResult checkGuess (int guess) {
        int compare = compareGuessToNum(guess);

        switch (compare) {
            case 1 -> {
                return GuessResult.TOO_HIGH;
            }
            case -1 -> {
                return GuessResult.TOO_LOW;
            }
        }
        // If not too high or too low, then correct
        beatLevel = true;
        return GuessResult.CORRECT;
    }

    private int compareGuessToNum (int guess) {
        Integer intGuess = guess;
        return intGuess.compareTo((Integer) randNum);
    }

    public int getMinNum() {
        return minNum;
    }

    public int getMaxNum() {
        return maxNum;
    }
}
