package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class DamageTile {
    private final Player player;
    private final StackPane pane;
    private ImageView imageView;
    private final UI ui;
    private double x;
    private double y;
    public DamageTile(Player player, StackPane pane, UI ui) {
        this.player = player;
        this.pane = pane;
        this.ui = ui;
    }
    public void createDamageTile() {

    }
}
