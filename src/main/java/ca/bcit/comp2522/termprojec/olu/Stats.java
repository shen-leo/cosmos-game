package ca.bcit.comp2522.termprojec.olu;

public class Stats {
    private int coinsCollected = 0;
    private int numberOfEnemiesPlayerHit = 0;

    public Stats() {

    }

    public void incrementCoinsCollected() {
        this.coinsCollected++;
    }
    public void incrementNumberOfEnemiesPlayerHit() {
        this.numberOfEnemiesPlayerHit++;
    }
    public int getCoinsCollected() {return this.coinsCollected;}

    public int getNumberOfEnemiesPlayerHit() {
        return numberOfEnemiesPlayerHit;
    }
}
