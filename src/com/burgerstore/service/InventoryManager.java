package com.burgerstore.service;

import com.burgerstore.exception.OutOfStockException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private static InventoryManager instance;
    private Map<String, Integer> stock;
    private final String FILE_NAME = "inventory.txt";

    private InventoryManager() {
        stock = new HashMap<>();
        loadInventory();
    }

    public static InventoryManager getInstance() {
        if (instance == null) instance = new InventoryManager();
        return instance;
    }

    private void loadInventory() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    stock.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("New inventory created.");
        }
    }

    public void saveInventory() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, Integer> entry : stock.entrySet()) {
                bw.write(entry.getKey() + ":" + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkStock(String itemName) throws OutOfStockException {
        String cleanName = itemName.split("\\(")[0].trim();
        if (!stock.containsKey(cleanName) || stock.get(cleanName) <= 0) {
            throw new OutOfStockException("Out of stock: " + cleanName);
        }
    }

    public void reduceStock(String itemName) {
        String cleanName = itemName.split("\\(")[0].trim();
        stock.put(cleanName, stock.getOrDefault(cleanName, 0) - 1);
    }

    // Mới: Hoàn trả lại kho (Dùng khi Remove món khỏi giỏ)
    public void restoreStock(String itemName) {
        String cleanName = itemName.split("\\(")[0].trim();
        stock.put(cleanName, stock.getOrDefault(cleanName, 0) + 1);
    }
}
