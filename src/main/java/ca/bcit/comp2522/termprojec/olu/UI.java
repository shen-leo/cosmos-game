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

public class UI {

    private final StackPane stackPane;
    private final SceneManager sceneManager;

    private ImageView heartOne;
    private ImageView heartTwo;
    private ImageView heartThree;

    private Text coinCounterText;

    public UI(StackPane stackPane, SceneManager sceneManager) {
        this.stackPane = stackPane;
        this.sceneManager = sceneManager;
        createCoinCounter();
    }


    public void createBackGroundTile()throws IOException {
        InputStream is = Files.newInputStream(Paths.get("src/main/resources/images/backgrounds/background.png"));
        Image img = new Image(is);
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(576);
        imageView.setFitHeight(576);
        stackPane.getChildren().addAll(imageView);

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

        heartOne.setTranslateX(-530);
        heartOne.setTranslateY(-350);

        heartTwo.setTranslateX(-456);
        heartTwo.setTranslateY(-350);

        heartThree.setTranslateX(-382);
        heartThree.setTranslateY(-350);

        stackPane.getChildren().addAll(heartOne, heartTwo, heartThree);

        firstHeart.close();
        secondHeart.close();
        thirdHeart.close();
    }
    public void removeHeart() {
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
            sceneManager.gameOver();
        }
    }

    private void createCoinCounter() {
        coinCounterText = new Text("0");
        coinCounterText.setFont(Font.font(50));
        coinCounterText.setTranslateY(-350);
        stackPane.getChildren().addAll(coinCounterText);
    }
    public void updateCoinCounter() {
        int currentCount = Integer.parseInt(coinCounterText.getText());
        currentCount++;
        coinCounterText.setText(String.valueOf(currentCount));
    }
}
