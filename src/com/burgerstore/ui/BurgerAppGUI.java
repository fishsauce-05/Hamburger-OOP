package com.burgerstore.ui;

import com.burgerstore.model.*;
import com.burgerstore.service.MealOrder;
import com.burgerstore.service.InventoryManager;
import com.burgerstore.exception.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BurgerAppGUI extends JFrame {

    // Data Models
    private DefaultListModel<MealOrder> cartModel;
    private JList<MealOrder> listCart;
    private InventoryManager inventory;
    
    // Data Maps
    private Map<String, String> burgerDescriptions;
    private Map<String, String> burgerPrepTimes; 
    private Map<String, String> burgerCalories;
    private Map<String, String> burgerSpiciness;
    private Map<String, String> burgerAllergens;
    private Map<String, String> burgerChefNotes;
    
    // UI Components
    private JComboBox<String> cmbBurger, cmbDrink, cmbDrinkSize, cmbSide;
    private JTextArea txtDescription, txtNotes;
    private JSpinner spnQuantity;
    private JLabel lblPrepTime, lblCalories, lblSpiciness; 
    private JLabel lblAllergens, lblChefNote;
    private List<JCheckBox> toppingCheckboxes;
    private JLabel lblTotal;
    
    // Options
    private final String[] BURGER_OPTIONS = {
        "Classic Beef Burger ($5.0)", "Spicy Chicken Burger ($5.5)", "BBQ Bacon Burger ($6.5)", 
        "Fish Filet Burger ($6.0)", "Veggie Burger ($5.0)",
        "Morning Deluxe Burger ($12.0)", "Evening Deluxe Burger ($14.0)",
        "Deluxe Ultimate Stack ($12.0)", "Deluxe Chicken Royale ($11.0)", "Deluxe Truffle Mushroom ($13.5)"
    };
    
    private final String[] DRINK_SIZES = {"SMALL (-$0.50)", "MEDIUM (Base)", "LARGE (+$1.00)"};
    
    private final String[] DRINK_OPTIONS = {
        "No Drink ($0.0)", 
        "Coca Cola ($1.5)", "Pepsi ($1.5)", "Sprite ($1.5)", 
        "Fanta Orange ($1.5)", "Mountain Dew ($1.5)", 
        "Iced Coffee ($2.0)", "Peach Tea ($2.0)", 
        "Strawberry Milkshake ($3.0)", "Chocolate Milkshake ($3.0)"
    };
    
    private final String[] SIDE_OPTIONS = {
        "No Side Item ($0.0)", 
        "French Fries ($2.5)", "Onion Rings ($3.0)", "Chicken Nuggets ($4.0)", 
        "Coleslaw Salad ($2.0)", "Mozzarella Sticks ($4.5)", "Apple Pie ($2.5)"
    };

    public BurgerAppGUI() {
        inventory = InventoryManager.getInstance();
        cartModel = new DefaultListModel<>();
        initAllData(); 

        setTitle("Burger Store - POS System");
        setSize(1150, 800); // Kích thước cửa sổ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // --- HEADER ---
        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(44, 62, 80));
        JLabel lblTitle = new JLabel("BURGER STORE POS SYSTEM", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Verdana", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBorder(new EmptyBorder(15,0,15,0));
        pnlHeader.add(lblTitle);
        add(pnlHeader, BorderLayout.NORTH);

        // --- LEFT PANEL (INPUT) ---
        JPanel pnlLeft = new JPanel();
        pnlLeft.setLayout(new BoxLayout(pnlLeft, BoxLayout.Y_AXIS));
        pnlLeft.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        // SECTION 1: BURGER
        pnlLeft.add(createLabel("1. Select Burger"));
        cmbBurger = new JComboBox<>(BURGER_OPTIONS);
        cmbBurger.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cmbBurger.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        cmbBurger.setAlignmentX(Component.LEFT_ALIGNMENT);
        cmbBurger.addActionListener(e -> { updateDescription(); validateToppingsOnChange(); });
        pnlLeft.add(cmbBurger);
        
        txtDescription = new JTextArea(4, 20); 
        txtDescription.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtDescription.setForeground(new Color(80, 80, 80));
        txtDescription.setEditable(false);
        txtDescription.setOpaque(false);
        txtDescription.setBorder(new EmptyBorder(5, 5, 5, 5));
        txtDescription.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlLeft.add(txtDescription);

        // --- PART A: TEXT INFO (Allergens & Chef Note) ---
        JPanel pnlExtraInfo = new JPanel(new GridLayout(2, 1, 0, 5));
        pnlExtraInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlExtraInfo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        pnlExtraInfo.setBackground(new Color(245, 245, 245)); 
        pnlExtraInfo.setBorder(new EmptyBorder(5, 10, 5, 5));

        lblAllergens = new JLabel("⚠️ Contains: Wheat, Dairy");
        lblAllergens.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblAllergens.setForeground(new Color(100, 100, 100));
        pnlExtraInfo.add(lblAllergens);

        lblChefNote = new JLabel("⭐ Chef says: Best paired with Coke!");
        lblChefNote.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblChefNote.setForeground(new Color(0, 102, 204));
        pnlExtraInfo.add(lblChefNote);

        pnlLeft.add(pnlExtraInfo);
        
        // --- PART B: FUNCTIONAL BAR (Quantity, Time, Cal) ---
        JPanel pnlInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 6));
        pnlInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlInfo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        pnlInfo.setBackground(new Color(235, 245, 251)); 
        pnlInfo.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(200, 200, 200)));

        JPanel pnlQty = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlQty.setOpaque(false);
        pnlQty.add(new JLabel("Qty:"));
        spnQuantity = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        JComponent editor = spnQuantity.getEditor();
        JFormattedTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
        tf.setColumns(2);
        pnlQty.add(spnQuantity);
        pnlInfo.add(pnlQty);

        lblPrepTime = new JLabel("⏳ 10m");
        lblPrepTime.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPrepTime.setForeground(new Color(211, 84, 0)); 
        pnlInfo.add(lblPrepTime);

        lblCalories = new JLabel("🔥 650 kcal");
        lblCalories.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCalories.setForeground(new Color(192, 57, 43)); 
        pnlInfo.add(lblCalories);

        lblSpiciness = new JLabel("🌶️ Mild");
        lblSpiciness.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblSpiciness.setForeground(new Color(39, 174, 96));
        pnlInfo.add(lblSpiciness);

        pnlLeft.add(pnlInfo);
        
        pnlLeft.add(Box.createVerticalStrut(15)); 

        // SECTION 2: EXTRAS
        pnlLeft.add(createLabel("2. Extras"));
        JPanel pnlTops = new JPanel(new GridLayout(0, 2, 5, 5));
        pnlTops.setAlignmentX(Component.LEFT_ALIGNMENT);
        toppingCheckboxes = new ArrayList<>();
        String[] tops = {"Cheese ($1.0)", "Bacon ($1.5)", "Fried Egg ($1.0)", "Lettuce ($0.5)", 
                         "Tomato ($0.5)", "Pickles ($0.5)", "Grilled Onion ($0.5)", "Mayo ($0.3)"};
        for(String t : tops) {
            JCheckBox cb = new JCheckBox(t); 
            cb.addActionListener(e -> checkToppingLimit(cb));
            toppingCheckboxes.add(cb); pnlTops.add(cb);
        }
        pnlTops.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        pnlLeft.add(pnlTops);

        // SECTION 3: FINISH
        pnlLeft.add(createLabel("3. Finish Order"));
        
        // Drink
        JPanel pnlDrinkRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlDrinkRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlDrinkRow.add(new JLabel("Drink: ")); 
        cmbDrink = new JComboBox<>(DRINK_OPTIONS); pnlDrinkRow.add(cmbDrink);
        pnlDrinkRow.add(Box.createHorizontalStrut(10));
        pnlDrinkRow.add(new JLabel("Size: ")); 
        cmbDrinkSize = new JComboBox<>(DRINK_SIZES); 
        cmbDrinkSize.setSelectedIndex(1); cmbDrinkSize.setEnabled(false); 
        cmbDrink.addActionListener(e -> {
            String selected = (String) cmbDrink.getSelectedItem();
            cmbDrinkSize.setEnabled(!selected.contains("No Drink"));
        });
        pnlDrinkRow.add(cmbDrinkSize);
        pnlLeft.add(pnlDrinkRow);
        
        // Side
        JPanel pnlSideRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        pnlSideRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlSideRow.add(new JLabel("Side:   "));
        cmbSide = new JComboBox<>(SIDE_OPTIONS); pnlSideRow.add(cmbSide);
        pnlLeft.add(pnlSideRow);

        // Notes
        JLabel lblNote = new JLabel("Special Instructions (Notes):");
        lblNote.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlLeft.add(lblNote);
        txtNotes = new JTextArea(2, 20);
        JScrollPane scrollNotes = new JScrollPane(txtNotes);
        scrollNotes.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollNotes.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollNotes.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        pnlLeft.add(scrollNotes);

        pnlLeft.add(Box.createVerticalStrut(15));
        JButton btnAdd = new JButton("ADD TO CART");
        btnAdd.setBackground(new Color(39, 174, 96)); btnAdd.setForeground(Color.BLACK);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnAdd.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        pnlLeft.add(btnAdd);
        
        // Thêm khoảng trắng cuối cùng để scroll đẹp hơn
        pnlLeft.add(Box.createVerticalStrut(20));

        // --- SPLIT PANE & SCROLL ---
        
        // BỌC CỘT TRÁI VÀO SCROLL PANE
        JScrollPane scrollLeft = new JScrollPane(pnlLeft);
        scrollLeft.setBorder(BorderFactory.createEmptyBorder()); // Bỏ viền cho đẹp
        scrollLeft.getVerticalScrollBar().setUnitIncrement(16); // Tăng tốc độ cuộn chuột
        scrollLeft.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // Chỉ cuộn dọc

        // RIGHT PANEL
        JPanel pnlRight = new JPanel(new BorderLayout());
        pnlRight.setBorder(BorderFactory.createTitledBorder("Current Order Cart"));
        listCart = new JList<>(cartModel);
        listCart.setFont(new Font("Monospaced", Font.PLAIN, 14));
        pnlRight.add(new JScrollPane(listCart), BorderLayout.CENTER);

        JPanel pnlCartActions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRemove = new JButton("Remove Selected");
        JButton btnDuplicate = new JButton("Duplicate Selected");
        pnlCartActions.add(btnRemove); pnlCartActions.add(btnDuplicate);
        pnlRight.add(pnlCartActions, BorderLayout.SOUTH);

        // Thay pnlLeft bằng scrollLeft trong SplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollLeft, pnlRight);
        splitPane.setDividerLocation(520); // Điều chỉnh vị trí chia đôi một chút
        add(splitPane, BorderLayout.CENTER);

        // FOOTER
        JPanel pnlFooter = new JPanel(new BorderLayout());
        pnlFooter.setBorder(new EmptyBorder(15, 20, 15, 20));
        pnlFooter.setBackground(new Color(236, 240, 241));
        lblTotal = new JLabel("TOTAL: $0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 28));
        lblTotal.setForeground(new Color(192, 57, 43));
        JButton btnCheckout = new JButton("CHECKOUT & PRINT");
        btnCheckout.setBackground(new Color(41, 128, 185)); btnCheckout.setForeground(Color.BLACK);
        btnCheckout.setFont(new Font("Arial", Font.BOLD, 16));
        btnCheckout.setPreferredSize(new Dimension(250, 50));
        pnlFooter.add(lblTotal, BorderLayout.WEST);
        pnlFooter.add(btnCheckout, BorderLayout.EAST);
        add(pnlFooter, BorderLayout.SOUTH);

        // EVENTS
        btnAdd.addActionListener(e -> addToCart());
        btnRemove.addActionListener(e -> removeFromCart());
        btnDuplicate.addActionListener(e -> duplicateItem());
        btnCheckout.addActionListener(e -> checkout());
        
        updateDescription();
    }

    // --- INIT DATA & LOGIC (GIỮ NGUYÊN) ---
    private void initAllData() {
        burgerDescriptions = new HashMap<>();
        burgerPrepTimes = new HashMap<>();
        burgerCalories = new HashMap<>();
        burgerSpiciness = new HashMap<>();
        burgerAllergens = new HashMap<>();
        burgerChefNotes = new HashMap<>();

        addBurgerData("Classic Beef", "100% Beef Patty, Special Sauce, Lettuce", "8-10m", "650 Kcal", "Non-Spicy", "Wheat, Dairy", "Classic choice!");
        addBurgerData("Spicy Chicken", "Crispy Chicken, Spicy Mayo, Pickles", "10-12m", "700 Kcal", "Hot 🌶️", "Wheat, Egg", "Adds a spicy kick!");
        addBurgerData("BBQ Bacon", "Beef Patty, Crispy Bacon, BBQ Sauce", "10-12m", "850 Kcal", "Mild", "Wheat, Pork", "Smoky flavor!");
        addBurgerData("Fish Filet", "White Fish Filet, Tartar Sauce, Cheese", "8-10m", "550 Kcal", "Non-Spicy", "Fish, Dairy, Wheat", "Light & Tasty.");
        addBurgerData("Veggie", "Plant-based Patty, Fresh Greens, Tomato", "8-10m", "450 Kcal", "Non-Spicy", "Wheat, Soy", "Healthy option.");
        addBurgerData("Morning", "Beef Patty, Fried Egg, Hashbrown, Coffee", "12-15m", "900 Kcal", "Non-Spicy", "Egg, Wheat", "Great start!");
        addBurgerData("Evening", "Double Beef, Cheddar Cheese, Rings", "15-18m", "1100 Kcal", "Mild", "Dairy, Wheat", "For big appetite.");
        addBurgerData("Ultimate", "Triple Patty, Triple Cheese, Bacon", "15-20m", "1500 Kcal", "Mild", "Dairy, Pork, Wheat", "The Boss Burger.");
        addBurgerData("Royale", "Premium Chicken Breast, Swiss Cheese, Ham", "12-15m", "750 Kcal", "Non-Spicy", "Dairy, Wheat", "Elegant taste.");
        addBurgerData("Truffle", "Beef Patty, Truffle Mayo, Mushrooms", "15-18m", "800 Kcal", "Non-Spicy", "Dairy, Egg", "Rich aroma.");
    }

    private void addBurgerData(String key, String desc, String time, String cal, String spicy, String allergens, String note) {
        burgerDescriptions.put(key, desc);
        burgerPrepTimes.put(key, time);
        burgerCalories.put(key, cal);
        burgerSpiciness.put(key, spicy);
        burgerAllergens.put(key, allergens);
        burgerChefNotes.put(key, note);
    }

    private void updateDescription() {
        String selected = (String) cmbBurger.getSelectedItem();
        String rawDesc = "Standard Burger";
        String time = "10m"; String cal = "600 Kcal"; String spicy = "Non-Spicy";
        String allergens = "None"; String note = "Enjoy!";

        for (String key : burgerDescriptions.keySet()) {
            if (selected.contains(key)) {
                rawDesc = burgerDescriptions.get(key);
                time = burgerPrepTimes.get(key);
                cal = burgerCalories.get(key);
                spicy = burgerSpiciness.get(key);
                allergens = burgerAllergens.get(key);
                note = burgerChefNotes.get(key);
                break;
            }
        }
        
        String[] ingredients = rawDesc.split(",");
        StringBuilder sb = new StringBuilder();
        for (String item : ingredients) sb.append(" • ").append(item.trim()).append("\n");
        txtDescription.setText(sb.toString());

        lblPrepTime.setText("⏳ " + time);
        lblCalories.setText("🔥 " + cal);
        lblSpiciness.setText("🌶️ " + spicy);
        
        if(spicy.contains("Hot")) lblSpiciness.setForeground(Color.RED);
        else if(spicy.contains("Mild")) lblSpiciness.setForeground(new Color(211, 84, 0));
        else lblSpiciness.setForeground(new Color(39, 174, 96));
        
        lblAllergens.setText("⚠️ Contains: " + allergens);
        lblChefNote.setText("⭐ Chef says: " + note);
    }

    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 14));
        l.setForeground(new Color(44, 62, 80));
        l.setBorder(new EmptyBorder(15, 0, 5, 0));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private void checkToppingLimit(JCheckBox changedBox) {
        if (!changedBox.isSelected()) return;
        String selectedBurger = (String) cmbBurger.getSelectedItem();
        int max = (selectedBurger.contains("Deluxe") || selectedBurger.contains("Morning") || selectedBurger.contains("Evening")) ? 5 : 3;
        int count = 0;
        for (JCheckBox cb : toppingCheckboxes) if (cb.isSelected()) count++;
        if (count > max) {
            JOptionPane.showMessageDialog(this, "Max " + max + " toppings allowed!", "Limit Reached", JOptionPane.WARNING_MESSAGE);
            changedBox.setSelected(false);
        }
    }

    private void validateToppingsOnChange() {
        String selectedBurger = (String) cmbBurger.getSelectedItem();
        int max = (selectedBurger.contains("Deluxe") || selectedBurger.contains("Morning") || selectedBurger.contains("Evening")) ? 5 : 3;
        int count = 0;
        for (JCheckBox cb : toppingCheckboxes) {
            if (cb.isSelected()) {
                count++;
                if (count > max) cb.setSelected(false);
            }
        }
    }

    private void addToCart() {
        try {
            int qty = (Integer) spnQuantity.getValue();
            for (int i = 0; i < qty; i++) {
                MealOrder order = createOrderFromInput();
                cartModel.addElement(order);
            }
            resetForm();
            updateTotal();
            if (qty > 1) JOptionPane.showMessageDialog(this, "Added " + qty + " items to cart!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error stopped at item", JOptionPane.ERROR_MESSAGE);
            updateTotal(); 
        }
    }

    private MealOrder createOrderFromInput() throws Exception {
        String rawBurger = (String) cmbBurger.getSelectedItem();
        String bName = rawBurger.split("\\(")[0].trim();
        double bPrice = Double.parseDouble(rawBurger.split("\\$")[1].replace(")", ""));
        inventory.checkStock(bName); 
        Burger burger;
        if (bName.contains("Morning")) {
            MorningBurger mb = new MorningBurger(); mb.validateTime(); burger = mb;
        } else if (bName.contains("Evening")) {
            EveningBurger eb = new EveningBurger(); eb.validateTime(); burger = eb;
        } else if (rawBurger.contains("Deluxe")) {
            burger = new DeluxeBurger(bName, bPrice);
        } else {
            burger = new Burger(bName, bPrice);
        }
        for (JCheckBox cb : toppingCheckboxes) {
            if (cb.isSelected()) {
                String rawTop = cb.getText();
                String tName = rawTop.split("\\(")[0].trim();
                inventory.checkStock(tName);
                double tPrice = Double.parseDouble(rawTop.split("\\$")[1].replace(")", ""));
                burger.addTopping(new SideItem(tName, tPrice));
            }
        }
        String rawDrink = (String) cmbDrink.getSelectedItem();
        String dName = rawDrink.split("\\(")[0].trim();
        Drink drink;
        if (dName.contains("No Drink")) {
            drink = new Drink("No Drink", "NONE", 0.0);
        } else {
            inventory.checkStock(dName);
            String rawSize = (String) cmbDrinkSize.getSelectedItem();
            String cleanSize = rawSize.split(" ")[0]; 
            drink = new Drink(dName, cleanSize, 2.0);
        }
        String rawSide = (String) cmbSide.getSelectedItem();
        String sName = rawSide.split("\\(")[0].trim();
        SideItem side;
        if (sName.contains("No Side")) {
            side = new SideItem("No Side Item", 0.0);
        } else {
            inventory.checkStock(sName);
            side = new SideItem(sName, Double.parseDouble(rawSide.split("\\$")[1].replace(")", "")));
        }
        inventory.reduceStock(bName);
        for (Item t : burger.getToppings()) inventory.reduceStock(t.getName());
        if (!dName.contains("No Drink")) inventory.reduceStock(dName);
        if (!sName.contains("No Side")) inventory.reduceStock(sName);
        inventory.saveInventory();
        String notes = txtNotes.getText();
        return new MealOrder(burger, drink, side, notes);
    }

    private void removeFromCart() {
        int index = listCart.getSelectedIndex();
        if (index >= 0) {
            MealOrder order = cartModel.get(index);
            inventory.restoreStock(order.getBurger().getName());
            for(Item t : order.getBurger().getToppings()) inventory.restoreStock(t.getName());
            if (!order.getDrink().getName().equals("No Drink")) inventory.restoreStock(order.getDrink().getName());
            if (!order.getSide().getName().equals("No Side Item")) inventory.restoreStock(order.getSide().getName());
            inventory.saveInventory();
            cartModel.remove(index);
            updateTotal();
        }
    }

    private void duplicateItem() {
        int index = listCart.getSelectedIndex();
        if (index >= 0) {
            MealOrder original = cartModel.get(index);
            try {
                inventory.checkStock(original.getBurger().getName());
                for (Item t : original.getBurger().getToppings()) inventory.checkStock(t.getName());
                if (!original.getDrink().getName().equals("No Drink")) inventory.checkStock(original.getDrink().getName());
                if (!original.getSide().getName().equals("No Side Item")) inventory.checkStock(original.getSide().getName());

                inventory.reduceStock(original.getBurger().getName());
                for (Item t : original.getBurger().getToppings()) inventory.reduceStock(t.getName());
                if (!original.getDrink().getName().equals("No Drink")) inventory.reduceStock(original.getDrink().getName());
                if (!original.getSide().getName().equals("No Side Item")) inventory.reduceStock(original.getSide().getName());
                inventory.saveInventory();

                Burger newBurger;
                if (original.getBurger() instanceof DeluxeBurger) {
                     if (original.getBurger() instanceof MorningBurger) newBurger = new MorningBurger();
                     else if (original.getBurger() instanceof EveningBurger) newBurger = new EveningBurger();
                     else newBurger = new DeluxeBurger(original.getBurger().getName(), original.getBurger().getBasePrice());
                } else {
                    newBurger = new Burger(original.getBurger().getName(), original.getBurger().getBasePrice());
                }
                
                for (Item t : original.getBurger().getToppings()) {
                    newBurger.addTopping(new SideItem(t.getName(), t.getBasePrice())); 
                }

                Drink newDrink = new Drink(original.getDrink().getName(), original.getDrink().getSize(), 2.0);
                if (original.getDrink().getName().equals("No Drink")) newDrink = new Drink("No Drink", "NONE", 0.0);
                SideItem newSide = new SideItem(original.getSide().getName(), original.getSide().getBasePrice());
                
                MealOrder duplicateOrder = new MealOrder(newBurger, newDrink, newSide, original.getNotes());
                cartModel.addElement(duplicateOrder);
                updateTotal();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Cannot Duplicate: " + ex.getMessage(), "Stock Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void checkout() {
        if (cartModel.isEmpty()) return;
        StringBuilder receipt = new StringBuilder();
        receipt.append("========== FINAL RECEIPT ==========\n");
        double grandTotal = 0;
        for (int i = 0; i < cartModel.getSize(); i++) {
            MealOrder order = cartModel.get(i);
            receipt.append("ITEM #").append(i + 1).append("\n");
            receipt.append(order.getReceiptDetails());
            receipt.append("-----------------------------------\n");
            grandTotal += order.getTotalPrice();
        }
        receipt.append(String.format("GRAND TOTAL: $%.2f\n", grandTotal));
        
        try (FileWriter fw = new FileWriter("receipt_final.txt")) {
            fw.write(receipt.toString());
        } catch (IOException ex) {}

        JTextArea area = new JTextArea(receipt.toString());
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Receipt", JOptionPane.INFORMATION_MESSAGE);
        cartModel.clear();
        updateTotal();
    }

    private void resetForm() {
        cmbBurger.setSelectedIndex(0);
        for(JCheckBox cb : toppingCheckboxes) cb.setSelected(false);
        cmbDrink.setSelectedIndex(0);
        cmbDrinkSize.setSelectedIndex(1);
        cmbDrinkSize.setEnabled(false);
        cmbSide.setSelectedIndex(0);
        txtNotes.setText("");
        spnQuantity.setValue(1);
    }

    private void updateTotal() {
        double total = 0;
        for (int i = 0; i < cartModel.getSize(); i++) total += cartModel.get(i).getTotalPrice();
        lblTotal.setText(String.format("TOTAL: $%.2f", total));
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new BurgerAppGUI().setVisible(true));
    }
}
