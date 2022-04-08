package ca.bcit.comp2522.termprojec.olu;

import java.io.File;
import java.util.HashMap;

public class MapManager {
    private final HashMap<Integer, File> backgroundMap = new HashMap<>();

    public MapManager() {
        backgroundMap.put(1, new File("src/main/resources/images/Backgrounds/green-background.jpg"));
        backgroundMap.put(2, new File("src/main/resources/images/Backgrounds/pink-background.jpg"));
    }

    public File getMap(int level) {
        return backgroundMap.get(level);
    }

}
