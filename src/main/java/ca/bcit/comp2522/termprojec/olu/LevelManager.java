package ca.bcit.comp2522.termprojec.olu;
/**
 * Manages Levels.
 * @author Urjit, Leo
 * @version 2022
 */
public final class LevelManager {
    private int level;

    private LevelManager() {
        this.level = 1;
    }

    // static factory method to initialize the LevelManager constructor
    static LevelManager initLevel() {
        return new LevelManager();
    }

    /**
     * Increments the level.
     */
    public void nextLevel() {
        this.level++;
    }

    /**
     * Resets the level.
     */
    public void resetLevel() {
        this.level = 1;
    }

    /**
     * Gets the Level.
     * @return Current level
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Sets the level.
     * @param level New level
     */
    public void setLevel(final int level) {
        this.level = level;
    }

}
