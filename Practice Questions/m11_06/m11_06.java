import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;

public class m11_06 extends Application {

    HiLo game;
    Stage window;

    Scene titleScene;
    Scene gameScene;
    Scene endScene;

    VBox titleScreen = new VBox();
    StackPane gameLayout = new StackPane();
    GridPane guessGrid = new GridPane();
    VBox endScreen = new VBox();

    Label title = new Label("100");

    Stopwatch time = new Stopwatch();
    Label progressBar = new Label("");
    NumField guessBox = new NumField("10", 2);

    Label currentMessage = new Label("");
    Label messageTooLow = new Label("TOO LOW");
    Label messageTooHigh = new Label("TOO HIGH");
    Label messageCorrect = new Label("CORRECT");

    Button startGame = new Button("Start Game");
    Button leaveGame = new Button("Leave Game");

    Label endScore = new Label("00:00:00");
    Button restartGame = new Button("New Game");
    Button quitGame = new Button ("Quit");

    ArrayList<Integer> levelMaxNums;
    String[] progressBarIcons = {"⏺", "⍟", "○"};

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        setup();
        window.setScene(titleScene);
        window.show();
    }

    private void setup() {
        setupGame();
        setupWindow();
        setupTimer();
        setupProgressBar();
        setupGuessGrid();
        setupGuessBox();
        setupGuessMessage();
        setupTitleScene();
        setupGameScene();
        setupEndScene();
    }

    private void setupGame() {
        game = new HiLo();
        time.setText("00:00:00");
    }

    private void setupWindow() {
        window.setTitle("100");

        window.setWidth(1000);
        window.setHeight(750);
        window.setMinWidth(800);
        window.setMinHeight(600);
    }

    private void setupTimer() {
        gameLayout.getChildren().add(time);
        StackPane.setAlignment(time, Pos.BOTTOM_LEFT);
        time.setFont(new Font("Segoe UI", 30));
    }

    private void setupProgressBar() {
        gameLayout.getChildren().add(progressBar);
        StackPane.setAlignment(progressBar, Pos.BOTTOM_RIGHT);
        progressBar.setFont(new Font("Segoe UI", 20));

        levelMaxNums = game.getLevelMaxNums();
        updateProgressBar();
    }

    private void setupGuessGrid() {
        int numCol = 3;

        for (int i = 0; i < numCol; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100.0 / numCol);
            if ((i == numCol - 1)) {
                cc.setHalignment(HPos.LEFT);
            } else {
                cc.setHalignment(HPos.CENTER);
            }
            guessGrid.getColumnConstraints().add(cc);
        }

        RowConstraints rc = new RowConstraints();
        rc.setValignment(VPos.CENTER);
        rc.setPercentHeight(100.0);
        guessGrid.getRowConstraints().add(rc);

        gameLayout.getChildren().add(guessGrid);
    }

    private void setupGuessBox() {
        guessGrid.add(guessBox, 1, 0);
        guessBox.setMaxSize(200,75);
        guessBox.setAlignment(Pos.CENTER);

        // Send input and clear box on ENTER if not empty
        guessBox.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.ENTER && !guessBox.getText().isEmpty()) {
                enterKeyPress();
            }
        });
    }

    private void setupGuessMessage() {
        guessGrid.add(messageTooLow, 2, 0);
        messageTooLow.setOpacity(0.0);
        messageTooLow.setFont(new Font("Segoe UI", 15));

        guessGrid.add(messageTooHigh, 2, 0);
        messageTooHigh.setOpacity(0.0);
        messageTooHigh.setFont(new Font("Segoe UI", 15));

        guessGrid.add(messageCorrect, 2, 0);
        messageCorrect.setOpacity(0.0);
        messageCorrect.setFont(new Font("Segoe UI", 15));
    }

    private void setupTitleScene() {
        titleScene = new Scene(titleScreen);
        titleScreen.setAlignment(Pos.CENTER);
        titleScreen.setSpacing(10.0);

        titleScreen.getChildren().add(title);
        title.setFont(new Font("Segoe UI", 100));

        titleScreen.getChildren().add(startGame);
        startGame.setPrefSize(200, 75);
        startGame.setFont(new Font("Segoe UI", 15));
        startGame.setOnAction(e -> {
            window.setScene(gameScene);
            time.start();
        });

        titleScreen.getChildren().add(leaveGame);
        leaveGame.setPrefSize(200, 75);
        leaveGame.setFont(new Font("Segoe UI", 15));
        leaveGame.setOnAction(e -> Platform.exit());
    }

    private void setupGameScene() {
        gameScene = new Scene(gameLayout);
        gameLayout.setPadding(new Insets(10, 10, 10, 10));
    }

    private void setupEndScene() {
        endScene = new Scene(endScreen);
        endScreen.setAlignment(Pos.CENTER);
        endScreen.setSpacing(10.0);

        endScreen.getChildren().add(endScore);
        endScore.setFont(new Font("Segoe UI", 100));

        endScreen.getChildren().add(restartGame);
        restartGame.setPrefSize(200, 75);
        restartGame.setFont(new Font("Segoe UI", 15));
        restartGame.setOnAction(e -> reset());

        endScreen.getChildren().add(quitGame);
        quitGame.setPrefSize(200, 75);
        quitGame.setFont(new Font("Segoe UI", 15));
        quitGame.setOnAction(e -> Platform.exit());
    }

    private void enterKeyPress() {
        int input = Integer.parseInt(guessBox.getText());
        guessBox.setText("");

        GuessResult result = game.guess(input);

        switch (result) {
            case TOO_LOW -> displayGuessMessage(GuessResult.TOO_LOW);
            case TOO_HIGH -> displayGuessMessage(GuessResult.TOO_HIGH);
            case CORRECT -> {
                displayGuessMessage(GuessResult.CORRECT);
                updateGuessBox();
                updateProgressBar();
            }
            case BEAT_GAME -> beatGame();
        }
    }

    private void displayGuessMessage (GuessResult result) {
        switch (result) {
            case TOO_LOW -> fadeOutIn(messageTooLow);
            case TOO_HIGH -> fadeOutIn(messageTooHigh);
            case CORRECT -> fadeOutIn(messageCorrect);
        }
    }

    private void fadeOutIn (Label node) {
        FadeTransition fadeOut = fadeOut(currentMessage);
        FadeTransition fadeIn = fadeIn(node);

        currentMessage = node;

        SequentialTransition fadeOutIn = new SequentialTransition();
        fadeOutIn.getChildren().addAll(fadeOut, fadeIn);
        fadeOutIn.play();
    }

    private FadeTransition fadeIn (Node node) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(150), node);
        fadeIn.setToValue(1.0);
        return fadeIn;
    }

    private FadeTransition fadeOut (Node node) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(150), node);
        fadeOut.setToValue(0.0);
        return fadeOut;
    }

    private void updateGuessBox() {
        String prompt = String.valueOf(game.getMaxNum());
        guessBox.setPromptText(prompt);
        guessBox.setMaxDigits(prompt.length());
    }

    private void updateProgressBar() {
        int levelIndex = game.getLevelIndex();
        String progressBarText = "";
        int progressBarIconIndex;

        for (int i = 0; i < levelMaxNums.size(); i++) {
            progressBarIconIndex = Integer.compare(i, levelIndex) + 1; // Calculates index for icon of level
            progressBarText = String.format("%s %d\n%s", progressBarIcons[progressBarIconIndex], levelMaxNums.get(i), progressBarText);
        }

        progressBar.setText(progressBarText);
    }

    private void beatGame() {
        time.stop();
        endScore.setText(time.getText());
        window.setScene(endScene);
    }

    private void reset() {
        setupGame();
        updateProgressBar();
        updateGuessBox();
        window.setScene(gameScene);
        time.start();
    }
}