package ca.bcit.comp2522.termprojec.olu;

public class User {
    private String name;
    private String username;
    private String password;

    private int totalDeaths;
    private int totalSouls;

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }

    public int getDeaths() {
        return this.totalDeaths;
    }

    public int getSouls() {
        return this.totalSouls;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setDeaths(final int deaths) {
        this.totalDeaths = deaths;
    }

    public void setSouls(final int souls) {
        this.totalSouls = souls;
    }

}
