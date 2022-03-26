package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ItemSpawner {

    private final Item item1;
    private final Item item2;
    private final Item item3;
    private final HashMap<String, Integer> playerCoord;

    public ItemSpawner(final StackPane root, final HashMap<String, Integer> playerCoord) {

        this.item1 = new Item(root);
        this.item2 = new Item(root);
        this.item3 = new Item(root);
        this.playerCoord = playerCoord;
    }

    public void spawnItems() throws IOException {

        do {
            item1.createItem(this.playerCoord);
            item2.createItem(this.playerCoord);
            item3.createItem(this.playerCoord);
        }
        while (!checkEqual());
    }

    private boolean checkEqual() {

        ArrayList<HashMap<String, Integer>> coordinateList = new ArrayList<>();

        coordinateList.add(item1.getCoordinates());
        coordinateList.add(item2.getCoordinates());
        coordinateList.add(item3.getCoordinates());
        coordinateList.add(playerCoord);

        for (int i = 0; i < coordinateList.size(); i++) {
            for (int j = i + 1; j < coordinateList.size(); j++) {
                if (coordinateList.get(i).equals(coordinateList.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
