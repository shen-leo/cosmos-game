package ca.bcit.comp2522.termprojec.olu;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Manages UI for game.
 * @author Urjit, Leo
 * @version 2022
 */
public class UI {
    /**
     * Boolean for if the player has the sword.
     */
    public boolean respawnSword = false;
    private final StackPane stackPane;
    private final SceneManager sceneManager;
    private final Countdown timer = new Countdown();
    private ImageView heartOne;
    private ImageView heartTwo;
    private ImageView heartThree;
    private ImageView sword;
    private int backgroundBound;

    private Text coinCounterText;
    private Text countdownText;

    private boolean specialLevel = false;

    /**
     * Constructs new UI.
     * @param stackPane Pane to place images on
     * @param sceneManager Scene Manager
     * @throws IOException If file for images not found
     */
    public UI(final StackPane stackPane, final SceneManager sceneManager) throws IOException {
        this.stackPane = stackPane;
        this.sceneManager = sceneManager;
        createCoinCounter();
        createRequiredSouls();
        createCountdown();
    }

    private void createBackGround(final InputStream is) {
        final int normalArea = 576;
        final int largeArea = 704;
        Image img = new Image(is);
        ImageView imageView = new ImageView(img);
        if (specialLevel) {
            imageView.setFitWidth(largeArea);
            imageView.setFitHeight(largeArea);
        } else {
            imageView.setFitWidth(normalArea);
            imageView.setFitHeight(normalArea);
        }
        stackPane.getChildren().addAll(imageView);
    }

    /**
     * Creates background tiles.
     * @throws IOException If file for images not found
     */
    public void createBackGroundTile()throws IOException {
        final int bound = 257;
        InputStream is;
        if (HelloApplication.getMapManager().getEnemy(HelloApplication.getLevelManager().getLevel()) != null) {
            is = Files.newInputStream(Paths.get(HelloApplication.getMapManager()
                    .getTiles(HelloApplication.getLevelManager().getLevel())));
        } else {
            is = Files.newInputStream(Paths.get("src/main/resources/images/backgrounds/background.png"));
        }
        this.backgroundBound = bound;

        createBackGround(is);
        is.close();
    }

    /**
     * Creates special background tiles.
     * @throws IOException If file for images not found
     */
    public void createSpecialBackGroundTile()throws IOException {
        final int countDownLocation = -390;
        final int bound = 321;
        this.specialLevel = true;
        countdownText.setTranslateY(countDownLocation);
        this.backgroundBound = bound;

        InputStream is;
        if (HelloApplication.getMapManager().getEnemy(HelloApplication.getLevelManager().getLevel()) != null) {
            is = Files.newInputStream(Paths.get(HelloApplication.getMapManager()
                    .getBigTiles(HelloApplication.getLevelManager().getLevel())));
        } else {
            is = Files.newInputStream(Paths.get("src/main/resources/images/backgrounds/biggerBackground.png"));
        }

        createBackGround(is);
        is.close();
    }

    /**
     * Creates Hearts.
     * @throws IOException If file for images not found
     */
    public void createHeart() throws IOException {
        final int pixelSize = 64;
        final int heart1Location = -540;
        final int heart1LocationY = -380;
        final int heart2Location = -466;
        final int heart2LocationY = -380;
        final int heart3Location = -392;
        final int heart3LocationY = -380;
        InputStream firstHeart = Files.newInputStream(Paths.get("src/main/resources/images/c.png"));
        InputStream secondHeart = Files.newInputStream(Paths.get("src/main/resources/images/c.png"));
        InputStream thirdHeart = Files.newInputStream(Paths.get("src/main/resources/images/c.png"));

        Image heartImageOne = new Image(firstHeart);
        Image heartImageTwo = new Image(secondHeart);
        Image heartImageThree = new Image(thirdHeart);

        heartOne = new ImageView(heartImageOne);
        heartTwo = new ImageView(heartImageTwo);
        heartThree = new ImageView(heartImageThree);

        heartOne.setFitWidth(pixelSize);
        heartOne.setFitHeight(pixelSize);

        heartTwo.setFitWidth(pixelSize);
        heartTwo.setFitHeight(pixelSize);

        heartThree.setFitWidth(pixelSize);
        heartThree.setFitHeight(pixelSize);

        heartOne.setTranslateX(heart1Location);
        heartOne.setTranslateY(heart1LocationY);

        heartTwo.setTranslateX(heart2Location);
        heartTwo.setTranslateY(heart2LocationY);

        heartThree.setTranslateX(heart3Location);
        heartThree.setTranslateY(heart3LocationY);

        stackPane.getChildren().addAll(heartOne, heartTwo, heartThree);

        firstHeart.close();
        secondHeart.close();
        thirdHeart.close();
    }

