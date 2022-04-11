package ca.bcit.comp2522.termprojec.olu;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Properties;

/**
 * Facilitates the back-end logic for the user registration form.
 * @author Urjit, Leo
 * @version 2022
 */
public class RegistrationForm extends JDialog {
    private static final int FORM_WIDTH = 450;
    private static final int FORM_HEIGHT = 474;
    private static final int INDEX_ONE = 1;
    private static final int INDEX_TWO = 2;
    private static final int INDEX_THREE = 3;
    private static final int INDEX_FOUR = 4;
    private static final int INDEX_FIVE = 5;

    private JTextField tfName;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JPasswordField pfConfirmPassword;
    private JButton btnRegister;
    private JButton btnCancel;
    private JPanel registerPanel;

    private User user;

    /**
     * Declares the registration form settings and defines action listeners for buttons.
     * @param parent the JFrame parent window
     */
    public RegistrationForm(final JFrame parent) {
        super(parent);
        setTitle("Create a New Account");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(FORM_WIDTH, FORM_HEIGHT));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                registerUser();
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

    private User addUserToDatabase(final String name, final String username, final String inputPassword) {

//        User user = null;
        int totalDeaths = 0;
        int totalSouls = 0;
        final String connectionURL = "jdbc:mysql://localhost:3306/comp2522-game";

        String password = PasswordHash.encrypt(inputPassword); // encrypts the inputted password

        final Properties connectionProperties = new Properties();
        connectionProperties.put("user", "root"); // change to local MySQL username
        connectionProperties.put("password", ""); // change to local MySQL password

        try {
            Connection conn = DriverManager.getConnection(connectionURL, connectionProperties);
            if (conn != null) {
                System.out.println("Successfully connected to MySQL database test");
            }

            // Connected to database successfully...
            assert conn != null;
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO users (name, username, password, totalDeaths, totalSouls) "
                    + "VALUES (?, ?, ?, ? , ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(INDEX_ONE, name);
            preparedStatement.setString(INDEX_TWO, username);
            preparedStatement.setString(INDEX_THREE, password);
            preparedStatement.setInt(INDEX_FOUR, totalDeaths);
            preparedStatement.setInt(INDEX_FIVE, totalSouls);

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

    /**
     * Getter for the registered user.
     * @return a User object, representing the registered user
     */
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
