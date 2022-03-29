package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.layout.StackPane;

public class Heart extends Item implements Collectable {
    private final UI ui;

    public Heart(StackPane pane, UI ui) {
        super(pane);
        this.ui = ui;
    }

    @Override
    public void collectable() {
        ui.addHeart();
    }

}
