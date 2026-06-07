//Structure
package oop.groupproject.pos.updated;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

//for UI elemnts
public class DashboardController implements Initializable {

    @FXML private Label totalProductsLabel;
    @FXML private Label lowStockLabel;
    @FXML private Label totalValueLabel;

    @FXML private TableView<Product> inventoryTable;
    @FXML private TableColumn<Product, String> idColumn;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, String> categoryColumn;
    @FXML private TableColumn<Product, Integer> quantityColumn;
    @FXML private TableColumn<Product, Double> priceColumn;
    @FXML private TableColumn<Product, String> statusColumn;

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField categoryField;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;
    @FXML private TextField searchField;

    private ObservableList<Product> productList;

//1:Initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        productList = FXCollections.observableArrayList();
        sampleData();
        
        inventoryTable.setItems(productList);
        updDb();   
    }

    @FXML
    private void search() {
        filterProducts(searchField.getText());
    }

    //2.sample
    private void sampleData() {
        sampleProduct("ID554", "Milk", "Fresh", 25, 16.50);
        sampleProduct("ID590", "Bread", "Fresh", 20, 8.90);
        sampleProduct("ID264", "Apples", "Produce", 6, 7.00);
        sampleProduct("ID777", "Dish Soap", "Household", 41, 6.00);
        sampleProduct("ID342", "Chicken Breast", "Meat", 7, 15.50);
        sampleProduct("ID767", "Tissues", "Household", 72, 8.90);
        sampleProduct("ID014", "Rice", "Package", 8, 32.90);
        sampleProduct("ID238", "Bananas", "Produce", 15, 5.50);
    }

    private void sampleProduct(String ID, String name, String category, int quantity, double price) {
        Product product = new Product(ID, name, category, quantity, price);
        productList.add(product);
    }

    
    //3.product features
    @FXML
    private void addProduct() {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String category = categoryField.getText().trim();
            String quantityText = quantityField.getText().trim();
            String priceText = priceField.getText().trim();

            if (id.isEmpty() || name.isEmpty() || category.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) {
                showAlert("Error", "Please fill in all fields.", Alert.AlertType.ERROR);
                return;
            }

            int quantity = Integer.parseInt(quantityText);
            double price = Double.parseDouble(priceText);

            if (quantity < 0 || price < 0) {
                showAlert("Error", "Quantity and price must be at least 1!!!", Alert.AlertType.ERROR);
                return;
            }

            sampleProduct(id, name, category, quantity, price);
            updDb();
            clearAll();

            showAlert("Success", "Product added successfully with ID: " + id, Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter valid character or number.", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void delProduct() {
        Product selected = inventoryTable.getSelectionModel().getSelectedItem();
        
        if (selected == null) {
            showAlert("Error", "Please select a product from the table to delete.", Alert.AlertType.ERROR);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete " + selected.getName() + "?");

        if (confirm.showAndWait().get() == ButtonType.OK) {
            productList.remove(selected);
            updDb();
        }
    }

    @FXML
    private void refresh() {
        searchField.clear();
        inventoryTable.setItems(productList);
        updDb();
    }

    //4. filter
    private void filterProducts(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            inventoryTable.setItems(productList);
            return;
        }

        ObservableList<Product> filteredList = FXCollections.observableArrayList();
        String lowerCaseSearch = searchText.toLowerCase();

        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(lowerCaseSearch) ||
                product.getCategory().toLowerCase().contains(lowerCaseSearch)) {
                filteredList.add(product);
            }
        }

        inventoryTable.setItems(filteredList);
    }

    //5. dashboard
    private void updDb() {
        int totalProducts = productList.size();
        int lowStock = 0;
        double totalValue = 0;

        for (Product product : productList) {
            if (product.getQuantity() < 10) {
                lowStock++;
            }

            totalValue += product.getQuantity() * product.getPrice();
        }

        totalProductsLabel.setText(String.valueOf(totalProducts));
        lowStockLabel.setText(String.valueOf(lowStock));
        totalValueLabel.setText(String.format("RM%.2f", totalValue));
    }

    private void clearAll() {
        idField.clear();
        nameField.clear();
        categoryField.clear();
        quantityField.clear();
        priceField.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}