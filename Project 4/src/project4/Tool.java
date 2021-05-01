package project4;

import javafx.scene.Cursor;

public enum Tool {
    CURSOR (Cursor.DEFAULT),
    LINE (Cursor.CROSSHAIR),
    CIRCLE (Cursor.CROSSHAIR),
    RECTANGLE (Cursor.CROSSHAIR);

    Cursor cursor;

    Tool (Cursor cursor) {
        this.cursor = cursor;
    }

    public Cursor getCursor() {
        return cursor;
    }
}