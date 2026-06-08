package oop.groupproject.pos.updated;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {

    @FXML private TableView<Product> managementTable;
    @FXML private TableColumn<Product, String> idColumn;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, String> categoryColumn;
    @FXML private TableColumn<Product, Integer> quantityColumn;
    @FXML private TableColumn<Product, Double> priceColumn;

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField categoryField;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;

    // 💡 Active reference link to the main dashboard instance
    private DashboardController mainDashboard;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Listen for row selections to automatically fill up the modification form textfields
        managementTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                idField.setText(newSel.getId());
                idField.setEditable(false); // ID shouldn't be edited once assigned
                nameField.setText(newSel.getName());
                categoryField.setText(newSel.getCategory());
                quantityField.setText(String.valueOf(newSel.getQuantity()));
                priceField.setText(String.valueOf(newSel.getPrice()));
            }
        });
    }

    /**
     * 💡 Injects the parent dashboard instance into this controller scene.
     * This links the pop-up table directly to the active user's actual stock.
     */
    public void setParentDashboard(DashboardController dashboard) {
        this.mainDashboard = dashboard;

        // Link this management window's table to read straight from the session's list
        if (mainDashboard != null && mainDashboard.inventoryTable != null) {
            managementTable.setItems(mainDashboard.inventoryTable.getItems());
        }
    }

    @FXML
    private void handleAdd() {
        if (mainDashboard == null) return;

        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String category = categoryField.getText().trim();

            if (id.isEmpty() || name.isEmpty() || category.isEmpty()) {
                showMsg("Verification Error", "All parameter fields must be filled.", Alert.AlertType.ERROR);
                return;
            }

            // 🔍 Scan the specific user's inventory list to prevent ID conflicts
            for (Product p : managementTable.getItems()) {
                if (p.getId().equalsIgnoreCase(id)) {
                    showMsg("ID Conflict", "Product with this ID already exists.", Alert.AlertType.ERROR);
                    return;
                }
            }

            int qty = Integer.parseInt(quantityField.getText().trim());
            double prc = Double.parseDouble(priceField.getText().trim());

            if (qty < 0 || prc <= 0) {
                showMsg("Validation Range Error", "Quantity must be >= 0 and Price must be > 0.", Alert.AlertType.ERROR);
                return;
            }

            mainDashboard.sampleProduct(id, name, category, qty, prc);

            // Permanently save
            mainDashboard.saveInventoryToFile();
            mainDashboard.updDb(); // Refresh UI metrics

            clearFields();
            showMsg("Success", "New item safely added.", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            showMsg("Input Format Exception", "Check numerical input formats.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleEdit() {
        if (mainDashboard == null) return;

        Product target = managementTable.getSelectionModel().getSelectedItem();
        if (target == null) {
            showMsg("Selection Missing", "Please select a target table row to apply updates.", Alert.AlertType.WARNING);
            return;
        }

        try {
            String name = nameField.getText().trim();
            String cat = categoryField.getText().trim();
            int qty = Integer.parseInt(quantityField.getText().trim());
            double prc = Double.parseDouble(priceField.getText().trim());

            if (name.isEmpty() || cat.isEmpty() || qty < 0 || prc <= 0) {
                showMsg("Validation Range Error", "Ensure fields are valid (Qty >= 0, Price > 0).", Alert.AlertType.ERROR);
                return;
            }

            // Apply updates straight down onto the selected object referencer
            target.setName(name);
            target.setCategory(cat);
            target.setQuantity(qty);
            target.setPrice(prc);

            managementTable.refresh();
            if (mainDashboard.inventoryTable != null) {
                mainDashboard.inventoryTable.refresh(); // Sync the background dashboard view instantly
            }

            // 💾 Save modifications right away to file
            mainDashboard.saveInventoryToFile();
            mainDashboard.updDb(); // Re-calculate total products and RM value

            clearFields();
            showMsg("Updated", "Product record updated.", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            showMsg("Input Format Exception", "Numerical formatting calculation error.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleDelete() {
        if (mainDashboard == null) return;

        Product target = managementTable.getSelectionModel().getSelectedItem();
        if (target == null) {
            showMsg("Selection Missing", "Please pick an item row first.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Confirm deleting product?", ButtonType.YES, ButtonType.NO);
        if (confirm.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            // Remove item row directly out of the shared UI observation pipeline
            managementTable.getItems().remove(target);

            // 💾 Update files and top dashboard statistics card widgets
            mainDashboard.saveInventoryToFile();
            mainDashboard.updDb();

            clearFields();
            managementTable.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void returnToDashboard() {
        // Since this window pops up as a secondary window (or inside a stage),
        // we can simply close it to return cleanly back to our primary dashboard view context.
        Stage stage = (Stage) managementTable.getScene().getWindow();
        stage.close();
    }

    private void clearFields() {
        idField.clear();
        idField.setEditable(true);
        nameField.clear();
        categoryField.clear();
        quantityField.clear();
        priceField.clear();
    }

    private void showMsg(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}