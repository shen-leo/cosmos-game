package ca.bcit.comp2522.termprojec.olu;
/**
 * Manages game Stats.
 * @author Urjit, Leo
 * @version 2022
 */
public class Stats {
    private int coinsCollected = 0;
    private int numberOfEnemiesPlayerHit = 0;

    /**
     * Zero Parameter constructor.
     */
    public Stats() {

    }

    /**
     * Increments coins collected.
     */
    public void incrementCoinsCollected() {
        this.coinsCollected++;
    }

    /**
     * Increments Damage Taken.
     */
    public void incrementNumberOfEnemiesPlayerHit() {
        this.numberOfEnemiesPlayerHit++;
    }

    /**
     * Get Coins collected.
     * @return Total Coins collected
     */
    public int getCoinsCollected() {
        return this.coinsCollected;
    }

    /**
     * Get Damage Taken.
     * @return Total damage taken
     */
    public int getNumberOfEnemiesPlayerHit() {
        return numberOfEnemiesPlayerHit;
    }
}
