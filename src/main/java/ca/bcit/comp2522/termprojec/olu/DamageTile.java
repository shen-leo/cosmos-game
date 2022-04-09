package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class DamageTile {
    private final Player player;
    private final StackPane pane;
    private ImageView imageView;
    private final UI ui;
    private double x;
    private double y;
    public DamageTile(Player player, StackPane pane, UI ui) throws IOException {
        this.player = player;
        this.pane = pane;
        this.ui = ui;
        createDamageTile();
    }
    public void createDamageTile() throws IOException {
        InputStream is = Files.newInputStream(Paths.get("src/main/resources/images/enemy/dangerTile.png"));

        imageView = HelloApplication.displaySprite(is);
        is.close();
        this.x = imageView.getTranslateX();
        this.y = imageView.getTranslateY();
        pane.getChildren().addAll(imageView);
    }
    public HashMap<String, Double> getCoordinates() {
        HashMap<String, Double> coordinates = new HashMap<>();
        coordinates.put("x", this.x);
        coordinates.put("y", this.y);
        return coordinates;
    }
}
