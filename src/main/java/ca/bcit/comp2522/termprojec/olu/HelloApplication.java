package ca.bcit.comp2522.termprojec.olu;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Create a new scene manager
        SceneManager sceneManager = new SceneManager(stage);

        // set name of the game
        stage.setTitle("Cosmos");
        // ensure user cannot resize
        stage.setResizable(false);
        // make scene-manager generate a new level
        stage.setScene(sceneManager.createGame());
        // show the game on screen
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}