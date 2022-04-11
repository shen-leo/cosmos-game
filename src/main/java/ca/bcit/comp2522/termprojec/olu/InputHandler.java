package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Input Handler.
 * @author Urjit, Leo
 * @version 2022
 */
public class InputHandler {
    private final Scene scene;
    private final Player player;
    private List<Enemy> enemies;
    private final ItemSpawner itemSpawner;
    private boolean respawnEnemy = false;
    private int enemyRespawnCounter = 0;
    private boolean specialLevel;

    /**
     * Constructor for new Input Handler.
     * @param scene Scene
     * @param player Player
     * @param enemies List of enemies
     * @param itemSpawner Item spawner
     */
    public InputHandler(final Scene scene, final Player player, final List<Enemy> enemies,
                        final ItemSpawner itemSpawner) {
        this.scene = scene;
        this.player = player;
        this.enemies = enemies;
        this.itemSpawner = itemSpawner;
        readInput();
    }

    /**
     * Constructor for new Input Handler for special levels.
     * @param scene Scene
     * @param player Player
     * @param enemies List of enemies
     * @param itemSpawner Item spawner
     * @param specialLevel Boolean, true if special level
     */
    public InputHandler(final Scene scene, final Player player, final List<Enemy> enemies,
                        final ItemSpawner itemSpawner, final boolean specialLevel) {
        this.scene = scene;
        this.player = player;
        this.enemies = enemies;
        this.itemSpawner = itemSpawner;
        this.specialLevel = specialLevel;
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
                default -> System.out.println("Press W, A, S, D or Arrow keys");
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
            if (enemies != null) {
                checkPlayerOverEnemy();
            }
        });
    }

    private void respawnEnemy() throws IOException {
        final int respawnCount = 3;
        if (enemyRespawnCounter == respawnCount) {
            if (specialLevel) {
                enemies = itemSpawner.spawnEnemy(player, 2);
            } else {
                enemies = itemSpawner.spawnEnemy(player, 1);
            }
            for (Enemy enemy : enemies) {
                enemy.displayEnemy();
            }
            respawnEnemy = false;
            enemyRespawnCounter = 0;
        }
    }
    private void checkPlayerOverEnemy() {
        boolean emptyEnemyList = false;
        for (Enemy enemy : enemies) {
            if (enemy.checkEnemyState(player)) {
                emptyEnemyList = true;
                respawnEnemy = true;
            }

        }
        if (emptyEnemyList) {
            for (Enemy enemy : enemies) {
                enemy.consume();
                enemy.nullImage();
            }
            enemies = null;
        }

    }
    private void moveEnemy() {
        final int bound = 11;
        final int canMove = 8;
        Random random = new Random();
        for (Enemy enemy : enemies) {
            int shouldMove = random.nextInt(bound);
            if (shouldMove < canMove) {
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