package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ItemSpawner {

    private final ArrayList<Item> items = new ArrayList<>();
    private final StackPane root;
    private final UI ui;
    public ItemSpawner(final StackPane root, final UI ui) {
        this.root = root;
        this.ui = ui;
    }

    public void spawnItems(Player player) throws Exception {

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
    }
    private void initialSpawn() throws Exception {
        Item coin = new Coin(root, ui);
        Item heart = new Heart(root, ui);
        Item sword = new Sword(root, ui);
        items.add(sword);
        items.add(heart);
        items.add(coin);
        for (Item item : items) {
            if (item.getType().equals("Sword")) {
                item.createItem(3);
            } else if (item.getType().equals("Coin")) {
                item.createItem(2);
            } else if (item.getType().equals("Heart")) {
                item.createItem(1);
            }
        }
    }

    private boolean checkEqual(final Player player) {
        ArrayList<HashMap<String, Double>> coordinateList = new ArrayList<>();
        for (Item item : items) {
            coordinateList.add(item.getCoordinates());
        }
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


    public void checkItemState(final Player player) {
        int swordIndex = 0;
        boolean removeSword = false;
        for (Item item : items) {
            if (item.getType().equals("Sword") && player.getCoordinates().equals(item.getCoordinates())) {
                player.playerHasSword = true;
                item.collectable();
                item.nullImage();
                item.consume();
                removeSword = true;
            } else if (item.getType().equals("Coin") && player.getCoordinates().equals(item.getCoordinates())) {
                item.collectable();
                item.setX(HelloApplication.generateRandomCoordinate());
                item.setY(HelloApplication.generateRandomCoordinate());
                while (checkEqual(player)) {
                    item.setX(HelloApplication.generateRandomCoordinate());
                    item.setY(HelloApplication.generateRandomCoordinate());
                }
            } else if (item.getType().equals("Heart") && player.getCoordinates().equals(item.getCoordinates())) {
                item.collectable();
                item.nullImage();
                item.consume();
                System.out.println("Touch Heart");
            }
        }
        if (removeSword) {
            items.remove(swordIndex);
        }
    }
}