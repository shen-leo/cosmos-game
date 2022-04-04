package ca.bcit.comp2522.termprojec.olu;

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
    private double x;
    private double y;
    public boolean playerHasSword = false;
    public Player(StackPane pane, UI ui) {
        this.pane = pane;
        this.ui = ui;
    }

    public void displayPlayer() throws IOException {
        InputStream is = Files.newInputStream(Paths.get("src/main/resources/images/player.png"));
        imageView = HelloApplication.displaySprite(is);
        is.close();
        x = imageView.getTranslateX();
        y = imageView.getTranslateY();
        pane.getChildren().addAll(imageView);
    }
    public boolean clamp(Double cordToCompare,int nextCord) {
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
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public HashMap<String, Double> getCoordinates() {
        HashMap<String, Double> coordinates = new HashMap<>();
        coordinates.put("x", this.x);
        coordinates.put("y", this.y);
        return coordinates;
    }
    @Override
    public String toString() { return String.format("(%f, %f)", x, y); }
}
