/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Managementview1 {

    // FORM
    public TextField txtName = new TextField();
    public TextField txtQty = new TextField();
    public TextField txtPrice = new TextField();

    // BUTTONS
    public Button btnAdd = new Button("Add Product");
    public Button btnDelete = new Button("Delete Product");
    public Button btnSave = new Button("Save File");
    public Button btnLoad = new Button("Load File");

    // TABLE
    public TableView<String> table = new TableView<>();
    public ObservableList<String> data = FXCollections.observableArrayList();

    private Scene scene;

    public Managementview1() {

        txtName.setPromptText("Product Name");
        txtQty.setPromptText("Quantity");
        txtPrice.setPromptText("Price");

        table.setItems(data);

        // ADD BUTTON ACTION
        btnAdd.setOnAction(e -> addProduct());

        // DELETE BUTTON ACTION
        btnDelete.setOnAction(e -> deleteProduct());

        // SAVE FILE
        btnSave.setOnAction(e -> saveToFile());

        // LOAD FILE
        btnLoad.setOnAction(e -> loadFromFile());

        VBox root = new VBox(10,
                txtName,
                txtQty,
                txtPrice,
                btnAdd,
                btnDelete,
                btnSave,
                btnLoad,
                table
        );

        scene = new Scene(root, 400, 500);
    }

    // ADD PRODUCT
    private void addProduct() {
        String item = txtName.getText() + " | " +
                      txtQty.getText() + " | " +
                      txtPrice.getText();

        data.add(item);

        txtName.clear();
        txtQty.clear();
        txtPrice.clear();
    }

    // DELETE PRODUCT
    private void deleteProduct() {
        String selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            data.remove(selected);
        }
    }

    // SAVE FILE (IO)
    private void saveToFile() {
        try {
            FileWriter fw = new FileWriter("product.txt");

            for (String item : data) {
                fw.write(item + "\n");
            }

            fw.close();
            System.out.println("Saved successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // LOAD FILE (IO)
    private void loadFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("product.txt"));

            String line;
            data.clear();

            while ((line = br.readLine()) != null) {
                data.add(line);
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Scene getScene() {
        return scene;
    }
}
    
