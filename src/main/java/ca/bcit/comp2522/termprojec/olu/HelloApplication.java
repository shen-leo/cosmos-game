package ca.bcit.comp2522.termprojec.olu;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Random;

public class HelloApplication extends Application {
    private User user;
    private static final LevelManager levelManager = LevelManager.initLevel();
    private static final Random random = new Random();
    private static int nextPowerOf2(int n) {
        n = n - 1;

        while ((n & n - 1) != 0) {
            n = n & n - 1;
        }

        int nextPowerOf2 = n << 1;
        if (nextPowerOf2 < 64 && nextPowerOf2 != 0) {
            return nextPowerOf2(random.nextInt(0, 257));
        }
        return nextPowerOf2;
    }
    public static int generateRandomCoordinate() {
        if (random.nextInt(0,2) == 0) {
            return nextPowerOf2(random.nextInt(0, 257));
        } else {
            return nextPowerOf2(random.nextInt(0, 257)) * -1;
        }
    }

    @Override
    public void start(final Stage stage) throws Exception {
        // Create a new scene manager
        SceneManager sceneManager = new SceneManager(stage, levelManager, user);

        // set name of the game
        stage.setTitle("Cosmos");
        // ensure user cannot resize
        stage.setResizable(false);
        // make scene-manager generate a new level
//        stage.setScene(sceneManager.createGame());
        stage.setScene(sceneManager.createTitleScene());
        // show the game on screen
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}