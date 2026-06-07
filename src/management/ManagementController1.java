/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package management;

public class ManagementController1 {

    public ManagementController1(InventoryManager manager, Managementview1 view) {

        // ADD
        view.btnAdd.setOnAction(e -> {
            try {
                String name = view.txtName.getText();
                int qty = Integer.parseInt(view.txtQty.getText());
                double price = Double.parseDouble(view.txtPrice.getText());

                manager.addProduct(name, qty, price);

                view.data.add(name + " | " + qty + " | " + price);

                view.txtName.clear();
                view.txtQty.clear();
                view.txtPrice.clear();

            } catch (Exception ex) {
                System.out.println("Invalid input!");
            }
        });

        // DELETE
        view.btnDelete.setOnAction(e -> {
            String selected = view.table.getSelectionModel().getSelectedItem();

            if (selected != null) {
                view.data.remove(selected);
                manager.deleteProduct(selected);
            }
        });

        // SAVE
        view.btnSave.setOnAction(e -> {
            manager.saveToFile();
        });

        // LOAD
        view.btnLoad.setOnAction(e -> {
            view.data.clear();
            view.data.addAll(manager.loadFromFile());
        });
    }
}

