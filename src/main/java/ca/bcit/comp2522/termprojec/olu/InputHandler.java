package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.List;
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
    private List<DamageTile> damageTiles;
    private final List<Enemy> enemies;
    private final ItemSpawner itemSpawner;
    private boolean respawnEnemy = false;
    private int enemyRespawnCounter = 0;
    public InputHandler(Scene scene, Player player, List<Enemy> enemies,
                        ItemSpawner itemSpawner) {
        this.scene = scene;
        this.player = player;
        this.enemies = enemies;
        this.itemSpawner = itemSpawner;
        readInput();
    }
    public InputHandler(Scene scene, Player player, List<Enemy> enemies,
                        ItemSpawner itemSpawner, List<DamageTile> damageTiles) {
        this.scene = scene;
        this.player = player;
        this.enemies = enemies;
        this.itemSpawner = itemSpawner;
        this.damageTiles = damageTiles;
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
            if (respawnEnemy) {
                enemyRespawnCounter++;
                try {
                    respawnEnemy();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                moveEnemy();
            }
            checkPlayerOverDamageTiles();
            checkPlayerOverEnemy();
        });
    }
    private void checkPlayerOverDamageTiles() {
        if (damageTiles != null) {
            for (DamageTile damageTile : damageTiles) {
                if (damageTile.getCoordinates().equals(player.getCoordinates())) {
                    player.takeDamage();
                }
            }
        }
    }
    private void respawnEnemy() throws IOException {
        if (enemyRespawnCounter == 3) {
            for (Enemy enemy : enemies) {
                enemy.displayEnemy();
            }
            respawnEnemy = false;
            enemyRespawnCounter = 0;
        }
    }
    private void checkPlayerOverEnemy() {
        Enemy tempEnemy = null;
        Enemy enemyToRemove = null;
        for (Enemy enemy : enemies) {
            tempEnemy = enemy.checkEnemyState(player);

            if (tempEnemy != null) {
                enemyToRemove = enemy;
                enemy.consume();
                enemy.nullImage();
                respawnEnemy = true;
            }
        }
        if (tempEnemy != null) {
            enemies.add(tempEnemy);
            enemies.remove(enemyToRemove);
        }
    }
    private void moveEnemy() {
        Random random = new Random();
        for (Enemy enemy : enemies) {
            int shouldMove = random.nextInt(11);
            if (shouldMove < 8) {
                enemy.startPathFind();
            }
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