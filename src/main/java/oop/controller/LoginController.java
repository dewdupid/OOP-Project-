package oop.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class LoginController {

    @FXML private Text titleText;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private Hyperlink toggleLink;
    @FXML private Label errorLabel;
    private static final String USER_FILE = "src/user/users.txt";
    private static final Map<String, String> userDatabase = new HashMap<>(); //user for when current program running (a temp before update)
    private boolean isRegisterMode = false;

    @FXML
    public void initialize() {
        loadUsers();
        registerButton.setVisible(false);
        registerButton.setManaged(false);
    }

    @FXML
    public void handleToggleMode() {
        if (!isRegisterMode) {
            switchToRegisterMode();
        } else {
            switchToLoginMode();
        }
    }

    @FXML
    public void handleLogin() {
        loadUsers();
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setTextFill(javafx.scene.paint.Color.RED);
            errorLabel.setText("Fields cannot be empty!");
            return;
        }
        if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) { // Validate credentials against HashMap
            navigateToDashboard();
        } else {
            errorLabel.setTextFill(javafx.scene.paint.Color.RED);
            errorLabel.setText("Invalid username or password!");
        }
    }

    @FXML
    public void handleRegister() {
        loadUsers();
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setTextFill(javafx.scene.paint.Color.RED);
            errorLabel.setText("Fields cannot be empty!");
            return;
        }
        if (userDatabase.containsKey(username)) {
            errorLabel.setTextFill(javafx.scene.paint.Color.RED);
            errorLabel.setText("Username already exists!");
            return;
        }
        userDatabase.put(username, password); // Save credentials to temporary memory
        saveUser(username, password);
        createUserInventoryFile(username);
        errorLabel.setTextFill(javafx.scene.paint.Color.GREEN);
        errorLabel.setText("Account registered successfully! Please login.");
        switchToLoginMode(); // Revert  to login mode
    }

    private void createUserInventoryFile(String username) {
        try {
            String filePath = "inventory/"
                    + username.toLowerCase().replaceAll("\\s+", "")
                    + "_inventory.txt";

            java.io.File file = new java.io.File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        userDatabase.clear();
        try {
            java.io.File file = new java.io.File(USER_FILE);

            // Create users folder if it doesn't exist
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
                try (java.io.BufferedWriter writer =
                             new java.io.BufferedWriter(
                                     new java.io.FileWriter(file))) {

                    writer.write("admin,1234");
                    writer.newLine();
                }
            }
            try (java.io.BufferedReader reader =
                         new java.io.BufferedReader(
                                 new java.io.FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");

                    if (parts.length == 2) {
                        userDatabase.put(
                                parts[0].trim(),
                                parts[1].trim()
                        );
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveUser(String username, String password) {
        try {
            java.io.File file = new java.io.File(USER_FILE);

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try (java.io.BufferedWriter writer =
                         new java.io.BufferedWriter(
                                 new java.io.FileWriter(file, true))) {

                writer.write(username + "," + password);
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*____________________________________
    This is all helper function (not involve "directly" with Login flow)
     -------------------------------------*/
    public void switchToRegisterMode() {
        isRegisterMode = true;
        titleText.setText("Register Account");
        loginButton.setVisible(false);
        loginButton.setManaged(false);
        registerButton.setVisible(true);
        registerButton.setManaged(true);
        toggleLink.setText("Already have an account? Back to Login");
        errorLabel.setText("");
        clearFields();
    }

    public void switchToLoginMode() {
        isRegisterMode = false;
        titleText.setText("Login System");
        loginButton.setVisible(true);
        loginButton.setManaged(true);
        registerButton.setVisible(false);
        registerButton.setManaged(false);
        toggleLink.setText("Don't have an account? Sign up here");
        clearFields();
    }

    private void navigateToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oop/Dashboard.fxml"));
            Parent dashboardRoot = loader.load();
            DashboardController dashboard = loader.getController();

            //username from login text field and pass over
            String loggedInUser = usernameField.getText().trim();
            dashboard.setSessionUser(loggedInUser);

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(dashboardRoot));
            stage.show();

        } catch (Exception e) {
            errorLabel.setTextFill(javafx.scene.paint.Color.RED);
            errorLabel.setText("Failed to load dashboard screen.");
            e.printStackTrace();
        }
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
    }
}