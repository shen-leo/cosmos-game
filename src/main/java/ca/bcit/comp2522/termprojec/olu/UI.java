package ca.bcit.comp2522.termprojec.olu;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class UI {

    private final StackPane stackPane;
    private final SceneManager sceneManager;
    private final Countdown timer = new Countdown();
    private ImageView heartOne;
    private ImageView heartTwo;
    private ImageView heartThree;
    private ImageView sword;


    private Text coinCounterText;
    private Text countdownText;
    public boolean respawnSword = false;
    private boolean specialLevel = false;
    public UI(StackPane stackPane, SceneManager sceneManager) throws IOException {
        this.stackPane = stackPane;
        this.sceneManager = sceneManager;
        createCoinCounter();
        createCountdown();
    }

    private void createBackGround(InputStream is) {
        Image img = new Image(is);
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(HelloApplication.BACKGROUND_WIDTH);
        imageView.setFitHeight(HelloApplication.BACKGROUND_HEIGHT);
        stackPane.getChildren().addAll(imageView);
    }
    public void createBackGroundTile()throws IOException {
        InputStream is = Files.newInputStream(Paths.get("src/main/resources/images/backgrounds/background.png"));
        HelloApplication.setBackgroundBound(257);
        HelloApplication.setBackgroundHeight(576);
        HelloApplication.setBackgroundWidth(576);
        createBackGround(is);
        is.close();
    }
    public void createSpecialBackGroundTile()throws IOException {
        this.specialLevel = true;
        countdownText.setTranslateY(-390);
        HelloApplication.setBackgroundBound(321);
        InputStream is = Files.newInputStream(Paths.get("src/main/resources/images/backgrounds/biggerBackground.png"));
        HelloApplication.setBackgroundHeight(704);
        HelloApplication.setBackgroundWidth(704);
        createBackGround(is);
        is.close();
    }
    public void createHeart() throws IOException {
        InputStream firstHeart = Files.newInputStream(Paths.get("src/main/resources/images/c.png"));
        InputStream secondHeart = Files.newInputStream(Paths.get("src/main/resources/images/c.png"));
        InputStream thirdHeart = Files.newInputStream(Paths.get("src/main/resources/images/c.png"));

        Image heartImageOne = new Image(firstHeart);
        Image heartImageTwo = new Image(secondHeart);
        Image heartImageThree = new Image(thirdHeart);

        heartOne = new ImageView(heartImageOne);
        heartTwo = new ImageView(heartImageTwo);
        heartThree = new ImageView(heartImageThree);

        heartOne.setFitWidth(64);
        heartOne.setFitHeight(64);

        heartTwo.setFitWidth(64);
        heartTwo.setFitHeight(64);

        heartThree.setFitWidth(64);
        heartThree.setFitHeight(64);

        heartOne.setTranslateX(-540);
        heartOne.setTranslateY(-380);

        heartTwo.setTranslateX(-466);
        heartTwo.setTranslateY(-380);

        heartThree.setTranslateX(-392);
        heartThree.setTranslateY(-380);

        stackPane.getChildren().addAll(heartOne, heartTwo, heartThree);

        firstHeart.close();
        secondHeart.close();
        thirdHeart.close();
    }
    public void removeHeart() {
        HelloApplication.stats.incrementNumberOfEnemiesPlayerHit();
        if (heartThree.isVisible()) {
            heartThree.setVisible(false);
        } else if (heartTwo.isVisible()) {
            heartTwo.setVisible(false);
        } else {
            heartOne.setVisible(false);
        }
        checkPlayerDead();
    }
    public void addHeart() {
        if (!heartTwo.isVisible()) {
            heartTwo.setVisible(true);
        } else if (!heartThree.isVisible()) {
            heartThree.setVisible(true);
        }
    }
    private void checkPlayerDead() {
        if (!heartOne.isVisible()) {
            // add code to stop timer
            timer.stopTimer();
            sceneManager.gameOver();
        }
    }

    private void createCoinCounter() throws IOException {
//        InputStream is = Files.newInputStream(Paths.get("src/main/resources/images/items/coin.png"));
        InputStream is = Files.newInputStream(Paths.get("src/main/resources/images/items/soul.gif"));
        Image img = new Image(is);
        ImageView coinDisplay = new ImageView(img);
        coinDisplay.setFitWidth(64);
        coinDisplay.setFitHeight(64);
        coinDisplay.setTranslateX(456);
        coinDisplay.setTranslateY(-350);
        coinCounterText = new Text("0");
        coinCounterText.setFont(Font.font(50));
        coinCounterText.setTranslateX(505);
        coinCounterText.setTranslateY(-353);
        stackPane.getChildren().addAll(coinCounterText, coinDisplay);
    }
    public void updateCoinCounter() {
        HelloApplication.stats.incrementCoinsCollected();
        int currentCount = Integer.parseInt(coinCounterText.getText());
        currentCount++;
        // update total coin counter
        sceneManager.getUser().incrementSouls();
        coinCounterText.setText(String.valueOf(currentCount));
    }

    private void createCountdown() {
        timer.startCountdown(this, 30); // changed for testing purposes
        countdownText = new Text("30"); // changed for testing purposes
        countdownText.setFont(Font.font(50));
        countdownText.setTranslateY(-350);

        stackPane.getChildren().addAll(countdownText);
    }

    public void updateCountdown(final int time) {
        countdownText.setText(String.valueOf(time));
        if (time == 0) {
            int currentCount = Integer.parseInt(coinCounterText.getText());
            if (currentCount >= 2) { // changed for testing purposes
                sceneManager.nextLevel();
            } else {
                sceneManager.gameOver();
            }
        }
    }

    public void addSword() {
        try {
            InputStream is = Files.newInputStream(Paths.get("src/main/resources/images/items/sword.png"));
            Image img = new Image(is);
            sword = new ImageView(img);
            sword.setFitWidth(64);
            sword.setFitHeight(64);
            sword.setTranslateX(456);
            sword.setTranslateY(-250);
            stackPane.getChildren().addAll(sword);

            is.close();
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

    }
    public void removeSword() {
        if (sword.isVisible()) {
            if (specialLevel) {
                respawnSword = true;
            }
            stackPane.getChildren().remove(sword);
        }
    }

    public void setRespawnSword(boolean respawnSword) {
        this.respawnSword = respawnSword;
    }
}
