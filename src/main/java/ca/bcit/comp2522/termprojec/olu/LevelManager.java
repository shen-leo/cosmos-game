package ca.bcit.comp2522.termprojec.olu;

public class LevelManager {
    private int level;

    private LevelManager() {
        this.level = 1;
    }

    // static factory method to initialize the LevelManager constructor
    static LevelManager initLevel() {
        return new LevelManager();
    }

    public void nextLevel() {
        this.level++;
    }

    public void resetLevel() {
        this.level = 1;
    }

    public int getLevel() {
        return this.level;
    }

}
