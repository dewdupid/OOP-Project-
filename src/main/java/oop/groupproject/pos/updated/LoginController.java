package oop.groupproject.pos.updated;

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

    // A simple in-memory user storage to save registered accounts while running
    private static final Map<String, String> userDatabase = new HashMap<>();
    private boolean isRegisterMode = false;

    @FXML
    public void initialize() {
        // Pre-populate our mock database with your default admin account
        if (userDatabase.isEmpty()) {
            userDatabase.put("admin", "1234");
        }
        // hide the main register process button action layout
        registerButton.setVisible(false);
        registerButton.setManaged(false);
    }

    @FXML
    public void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setTextFill(javafx.scene.paint.Color.RED);
            errorLabel.setText("Fields cannot be empty!");
            return;
        }

        // Validate credentials against our HashMap database
        if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
            navigateToDashboard();
        } else {
            errorLabel.setTextFill(javafx.scene.paint.Color.RED);
            errorLabel.setText("Invalid username or password!");
        }
    }

    @FXML
    public void handleRegister() {
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

        // Save the credentials to our temporary memory map
        userDatabase.put(username, password);

        errorLabel.setTextFill(javafx.scene.paint.Color.GREEN);
        errorLabel.setText("Account registered successfully! Please login.");

        // Revert UI view state back to login mode automatically
        switchToLoginMode();
    }

    @FXML
    public void handleToggleMode() {
        if (!isRegisterMode) {
            switchToRegisterMode();
        } else {
            switchToLoginMode();
        }
    }

    private void switchToRegisterMode() {
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

    private void switchToLoginMode() {
        isRegisterMode = false;
        titleText.setText("Login System");
        loginButton.setVisible(true);
        loginButton.setManaged(true);
        registerButton.setVisible(false);
        registerButton.setManaged(false);
        toggleLink.setText("Don't have an account? Sign up here");
        clearFields();
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
    }

    private void navigateToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oop/groupproject/pos/updated/FXMLDocument.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Inventory Management System - Dashboard");

            stage.setWidth(1100);
            stage.setHeight(750);
            stage.centerOnScreen();

            stage.show();
        } catch (Exception e) {
            errorLabel.setTextFill(javafx.scene.paint.Color.RED);
            errorLabel.setText("Failed to load dashboard screen.");
            e.printStackTrace();
        }
    }
}