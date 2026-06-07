/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management;

import model.InventoryManager;

public class ManagementController1 {

    public ManagementController1(InventoryManager manager, Managementview1 view) {

        // CONNECT ADD BUTTON
        view.btnAdd.setOnAction(e -> {
            String name = view.txtName.getText();
            int qty = Integer.parseInt(view.txtQty.getText());
            double price = Double.parseDouble(view.txtPrice.getText());

            manager.addProduct(name, qty, price);
        });

        // CONNECT DELETE BUTTON
        view.btnDelete.setOnAction(e -> {
            manager.deleteProduct();
        });
    }
}
