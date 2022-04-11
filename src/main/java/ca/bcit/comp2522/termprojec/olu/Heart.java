package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Objects;

/**
 * Spawn and Manage Hearts.
 * @author Urjit, Leo
 * @version 2022
 */
public class Heart extends Item implements Collectable {
    private final UI ui;
    /**
     * Constructs a new Heart.
     * @param pane Pane to add Heart to
     * @param ui UI to manage heart sprite
     */
    public Heart(final StackPane pane, final UI ui) {
        super(pane);
        this.ui = ui;
    }

    /**
     * Collectable method for heart.
     */
    @Override
    public void collectable() {
        String musicFile = "src/main/resources/sfx/health.wav";

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        ui.addHeart();
    }

    @Override
    public void respawn() {
    }

    /**
     * Get this objects type.
     * @return Returns Heart
     */
    @Override
    public String getType() {
        return "Heart";
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
        Heart heart = (Heart) o;
        return Objects.equals(ui, heart.ui);
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
