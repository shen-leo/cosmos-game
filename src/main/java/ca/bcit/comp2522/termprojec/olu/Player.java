package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;

/**
 * Handles all the movement and logic for the player.
 * @author Urjit, Leo
 * @version 2022
 */
public class Player {
    private static final int POS_64 = 64;
    private static final int NEG_64 = -64;
    private final StackPane pane;
    private ImageView imageView;
    private final UI ui;
    private double x;
    private double y;
    private boolean playerHasSword = false;

    /**
     * Player constructor.
     * @param pane Stack pane for player sprite
     * @param ui Ui to add and remove player heats
     */
    public Player(final StackPane pane, final UI ui) {
        this.pane = pane;
        this.ui = ui;
    }

    /**
     * Displays a new sprite for player.
     * @throws IOException If player sprite file not found
     */
    public void displayPlayer() throws IOException {
        InputStream is = Files.newInputStream(Paths.get("src/main/resources/images/player.gif"));
        imageView = HelloApplication.displaySprite(is);
        is.close();
        x = imageView.getTranslateX();
        y = imageView.getTranslateY();
        pane.getChildren().addAll(imageView);
    }
    private boolean clamp(final Double cordToCompare, final int nextCord) {
        return cordToCompare + nextCord <= (HelloApplication.BACKGROUND_BOUND - 1)
                && cordToCompare + nextCord >= (HelloApplication.BACKGROUND_BOUND - 1) * -1;
    }

    /**
     * Move player to the right.
     */
    public void moveRight() {
        if (clamp(x, POS_64)) {
            x = x + POS_64;
            imageView.setTranslateX(x);
        } else {
            ui.removeHeart();
        }
    }

    /**
     * Move player to the left.
     */
    public void moveLeft() {
        if (clamp(x, NEG_64)) {
            x = x - POS_64;
            imageView.setTranslateX(x);
        } else {
            ui.removeHeart();
        }
    }
    /**
     * Move player to the up.
     */
    public void moveUp() {
        if (clamp(y, NEG_64)) {
            y = y - POS_64;
            imageView.setTranslateY(y);
        } else {
            ui.removeHeart();
        }
    }
    /**
     * Move player to the down.
     */
    public void moveDown() {
        if (clamp(y, POS_64)) {
            y = y + POS_64;
            imageView.setTranslateY(y);
        } else {
            ui.removeHeart();
        }
    }
    /**
     * Get player X coordinate.
     * @return Players current X coordinate
     */
    public double getX() {
        return this.x;
    }
    /**
     * Get player Y coordinate.
     * @return Players current Y coordinate
     */
    public double getY() {
        return this.y;
    }

    /**
     * Get playerHasSword boolean.
     * @return True or False if the playerHasSword
     */
    public boolean getPlayerHasSword() {
        return this.playerHasSword;
    }

    /**
     * Set playerHasSword boolean.
     * @param playerHasSword Boolean state of playerHasSword
     */
    public void setPlayerHasSword(final boolean playerHasSword) {
        this.playerHasSword = playerHasSword;
    }

    /**
     * Get player X and Y coordinate.
     * @return Players current X and Y coordinate wrapped in a HashMap
     */
    public HashMap<String, Double> getCoordinates() {
        HashMap<String, Double> coordinates = new HashMap<>();
        coordinates.put("x", this.x);
        coordinates.put("y", this.y);
        return coordinates;
    }
    /**
     * To string method for player.
     */
    @Override
    public String toString() {
        return String.format("(%f, %f)", x, y);
    }
    /**
     * Equals method for player.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return Double.compare(player.x, x) == 0
                && Double.compare(player.y, y) == 0 && playerHasSword == player.playerHasSword
                && Objects.equals(pane, player.pane)
                && Objects.equals(imageView, player.imageView)
                && Objects.equals(ui, player.ui);
    }
    /**
     * Hashcode method for player.
     */
    @Override
    public int hashCode() {
        return Objects.hash(pane, imageView, ui, x, y, playerHasSword);
    }
}
