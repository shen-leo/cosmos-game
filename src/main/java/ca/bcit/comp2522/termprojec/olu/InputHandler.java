package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

/**
 * .
 *
 * @author Urjit
 * @version 2022
 */
public class InputHandler {
    private final Scene scene;
    private final Player player;
    public InputHandler(Scene scene, Player player) {
        this.scene = scene;
        this.player = player;
        readInput();
    }

    private void readInput() {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                // Change face direction
                case A -> player.moveLeft();
                case D -> player.moveRight();
                case W -> player.moveUp();
                case S -> player.moveDown();
                // Move direction
                case RIGHT -> System.out.println("You pressed right");

                case LEFT -> System.out.println("You pressed left");

                case DOWN -> System.out.println("You pressed down");

                case UP -> System.out.println("You pressed up");

            }
        });

    }
}
