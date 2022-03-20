package ca.bcit.comp2522.termprojec.olu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class UIHandler {
    @FXML
    private Label direction;

    @FXML
    protected void movePlayer() {
        direction.setText("Welcome to JavaFX Application!");
    }

}