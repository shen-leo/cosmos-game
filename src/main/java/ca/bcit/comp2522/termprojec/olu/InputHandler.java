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
    private final ItemSpawner itemSpawner;
    public InputHandler(Scene scene, Player player, ItemSpawner itemSpawner) {
        this.scene = scene;
        this.player = player;
        this.itemSpawner = itemSpawner;
        readInput();
    }

    private void readInput() {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                // Change face direction
                case A -> {
                    player.moveLeft();
                    this.itemSpawner.checkItemState(player); }
                case D -> {
                    player.moveRight();
                    this.itemSpawner.checkItemState(player); }
                case W -> {
                    player.moveUp();
                    this.itemSpawner.checkItemState(player); }
                case S -> {
                    player.moveDown();
                    this.itemSpawner.checkItemState(player); }
                // Move direction
                case RIGHT -> System.out.println("You pressed right");
                case LEFT -> System.out.println("You pressed left");
                case DOWN -> System.out.println("You pressed down");
                case UP -> System.out.println("You pressed up");
            }
        });
    }
}
