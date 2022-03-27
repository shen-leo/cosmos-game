package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;


public class Item {
    private final StackPane pane;
    private ImageView imageView;
    private final Random random;
    private int x = 0;
    private int y = 0;

    public Item(StackPane pane) {
        this.pane = pane;
        random = new Random();
    }

    private int nextPowerOf2(int n) {
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
    private int generateRandomCoordinate() {
        if (random.nextInt(0,2) == 0) {
            return nextPowerOf2(random.nextInt(0, 257));
        } else {
            return nextPowerOf2(random.nextInt(0, 257)) * -1;
        }
    }

    // creates the visual representation of the item instance
    public void createItem() throws IOException {

        x = generateRandomCoordinate();
        y = generateRandomCoordinate();

        InputStream is = Files.newInputStream(Paths.get("src/main/resources/images/heart_icon.png"));
        Image img = new Image(is);
        is.close();

        imageView = new ImageView(img);
        imageView.setFitWidth(64);
        imageView.setFitHeight(64);

        imageView.setTranslateX(x);
        imageView.setTranslateY(y);
        System.out.println(y);
        pane.getChildren().addAll(imageView);
    }

    // returns the coordinates of the current item instance
    public HashMap<String, Integer> getCoordinates() {
        HashMap<String, Integer> coordinates = new HashMap<>();
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
}
