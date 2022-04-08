package ca.bcit.comp2522.termprojec.olu;

import java.io.File;
import java.util.HashMap;

public class MapManager {
    private final HashMap<Integer, File> backgroundMap = new HashMap<>();
    private final HashMap<Integer, String> enemiesMap = new HashMap<>();

    public MapManager() {
        backgroundMap.put(1, new File("src/main/resources/images/Backgrounds/green-background.jpg"));
        backgroundMap.put(2, new File("src/main/resources/images/Backgrounds/pink-background.jpg"));

        enemiesMap.put(1, "src/main/resources/images/enemy/warlock.gif");
        enemiesMap.put(2, "src/main/resources/images/enemy/archfiend.gif");
    }

    public File getMap(int level) {
        return backgroundMap.get(level);
    }

    public String getEnemy(int level) {
        return enemiesMap.get(level);
    }

}
