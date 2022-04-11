package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Objects;

/**
 * Spawn and Manage Coins.
 * @author Urjit, Leo
 * @version 2022
 */
public class Coin extends Item implements Collectable {
    private final UI ui;

    /**
     * Constructs a new Coin.
     * @param pane Pane to add Coin to
     * @param ui UI to manage coin sprite
     */
    public Coin(final StackPane pane, final UI ui) {
        super(pane);
        this.ui = ui;
    }

    /**
     * Collectable method for coin.
     */
    @Override
    public void collectable() {
        String musicFile = "src/main/resources/sfx/coins.wav";

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        ui.updateCoinCounter();
    }

    /**
     * Respawn method for coin.
     */
    @Override
    public void respawn() {
        super.setX(HelloApplication.generateRandomCoordinate());
        super.setY(HelloApplication.generateRandomCoordinate());
    }
    /**
     * Get this objects type.
     * @return Returns Coin
     */
    @Override
    public String getType() {
        return "Coin";
    }
    /**
     * Equals method.
     * @param o Object to compare
     * @return True if equal
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Coin coin = (Coin) o;
        return Objects.equals(ui, coin.ui);
    }
    /**
     * Hashcode method.
     * @return Hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ui);
    }
}
