/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management;

import model.InventoryManager;

public class ManagementController1 {

    public ManagementController1(InventoryManager manager, Managementview1 view) {

        // ADD BUTTON
        view.btnAdd.setOnAction(e -> {
            try {
                String name = view.txtName.getText();
                int qty = Integer.parseInt(view.txtQty.getText());
                double price = Double.parseDouble(view.txtPrice.getText());

                // ADD TO MODEL
                manager.addProduct(name, qty, price);

                // UPDATE TABLE VIEW
                view.data.add(name + " | " + qty + " | " + price);

                // CLEAR INPUT
                view.txtName.clear();
                view.txtQty.clear();
                view.txtPrice.clear();

            } catch (Exception ex) {
                System.out.println("Invalid input!");
            }
        });

        // DELETE BUTTON
        view.btnDelete.setOnAction(e -> {
            String selected = view.table.getSelectionModel().getSelectedItem();

            if (selected != null) {
                view.data.remove(selected);
                manager.deleteProduct();
            }
        });

        // SAVE FILE
        view.btnSave.setOnAction(e -> {
            view.saveToFile();
        });

        // LOAD FILE
        view.btnLoad.setOnAction(e -> {
            view.loadFromFile();
        });
    }
}
