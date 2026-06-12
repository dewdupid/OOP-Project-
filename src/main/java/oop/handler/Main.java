/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package oop.handler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Change this line to load the Login page instead of the Dashboard
        Parent root = FXMLLoader.load(getClass().getResource("/oop/Login.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System - Login");
        stage.setScene(scene);
        stage.setMinWidth(400); // Smaller initial dimension for a login panel
        stage.setMinHeight(400);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
