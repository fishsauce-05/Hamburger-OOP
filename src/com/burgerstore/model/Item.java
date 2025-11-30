package com.burgerstore.model;

public abstract class Item {
    private String name;
    private String type;
    private double price;
    private String size; // SMALL, MEDIUM, LARGE

    public Item(String name, String type, double price) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.size = "MEDIUM"; // Mặc định size vừa
    }

    public String getName() { return name; }
    public double getBasePrice() { return price; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    // Đa hình (Polymorphism): Tính giá thay đổi theo size
    public double getAdjustedPrice() {
        return switch (size.toUpperCase()) {
            case "SMALL" -> price - 0.5;
            case "LARGE" -> price + 1.0;
            default -> price;
        };
    }
}