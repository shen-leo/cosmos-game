package ca.bcit.comp2522.termprojec.olu;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Facilitates the back-end logic for the user login form.
 * @author Urjit, Leo
 * @version 2022
 */
public class LoginForm extends JDialog {
    private static final int FORM_WIDTH = 450;
    private static final int FORM_HEIGHT = 474;

    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private JPanel loginPanel;

    private User user;

    /**
     * Declares the registration form settings and retrieves text inputs from the form's text and password fields.
     * @param parent the JFrame parent window
     */
    public LoginForm(final JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(FORM_WIDTH, FORM_HEIGHT));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                String username = tfUsername.getText();
                String password = String.valueOf(pfPassword.getPassword());

                user = getAuthenticatedUser(username, password);
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private User getAuthenticatedUser(final String username, final String inputPassword) {
//        User user = null;

        String password = PasswordHash.encrypt(inputPassword); // converts the inputted password to encrypted form

        final String urlConnection = "jdbc:mysql://localhost:3306/comp2522-game-cosmos";

        final Properties connectionProperties = new Properties();
        connectionProperties.put("user", "comp2522"); // change to local MySQL username
        connectionProperties.put("password", "I was born in 1973"); // change to local MySQL password

        try {
            Connection conn = DriverManager.getConnection(urlConnection, connectionProperties);
            if (conn != null) {
                System.out.println("Successfully connected to MySQL database test");
            }

            // Connected to database successfully...
            assert conn != null;
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setName(resultSet.getString("name"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setDeaths(resultSet.getInt("totalDeaths"));
                user.setSouls(resultSet.getInt("totalSouls"));
            }
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        setVisible(false);
        dispose();
        return user;
    }

    /**
     * Method to call the authentication process and form.
     * @return a User object, which is the currently authenticated user
     */
    public static User loginUser() {
        LoginForm loginForm = new LoginForm(null);
        User user = loginForm.user;

        if (user != null) {
            System.out.println("Successful Authentication of: " + user.getName());
            System.out.println("Username: " + user.getUsername());
        } else {
            System.out.println("Authentication cancelled");
        }

        return user;
    }
}

