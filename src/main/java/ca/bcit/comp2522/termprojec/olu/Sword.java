package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Objects;

/**
 * Spawn and Manage Swords.
 * @author Urjit, Leo
 * @version 2022
 */
public class Sword extends Item implements Collectable {
    private final UI ui;

    /**
     * Constructs a new Sword.
     * @param pane Pane to add Sword to
     * @param ui UI to manage Sword sprite
     */
    public Sword(final StackPane pane, final UI ui) {
        super(pane);
        this.ui = ui;
    }
    /**
     * Collectable method for Sword.
     */
    @Override
    public void collectable() {
        String musicFile = "src/main/resources/sfx/sword.wav";

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        ui.addSword();
    }

    /**
     * Respawn method for Sword.
     */
    @Override
    public void respawn() {
        super.setX(HelloApplication.generateRandomCoordinate());
        super.setY(HelloApplication.generateRandomCoordinate());
    }
    /**
     * Get this objects type.
     * @return Returns Sword
     */
    @Override
    public String getType() {
        return "Sword";
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
        Sword sword = (Sword) o;
        return Objects.equals(ui, sword.ui);
    }

    /**
     * Hashcode method.
     * @return Hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(ui);
    }
}
