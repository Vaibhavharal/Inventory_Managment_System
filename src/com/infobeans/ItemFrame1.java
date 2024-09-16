package com.infobeans;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ItemFrame1 extends JFrame {

    private JTextField itemCodeField;
    private JTextField nameField;
    private JTextField quantityField;
    private JTextField priceField;
    private JComboBox<String> categoryNameComboBox;
    private JComboBox<String> supplierNameComboBox;
    private JTable itemsTable;
    private DefaultTableModel tableModel;
    private Map<String, Integer> categoryNameToIdMap = new HashMap<>();
    private Map<String, Integer> supplierNameToIdMap = new HashMap<>();

    public ItemFrame1() {
        // Set up the frame
        setTitle("Item Frame");
        setSize(1000, 600); // Adjusted size to accommodate JTable
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create and set up the panels
        JPanel detailPanel = createDetailPanel();
        JPanel tablePanel = createTablePanel();

        // Add panels to the frame using JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, detailPanel, tablePanel);
        splitPane.setDividerLocation(400); // Set initial divider location
        add(splitPane, BorderLayout.CENTER);

        // Load initial data into table and combo boxes
        loadTableData();
        populateComboBoxes();
    }

    private JPanel createDetailPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components

        // Set background color
        panel.setBackground(new Color(245, 245, 245)); // Light gray background

        JLabel headingLabel = new JLabel("ITEM PAGE", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setForeground(Color.BLACK);
        headingLabel.setPreferredSize(new Dimension(getWidth(), 50)); // Set preferred size for consistent appearance
        add(headingLabel, BorderLayout.NORTH);

        // Create and position labels and text fields
        gbc.gridwidth = 1; // Reset to default
        gbc.fill = GridBagConstraints.NONE;

        // Label and text field for Item Code
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Item Code:"), gbc);

        gbc.gridx = 1;
        itemCodeField = new JTextField(15);
        itemCodeField.setPreferredSize(new Dimension(200, 30));
        panel.add(itemCodeField, gbc);

        // Label and text field for Item Name
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Item Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(15);
        nameField.setPreferredSize(new Dimension(200, 30));
        panel.add(nameField, gbc);

        // Label and text field for Quantity
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Quantity:"), gbc);

        gbc.gridx = 1;
        quantityField = new JTextField(15);
        quantityField.setPreferredSize(new Dimension(200, 30));
        panel.add(quantityField, gbc);

        // Label and text field for Price
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Price:"), gbc);

        gbc.gridx = 1;
        priceField = new JTextField(15);
        priceField.setPreferredSize(new Dimension(200, 30));
        panel.add(priceField, gbc);

        // Label and JComboBox for Category Name
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Category Name:"), gbc);

        gbc.gridx = 1;
        categoryNameComboBox = new JComboBox<>();
        categoryNameComboBox.setPreferredSize(new Dimension(200, 30));
        panel.add(categoryNameComboBox, gbc);

        // Label and JComboBox for Supplier Name
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Supplier Name:"), gbc);

        gbc.gridx = 1;
        supplierNameComboBox = new JComboBox<>();
        supplierNameComboBox.setPreferredSize(new Dimension(200, 30));
        panel.add(supplierNameComboBox, gbc);

        // Create and position buttons
        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton addButton = new JButton("Create");
        addButton.setPreferredSize(new Dimension(150, 40));
        addButton.setBackground(new Color(144, 238, 144)); // LightGreen
        addButton.setForeground(Color.BLACK);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(addButton, gbc);

        JButton updateButton = new JButton("Update");
        updateButton.setPreferredSize(new Dimension(150, 40));
        updateButton.setBackground(new Color(173, 216, 230)); // LightBlue
        updateButton.setForeground(Color.BLACK);
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        panel.add(updateButton, gbc);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(150, 40));
        deleteButton.setBackground(new Color(255, 99, 71)); // Tomato
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(deleteButton, gbc);

        JButton retrieveButton = new JButton("Read");
        retrieveButton.setPreferredSize(new Dimension(150, 40));
        retrieveButton.setBackground(new Color(255, 228, 225)); // MistyRose
        retrieveButton.setForeground(Color.BLACK);
        retrieveButton.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        panel.add(retrieveButton, gbc);

        // Action listeners for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addData();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteData();
            }
        });

        retrieveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retrieveData();
            }
        });

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245)); // Light gray background

        // Create JTable and JScrollPane
        String[] columnNames = {"Item Code", "Item Name", "Quantity", "Price", "Category Name", "Supplier Name"};
        tableModel = new DefaultTableModel(columnNames, 0);
        itemsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(itemsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Method to clear all text fields and combo boxes
    private void clearFields() {
        itemCodeField.setText("");
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        categoryNameComboBox.setSelectedIndex(-1); // Set to default
        supplierNameComboBox.setSelectedIndex(-1); // Set to default
    }

    // Database operations
    private void addData() {
        try (Connection conn = DatabaseUtils.getConnection()) {
            String sql = "INSERT INTO items (ItemID, ItemName, Quantity, Price, CategoryID, SupplierID) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, itemCodeField.getText());
                stmt.setString(2, nameField.getText());
                stmt.setInt(3, Integer.parseInt(quantityField.getText()));
                stmt.setBigDecimal(4, new BigDecimal(priceField.getText()));
                stmt.setInt(5, categoryNameToIdMap.get(categoryNameComboBox.getSelectedItem()));
                stmt.setInt(6, supplierNameToIdMap.get(supplierNameComboBox.getSelectedItem()));
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data added successfully");
                clearFields(); // Clear fields after operation
                loadTableData(); // Refresh table data
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding data");
        }
    }

    private void updateData() {
        try (Connection conn = DatabaseUtils.getConnection()) {
            String sql = "UPDATE items SET ItemName = ?, Quantity = ?, Price = ?, CategoryID = ?, SupplierID = ? WHERE ItemID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nameField.getText());
                stmt.setInt(2, Integer.parseInt(quantityField.getText()));
                stmt.setBigDecimal(3, new BigDecimal(priceField.getText()));
                stmt.setInt(4, categoryNameToIdMap.get(categoryNameComboBox.getSelectedItem()));
                stmt.setInt(5, supplierNameToIdMap.get(supplierNameComboBox.getSelectedItem()));
                stmt.setString(6, itemCodeField.getText());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data updated successfully");
                clearFields(); // Clear fields after operation
                loadTableData(); // Refresh table data
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating data");
        }
    }

    private void deleteData() {
        try (Connection conn = DatabaseUtils.getConnection()) {
            String sql = "DELETE FROM items WHERE ItemID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, itemCodeField.getText());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data deleted successfully");
                clearFields(); // Clear fields after operation
                loadTableData(); // Refresh table data
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting data");
        }
    }

    private void retrieveData() {
        try (Connection conn = DatabaseUtils.getConnection()) {
            String sql = "SELECT * FROM items WHERE ItemID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, itemCodeField.getText());
                try (ResultSet rs = stmt.executeQuery()) {
                    // Clear previous rows
                    tableModel.setRowCount(0);

                    // Add new rows
                    while (rs.next()) {
                        Object[] rowData = {
                            rs.getString("ItemID"),
                            rs.getString("ItemName"),
                            rs.getInt("Quantity"),
                            rs.getBigDecimal("Price"),
                            getCategoryNameById(rs.getInt("CategoryID")),
                            getSupplierNameById(rs.getInt("SupplierID"))
                        };
                        tableModel.addRow(rowData);
                    }

                    if (tableModel.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(this, "No data found");
                    }
                }
                clearFields(); // Optionally, clear fields after retrieving data
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving data");
        }
    }

    private void loadTableData() {
        try (Connection conn = DatabaseUtils.getConnection()) {
            String sql = "SELECT * FROM items";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                // Clear previous rows
                tableModel.setRowCount(0);

                // Add new rows
                while (rs.next()) {
                    Object[] rowData = {
                        rs.getString("ItemID"),
                        rs.getString("ItemName"),
                        rs.getInt("Quantity"),
                        rs.getBigDecimal("Price"),
                        getCategoryNameById(rs.getInt("CategoryID")),
                        getSupplierNameById(rs.getInt("SupplierID"))
                    };
                    tableModel.addRow(rowData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading table data");
        }
    }

    private void populateComboBoxes() {
        try (Connection conn = DatabaseUtils.getConnection()) {
            // Populate Category Name ComboBox
            String sql = "SELECT CategoryID, CategoryName FROM Categories";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String categoryName = rs.getString("CategoryName");
                    int categoryId = rs.getInt("CategoryID");
                    categoryNameComboBox.addItem(categoryName);
                    categoryNameToIdMap.put(categoryName, categoryId);
                }
            }

            // Populate Supplier Name ComboBox
            sql = "SELECT SupplierID, SupplierName FROM Suppliers";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String supplierName = rs.getString("SupplierName");
                    int supplierId = rs.getInt("SupplierID");
                    supplierNameComboBox.addItem(supplierName);
                    supplierNameToIdMap.put(supplierName, supplierId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error populating combo boxes");
        }
    }

    private String getCategoryNameById(int categoryId) {
        try (Connection conn = DatabaseUtils.getConnection()) {
            String sql = "SELECT CategoryName FROM Categories WHERE CategoryID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, categoryId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("CategoryName");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getSupplierNameById(int supplierId) {
        try (Connection conn = DatabaseUtils.getConnection()) {
            String sql = "SELECT SupplierName FROM Suppliers WHERE SupplierID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, supplierId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("SupplierName");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ItemFrame1().setVisible(true);
            }
        });
    }
}

class DatabaseUtils {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/InventoryDB";
        String user = "root";
        String password = "root";
        return DriverManager.getConnection(url, user, password);
    }
}
