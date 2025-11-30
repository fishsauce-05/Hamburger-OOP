package com.burgerstore.model;

public class Drink extends Item {
    public Drink(String name, String size, double price) {
        super(name, "DRINK", price);
        setSize(size);
    }
}