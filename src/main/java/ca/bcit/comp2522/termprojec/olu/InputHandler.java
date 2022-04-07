package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.util.Random;

/**
 * .
 *
 * @author Urjit
 * @version 2022
 */
public class InputHandler {
    private final Scene scene;
    private final Player player;
    private Enemy enemy;
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
            moveEnemy();
            switch (key.getCode()) {
                // Change face direction
                case A, LEFT -> moveLeft();
                case D, RIGHT -> moveRight();
                case W, UP -> moveUp();
                case S, DOWN -> moveDown();
            }
        });
    }
    private void moveEnemy() {
        Random random = new Random();
        Enemy tempEnemy;
        int shouldMove = random.nextInt(11);
        if (shouldMove < 9) {
            enemy.startPathFind();
        }
        tempEnemy = enemy.checkEnemyState(player);
        if (tempEnemy != null) {
            this.enemy = tempEnemy;
        }
    }
    private void moveRight() {
        player.moveRight();
        try {
            this.itemSpawner.checkItemState(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void moveLeft() {
        player.moveLeft();
        try {
            this.itemSpawner.checkItemState(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void moveUp() {
        player.moveUp();
        try {
            this.itemSpawner.checkItemState(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void moveDown() {
        player.moveDown();
        try {
            this.itemSpawner.checkItemState(player);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}