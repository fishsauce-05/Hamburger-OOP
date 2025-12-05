package com.parking.util;

import java.util.UUID;

public class IdGenerator {
    public static String next() {
        return UUID.randomUUID().toString();
    }
}
