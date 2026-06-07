/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop.groupproject.pos.updated;

public class Product {
    private String id;
    private String name;
    private String category;
    private int quantity;
    private double price;

    public Product(String ID, String name, String category, int quantity, double price) {
        this.id = ID;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    // Logic metric rule for contextual table representation
    public String getStatus() {
        if (quantity == 0) {
            return "Out of Stock";
        } else if (quantity < 10) {
            return "Low Stock";
        } else {
            return "Available";
        }
    }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(double price) { this.price = price; }
}