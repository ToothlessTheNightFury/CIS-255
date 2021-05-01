package project4;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Settings {

    private ArrayList<Panel> panelList = new ArrayList<>();

    private Tool currentTool;
    private Color currentColor;
    private double toolSize;
    private boolean isRectangleFilled;
    private boolean isCircleFilled;

    private final Tool DEFAULT_CURRENT_TOOL = Tool.CURSOR;
    private final Color DEFAULT_COLOR = Color.RED;
    private final double DEFAULT_TOOL_SIZE = 5;
    private final boolean DEFAULT_IS_RECTANGLE_FILLED = false;
    private final boolean DEFAULT_IS_CIRCLE_FILLED = false;

    public Settings() {
        resetToDefault();
    }
    public void resetToDefault() {
        currentTool = DEFAULT_CURRENT_TOOL;
        currentColor = DEFAULT_COLOR;
        toolSize = DEFAULT_TOOL_SIZE;
        isRectangleFilled = DEFAULT_IS_RECTANGLE_FILLED;
        isCircleFilled = DEFAULT_IS_CIRCLE_FILLED;
        printSettings();
    }
    public void printSettings() {
        System.out.println(this);
    }
    public void registerListener (Panel panel) {
        panelList.add(panel);
    }
    public void updateAllPanels() {
        for (Panel panel : panelList) {
            panel.onSettingsUpdated();
        }
    }

    public Tool getCurrentTool() {
        return currentTool;
    }
    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
        System.out.println("Current Tool: " + currentTool);
        updateAllPanels();
    }

    public Color getCurrentColor() {
        return currentColor;
    }
    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
        System.out.println("Current Color: " + currentColor);
        updateAllPanels();
    }

    public double getToolSize() {
        return toolSize;
    }
    public void setToolSize(double toolSize) {
        this.toolSize = toolSize;
        System.out.println("Tool Size: " + toolSize);
        updateAllPanels();
    }

    public boolean isRectangleFilled() {
        return isRectangleFilled;
    }
    public void setRectangleFilled(boolean rectangleFilled) {
        isRectangleFilled = rectangleFilled;
        System.out.println("Is Rectangle Filled: " + isRectangleFilled);
    }

    public boolean isCircleFilled() {
        return isCircleFilled;
    }
    public void setCircleFilled(boolean circleFilled) {
        isCircleFilled = circleFilled;
        System.out.println("Is Circle Filled: " + isCircleFilled);
    }

    @Override
    public String toString() {
        return String.format(
                "SETTINGS\n" +
                        "===============\n" +
                        "Current Tool: %s\n" +
                        "Current Color: %s\n" +
                        "Current Tool Size: %f\n" +
                        "Is Rectangle Filled: %s\n" +
                        "Is Circle Filled: %s\n",
                currentTool, currentColor, toolSize, isRectangleFilled, isCircleFilled);
    }
}
