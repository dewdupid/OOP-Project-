package oop.groupproject.pos.updated; // Structure

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.HashMap;
import java.util.Map;
import javafx.stage.Stage;

// for UI elements
public class DashboardController implements Initializable {

    @FXML private Label totalProductsLabel;
    @FXML private Label lowStockLabel;
    @FXML private Label totalValueLabel;

    // 📊 Sidebar statistic label injections
    @FXML private Label avgPriceLabel;
    @FXML private Label highestPriceLabel;
    @FXML private Label outOfStockLabel;

    // 📈 Bar chart layout elements
    @FXML private BarChart<String, Number> categoryBarChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;

    @FXML public TableView<Product> inventoryTable;
    @FXML private TableColumn<Product, String> idColumn;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, String> categoryColumn;
    @FXML private TableColumn<Product, Integer> quantityColumn;
    @FXML private TableColumn<Product, Double> priceColumn;
    @FXML private TableColumn<Product, String> statusColumn;

    @FXML private TextField searchField;

    private ObservableList<Product> productList; // store all product
    private String currentUserSession; // Track active user context name
    private String currentFilePath;

    // Initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        productList = FXCollections.observableArrayList(); // creates product list
        inventoryTable.setItems(productList); // shows product in table
    }

    /**
     * Receives the username from the Login screen and initializes the session inventory.
     */
    public void setSessionUser(String username) {
        // Clean name to make a safe filename layout (e.g., "dhia_inventory.txt")
        this.currentUserSession = username.toLowerCase().replaceAll("\\s+", "");
        this.currentFilePath = currentUserSession + "_inventory.txt";

        productList.clear(); // clear product from before
        loadInventoryFromFile(); // Search if this specific file is already there!
        updDb();
    }

    // ========================================================================
    // FILE I/O OPERATIONS: Individual File
    // ========================================================================

    /**
     * Reads from the user's personal text file.
     * If the file is already there, it loads it. If not, it creates it.
     */
    public void loadInventoryFromFile() {
        if (currentFilePath == null) return;
        File file = new File(currentFilePath);

        if (!file.exists()) { // Search if the user file exists
            System.out.println("No existing file found for this user. Seeding personal file...");
            createSampleFile(); // Seeds default items and auto-saves this user's file
            return;
        }

        System.out.println("User file found! Synchronizing personal inventory..."); // If it is already there, load it directly
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) { // reading file
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] linePart = line.split(",");
                if (linePart.length == 5) {
                    String id = linePart[0].trim();
                    String name = linePart[1].trim();
                    String category = linePart[2].trim();
                    int quantity = Integer.parseInt(linePart[3].trim());
                    double price = Double.parseDouble(linePart[4].trim());
                    sampleProduct(id, name, category, quantity, price);
                }
            }
        } catch (IOException | NumberFormatException e) {
            showAlert("Storage Error", "Could not parse personal database content safely.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Overwrites this user's individual file with their active table content.
     */
    public void saveInventoryToFile() {
        if (currentFilePath == null) return;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFilePath))) {
            for (Product p : productList) {
                String line = String.format("%s,%s,%s,%d,%.2f",
                        p.getId(),
                        p.getName(),
                        p.getCategory(),
                        p.getQuantity(),
                        p.getPrice()
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            showAlert("Save Error", "Could not write updates to personal storage.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Seeds baseline data for a user who does not have an inventory yet.
     */
    private void createSampleFile() {
        sampleProduct("ID554", "Milk", "Fresh", 25, 16.50);
        sampleProduct("ID590", "Bread", "Fresh", 20, 8.90);
        sampleProduct("ID264", "Apples", "Produce", 6, 7.00);
        sampleProduct("ID777", "Dish Soap", "Household", 41, 6.00);
        sampleProduct("ID342", "Chicken Breast", "Meat", 7, 15.50);
        sampleProduct("ID767", "Tissues", "Household", 72, 8.90);
        sampleProduct("ID014", "Rice", "Package", 8, 32.90);
        sampleProduct("ID238", "Bananas", "Produce", 15, 5.50);

        // Save immediately down to personal tracking file
        saveInventoryToFile();
    }

    // ========================================================================
    // END OF FILE I/O
    // ========================================================================

    @FXML
    private void search() {
        filterProducts(searchField.getText());
    }

    @FXML
    private void handleBackToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oop/groupproject/pos/updated/Login.fxml"));
            javafx.scene.Parent root = loader.load();

            Stage stage = (Stage) searchField.getScene().getWindow();

            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Inventory Management System - Login");

            stage.setWidth(865);
            stage.setHeight(540);
            stage.centerOnScreen();

            stage.show();
        } catch (Exception e) {
            showAlert("Error", "Failed to navigate back to the login screen.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void sampleProduct(String ID, String name, String category, int quantity, double price) {
        Product product = new Product(ID, name, category, quantity, price);
        productList.add(product);
    }

    @FXML
    public void refresh() {
        searchField.clear();
        productList.clear();
        loadInventoryFromFile();
        updDb();
    }

    @FXML
    public void deleteProduct() {
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

            // Sync deletion down to this user's personal file
            saveInventoryToFile();

            updDb();
        }
    }

    // Filter feature
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

    // Dashboard Calculations
    @FXML
    public void updDb() {
        int totalProducts = productList.size();
        int lowStock = 0;
        int outOfStock = 0;
        double totalValue = 0;

        double combinedPriceSum = 0;
        double highestPriceSeen = 0;
        String highestPriceItemName = "-";

        Map<String, Integer> categoryMap = new HashMap<>();

        for (Product product : productList) {
            // Track low stock and complete zero inventory
            if (product.getQuantity() == 0) {
                outOfStock++;
            } else if (product.getQuantity() < 10) {
                lowStock++;
            }

            // Accumulate running financial balances
            totalValue += product.getQuantity() * product.getPrice();
            combinedPriceSum += product.getPrice();

            // Scan for peak inventory pricing targets
            if (product.getPrice() > highestPriceSeen) {
                highestPriceSeen = product.getPrice();
                highestPriceItemName = product.getName();
            }

            // Accumulate stock volume quantities by grouping categories together
            String cat = product.getCategory();
            categoryMap.put(cat, categoryMap.getOrDefault(cat, 0) + product.getQuantity());
        }

        // Calculate statistical averages safely
        double averageUnitPrice = (totalProducts > 0) ? (combinedPriceSum / totalProducts) : 0.0;

        // 1. Update the original dashboard cards
        totalProductsLabel.setText(String.valueOf(totalProducts));
        lowStockLabel.setText(String.valueOf(lowStock));
        totalValueLabel.setText(String.format("RM%.2f", totalValue));

        // 2. Update your new sidebar insight values
        if (avgPriceLabel != null) avgPriceLabel.setText(String.format("RM%.2f", averageUnitPrice));
        if (outOfStockLabel != null) outOfStockLabel.setText(String.valueOf(outOfStock));

        if (highestPriceLabel != null) {
            if (totalProducts > 0) {
                highestPriceLabel.setText(String.format("%s (RM%.2f)", highestPriceItemName, highestPriceSeen));
            } else {
                highestPriceLabel.setText("-");
            }
        }

        // 3. Clear and update graph visual series
        if (categoryBarChart != null) {
            // 💡 CRITICAL FIX: Clear old categories from the axis completely
            // so it forces JavaFX to redraw new categories immediately!
            if (xAxis != null) {
                xAxis.getCategories().clear();
            }

            categoryBarChart.getData().clear();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Product Stock Level");

            for (Map.Entry<String, Integer> entry : categoryMap.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }

            if (yAxis != null) {
                yAxis.setTickUnit(1.0);
                yAxis.setMinorTickVisible(false);
            }

            categoryBarChart.getData().add(series);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void openManagementWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oop/groupproject/pos/updated/Transaction.fxml"));
            javafx.scene.Parent root = loader.load();

            // Get the controller instance for the pop-up transaction scene
            TransactionController controller = loader.getController();

            // This hooks both controller pipelines together
            controller.setParentDashboard(this);

            Stage popUpStage = new Stage();
            popUpStage.setTitle("Product Management System");
            popUpStage.setScene(new javafx.scene.Scene(root));
            popUpStage.initOwner(searchField.getScene().getWindow());
            popUpStage.show();

        } catch (IOException e) {
            showAlert("Window Error", "Could not initialize management transaction module layout view.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
}