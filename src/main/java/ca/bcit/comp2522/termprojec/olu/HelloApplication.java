package ca.bcit.comp2522.termprojec.olu;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class HelloApplication extends Application {
    private User user;
    private static final LevelManager levelManager = LevelManager.initLevel();
    private static final Random random = new Random();
    private static final int PIXEL_COUNT = 64;
    public static ImageView displaySprite(InputStream inputStream) throws IOException {
        Image img = new Image(inputStream);
        inputStream.close();

        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(PIXEL_COUNT);
        imageView.setFitHeight(PIXEL_COUNT);
        int x = generateRandomCoordinate();
        int y = generateRandomCoordinate();
        imageView.setTranslateX(x);
        imageView.setTranslateY(y);
        return imageView;
    }
    private static int nextPowerOf2(int n) {
        n = n - 1;

        while ((n & n - 1) != 0) {
            n = n & n - 1;
        }

        int nextPowerOf2 = n << 1;
        if (nextPowerOf2 < PIXEL_COUNT && nextPowerOf2 != 0) {
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