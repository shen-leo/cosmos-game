package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemSpawner {

    private final Item coin;
    private final Item heart;

    public ItemSpawner(final StackPane root, final UI ui) {

        this.coin = new Coin(root, ui);
        this.heart = new Heart(root, ui);
    }

    public void spawnItems(Player player) throws Exception {

        do {

            coin.createItem(2);
            heart.createItem(1);

        }
        while (checkEqual(player));
    }

    private boolean checkEqual(final Player player) {

        ArrayList<HashMap<String, Integer>> coordinateList = new ArrayList<>();
        coordinateList.add(coin.getCoordinates());
        coordinateList.add(heart.getCoordinates());
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

        if (player.getCoordinates().equals(coin.getCoordinates())) {
            coin.collectable();
            do {
                coin.respawn();
            }
            while (checkEqual(player));
        }

        if (player.getCoordinates().equals(heart.getCoordinates())) {
            heart.collectable();
            heart.nullImage();
            heart.consume();
            System.out.println("Touch Item2");
        }
    }

}
