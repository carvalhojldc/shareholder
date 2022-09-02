package com.shareholder;

import com.shareholder.config.ProgramDef;
import com.shareholder.config.SystemInstall;
import com.shareholder.config.log.Log;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static void run(String[] args) {
        String path = SystemInstall.getPath();
        if (!SystemInstall.verifyIfPathExists(path)) {
            SystemInstall.createSystemPath(path);
        }

        Log.configure();

        Log.getLogger(Class.class).info("Starting " + ProgramDef.getProgramName());

        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/ui/manager.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(ProgramDef.getProgramName() + " - Gerenciamento de ações");
        stage.setScene(scene);
        stage.getIcons().add(new Image("/logo.png"));
        stage.show();
    }
}