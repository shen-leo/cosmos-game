package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.layout.StackPane;

public class Coin extends Item implements Collectable{
    private final UI ui;
    public Coin(StackPane pane, UI ui) {
        super(pane);
        this.ui = ui;
    }

    @Override
    public void collectable() {
        ui.updateCoinCounter();
    }

    @Override
    public void respawn() {
        super.setX(HelloApplication.generateRandomCoordinate());
        super.setY(HelloApplication.generateRandomCoordinate());
    }
}
