package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;


public class Item {
    private final StackPane pane;
    private ImageView imageView;
    private double x = 0;
    private double y = 0;

    public Item(StackPane pane) {
        this.pane = pane;
    }


    // creates the visual representation of the item instance
    public void createItem(int itemName) throws Exception {
        InputStream is = null;

        switch (itemName) {
            case 1 -> is = Files.newInputStream(Paths.get("src/main/resources/images/items/heart_icon.png"));
            case 2 -> is = Files.newInputStream(Paths.get("src/main/resources/images/items/coin.png"));
        }

        x = HelloApplication.generateRandomCoordinate();
        y = HelloApplication.generateRandomCoordinate();

        assert is != null;
        Image img = new Image(is);
        is.close();

        imageView = new ImageView(img);
        imageView.setFitWidth(64);
        imageView.setFitHeight(64);

        imageView.setTranslateX(x);
        imageView.setTranslateY(y);
        pane.getChildren().addAll(imageView);
    }

    public void setX(int x) {
        this.x = x;
        imageView.setTranslateX(x);
    }

    public void setY(int y) {
        this.y = y;
        imageView.setTranslateY(y);
    }

    // returns the coordinates of the current item instance
    public HashMap<String, Double> getCoordinates() {
        HashMap<String, Double> coordinates = new HashMap<>();
        coordinates.put("x", this.x);
        coordinates.put("y", this.y);
        return coordinates;
    }

    // removes item instance's imageview
    public void nullImage() {
        this.imageView.setImage(null);
    }

    // removes the item from the playable board
    public void consume() {
        this.x = -1;
        this.y = -1;
    }

    public void collectable() {
    }

    public void respawn() {
    }
}
