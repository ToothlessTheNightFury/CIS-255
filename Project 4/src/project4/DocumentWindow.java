package project4;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import static java.lang.Math.abs;

public class DocumentWindow extends Pane {

    private Scene scene;
    private Settings settings;
    private Node currentManipulatedNode;

    private double startX;
    private double startY;
    private double endX;
    private double endY;

    public DocumentWindow (Settings settings, Scene scene) {
        this.settings = settings;
        this.scene = scene;
        setup();
    }

    public void setup() {
        decorateWindow();
        setClippingRect();
        setupOnMouseHover();
        setupToolHandler();
    }

    private void decorateWindow() {
        this.setStyle("-fx-border-color: black");
    }

    private void setClippingRect() {
        Rectangle clipRect = new Rectangle(getWidth(), getHeight());
        clipRect.heightProperty().bind(heightProperty());
        clipRect.widthProperty().bind(widthProperty());
        setClip(clipRect);
    }

    private void setupOnMouseHover() {
        setOnMouseEntered(this::changeToToolCursor);
        setOnMouseExited(this::resetCursor);
    }

    private void resetCursor(MouseEvent mouseEvent) {
        scene.setCursor(Cursor.DEFAULT);
    }

    private void changeToToolCursor(MouseEvent mouseEvent) {
        Tool currentTool = settings.getCurrentTool();
        Cursor cursor = currentTool.getCursor();
        scene.setCursor(cursor);
    }

    private void setupToolHandler() {
        setOnMousePressed(this::toolHandlerPressed);
        setOnMouseDragged(this::toolHandlerDragged);
    }

    private void toolHandlerPressed(MouseEvent mouseEvent) {
        if (!mouseEvent.isPrimaryButtonDown()) {
            return;
        }
        requestFocus();

        Tool currentTool = settings.getCurrentTool();
        startX = mouseEvent.getX();
        startY = mouseEvent.getY();
        printPosition("MOUSE PRESSED", startX, startY);

        switch (currentTool) {
            case CURSOR -> moveToolPressHandler(mouseEvent);
            case LINE -> lineToolPressHandler(mouseEvent);
            case CIRCLE -> circleToolPressHandler(mouseEvent);
            case RECTANGLE -> rectangleToolPressHandler(mouseEvent);
        }
    }

    // For handling mouse clicks when using move tool. Currently does nothing
    private void moveToolPressHandler(MouseEvent mouseEvent) {
    }

    private void lineToolPressHandler(MouseEvent mouseEvent) {
        Line newLine = new Line();
        newLine.setStrokeWidth(settings.getToolSize());
        newLine.setStroke(settings.getCurrentColor());

        getChildren().add(newLine);
        currentManipulatedNode = newLine;

        newLine.setStartX(startX);
        newLine.setStartY(startY);
        newLine.setEndX(startX);
        newLine.setEndY(startY);
    }

    private void circleToolPressHandler(MouseEvent mouseEvent) {
        Ellipse newEllipse = new Ellipse();
        newEllipse.setStrokeWidth(settings.getToolSize());
        newEllipse.setStroke(settings.getCurrentColor());
        newEllipse.setFill(settings.isCircleFilled() ? settings.getCurrentColor() : Color.TRANSPARENT);

        getChildren().add(newEllipse);
        currentManipulatedNode = newEllipse;
    }

    private void rectangleToolPressHandler(MouseEvent mouseEvent) {
        Rectangle newRectangle = new Rectangle();
        newRectangle.setStrokeWidth(settings.getToolSize());
        newRectangle.setStroke(settings.getCurrentColor());
        newRectangle.setFill(settings.isRectangleFilled() ? settings.getCurrentColor() : Color.TRANSPARENT);

        getChildren().add(newRectangle);
        currentManipulatedNode = newRectangle;
    }

    private void toolHandlerDragged(MouseEvent mouseEvent) {
        if (!mouseEvent.isPrimaryButtonDown()) {
            return;
        }

        Tool currentTool = settings.getCurrentTool();
        endX = mouseEvent.getX();
        endY = mouseEvent.getY();
        printPosition("MOUSE DRAGGED", endX, endY);

        switch (currentTool) {
            case CURSOR -> cursorToolDragHandler(mouseEvent);
            case LINE -> lineToolDragHandler(mouseEvent);
            case CIRCLE -> circleToolDragHandler(mouseEvent);
            case RECTANGLE -> rectangleToolDragHandler(mouseEvent);
        }
    }

    private void cursorToolDragHandler(MouseEvent mouseEvent) {
    }

    private void lineToolDragHandler(MouseEvent mouseEvent) {
        Line line = (Line) currentManipulatedNode;

        line.setEndX(endX);
        line.setEndY(endY);
    }

    private void circleToolDragHandler(MouseEvent mouseEvent) {
        Ellipse ellipse = (Ellipse) currentManipulatedNode;

        ellipse.setCenterX((startX + endX) / 2);
        ellipse.setCenterY((startY + endY) / 2);
        ellipse.setRadiusX(abs(endX - startX) / 2);
        ellipse.setRadiusY(abs(endY - startY) / 2);
    }

    private void rectangleToolDragHandler(MouseEvent mouseEvent) {
        Rectangle rectangle = (Rectangle) currentManipulatedNode;

        rectangle.setWidth(abs(endX - startX));
        rectangle.setHeight(abs(endY - startY));
        rectangle.setX((endX - startX > 0) ? startX : endX);
        rectangle.setY((endY - startY < 0) ? endY : startY);
    }

    private void printPosition(String message, double x, double y) {
        System.out.printf("\n%s\nX: %f\nY: %f\n", message, x, y);
    }
}
