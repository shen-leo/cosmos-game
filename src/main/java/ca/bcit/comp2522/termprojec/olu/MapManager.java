package ca.bcit.comp2522.termprojec.olu;

import java.io.File;
import java.util.HashMap;
/**
 * Manages Map specific graphical assets.
 * @author Urjit, Leo
 * @version 2022
 */
public class MapManager {
    private static final int INDEX_ONE = 1;
    private static final int INDEX_TWO = 2;
    private static final int INDEX_THREE = 3;
    private static final int INDEX_FOUR = 4;
    private static final int INDEX_FIVE = 5;
    private static final int MAPSET_MOD_VALUE = 5;

    private final HashMap<Integer, File> backgroundMap = new HashMap<>();
    private final HashMap<Integer, String> enemiesMap = new HashMap<>();
    private final HashMap<Integer, String> tilesMap = new HashMap<>();
    private final HashMap<Integer, String> bigTilesMap = new HashMap<>();

    /**
     * Zero Parameter Constructor to set the level assets.
     */
    public MapManager() {
        backgroundMap.put(INDEX_ONE, new File("src/main/resources/images/Backgrounds/wallpapers/purple_nebula.jpg"));
        backgroundMap.put(INDEX_TWO, new File("src/main/resources/images/Backgrounds/wallpapers/green_nebula.jpg"));
        backgroundMap.put(INDEX_THREE, new File("src/main/resources/images/Backgrounds/wallpapers/sand_nebula.jpg"));
        backgroundMap.put(INDEX_FOUR, new File("src/main/resources/images/Backgrounds/wallpapers/blue_nebula.jpg"));
        backgroundMap.put(INDEX_FIVE, new File("src/main/resources/images/Backgrounds/wallpapers/red_nebula.jpg"));

        tilesMap.put(INDEX_ONE, "src/main/resources/images/Backgrounds/purple_background.png");
        tilesMap.put(INDEX_TWO, "src/main/resources/images/Backgrounds/green_background.png");
        tilesMap.put(INDEX_THREE, "src/main/resources/images/Backgrounds/sand_background.png");
        tilesMap.put(INDEX_FOUR, "src/main/resources/images/Backgrounds/blue_background.png");
        tilesMap.put(INDEX_FIVE, "src/main/resources/images/Backgrounds/black_background.png");

        bigTilesMap.put(INDEX_ONE, "src/main/resources/images/Backgrounds/purple_bigger_background.png");
        bigTilesMap.put(INDEX_TWO, "src/main/resources/images/Backgrounds/green_bigger_background.png");
        bigTilesMap.put(INDEX_THREE, "src/main/resources/images/Backgrounds/sand_bigger_background.png");
        bigTilesMap.put(INDEX_FOUR, "src/main/resources/images/Backgrounds/blue_bigger_background.png");
        bigTilesMap.put(INDEX_FIVE, "src/main/resources/images/Backgrounds/black_bigger_background.png");

        enemiesMap.put(INDEX_ONE, "src/main/resources/images/enemy/eye_monster.gif");
        enemiesMap.put(INDEX_TWO, "src/main/resources/images/enemy/chimera.gif");
        enemiesMap.put(INDEX_THREE, "src/main/resources/images/enemy/anubis.gif");
        enemiesMap.put(INDEX_FOUR, "src/main/resources/images/enemy/warlock.gif");
        enemiesMap.put(INDEX_FIVE, "src/main/resources/images/enemy/archfiend.gif");
    }

    private int convertLevel(final int level) {
        return level % MAPSET_MOD_VALUE % MAPSET_MOD_VALUE;
    }

    /**
     * Get the game's background.
     * @param level Current level
     * @return File corresponding to level
     */
    public File getMap(final int level) {
        final int maxLevel = 5;
        if (level > maxLevel) {
            return backgroundMap.get(convertLevel(level));
        }
        return backgroundMap.get(level);
    }

    /**
     * Get the enemy graphics.
     * @param level Current level
     * @return String file path for enemy graphic
     */
    public String getEnemy(final int level) {
        final int maxLevel = 5;
        if (level > maxLevel) {
            return enemiesMap.get(convertLevel(level));
        }
        return enemiesMap.get(level);
    }

    /**
     * Get regular tiles.
     * @param level Current Level
     * @return String path for tile
     */
    public String getTiles(final int level) {
        final int maxLevel = 5;
        if (level > maxLevel) {
            return tilesMap.get(convertLevel(level));
        }
        return tilesMap.get(level);
    }

    /**
     * Get bigger tileset for special levels.
     * @param level Current level
     * @return String path for big tiles
     */
    public String getBigTiles(final int level) {
        final int maxLevel = 5;
        if (level > maxLevel) {
            return bigTilesMap.get(convertLevel(level));
        }
        return bigTilesMap.get(level);
    }

}
