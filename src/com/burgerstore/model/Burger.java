package com.burgerstore.model;

import com.burgerstore.exception.ToppingLimitException;
import java.util.ArrayList;
import java.util.List;

public class Burger extends Item {
    protected List<Item> toppings;
    protected int maxToppings;

    public Burger(String name, double price) {
        super(name, "BURGER", price);
        this.toppings = new ArrayList<>();
        this.maxToppings = 3; // Burger thường tối đa 3 topping
    }

    public void addTopping(Item topping) throws ToppingLimitException {
        if (toppings.size() < maxToppings) {
            toppings.add(topping);
        } else {
            throw new ToppingLimitException("Loại burger này chỉ cho phép tối đa " + maxToppings + " topping!");
        }
    }

    public List<Item> getToppings() {
        return toppings;
    }

    @Override
    public double getAdjustedPrice() {
        double total = super.getAdjustedPrice();
        for (Item topping : toppings) {
            total += topping.getAdjustedPrice();
        }
        return total;
    }
}