    /**
     * Removes hearts from UI.
     */
    public void removeHeart() {
        HelloApplication.STATS.incrementNumberOfEnemiesPlayerHit();
        if (heartThree.isVisible()) {
            heartThree.setVisible(false);
        } else if (heartTwo.isVisible()) {
            heartTwo.setVisible(false);
        } else {
            heartOne.setVisible(false);
        }
        checkPlayerDead();
    }

    /**
     * Add hearts to UI.
     */
    public void addHeart() {
        if (!heartTwo.isVisible()) {
            heartTwo.setVisible(true);
        } else if (!heartThree.isVisible()) {
            heartThree.setVisible(true);
        }
    }

    /**
     * Checks if player has died.
     */
    private void checkPlayerDead() {
        if (!heartOne.isVisible()) {
            timer.stopTimer();
            sceneManager.gameOver();
        }
    }

    private void createCoinCounter() throws IOException {
        final int pixelSize = 64;
        final int location = 456;
        final int locationY = -350;
        final int fontSize = 50;
        final int textLocation = 505;
        final int textLocationY = -353;
        InputStream is = Files.newInputStream(Paths.get("src/main/resources/images/items/soul.gif"));
        Image img = new Image(is);
        ImageView coinDisplay = new ImageView(img);
        coinDisplay.setFitWidth(pixelSize);
        coinDisplay.setFitHeight(pixelSize);
        coinDisplay.setTranslateX(location);
        coinDisplay.setTranslateY(locationY);
        coinCounterText = new Text("0");
        coinCounterText.setFill(Color.WHITE);
        coinCounterText.setFont(Font.font(fontSize));
        coinCounterText.setTranslateX(textLocation);
        coinCounterText.setTranslateY(textLocationY);
        stackPane.getChildren().addAll(coinCounterText, coinDisplay);
    }

    /**
     * Updates coin counter.
     */
    public void updateCoinCounter() {
        HelloApplication.STATS.incrementCoinsCollected();
        int currentCount = Integer.parseInt(coinCounterText.getText());
        currentCount++;
        // update total coin counter
        if (sceneManager.getUser() != null) {
            sceneManager.getUser().incrementSouls();
        }
        coinCounterText.setText(String.valueOf(currentCount));
    }

    private void createCountdown() {
        final int duration = 40;
        final int font = 50;
        final int locationY = -350;
        timer.startCountdown(this, duration); // changed for testing purposes
        countdownText = new Text("40"); // changed for testing purposes
        countdownText.setFill(Color.WHITE);
        countdownText.setFont(Font.font(font));
        countdownText.setTranslateY(locationY);

        stackPane.getChildren().addAll(countdownText);
    }

    /**
     * Updates countdown.
     * @param time Length of countdown
     */
    public void updateCountdown(final int time) {
        countdownText.setText(String.valueOf(time));
        if (time == 0) {
            int currentCount = Integer.parseInt(coinCounterText.getText());
            if (currentCount >= HelloApplication.getLevelManager().getLevel()) { // changed for testing purposes
                sceneManager.nextLevel();
            } else {
                sceneManager.gameOver();
            }
        }
    }

    /**
     * Get background bound of image.
     * @return Current background Bound
     */
    public int getBackgroundBound() {
        return backgroundBound;
    }

    private void createRequiredSouls() {
        final int font = 25;
        final int locationX = 480;
        final int locationY = -300;
        Text requiredSoulsText = new Text(
                String.format("Required Souls: %d", HelloApplication.getLevelManager().getLevel()));
        requiredSoulsText.setFill(Color.WHITE);
        requiredSoulsText.setFont(Font.font(font));
        requiredSoulsText.setTranslateX(locationX);
        requiredSoulsText.setTranslateY(locationY);
        stackPane.getChildren().addAll(requiredSoulsText);
    }

    /**
     * Adds swords to UI.
     */
    public void addSword() {
        final int size = 64;
        final int locationX = 480;
        final int locationY = -230;
        try {
            InputStream is = Files.newInputStream(Paths.get("src/main/resources/images/items/shield.png"));
            Image img = new Image(is);
            sword = new ImageView(img);
            sword.setFitWidth(size);
            sword.setFitHeight(size);
            sword.setTranslateX(locationX);
            sword.setTranslateY(locationY);
            stackPane.getChildren().addAll(sword);

            is.close();
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Removes sword from UI.
     */
    public void removeSword() {
        if (sword.isVisible()) {
            if (specialLevel) {
                respawnSword = true;
            }
            stackPane.getChildren().remove(sword);
        }
    }

    /**
     * Sets sword respawn.
     * @param respawnSword state of respawnSword
     */
    public void setRespawnSword(final boolean respawnSword) {
        this.respawnSword = respawnSword;
    }
}
