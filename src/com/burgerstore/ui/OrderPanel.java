package com.burgerstore.ui;

import com.burgerstore.model.*;
import com.burgerstore.service.InventoryManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OrderPanel extends JPanel {
    private InventoryManager inventory;
    private DefaultListModel<MealOrder> cartModel;
    private Runnable onCartChanged;

    private JComboBox<String> cmbBurger, cmbDrink, cmbDrinkSize, cmbSide;
    private JTextArea txtNotes;
    private JSpinner spnQuantity;
    private List<JCheckBox> toppingCheckboxes;

    private final String[] BURGER_OPTIONS = {
        "Classic Beef Burger (50.000 đ)", "Spicy Chicken Burger (55.000 đ)", "BBQ Bacon Burger (65.000 đ)",
        "Fish Filet Burger (60.000 đ)", "Veggie Burger (50.000 đ)",
        "Morning Deluxe Burger (120.000 đ)", "Evening Deluxe Burger (140.000 đ)",
        "Deluxe Ultimate Stack (120.000 đ)", "Deluxe Chicken Royale (110.000 đ)", "Deluxe Truffle Mushroom (135.000 đ)"
    };

    private final String[] DRINK_SIZES = {"SMALL (-5.000 đ)", "MEDIUM (Gốc)", "LARGE (+10.000 đ)"};

    private final String[] DRINK_OPTIONS = {
        "No Drink (0 đ)", "Coca Cola (15.000 đ)", "Pepsi (15.000 đ)", "Sprite (15.000 đ)",
        "Fanta Orange (15.000 đ)", "Mountain Dew (15.000 đ)",
        "Iced Coffee (20.000 đ)", "Peach Tea (20.000 đ)",
        "Strawberry Milkshake (30.000 đ)", "Chocolate Milkshake (30.000 đ)"
    };

    private final String[] SIDE_OPTIONS = {
        "No Side Item (0 đ)", "French Fries (25.000 đ)", "Onion Rings (30.000 đ)", "Chicken Nuggets (40.000 đ)",
        "Coleslaw Salad (20.000 đ)", "Mozzarella Sticks (45.000 đ)", "Apple Pie (25.000 đ)"
    };

    public OrderPanel(InventoryManager inventory, DefaultListModel<MealOrder> cartModel, Runnable onCartChanged) {
        this.inventory = inventory;
        this.cartModel = cartModel;
        this.onCartChanged = onCartChanged;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JPanel pnlLeft = new JPanel();
        pnlLeft.setLayout(new BoxLayout(pnlLeft, BoxLayout.Y_AXIS));
        pnlLeft.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Burger
        pnlLeft.add(createLabel("1. Select Burger"));
        cmbBurger = new JComboBox<>(BURGER_OPTIONS);
        cmbBurger.setFont(new Font("Segoe UI", Font.BOLD, 13)); 
        cmbBurger.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        cmbBurger.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlLeft.add(cmbBurger);

        pnlLeft.add(Box.createVerticalStrut(10));

        // Extras
        pnlLeft.add(createLabel("2. Extras"));
        JPanel pnlTops = new JPanel(new GridLayout(0, 2, 5, 5));
        pnlTops.setAlignmentX(Component.LEFT_ALIGNMENT);
        toppingCheckboxes = new ArrayList<>();
        String[] tops = {"Cheese (10.000 đ)", "Bacon (15.000 đ)", "Fried Egg (10.000 đ)", "Lettuce (5.000 đ)", 
                         "Tomato (5.000 đ)", "Pickles (5.000 đ)", "Grilled Onion (5.000 đ)", "Mayo (3.000 đ)"};
        for (String t : tops) {
            JCheckBox cb = new JCheckBox(t);
            toppingCheckboxes.add(cb);
            pnlTops.add(cb);
        }
        pnlTops.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        pnlLeft.add(pnlTops);

        pnlLeft.add(Box.createVerticalStrut(10));

        // Finish
        pnlLeft.add(createLabel("3. Finish Order"));

        JPanel pnlDrinkRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlDrinkRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlDrinkRow.add(new JLabel("Drink: "));
        cmbDrink = new JComboBox<>(DRINK_OPTIONS); pnlDrinkRow.add(cmbDrink);
        pnlDrinkRow.add(Box.createHorizontalStrut(10));
        pnlDrinkRow.add(new JLabel("Size: "));
        cmbDrinkSize = new JComboBox<>(DRINK_SIZES); 
        cmbDrinkSize.setSelectedIndex(1); 
        cmbDrinkSize.setEnabled(false);
        cmbDrink.addActionListener(e -> {
            String selected = (String) cmbDrink.getSelectedItem();
            cmbDrinkSize.setEnabled(!selected.contains("No Drink"));
        });
        pnlDrinkRow.add(cmbDrinkSize);
        pnlLeft.add(pnlDrinkRow);

        JPanel pnlSideRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        pnlSideRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlSideRow.add(new JLabel("Side:   "));
        cmbSide = new JComboBox<>(SIDE_OPTIONS); pnlSideRow.add(cmbSide);
        pnlLeft.add(pnlSideRow);

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
        JPanel pnlFinish = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlFinish.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlFinish.add(new JLabel("Qty:"));
        spnQuantity = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        pnlFinish.add(spnQuantity);
        JButton btnAdd = new JButton("ADD TO CART");
        btnAdd.setBackground(new Color(39, 174, 96)); btnAdd.setForeground(Color.WHITE);
        btnAdd.addActionListener(e -> addToCart());
        pnlFinish.add(btnAdd);
        pnlLeft.add(pnlFinish);

        pnlLeft.add(Box.createVerticalStrut(20));

        JScrollPane scrollLeft = new JScrollPane(pnlLeft);
        scrollLeft.setBorder(BorderFactory.createEmptyBorder());
        scrollLeft.getVerticalScrollBar().setUnitIncrement(16);
        scrollLeft.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollLeft, BorderLayout.CENTER);
    }

    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 14));
        l.setForeground(new Color(44, 62, 80));
        l.setBorder(new EmptyBorder(15, 0, 5, 0));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    // Helper Parse Price
    private double parsePrice(String text) {
        try {
            if (!text.contains("(")) return 0;
            String pricePart = text.substring(text.lastIndexOf("(") + 1, text.lastIndexOf(")"));
            pricePart = pricePart.replace("đ", "").replace(".", "").trim();
            if (pricePart.startsWith("+")) pricePart = pricePart.substring(1);
            return Double.parseDouble(pricePart);
        } catch (Exception e) { return 0; }
    }

    private void addToCart() {
        try {
            int qty = (Integer) spnQuantity.getValue();
            for (int i = 0; i < qty; i++) {
                MealOrder order = createOrderFromInput();
                cartModel.addElement(order);
            }
            resetForm();
            if (onCartChanged != null) onCartChanged.run();
            if (qty > 1) JOptionPane.showMessageDialog(this, "Added " + qty + " items to cart!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error stopped at item", JOptionPane.ERROR_MESSAGE);
            if (onCartChanged != null) onCartChanged.run();
        }
    }

    private MealOrder createOrderFromInput() throws Exception {
        String rawBurger = (String) cmbBurger.getSelectedItem();
        String bName = rawBurger.split("\\(")[0].trim();
        double bPrice = parsePrice(rawBurger);
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
                double tPrice = parsePrice(rawTop);
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
            side = new SideItem(sName, parsePrice(rawSide));
        }
        
        inventory.reduceStock(bName);
        for (Item t : burger.getToppings()) inventory.reduceStock(t.getName());
        if (!dName.contains("No Drink")) inventory.reduceStock(dName);
        if (!sName.contains("No Side")) inventory.reduceStock(sName);
        inventory.saveInventory();
        
        String notes = txtNotes.getText();
        return new MealOrder(burger, drink, side, notes);
    }

    private void resetForm() {
        cmbBurger.setSelectedIndex(0);
        for (JCheckBox cb : toppingCheckboxes) cb.setSelected(false);
        cmbDrink.setSelectedIndex(0);
        cmbDrinkSize.setSelectedIndex(1);
        cmbDrinkSize.setEnabled(false);
        cmbSide.setSelectedIndex(0);
        txtNotes.setText("");
        spnQuantity.setValue(1);
    }
}
