package oop.groupproject.pos.updated;

public class Product {
    private String id;
    private String name;
    private String category;
    private int quantity;
    private double price;

    // Standard base constructor
    public Product(String id, String name, String category, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    //inheritance
    public static Product create(String id, String name, String category, int quantity, double price) {
        if (category == null) {
            return new Product(id, name, category, quantity, price);
        }

        String cleanCat = category.trim().toLowerCase();
        if (cleanCat.equals("fresh") || cleanCat.equals("produce") || cleanCat.equals("meat")) {
            return new PerishableProduct(id, name, category, quantity, price);
        } else if (cleanCat.equals("household")) {
            return new DurableProduct(id, name, category, quantity, price);
        } else {
            return new Product(id, name, category, quantity, price); // Standard fallback
        }
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(double price) { this.price = price; }

    // Logic table representation
    public String getStatus() {
        if (quantity == 0) {
            return "Out of Stock";
        } else if (quantity < 10) {
            return "Low Stock";
        } else {
            return "Available";
        }
    }

    // Inherits everything from Product
    public static class PerishableProduct extends Product {

        public PerishableProduct(String id, String name, String category, int quantity, double price) {
            super(id, name, category, quantity, price);
        }

        @Override
        public String getStatus() {
            if (getQuantity() == 0) return "Expired/Out of Stock";
            return super.getStatus();
        }
    }

    // Inherits everything from Product
    public static class DurableProduct extends Product {

        public DurableProduct(String id, String name, String category, int quantity, double price) {
            super(id, name, category, quantity, price);
        }
    }
}