package oop.groupproject.pos.updated;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SampleData {
    private static final ObservableList<Product> productList = FXCollections.observableArrayList();

    static {
        productList.add(new Product("ID554", "Milk", "Fresh", 25, 16.50));
        productList.add(new Product("ID590", "Bread", "Fresh", 20, 8.90));
        productList.add(new Product("ID264", "Apples", "Produce", 6, 7.00));
        productList.add(new Product("ID777", "Dish Soap", "Household", 41, 6.00));
        productList.add(new Product("ID342", "Chicken Breast", "Meat", 7, 15.50));
        productList.add(new Product("ID767", "Tissues", "Household", 72, 8.90));
        productList.add(new Product("ID014", "Rice", "Package", 8, 32.90));
        productList.add(new Product("ID238", "Bananas", "Produce", 15, 5.50));
    }

    public static ObservableList<Product> getProductList() {
        return productList;
    }
}

