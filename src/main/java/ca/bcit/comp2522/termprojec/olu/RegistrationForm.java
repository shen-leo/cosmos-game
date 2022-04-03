package ca.bcit.comp2522.termprojec.olu;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Properties;

public class RegistrationForm extends JDialog {
    private JTextField tfName;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JPasswordField pfConfirmPassword;
    private JButton btnRegister;
    private JButton btnCancel;
    private JPanel registerPanel;

    private User user;

    public RegistrationForm(final JFrame parent) {
        super(parent);
        setTitle("Create a New Account");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }

    private void registerUser() {
        String name = tfName.getText();
        String username = tfUsername.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String confirmPassword = String.valueOf(pfConfirmPassword.getPassword());

        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Passwords do not match",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        user = addUserToDatabase(name, username, password);
        if (user != null) {
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to register new user",
                    "Try Again", JOptionPane.ERROR_MESSAGE);
        }
    }

    private User addUserToDatabase(String name, String username, String password) {
        User user = null;
        int totalDeaths = 0;
        int totalSouls = 0;
        final String DB_URL = "jdbc:mysql://localhost:3306/comp2522-game";

        final Properties connectionProperties = new Properties();
        connectionProperties.put("user", "root"); // change to local MySQL username
        connectionProperties.put("password", ""); // change to local MySQL password

        try {
            Connection conn = DriverManager.getConnection(DB_URL, connectionProperties);
            if (conn != null) {
                System.out.println("Successfully connected to MySQL database test");
            }

            // Connected to database successfully...
            assert conn != null;
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO users (name, username, password, totalDeaths, totalSouls) "
                    + "VALUES (?, ?, ?, ? , ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setInt(4, totalDeaths);
            preparedStatement.setInt(5, totalSouls);


            //Insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new User();
                user.setName(name);
                user.setUsername(username);
                user.setPassword(password);
                user.setDeaths(totalDeaths);
                user.setSouls(totalSouls);
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                JOptionPane.showMessageDialog(this, "Username is already in use",
                        "Try Again", JOptionPane.ERROR_MESSAGE);
            } else {
                e.printStackTrace();
            }
        }

        setVisible(false);
        dispose();
        return user;
    }

    public static User getRegisterUser() {

        RegistrationForm myForm = new RegistrationForm(null);
        User user = myForm.user;
        if (user != null) {
            System.out.println("Successful registration of: " + user.getUsername());
        } else {
            System.out.println("Registration cancelled");
        }

        return user;
    }
}
