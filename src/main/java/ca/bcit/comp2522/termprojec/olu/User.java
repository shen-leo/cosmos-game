package ca.bcit.comp2522.termprojec.olu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Manages User data.
 * @author Urjit, Leo
 * @version 2022
 */
public class User {
    private String name;
    private String username;

    private int totalDeaths = 0;
    private int totalSouls = 0;

    /**
     * Get Name.
     * @return Returns users name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get username.
     * @return Returns users username
     */
    public String getUsername() {
        return this.username;
    }
    /**
     * Set Name.
     * @param name Users new name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Set new username.
     * @param username New username
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Set new password.
     * @param password New password
     */
    public void setPassword(final String password) {
    }

    /**
     * Set deaths.
     * @param deaths Set total deaths
     */
    public void setDeaths(final int deaths) {
        this.totalDeaths = deaths;
    }

    /**
     * Set souls.
     * @param souls Set users souls collected
     */
    public void setSouls(final int souls) {
        this.totalSouls = souls;
    }

    /**
     * Increment Souls collected.
     */
    public void incrementSouls() {
        this.totalSouls++;
    }

    /**
     * Increment Deaths.
     */
    public void incrementDeaths() {
        this.totalDeaths++;
    }

    /**
     * Save Game Data.
     * @throws SQLException If database could not connect
     * @throws ClassNotFoundException If Sql connector not found
     */
    public void saveGame() throws SQLException, ClassNotFoundException {
        final int number = 3;
        Class.forName("com.mysql.cj.jdbc.Driver");

        // We identify the driver, the rdbms, the host, the port, and the schema name
        final String sqlURL = "jdbc:mysql://localhost:3306/comp2522-game-cosmos";

        // We need to send a user and a password when we try to connect!
        final Properties connectionProperties = new Properties();
        connectionProperties.put("user", "root");
        connectionProperties.put("password", "");

        // We establish a connection...
        final Connection connection = DriverManager.getConnection(sqlURL, connectionProperties);
        if (connection != null) {
            System.out.println("Successfully connected to MySQL database test");
        }

        try {
            // Create a statement to send on the connection...
            assert connection != null;
            Statement stmt = connection.createStatement();

            String sql = "UPDATE users SET totalDeaths = ?, totalSouls = ? WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, this.totalDeaths);
            preparedStatement.setInt(2, this.totalSouls);
            preparedStatement.setString(number, this.username);
            preparedStatement.executeUpdate();

            stmt.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("SQL Exception caught, invalid SQL query!");
        }
    }
}
