import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.management.PlatformLoggingMXBean;

public class FileNameBox {

    private static Stage window;

    public static String getFileName() {
        return getFileName("");
    }

    public static String getFileName (String defaultText) {
        window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Order Confirmation");
        window.setMinWidth(300);
        window.setMinHeight(150);
        window.setWidth(300);
        window.setMinHeight(150);
        window.setResizable(false);

        window.setOnCloseRequest(e -> closeRequest());

        VBox mainVBox = new VBox();
        mainVBox.setSpacing(10);
        mainVBox.setPadding(new Insets(10, 10, 10, 10));

        Label fileNamePrompt = new Label("Please enter the file name to open:");
        mainVBox.getChildren().add(fileNamePrompt);

        TextField fileNameField = new TextField();
        fileNameField.setText(defaultText);
        mainVBox.getChildren().add(fileNameField);

        HBox buttonHBox = new HBox();
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.setSpacing(25);
        mainVBox.getChildren().add(buttonHBox);

        Button loadFileButton = new Button("Load File");
        buttonHBox.getChildren().add(loadFileButton);
        loadFileButton.setOnAction(e -> {
            window.close();
        });

        Button cancelButton = new Button("Cancel");
        buttonHBox.getChildren().add(cancelButton);
        cancelButton.setOnAction(e -> closeRequest());

        Scene scene = new Scene(mainVBox);
        window.setScene(scene);
        window.showAndWait();

        return fileNameField.getText();
    }

    private static void closeRequest() {
        Platform.exit();
        System.exit(0);
    }
}
