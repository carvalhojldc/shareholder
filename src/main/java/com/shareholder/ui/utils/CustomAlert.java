package com.shareholder.ui.utils;

import com.shareholder.config.ProgramDef;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CustomAlert {
    public static void dialog(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(ProgramDef.getProgramName());
        alert.setHeaderText(null);
        alert.setContentText(message);
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/logo.png"));

        alert.showAndWait();
    }

}
