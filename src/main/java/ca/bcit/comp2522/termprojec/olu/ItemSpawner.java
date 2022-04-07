package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class ItemSpawner {

    private final ArrayList<Item> items = new ArrayList<>();

    public ItemSpawner(final StackPane root, final UI ui) {

        Item coin = new Coin(root, ui);
        Item heart = new Heart(root, ui);
        Item sword = new Sword(root, ui);
        items.add(sword);
        items.add(heart);
        items.add(coin);
    }

    public void spawnItems(Player player) throws Exception {
        initialSpawn();
        while (!checkEqual(player)) {
            initialSpawn();
        }
    }

    private void initialSpawn() throws Exception {
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

        ArrayList<String> coordinateList = new ArrayList<>();
        for (Item item : items) {
            coordinateList.add(item.toString());
        }
        coordinateList.add(player.toString());

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
                do {
                    item.respawn();
                }
                while (checkEqual(player));
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