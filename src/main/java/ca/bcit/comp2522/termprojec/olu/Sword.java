package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.layout.StackPane;

import java.util.Objects;

public class Sword extends Item implements Collectable {
    private final UI ui;
    public Sword(StackPane pane, UI ui) {
        super(pane);
        this.ui = ui;
    }

    @Override
    public void collectable() {
        ui.addSword();
    }

    @Override
    public void respawn() {
        super.setX(HelloApplication.generateRandomCoordinate());
        super.setY(HelloApplication.generateRandomCoordinate());
    }
    @Override
    public String getType() {
        return "Sword";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sword sword = (Sword) o;
        return Objects.equals(ui, sword.ui);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ui);
    }
}
