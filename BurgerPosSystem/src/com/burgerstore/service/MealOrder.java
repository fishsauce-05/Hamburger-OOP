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

    public Burger getBurger() { return burger; }
    public Drink getDrink() { return drink; }
    public SideItem getSide() { return side; }
    public String getNotes() { return notes; }

    public double getTotalPrice() {
        return burger.getAdjustedPrice() + drink.getAdjustedPrice() + side.getAdjustedPrice();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(burger.getName());

        if (!drink.getName().contains("No Drink")) {
            sb.append(" + ").append(drink.getName());
        }

        if (!side.getName().contains("No Side")) {
            sb.append(" + ").append(side.getName());
        }

        sb.append(String.format(" = $%.2f", getTotalPrice()));

        if (!notes.equals("None") && !notes.isEmpty()) {
            String shortNote = notes.length() > 15 ? notes.substring(0, 12) + "..." : notes;
            sb.append(" | Note: ").append(shortNote);
        }

        return sb.toString();
    }

    public String getReceiptDetails() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(String.format(">> %s $%.2f\n", burger.getName(), burger.getBasePrice()));
        for (Item t : burger.getToppings()) {
            double tPrice = (burger instanceof DeluxeBurger) ? 0.0 : t.getAdjustedPrice();
            sb.append(String.format("   + %s $%.2f\n", t.getName(), tPrice));
        }
        
        if (!drink.getName().contains("No Drink")) {
            sb.append(String.format("   + Drink: %s $%.2f\n", drink.getName() + " [" + drink.getSize() + "]", drink.getAdjustedPrice()));
        }
        
        if (!side.getName().contains("No Side")) {
            sb.append(String.format("   + Side:  %s $%.2f\n", side.getName(), side.getAdjustedPrice()));
        }
        
        if (!notes.equals("None") && !notes.isEmpty()) {
            sb.append(String.format("   * NOTE: %s\n", notes));
        }
        
        sb.append(String.format("   SUBTOTAL: %30s\n", String.format("$%.2f", getTotalPrice())));
        return sb.toString();
    }
}
