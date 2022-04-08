package ca.bcit.comp2522.termprojec.olu;

import java.sql.*;
import java.util.Properties;


public class User {
    private String name;
    private String username;
    private String password;

    private int totalDeaths = 0;
    private int totalSouls = 0;

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

    public void incrementSouls() {
        this.totalSouls++;
    }

    public void incrementDeaths() {
        this.totalDeaths++;
    }

    public void saveGame() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        // We identify the driver, the rdbms, the host, the port, and the schema name
        final String sqlURL = "jdbc:mysql://localhost:3306/comp2522-game";

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
            preparedStatement.setString(3, this.username);
            preparedStatement.executeUpdate();
            // SQL query to insert a new tuple into the users table
//            stmt.executeUpdate("UPDATE users SET totalDeaths = ?, totalSouls = ? WHERE username = ?");

        } catch (SQLException e) {
            System.out.println("SQL Exception caught, invalid SQL query!");
        }
    }
}
