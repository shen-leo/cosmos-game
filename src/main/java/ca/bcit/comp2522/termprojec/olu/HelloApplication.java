package ca.bcit.comp2522.termprojec.olu;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import static javafx.scene.media.MediaPlayer.INDEFINITE;

public class HelloApplication extends Application {
    private static User user = new User();
    private static final LevelManager levelManager = LevelManager.initLevel();
    private static final MapManager mapManager = new MapManager();
    private static final Random random = new Random();
    public static final Stats stats = new Stats();
    MediaPlayer mediaPlayer;

    private static final int PIXEL_COUNT = 64;
    public static int BACKGROUND_WIDTH = 576;
    public static int BACKGROUND_HEIGHT = 576;
    public static int BACKGROUND_BOUND = 257;
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
    public void start(final Stage stage) {
        playMusic();
        // Create a new scene manager
        SceneManager sceneManager = new SceneManager(stage, levelManager, mapManager, user);

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
    private void playMusic() {
        String musicFile = "src/main/resources/sfx/background.wav";

        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.1);
        mediaPlayer.setCycleCount(INDEFINITE);
        mediaPlayer.play();
    }
    public static void setBackgroundHeight(int backgroundHeight) {
        BACKGROUND_HEIGHT = backgroundHeight;
    }

    public static void setBackgroundWidth(int backgroundWidth) {
        BACKGROUND_WIDTH = backgroundWidth;
    }
    public static void setBackgroundBound(int backgroundBound) {
        BACKGROUND_BOUND = backgroundBound;
    }

    public static MapManager getMapManager() {
        return mapManager;
    }

    public static LevelManager getLevelManager() {
        return levelManager;
    }

    public static void setUser(User inputUser) {
        user = inputUser;
    }

    public static void main(String[] args) {
        launch();
    }
}