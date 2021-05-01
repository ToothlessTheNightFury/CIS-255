package project4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage window;
    private BorderPane workspace = new BorderPane();
    private Scene scene = new Scene(workspace);

    private Settings settings = new Settings();

    private DocumentWindow documentWindow = new DocumentWindow(settings, scene);
    private OptionsPanel optionsPanel = new OptionsPanel(settings);
    private ToolPanel toolPanel = new ToolPanel(settings, documentWindow);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        setup();
        window.setScene(scene);
        window.show();
    }

    public void setup() {
        setupWindow();
        setupWorkspace();
        setupListeners();
    }

    private void setupWindow() {
        window.setHeight(500);
        window.setWidth(750);
        window.setMinHeight(500);
        window.setMinWidth(750);
    }

    private void setupWorkspace() {
        workspace.setLeft(toolPanel);
        workspace.setCenter(documentWindow);
        workspace.setTop(optionsPanel);
    }

    private void setupListeners() {
        settings.registerListener(optionsPanel);
    }
}