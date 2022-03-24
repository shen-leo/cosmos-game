package ca.bcit.comp2522.termprojec.olu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneManager sceneManager = new SceneManager(stage);

        stage.setTitle("Cosmos");
        stage.setResizable(false);
        stage.setScene(sceneManager.createGame());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}