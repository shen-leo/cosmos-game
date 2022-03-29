package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class Player {
    private final StackPane pane;
    private ImageView imageView;
    private final UI ui;
    private int x;
    private int y;
    public Player(StackPane pane, UI ui) {
        this.pane = pane;
        this.ui = ui;
    }

    public void displayPlayer() throws IOException {
        InputStream is = Files.newInputStream(Paths.get("src/main/resources/images/player.png"));
        Image img = new Image(is);
        is.close();

        imageView = new ImageView(img);
        imageView.setFitWidth(64);
        imageView.setFitHeight(64);
        x = HelloApplication.generateRandomCoordinate();
        y = HelloApplication.generateRandomCoordinate();
        imageView.setTranslateX(x);
        imageView.setTranslateY(y);
        System.out.println(y);
        pane.getChildren().addAll(imageView);
    }
    public boolean clamp(int cordToCompare,int nextCord) {
        return cordToCompare + nextCord <= 64 * 4 && cordToCompare + nextCord >= 64 * -4;
    }
    public void moveRight() {
        if (clamp(x,64)) {
            x = x + 64;
            imageView.setTranslateX(x);
        } else {
            ui.removeHeart();
        }
    }
    public void moveLeft() {
        if (clamp(x,-64)) {
            x = x - 64;
            imageView.setTranslateX(x);
        } else {
            ui.removeHeart();
        }
    }
    public void moveUp() {
        if (clamp(y,-64)) {
            y = y - 64;
            imageView.setTranslateY(y);
        } else {
            ui.removeHeart();
        }
    }
    public void moveDown() {
        if (clamp(y,64)) {
            y = y + 64;
            imageView.setTranslateY(y);
        } else {
            ui.removeHeart();
        }
    }

    public HashMap<String, Integer> getCoordinates() {
        HashMap<String, Integer> coordinates = new HashMap<>();
        coordinates.put("x", this.x);
        coordinates.put("y", this.y);
        return coordinates;
    }
}
