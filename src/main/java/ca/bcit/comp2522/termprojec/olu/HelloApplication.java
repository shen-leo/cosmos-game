package ca.bcit.comp2522.termprojec.olu;

import javafx.application.Application;
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
/**
 * Application Manager.
 * @author Urjit, Leo
 * @version 2022
 */
public class HelloApplication extends Application {
    /**
     * Stats collector for game.
     */
    public static final Stats STATS = new Stats();

    private static User user = new User();
    private static final LevelManager LEVEL_MANAGER = LevelManager.initLevel();
    private static final MapManager MAP_MANAGER = new MapManager();
    private static final Random RANDOM = new Random();

    private static final int PIXEL_COUNT = 64;

    private static MediaPlayer mediaPlayer;
    /**
     * Creates a new Sprite object.
     * @param inputStream Input Stream for the sprite file
     * @return New Image View
     * @throws IOException If file not found
     */
    public static ImageView displaySprite(final InputStream inputStream) throws IOException {
        Image img = new Image(inputStream);
        inputStream.close();

        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(PIXEL_COUNT);
        imageView.setFitHeight(PIXEL_COUNT);
        double x = generateRandomCoordinate();
        double y = generateRandomCoordinate();
        imageView.setTranslateX(x);
        imageView.setTranslateY(y);
        return imageView;
    }
    private static int nextPowerOf2(final int number) {
        int n = number;
        final int bound = 257;
        n = n - 1;

        while ((n & n - 1) != 0) {
            n = n & n - 1;
        }

        int nextPowerOf2 = n << 1;
        if (nextPowerOf2 < PIXEL_COUNT && nextPowerOf2 != 0) {
            return nextPowerOf2(RANDOM.nextInt(0, bound));
        }
        return nextPowerOf2;
    }

    /**
     * Generates a new random coordinate.
     * @return New coordinate
     */
    public static double generateRandomCoordinate() {
        final int bound = 257;
        final int randomChance = 2;
        if (RANDOM.nextInt(0, randomChance) == 0) {
            return nextPowerOf2(RANDOM.nextInt(0, bound));
        } else {
            return nextPowerOf2(RANDOM.nextInt(0, bound)) * -1;
        }
    }

    /**
     * Starts the program.
     * @param stage Window stage
     */
    @Override
    public void start(final Stage stage) {
        playMusic();
        // Create a new scene manager
        SceneManager sceneManager = new SceneManager(stage, LEVEL_MANAGER, MAP_MANAGER, user);

        // set name of the game
        stage.setTitle("Cosmos");
        // ensure user cannot resize
        stage.setResizable(false);
        // make scene-manager generate a new level
        stage.setScene(sceneManager.createTitleScene());
        // show the game on screen
        stage.show();

    }
    private void playMusic() {
        String musicFile = "src/main/resources/sfx/background.wav";
        final double volume = 0.1;
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(volume);
        mediaPlayer.setCycleCount(INDEFINITE);
        mediaPlayer.play();
    }


    /**
     * Get Map Manager instance.
     * @return This map manager instance
     */
    public static MapManager getMapManager() {
        return MAP_MANAGER;
    }

    /**
     * Get Level Manager instance.
     * @return This level manager instance
     */
    public static LevelManager getLevelManager() {
        return LEVEL_MANAGER;
    }

    /**
     * Set the user.
     * @param inputUser Current User
     */
    public static void setUser(final User inputUser) {
        user = inputUser;
    }

    /**
     * Driver of the program.
     * @param args System Arguments, Not Used
     */
    public static void main(final String[] args) {
        launch();
    }
}
