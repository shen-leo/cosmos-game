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
    private final Enemy enemy;
    private final ItemSpawner itemSpawner;

    public InputHandler(Scene scene, Player player, Enemy enemy,
                        ItemSpawner itemSpawner) {
        this.scene = scene;
        this.player = player;
        this.enemy = enemy;
        this.itemSpawner = itemSpawner;
        readInput();
    }

    private void readInput() {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                // Change face direction
                case A, LEFT -> moveLeft();
                case D, RIGHT -> moveRight();
                case W, UP -> moveUp();
                case S, DOWN -> moveDown();
            }
        });
    }

    private void moveRight() {
        enemy.moveEnemy();
        player.moveRight();
        try {
            this.itemSpawner.checkItemState(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void moveLeft() {
        enemy.moveEnemy();
        player.moveLeft();
        try {
            this.itemSpawner.checkItemState(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void moveUp() {
        enemy.moveEnemy();
        player.moveUp();
        try {
            this.itemSpawner.checkItemState(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void moveDown() {
        enemy.moveEnemy();
        player.moveDown();
        try {
            this.itemSpawner.checkItemState(player);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}