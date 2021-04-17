import java.util.ArrayList;

public class HiLo {

    private HiLoLevel currentLevel;
    private int levelIndex = 0;
    private boolean beatGame = false;

    private ArrayList<HiLoLevel> levels = new ArrayList<>();

    public HiLo() {
        setupDefault();
    }

    public void setupDefault() {
        levels.clear();
        addLevel(1, 10, 5);
        addLevel(1, 25, 3);
        addLevel(1, 50, 2);
        addLevel(1, 100, 1);
        currentLevel = levels.get(levelIndex);
    }

    private void addLevel (int minNum, int maxNum, int numOfLevels) {
        for (int i = 0; i < numOfLevels; i++) {
            levels.add(new HiLoLevel(minNum, maxNum));
        }
    }

    public GuessResult guess (int guess) {
        GuessResult result = currentLevel.checkGuess(guess);

        if (result == GuessResult.CORRECT) {
            return advanceLevel();
        }
        return result;
    }

    private GuessResult advanceLevel() {
        levelIndex++;

        if (levelIndex == levels.size()) {
            beatGame = true;
            return GuessResult.BEAT_GAME;
        }
        currentLevel = levels.get(levelIndex);
        return GuessResult.CORRECT;
    }

    public int getMaxNum() {
        return currentLevel.getMaxNum();
    }

    public int getLevelIndex() {
        return levelIndex;
    }

    public ArrayList<Integer> getLevelMaxNums() {
        ArrayList<Integer> levelMaxNums = new ArrayList<>();

        for (HiLoLevel level : levels) {
            levelMaxNums.add(level.getMaxNum());
        }
        return levelMaxNums;
    }
}
