package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;

/**
 * Create and update Items.
 * @author Urjit, Leo
 * @version 2022
 */
public abstract class Item {
    private static final int PIXEL_SIZE = 64;
    private final StackPane pane;
    private ImageView imageView;
    private double x = 0;
    private double y = 0;
    /**
     * Constructor for Item class.
     * @param pane Pane to add item to
     */
    public Item(final StackPane pane) {
        this.pane = pane;
    }


    /**
     * Creates and displays the Item.
     * @param itemID ID of the item to create
     * @throws Exception If file not found
     */
    public void createItem(final int itemID) throws Exception {
        InputStream is;
        final int caseThree = 3;
        switch (itemID) {
            case 2 -> {
                is = Files.newInputStream(Paths.get("src/main/resources/images/items/soul.gif"));
            }
            case caseThree -> {
                is = Files.newInputStream(Paths.get("src/main/resources/images/items/shield.png"));
            }
            default -> {
                is = Files.newInputStream(Paths.get("src/main/resources/images/items/red_potion.gif"));
            }
        }

        x = HelloApplication.generateRandomCoordinate();
        y = HelloApplication.generateRandomCoordinate();

        Image img = new Image(is);
        is.close();

        imageView = new ImageView(img);
        imageView.setFitWidth(PIXEL_SIZE);
        imageView.setFitHeight(PIXEL_SIZE);

        imageView.setTranslateX(x);
        imageView.setTranslateY(y);
        pane.getChildren().addAll(imageView);
    }

    /**
     * Set Item X coordinate.
     * @param x New X coordinate
     */
    public void setX(final int x) {
        this.x = x;
        imageView.setTranslateX(x);
    }
    /**
     * Set Item Y coordinate.
     * @param y New Y coordinate
     */
    public void setY(final int y) {
        this.y = y;
        imageView.setTranslateY(y);
    }

    /**
     * Get the Items X and Y coordinate.
     * @return Returns the coordinates of the current item instance.
     */
    public HashMap<String, Double> getCoordinates() {
        HashMap<String, Double> coordinates = new HashMap<>();
        coordinates.put("x", this.x);
        coordinates.put("y", this.y);
        return coordinates;
    }

    /**
     * Removes item instance's imageview.
     */
    public void nullImage() {
        this.imageView.setImage(null);
    }

    /**
     * Removes the item from the playable board.
     */
    public void consume() {
        final int outOfBounds = -100000;
        this.x = outOfBounds;
        this.y = outOfBounds;
    }

    /**
     * Collectable method.
     */
    public void collectable() {
    }

    /**
     * Set the items image view visibility.
     * @param visibility True or False
     */
    public void setImageView(final boolean visibility) {
        this.imageView.setVisible(visibility);
    }

    /**
     * To string method.
     * @return This item coordinates
     */
    @Override
    public String toString() {
        return String.format("(%f, %f)", x, y);
    }

    /**
     * Respawn Method for item.
     */
    public abstract void respawn();

    /**
     * Get type of this item.
     * @return String Type of this Item
     */
    public String getType() {
        return "";
    }

    /**
     * Equals method.
     * @param o Object to compare
     * @return True if objects are same else false
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return Double.compare(item.x, x) == 0
                && Double.compare(item.y, y) == 0
                && Objects.equals(pane, item.pane)
                && Objects.equals(imageView, item.imageView);
    }

    /**
     * Hashcode method.
     * @return Hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(pane, imageView, x, y);
    }
}
