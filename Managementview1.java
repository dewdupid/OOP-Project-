/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class Managementview1 {

    // FORM
    public TextField txtName = new TextField();
    public TextField txtQty = new TextField();
    public TextField txtPrice = new TextField();

    // BUTTONS
    public Button btnAdd = new Button("Add Product");
    public Button btnDelete = new Button("Delete Product");

    // TABLE
    public TableView<String> table = new TableView<>();

    private Scene scene;

    public Managementview1() {

        txtName.setPromptText("Product Name");
        txtQty.setPromptText("Quantity");
        txtPrice.setPromptText("Price");

        VBox root = new VBox(10,
                txtName,
                txtQty,
                txtPrice,
                btnAdd,
                btnDelete,
                table
        );

        scene = new Scene(root, 400, 450);
    }

    public Scene getScene() {
        return scene;
    }
}