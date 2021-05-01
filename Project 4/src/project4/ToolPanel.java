package project4;

import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class ToolPanel extends ToolBar {

    private Settings settings;
    private DocumentWindow documentWindow;

    private final ToggleGroup toolGroup = new ToggleGroup();

    private RadioButton cursorToolButton = new RadioButton("MOVE");
    private RadioButton lineToolButton = new RadioButton("LINE");
    private RadioButton circleToolButton = new RadioButton("CIRCLE");
    private RadioButton rectangleToolButton = new RadioButton("RECTANGLE");

    private ColorPicker foregroundColorPicker = new ColorPicker();

    public ToolPanel (Settings settings, DocumentWindow documentWindow) {
        this.settings = settings;
        this.documentWindow = documentWindow;
        setup();
    }

    private void setup() {
        setupToolBar();
        setupToggleGroup();
        setupCursorTool();
        setupLineTool();
        setupCircleTool();
        setupRectangleTool();
        setupClearButton();
        setupForegroundColorPicker();
    }

    private void setupToolBar() {
        // Sets orientation to vertical
        setOrientation(Orientation.VERTICAL);

        // Selects button for selected tool from settings
        selectButtonForSelectedTool();

        // Selects color for selected color from settings
        selectColorFromSettings();
    }

    private void setupToggleGroup() {
        cursorToolButton.setToggleGroup(toolGroup);
        lineToolButton.setToggleGroup(toolGroup);
        circleToolButton.setToggleGroup(toolGroup);
        rectangleToolButton.setToggleGroup(toolGroup);
    }

    private void setupCursorTool() {
        getItems().add(cursorToolButton);
        getItems().add(new Separator());

        cursorToolButton.setOnAction(e -> settings.setCurrentTool(Tool.CURSOR));
    }

    private void setupLineTool() {
        getItems().add(lineToolButton);
        getItems().add(new Separator());

        lineToolButton.setOnAction(e -> settings.setCurrentTool(Tool.LINE));
    }

    private void setupCircleTool() {
        getItems().add(circleToolButton);
        getItems().add(new Separator());

        circleToolButton.setOnAction(e -> settings.setCurrentTool(Tool.CIRCLE));
    }

    private void setupRectangleTool() {
        getItems().add(rectangleToolButton);
        getItems().add(new Separator());

        rectangleToolButton.setOnAction(e -> settings.setCurrentTool(Tool.RECTANGLE));
    }

    private void setupClearButton() {
        HBox clearButtonLayout = new HBox();
        clearButtonLayout.setSpacing(5.0);

        Button clearButton = new Button();
        clearButton.setMaxSize(16.0, 17.0);
        clearButton.setMinSize(16.0, 17.0);
        clearButton.setOnAction(e -> {
            documentWindow.getChildren().clear();
        });

        Label clearButtonLabel = new Label("CLEAR");

        clearButtonLayout.getChildren().addAll(clearButton, clearButtonLabel);
        getItems().add(clearButtonLayout);
        getItems().add(new Separator());
    }

    private void setupForegroundColorPicker() {
        getItems().add(foregroundColorPicker);
        foregroundColorPicker.getStyleClass().add("button");
        foregroundColorPicker.setOnAction(e -> settings.setCurrentColor(foregroundColorPicker.getValue()));
    }

    private void selectButtonForSelectedTool() {
        Tool currentTool = settings.getCurrentTool();

        switch (currentTool) {
            case CURSOR -> cursorToolButton.setSelected(true);
            case LINE -> lineToolButton.setSelected(true);
            case CIRCLE -> circleToolButton.setSelected(true);
            case RECTANGLE -> rectangleToolButton.setSelected(true);
        }
    }

    private void selectColorFromSettings() {
        foregroundColorPicker.setValue(settings.getCurrentColor());
    }
}
