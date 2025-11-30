package com.burgerstore.model;

public class DeluxeBurger extends Burger {

    // Constructor linh hoạt hơn phiên bản cũ
    public DeluxeBurger(String name, double price) {
        super(name, price);
        this.maxToppings = 5; // Deluxe cho phép 5 topping
    }

    @Override
    public double getAdjustedPrice() {
        // Deluxe Burger không tính thêm tiền topping
        return getBasePrice();
    }
}