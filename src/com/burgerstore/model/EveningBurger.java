package com.burgerstore.model;

import com.burgerstore.exception.TimeRestrictionException;
import java.time.LocalTime;

public class EveningBurger extends DeluxeBurger {
    public EveningBurger() {
        super("Evening Deluxe Burger", 14.00);
    }

    // Logic kiểm tra giờ: 19:00 - 21:00 (7pm - 9pm)
    public void validateTime() throws TimeRestrictionException {
        LocalTime now = LocalTime.now();
        LocalTime start = LocalTime.of(19, 0);
        LocalTime end = LocalTime.of(21, 0);

        if (now.isBefore(start) || now.isAfter(end)) {
            throw new TimeRestrictionException("Evening Burger is only served between 19:00 and 21:00. Current time: " + now.getHour() + ":" + now.getMinute());
        }
    }
}
