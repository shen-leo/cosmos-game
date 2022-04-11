package ca.bcit.comp2522.termprojec.olu;

import java.io.File;
import java.util.HashMap;

public class MapManager {
    private final HashMap<Integer, File> backgroundMap = new HashMap<>();
    private final HashMap<Integer, String> enemiesMap = new HashMap<>();
    private final HashMap<Integer, String> tilesMap = new HashMap<>();
    private final HashMap<Integer, String> bigTilesMap = new HashMap<>();

    public MapManager() {
        backgroundMap.put(1, new File("src/main/resources/images/Backgrounds/wallpapers/purple_nebula.jpg"));
        backgroundMap.put(2, new File("src/main/resources/images/Backgrounds/wallpapers/green_nebula.jpg"));
        backgroundMap.put(3, new File("src/main/resources/images/Backgrounds/wallpapers/sand_nebula.jpg"));
        backgroundMap.put(4, new File("src/main/resources/images/Backgrounds/wallpapers/blue_nebula.jpg"));
        backgroundMap.put(5, new File("src/main/resources/images/Backgrounds/wallpapers/red_nebula.jpg"));

        tilesMap.put(1, "src/main/resources/images/Backgrounds/purple_background.png");
        tilesMap.put(2, "src/main/resources/images/Backgrounds/green_background.png");
        tilesMap.put(3, "src/main/resources/images/Backgrounds/sand_background.png");
        tilesMap.put(4, "src/main/resources/images/Backgrounds/blue_background.png");
        tilesMap.put(5, "src/main/resources/images/Backgrounds/black_background.png");

        bigTilesMap.put(1, "src/main/resources/images/Backgrounds/purple_bigger_background.png");
        bigTilesMap.put(2, "src/main/resources/images/Backgrounds/green_bigger_background.png");
        bigTilesMap.put(3, "src/main/resources/images/Backgrounds/sand_bigger_background.png");
        bigTilesMap.put(4, "src/main/resources/images/Backgrounds/blue_bigger_background.png");
        bigTilesMap.put(5, "src/main/resources/images/Backgrounds/black_bigger_background.png");

        enemiesMap.put(1, "src/main/resources/images/enemy/eye_monster.gif");
        enemiesMap.put(2, "src/main/resources/images/enemy/chimera.gif");
        enemiesMap.put(3, "src/main/resources/images/enemy/anubis.gif");
        enemiesMap.put(4, "src/main/resources/images/enemy/warlock.gif");
        enemiesMap.put(5, "src/main/resources/images/enemy/archfiend.gif");
    }

    private int convertLevel(final int level) {
        return level % 5 % 5;
    }

    public File getMap(final int level) {
        if (level > 5) {
            return backgroundMap.get(convertLevel(level));
        }
        return backgroundMap.get(level);
    }

    public String getEnemy(final int level) {
        if (level > 5) {
            return enemiesMap.get(convertLevel(level));
        }
        return enemiesMap.get(level);
    }

    public String getTiles(final int level) {
        if (level > 5) {
            return tilesMap.get(convertLevel(level));
        }
        return tilesMap.get(level);
    }

    public String getBigTiles(final int level) {
        if (level > 5) {
            return bigTilesMap.get(convertLevel(level));
        }
        return bigTilesMap.get(level);
    }

}
