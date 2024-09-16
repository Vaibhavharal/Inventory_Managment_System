package com.infobeans;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class OrderFrame extends JFrame {

    private JTextField orderIDField;
    private JTextField orderDateField;
    private JTextField quantityOrderedField;
    private JTextField totalPriceField;
    private JComboBox<String> itemNameComboBox; // Changed to itemNameComboBox
    private JButton createButton;
    private JButton readButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable ordersTable;
    private DefaultTableModel tableModel;
     

    private static final String URL = "jdbc:mysql://localhost:3306/InventoryDB";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public OrderFrame() {
        setTitle("Order Frame");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create Heading Label
        JLabel headingLabel = new JLabel("ORDER PAGE", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setForeground(Color.BLACK);
        headingLabel.setPreferredSize(new Dimension(getWidth(), 50)); // Set preferred size for consistent appearance
        add(headingLabel, BorderLayout.NORTH);

        // Create Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new String[]{"Order ID", "Order Date", "Item Name", "Quantity Ordered", "Total Price"}, 0);
        ordersTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(ordersTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Create Details Panel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Form Fields
        JLabel orderIDLabel = new JLabel("Order ID:");
        orderIDLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        detailsPanel.add(orderIDLabel, gbc);

        orderIDField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        detailsPanel.add(orderIDField, gbc);

        JLabel orderDateLabel = new JLabel("Order Date:");
        orderDateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        detailsPanel.add(orderDateLabel, gbc);

        orderDateField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        detailsPanel.add(orderDateField, gbc);

        JLabel itemNameLabel = new JLabel("Item Name:");
        itemNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        detailsPanel.add(itemNameLabel, gbc);

        itemNameComboBox = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        detailsPanel.add(itemNameComboBox, gbc);

        JLabel quantityOrderedLabel = new JLabel("Quantity Ordered:");
        quantityOrderedLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        detailsPanel.add(quantityOrderedLabel, gbc);

        quantityOrderedField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        detailsPanel.add(quantityOrderedField, gbc);

        JLabel totalPriceLabel = new JLabel("Total Price:");
        totalPriceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        detailsPanel.add(totalPriceLabel, gbc);

        totalPriceField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        detailsPanel.add(totalPriceField, gbc);
        
       
        

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4, 10, 10));

        createButton = new JButton("Create");
        createButton.setBackground(new Color(144, 238, 144)); // LightGreen
        createButton.setForeground(Color.BLACK);
        createButton.setFont(new Font("Arial", Font.BOLD, 14));
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createOrder();
            }
        });
        buttonPanel.add(createButton);

        readButton = new JButton("Read");
        readButton.setBackground(new Color(173, 216, 230)); // LightBlue
        readButton.setForeground(Color.BLACK);
        readButton.setFont(new Font("Arial", Font.BOLD, 14));
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readOrder();
            }
        });
        buttonPanel.add(readButton);

        updateButton = new JButton("Update");
        updateButton.setBackground(new Color(255, 228, 225)); // MistyRose
        updateButton.setForeground(Color.BLACK);
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateOrder();
            }
        });
        buttonPanel.add(updateButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBackground(new Color(255, 99, 71)); // Tomato
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOrder();
            }
        });
        buttonPanel.add(deleteButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        detailsPanel.add(buttonPanel, gbc);

        // Split Pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, detailsPanel, tablePanel);
        splitPane.setDividerLocation(400); // Adjust divider location
        add(splitPane, BorderLayout.CENTER);

        // Load initial data
        loadItems();
        loadOrders();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private void createOrder() {
        Connection conn = null;
        PreparedStatement orderStmt = null;
        PreparedStatement updateItemStmt = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Get the selected item name from the ComboBox
            String selectedItemName = (String) itemNameComboBox.getSelectedItem();

            // SQL query to get item ID from item name
            String getItemIDSql = "SELECT ItemID FROM items WHERE ItemName = ?";
            try (PreparedStatement getItemIDStmt = conn.prepareStatement(getItemIDSql)) {
                getItemIDStmt.setString(1, selectedItemName);
                ResultSet rs = getItemIDStmt.executeQuery();
                if (rs.next()) {
                    int itemID = rs.getInt("ItemID");

                    // SQL query to insert a new order
                    String insertOrderSql = "INSERT INTO orders (OrderDate, ItemID, QuantityOrdered, TotalPrice) VALUES (?, ?, ?, ?)";
                    orderStmt = conn.prepareStatement(insertOrderSql);
                    orderStmt.setDate(1, Date.valueOf(orderDateField.getText())); // Assuming date is in YYYY-MM-DD format
                    orderStmt.setInt(2, itemID);
                    orderStmt.setInt(3, Integer.parseInt(quantityOrderedField.getText()));
                    orderStmt.setDouble(4, Double.parseDouble(totalPriceField.getText()));

                    // Execute the order insertion
                    int rowsAffected = orderStmt.executeUpdate();

                    if (rowsAffected == 0) {
                        throw new SQLException("Order insertion failed, no rows affected.");
                    }

                    // SQL query to update item quantity
                    String updateItemSql = "UPDATE items SET Quantity = Quantity - ? WHERE ItemID = ?";
                    updateItemStmt = conn.prepareStatement(updateItemSql);
                    updateItemStmt.setInt(1, Integer.parseInt(quantityOrderedField.getText())); // Quantity ordered
                    updateItemStmt.setInt(2, itemID);

                    // Execute the item quantity update
                    int itemRowsUpdated = updateItemStmt.executeUpdate();

                    if (itemRowsUpdated == 0) {
                        throw new SQLException("Item update failed, no rows affected.");
                    }

                    // Commit the transaction
                    conn.commit();

                    JOptionPane.showMessageDialog(this, "Order created and item quantity updated successfully!");
                    clearFields(); // Clear fields after creating
                    loadOrders(); // Refresh table data
                } else {
                    JOptionPane.showMessageDialog(this, "Selected item not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback transaction in case of error
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "Error creating order and updating item quantity.");
        } finally {
            // Close resources
            try {
                if (orderStmt != null) orderStmt.close();
                if (updateItemStmt != null) updateItemStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void readOrder() {
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM orders WHERE OrderID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, Integer.parseInt(orderIDField.getText()));
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        orderDateField.setText(rs.getDate("OrderDate").toString());
                        // SQL query to get item name from item ID
                        String getItemNameSql = "SELECT ItemName FROM items WHERE ItemID = ?";
                        try (PreparedStatement getItemNameStmt = conn.prepareStatement(getItemNameSql)) {
                            getItemNameStmt.setInt(1, rs.getInt("ItemID"));
                            ResultSet itemRs = getItemNameStmt.executeQuery();
                            if (itemRs.next()) {
                                itemNameComboBox.setSelectedItem(itemRs.getString("ItemName")); // Set ComboBox selection
                            }
                        }
                        quantityOrderedField.setText(String.valueOf(rs.getInt("QuantityOrdered")));
                        totalPriceField.setText(String.valueOf(rs.getDouble("TotalPrice")));
                    } else {
                        JOptionPane.showMessageDialog(this, "Order not found.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading order.");
        }
    }

    private void updateOrder() {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Get the selected item name from the ComboBox
            String selectedItemName = (String) itemNameComboBox.getSelectedItem();

            // SQL query to get item ID from item name
            String getItemIDSql = "SELECT ItemID FROM items WHERE ItemName = ?";
            try (PreparedStatement getItemIDStmt = conn.prepareStatement(getItemIDSql)) {
                getItemIDStmt.setString(1, selectedItemName);
                ResultSet rs = getItemIDStmt.executeQuery();
                if (rs.next()) {
                    int itemID = rs.getInt("ItemID");

                    // SQL query to update order
                    String sql = "UPDATE orders SET OrderDate = ?, ItemID = ?, QuantityOrdered = ?, TotalPrice = ? WHERE OrderID = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setDate(1, Date.valueOf(orderDateField.getText())); // Assuming date is in YYYY-MM-DD format
                    pstmt.setInt(2, itemID);
                    pstmt.setInt(3, Integer.parseInt(quantityOrderedField.getText()));
                    pstmt.setDouble(4, Double.parseDouble(totalPriceField.getText()));
                    pstmt.setInt(5, Integer.parseInt(orderIDField.getText()));
                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Order updated successfully!");
                        clearFields(); // Clear fields after updating
                        loadOrders(); // Refresh table data
                    } else {
                        JOptionPane.showMessageDialog(this, "Order not found.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Selected item not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback transaction in case of error
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "Error updating order.");
        } finally {
            // Close resources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteOrder() {
        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM orders WHERE OrderID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, Integer.parseInt(orderIDField.getText()));
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Order deleted successfully!");
                    clearFields(); // Clear fields after deleting
                    loadOrders(); // Refresh table data
                } else {
                    JOptionPane.showMessageDialog(this, "Order not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting order.");
        }
    }

    private void loadItems() {
        try (Connection conn = getConnection()) {
            String sql = "SELECT ItemName FROM items"; // Adjusted to select ItemName
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                itemNameComboBox.removeAllItems();
                while (rs.next()) {
                    itemNameComboBox.addItem(rs.getString("ItemName")); // Add item names to ComboBox
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading item names.");
        }
    }

    private void loadOrders() {
        try (Connection conn = getConnection()) {
            String sql = "SELECT orders.OrderID, orders.OrderDate, items.ItemName, orders.QuantityOrdered, orders.TotalPrice " +
                         "FROM orders JOIN items ON orders.ItemID = items.ItemID";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                tableModel.setRowCount(0); // Clear existing rows
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                        rs.getInt("OrderID"),
                        rs.getDate("OrderDate"),
                        rs.getString("ItemName"), // Changed to ItemName
                        rs.getInt("QuantityOrdered"),
                        rs.getDouble("TotalPrice")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading orders.");
        }
    }

    private void clearFields() {
        orderIDField.setText("");
        orderDateField.setText("");
        quantityOrderedField.setText("");
        totalPriceField.setText("");
        itemNameComboBox.setSelectedIndex(-1); // Clear selection in ComboBox
    }

    public static void main(String[] args) {
        OrderFrame frame = new OrderFrame();
        frame.setVisible(true);
    }
}
