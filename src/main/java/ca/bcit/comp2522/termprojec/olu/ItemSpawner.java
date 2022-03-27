package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ItemSpawner {

    private final Item item1;
    private final Item item2;
//    private final Item item3;
//    private final HashMap<String, Integer> initialPlayerCoord;

    public ItemSpawner(final StackPane root) {

        this.item1 = new Item(root);
        this.item2 = new Item(root);
//        this.item3 = new Item(root);
//        this.initialPlayerCoord = player.getCoordinates();
    }

    public void spawnItems(Player player) throws IOException {

        do {
            item1.createItem();
            item2.createItem();
//            item3.createItem();
        }
        while (!checkEqual(player));
    }

    private boolean checkEqual(final Player player) {

        ArrayList<HashMap<String, Integer>> coordinateList = new ArrayList<>();
        coordinateList.add(item1.getCoordinates());
        coordinateList.add(item2.getCoordinates());
//        coordinateList.add(item3.getCoordinates());
        coordinateList.add(player.getCoordinates());

        for (int i = 0; i < coordinateList.size(); i++) {
            for (int j = i + 1; j < coordinateList.size(); j++) {
                if (coordinateList.get(i).equals(coordinateList.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }


    public void checkItemState(final Player player) {

        if (player.getCoordinates().equals(item1.getCoordinates())) {
            item1.nullImage();
            item1.consume();
            System.out.println("Touch Item1");
        }

        if (player.getCoordinates().equals(item2.getCoordinates())) {
            item2.nullImage();
            item2.consume();
            System.out.println("Touch Item2");
        }
    }

}
