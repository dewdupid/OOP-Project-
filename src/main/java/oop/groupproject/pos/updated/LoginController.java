package oop.groupproject.pos.updated;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Hardcoded check for demo purposes (Replace with database validation later)
        if (username.equals("admin") && password.equals("1234")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/oop/groupproject/pos/updated/FXMLDocument.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) usernameField.getScene().getWindow();

                // Set the dashboard scene
                stage.setScene(new Scene(root));
                stage.setTitle("Inventory Management System - Dashboard");

                // Automatically maximize or expand the window for the dashboard layout
                stage.setWidth(1100);  // Make the window wider for inventory tables
                stage.setHeight(750);  // Make it taller
                stage.centerOnScreen(); // Center it cleanly on their monitor

                stage.show();

            } catch (Exception e) {
                errorLabel.setText("Failed to load dashboard screen.");
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Invalid username or password!");
        }
    }
}
