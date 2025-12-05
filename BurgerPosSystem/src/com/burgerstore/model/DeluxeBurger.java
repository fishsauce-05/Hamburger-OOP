package com.burgerstore.model;

public class DeluxeBurger extends Burger {

    public DeluxeBurger(String name, double price) {
        super(name, price);
        this.maxToppings = 5; // Deluxe cho phép 5 topping
    }

    @Override
    public double getAdjustedPrice() {
        return getBasePrice();
    }
}