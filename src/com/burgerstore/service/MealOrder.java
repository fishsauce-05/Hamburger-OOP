package com.burgerstore.service;

import com.burgerstore.model.*;

public class MealOrder {
    private Burger burger;
    private Drink drink;
    private SideItem side;
    private String notes;

    public MealOrder(Burger burger, Drink drink, SideItem side, String notes) {
        this.burger = burger; 
        this.drink = drink; 
        this.side = side;
        this.notes = (notes == null || notes.trim().isEmpty()) ? "None" : notes.trim();
    }

    public MealOrder() {
        this(new Burger("Classic Beef (Default)", 5.00), 
             new Drink("Coke", "SMALL", 1.50), 
             new SideItem("Fries", 2.50), 
             "Default Meal");
    }

    public Burger getBurger() { return burger; }
    public Drink getDrink() { return drink; }
    public SideItem getSide() { return side; }
    public String getNotes() { return notes; }

    public double getTotalPrice() {
        return burger.getAdjustedPrice() + drink.getAdjustedPrice() + side.getAdjustedPrice();
    }

    // --- LOGIC HIỂN THỊ TRÊN DANH SÁCH (JLIST) ---
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(burger.getName()); // Burger luôn hiện

        // Chỉ hiện Drink nếu không phải là "No Drink"
        if (!drink.getName().contains("No Drink")) {
            sb.append(" + ").append(drink.getName());
        }

        // Chỉ hiện Side nếu không phải là "No Side"
        if (!side.getName().contains("No Side")) {
            sb.append(" + ").append(side.getName());
        }

        sb.append(String.format(" = $%.2f", getTotalPrice()));

        // Hiển thị note ngắn gọn nếu có
        if (!notes.equals("None") && !notes.isEmpty()) {
            String shortNote = notes.length() > 15 ? notes.substring(0, 12) + "..." : notes;
            sb.append(" | Note: ").append(shortNote);
        }

        return sb.toString();
    }

    // --- LOGIC IN HÓA ĐƠN CHI TIẾT ---
    public String getReceiptDetails() {
        StringBuilder sb = new StringBuilder();
        
        // 1. In Burger (Luôn có)
        sb.append(String.format(">> %-30s $%.2f\n", burger.getName(), burger.getBasePrice()));
        for (Item t : burger.getToppings()) {
            double tPrice = (burger instanceof DeluxeBurger) ? 0.0 : t.getAdjustedPrice();
            sb.append(String.format("   + %-28s $%.2f\n", t.getName(), tPrice));
        }
        
        // 2. In Drink (Nếu có gọi)
        if (!drink.getName().contains("No Drink")) {
            sb.append(String.format("   + Drink: %-21s $%.2f\n", drink.getName() + " [" + drink.getSize() + "]", drink.getAdjustedPrice()));
        }
        
        // 3. In Side (Nếu có gọi)
        if (!side.getName().contains("No Side")) {
            sb.append(String.format("   + Side:  %-21s $%.2f\n", side.getName(), side.getAdjustedPrice()));
        }
        
        // 4. In Note
        if (!notes.equals("None") && !notes.isEmpty()) {
            sb.append(String.format("   * NOTE: %s\n", notes));
        }
        
        sb.append(String.format("   SUBTOTAL: %30s\n", String.format("$%.2f", getTotalPrice())));
        return sb.toString();
    }
}
