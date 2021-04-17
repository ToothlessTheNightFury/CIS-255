import javafx.scene.control.*;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

class NumField extends TextField {

    NumField (String promptText, int maxDigits) {
        this(maxDigits);
        setPromptText(promptText);
    }

    NumField (int maxDigits) {
        setup();
        setMaxDigits(maxDigits);
    }

    private void setup() {
        // Make prompt text appear even when NumField is selected
        setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
    }

    public void setMaxDigits (int maxDigits) {
        if (maxDigits < 1) {
            maxDigits = 1;
        }

        // Restrict input to digits up to maxDigits
        Pattern pattern = Pattern.compile("[\\d]{0," + maxDigits + "}");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> pattern.matcher(change.getControlNewText()).matches() ? change : null);
        setTextFormatter(formatter);
    }
}