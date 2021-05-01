package project4;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class OptionsPanel extends Pane implements Panel {

    private Settings settings;

    private HBox movePanel = new HBox();
    private HBox linePanel = new HBox();
    private HBox circlePanel = new HBox();
    private HBox rectanglePanel = new HBox();

    private ArrayList<NumberField> sizeFieldList = new ArrayList<>();

    public OptionsPanel(Settings settings) {
        this.settings = settings;
        setup();
    }

    public void setup() {
        createCursorPanel();
        createLinePanel();
        createCirclePanel();
        createRectanglePanel();
        updateSizeFields();
        onSettingsUpdated();
    }

    private void setupHBox (HBox hBox) {
        hBox.setMinHeight(35.0);
        hBox.setMaxHeight(35.0);
        hBox.setSpacing(5.0);
        hBox.setPadding(new Insets(5, 5, 5, 5));
        hBox.setAlignment(Pos.CENTER);
    }

    private void createCursorPanel() {
        setupHBox(movePanel);
    }

    private void createLinePanel() {
        setupHBox(linePanel);
        addSizeField(linePanel);
    }

    private void createCirclePanel() {
        setupHBox(circlePanel);
        addSizeField(circlePanel);

        Label isFilledLabel = new Label ("Is Filled?");
        CheckBox isFilledBox = new CheckBox();
        isFilledBox.setOnAction(e -> {
            settings.setCircleFilled(isFilledBox.isSelected());
        });
        circlePanel.getChildren().addAll(isFilledLabel, isFilledBox);
    }

    private void createRectanglePanel() {
        setupHBox(rectanglePanel);
        addSizeField(rectanglePanel);

        Label isFilledLabel = new Label ("Is Filled?");
        CheckBox isFilledBox = new CheckBox();
        isFilledBox.setOnAction(e -> {
            settings.setRectangleFilled(isFilledBox.isSelected());
        });
        rectanglePanel.getChildren().addAll(isFilledLabel, isFilledBox);
    }

    @Override
    public void onSettingsUpdated() {
        Tool currentTool = settings.getCurrentTool();
        getChildren().clear();

        switch (currentTool) {
            case CURSOR -> showCursorPanel();
            case LINE -> showLinePanel();
            case CIRCLE -> showCirclePanel();
            case RECTANGLE -> showRectanglePanel();
        }
    }

    private void updateSizeFields() {
        String size = String.valueOf((int) settings.getToolSize());
        for (NumberField sizeField : sizeFieldList) {
            sizeField.setText(size);
        }
    }

    private void showCursorPanel() {
        getChildren().add(movePanel);
    }

    private void showLinePanel() {
        getChildren().add(linePanel);
    }

    private void showCirclePanel() {
        getChildren().add(circlePanel);
    }

    private void showRectanglePanel() {
        getChildren().add(rectanglePanel);
    }

    private void addSizeField (HBox hBox) {
        Label sizeLabel = new Label("Size:");
        NumberField sizeField = new NumberField();
        sizeField.setMaxWidth(50);
        sizeField.textProperty().addListener((obs, oldText, newText) -> {
            String text = sizeField.getText();
            if (text.length() == 0 || !sizeField.isFocused()) {
                return;
            }

            double size = Double.parseDouble(text);
            settings.setToolSize(size);
            updateSizeFields();
        });
        sizeFieldList.add(sizeField);
        hBox.getChildren().addAll(sizeLabel, sizeField);
    }
}
