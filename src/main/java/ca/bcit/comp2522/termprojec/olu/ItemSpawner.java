package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.layout.StackPane;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Spawn and Manage Items.
 * @author Urjit, Leo
 * @version 2022
 */
public class ItemSpawner {

    private final ArrayList<Item> items = new ArrayList<>();
    private final StackPane root;
    private final UI ui;
    private ArrayList<HashMap<String, Double>> itemLocations = new ArrayList<>();
    /**
     * Constructor for ItemSpawner.
     * @param root Root to add items to
     * @param ui UI to update and manage items
     */
    public ItemSpawner(final StackPane root, final UI ui) {
        this.root = root;
        this.ui = ui;
    }

    /**
     * Spawns items.
     * @param player Player
     * @throws Exception If file for item is not found
     */
    public void spawnItems(final Player player) throws Exception {

        initialSpawn();
        while (checkEqual(player)) {
            removeSprites();
            initialSpawn();
        }
    }
    private void removeSprites() {
        for (Item item : items) {
            item.nullImage();
            item.consume();
        }
        Iterator<Item> it = items.iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        itemLocations = new ArrayList<>();
    }
    private void initialSpawn() throws Exception {
        final int swordID = 3;
        final int coinID = 2;
        final int heartID = 1;
        Item coin = new Coin(root, ui);
        Item heart = new Heart(root, ui);
        Item sword = new Sword(root, ui);
        items.add(sword);
        items.add(heart);
        items.add(coin);
        for (Item item : items) {
            if (item.getType().equals("Sword")) {
                item.createItem(swordID);
            } else if (item.getType().equals("Coin")) {
                item.createItem(coinID);
            } else if (item.getType().equals("Heart")) {
                item.createItem(heartID);
            }
        }
        itemLocations.add(coin.getCoordinates());
        itemLocations.add(heart.getCoordinates());
        itemLocations.add(sword.getCoordinates());
    }

    private boolean checkEqual(final Player player) {
        ArrayList<HashMap<String, Double>> coordinateList = new ArrayList<>(itemLocations);
        coordinateList.add(player.getCoordinates());


        for (int i = 0; i < coordinateList.size(); i++) {
            for (int j = i + 1; j < coordinateList.size(); j++) {
                if (coordinateList.get(i).equals(coordinateList.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkIfExists(final HashMap<String, Double> newCoordinate) {
        for (HashMap<String, Double> cord : itemLocations) {
            if (cord.equals(newCoordinate)) {
                return true;
            }
        }
        return false;
    }
    private HashMap<String, Double> makeNewCoordinate() {
        HashMap<String, Double> coordinate = new HashMap<>();
        coordinate.put("x", HelloApplication.generateRandomCoordinate());
        coordinate.put("y", HelloApplication.generateRandomCoordinate());
        return coordinate;
    }
    /**
     * Check if items or player collide.
     * @param player Player
     */
    public void checkItemState(final Player player) {
        for (Item item : items) {
            if (item.getType().equals("Sword")) {
                if (player.getCoordinates().equals(item.getCoordinates())) {
                    player.setPlayerHasSword(true);
                    item.collectable();
                    item.consume();
                    item.setImageView(false);
                }
                if (ui.respawnSword) {
                    ui.setRespawnSword(false);
                    item.setImageView(true);
                    HashMap<String, Double> newCord = makeNewCoordinate();
                    while (checkIfExists(newCord)) {
                        newCord = makeNewCoordinate();
                    }
                    item.setX(newCord.get("x"));
                    item.setY(newCord.get("y"));
                }
            } else if (item.getType().equals("Coin") && player.getCoordinates().equals(item.getCoordinates())) {
                item.collectable();
                HashMap<String, Double> newCord = makeNewCoordinate();
                while (checkIfExists(newCord)) {
                    newCord = makeNewCoordinate();
                }
                item.setX(newCord.get("x"));
                item.setY(newCord.get("y"));
            } else if (item.getType().equals("Heart") && player.getCoordinates().equals(item.getCoordinates())) {
                item.collectable();
                item.nullImage();
                item.consume();
            }
        }
    }

    /**
     * Spawn new enemies.
     * @param player Player
     * @param quantity Number of enemies to spawn
     * @return Arraylist of enemies
     */
    public ArrayList<Enemy> spawnEnemy(final Player player, final int quantity) {
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            enemies.add(new Enemy(player, root, ui));
        }
        return enemies;
    }
}
