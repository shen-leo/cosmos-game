package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Heart extends Item implements Collectable {
    private final UI ui;

    public Heart(StackPane pane, UI ui) {
        super(pane);
        this.ui = ui;
    }

    @Override
    public void collectable() {
        String musicFile = "src/main/resources/sfx/health.wav";

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        ui.addHeart();
    }
    @Override
    public String getType() {
        return "Heart";
    }
}
