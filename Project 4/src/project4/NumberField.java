package project4;

import javafx.scene.control.*;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class NumberField extends TextField {

    public NumberField() {
        //setNumericInputOnly();
    }

    private void setNumericInputOnly() {
        Pattern pattern = Pattern.compile("[\\d]*");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> pattern.matcher(change.getControlNewText()).matches() ? change : null);
        setTextFormatter(formatter);
    }
}