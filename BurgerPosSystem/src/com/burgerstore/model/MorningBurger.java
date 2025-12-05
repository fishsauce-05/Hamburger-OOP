package com.burgerstore.model;

import com.burgerstore.exception.TimeRestrictionException;
import java.time.LocalTime;

public class MorningBurger extends DeluxeBurger {
    public MorningBurger() {
        super("Morning Deluxe Burger", 12.00);
    }

    // Logic kiểm tra giờ: 7:00 - 10:00
    public void validateTime() throws TimeRestrictionException {
        LocalTime now = LocalTime.now();
        LocalTime start = LocalTime.of(7, 0);
        LocalTime end = LocalTime.of(10, 0);

        if (now.isBefore(start) || now.isAfter(end)) {
            throw new TimeRestrictionException("Morning Burger is only served between 07:00 and 10:00. Current time: " + now.getHour() + ":" + now.getMinute());
        }
    }
}
