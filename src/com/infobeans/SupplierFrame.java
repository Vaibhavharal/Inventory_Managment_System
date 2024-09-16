//package com.infobeans;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.sql.*;
//
//public class SupplierFrame extends JFrame {
//
//    private JTextField supplierIDField;
//    private JTextField supplierNameField;
//    private JTextField contactInfoField;
//    private JButton createButton;
//    private JButton readButton;
//    private JButton updateButton;
//    private JButton deleteButton;
//    
//    // Database connection details
//    private static final String URL = "jdbc:mysql://localhost:3306/InventoryDB";
//    private static final String USER = "root";
//    private static final String PASSWORD = "root";
//
//    public SupplierFrame() {
//        setTitle("Supplier Frame");
//        setSize(1650, 1080);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(null);
//
//        // Set background color
//        getContentPane().setBackground(new Color(240, 248, 255)); // AliceBlue background
//        
//        JLabel headingLabel = new JLabel("SUPPLIER PAGE");
//        headingLabel.setBounds(0, 20, 1650, 50); // Full width of the frame
//        headingLabel.setFont(new Font("Verdana", Font.BOLD, 40));
//        headingLabel.setForeground(new Color(220, 20, 60)); // Crimson Red
//       // headingLabel.setForeground(new Color(0, 102, 204)); // Dark blue color
//        headingLabel.setBackground(new Color(255, 255, 255)); // White background
//        headingLabel.setOpaque(true); // Make background color visible
//        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        headingLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, new Color(0, 102, 204))); // Bottom border
//        add(headingLabel);
//
//        // Create and add labels
//        JLabel supplierIDLabel = new JLabel("Supplier ID:");
//        supplierIDLabel.setBounds(675, 150, 100, 30);
//        supplierIDLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        add(supplierIDLabel);
//
//        JLabel supplierNameLabel = new JLabel("Supplier Name:");
//        supplierNameLabel.setBounds(675, 230, 120, 30);
//        supplierNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        add(supplierNameLabel);
//
//        JLabel contactInfoLabel = new JLabel("Contact Info:");
//        contactInfoLabel.setBounds(675, 310, 100, 30);
//        contactInfoLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        add(contactInfoLabel);
//
//        // Create and add text fields
//        supplierIDField = new JTextField();
//        supplierIDField.setBounds(675, 180, 300, 35);
//        supplierIDField.setBackground(Color.WHITE);
//        add(supplierIDField);
//
//        supplierNameField = new JTextField();
//        supplierNameField.setBounds(675, 260, 300, 35);
//        supplierNameField.setBackground(Color.WHITE);
//        add(supplierNameField);
//
//        contactInfoField = new JTextField();
//        contactInfoField.setBounds(675, 340, 300, 35);
//        contactInfoField.setBackground(Color.WHITE);
//        add(contactInfoField);
//
//        // Create and add buttons
//        createButton = new JButton("Create");
//        createButton.setBounds(600, 400, 120, 35);
//        createButton.setBackground(new Color(144, 238, 144)); // LightGreen
//        createButton.setForeground(Color.BLACK);
//        createButton.setFont(new Font("Arial", Font.BOLD, 14));
//        createButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                createSupplier();
//            }
//        });
//        add(createButton);
//
//        readButton = new JButton("Read");
//        readButton.setBounds(740, 400, 120, 35);
//        readButton.setBackground(new Color(173, 216, 230)); // LightBlue
//        readButton.setForeground(Color.BLACK);
//        readButton.setFont(new Font("Arial", Font.BOLD, 14));
//        readButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                readSupplier();
//            }
//        });
//        add(readButton);
//
//        updateButton = new JButton("Update");
//        updateButton.setBounds(880, 400, 120, 35);
//        updateButton.setBackground(new Color(255, 228, 225)); // MistyRose
//        updateButton.setForeground(Color.BLACK);
//        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
//        updateButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                updateSupplier();
//            }
//        });
//        add(updateButton);
//
//        deleteButton = new JButton("Delete");
//        deleteButton.setBounds(1020, 400, 120, 35);
//        deleteButton.setBackground(new Color(255, 99, 71)); // Tomato
//        deleteButton.setForeground(Color.BLACK);
//        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
//        deleteButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                deleteSupplier();
//            }
//        });
//        add(deleteButton);
//    }
//
//    private Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(URL, USER, PASSWORD);
//    }
//
//    private void createSupplier() {
//        try (Connection conn = getConnection()) {
//            String sql = "INSERT INTO suppliers (SupplierID, SupplierName, ContactInfo) VALUES (?, ?, ?)";
//            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//                pstmt.setInt(1, Integer.parseInt(supplierIDField.getText()));
//                pstmt.setString(2, supplierNameField.getText());
//                pstmt.setString(3, contactInfoField.getText());
//                pstmt.executeUpdate();
//                JOptionPane.showMessageDialog(this, "Supplier created successfully!");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Error creating supplier.");
//        }
//    }
//
//    private void readSupplier() {
//        try (Connection conn = getConnection()) {
//            String sql = "SELECT * FROM suppliers WHERE SupplierID = ?";
//            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//                pstmt.setInt(1, Integer.parseInt(supplierIDField.getText()));
//                try (ResultSet rs = pstmt.executeQuery()) {
//                    if (rs.next()) {
//                        supplierNameField.setText(rs.getString("SupplierName"));
//                        contactInfoField.setText(rs.getString("ContactInfo"));
//                    } else {
//                        JOptionPane.showMessageDialog(this, "Supplier not found.");
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Error reading supplier.");
//        }
//    }
//
//    private void updateSupplier() {
//        try (Connection conn = getConnection()) {
//            String sql = "UPDATE suppliers SET SupplierName = ?, ContactInfo = ? WHERE SupplierID = ?";
//            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//                pstmt.setString(1, supplierNameField.getText());
//                pstmt.setString(2, contactInfoField.getText());
//                pstmt.setInt(3, Integer.parseInt(supplierIDField.getText()));
//                int rowsAffected = pstmt.executeUpdate();
//                if (rowsAffected > 0) {
//                    JOptionPane.showMessageDialog(this, "Supplier updated successfully!");
//                } else {
//                    JOptionPane.showMessageDialog(this, "Supplier not found.");
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Error updating supplier.");
//        }
//    }
//
//    private void deleteSupplier() {
//        try (Connection conn = getConnection()) {
//            String sql = "DELETE FROM suppliers WHERE SupplierID = ?";
//            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//                pstmt.setInt(1, Integer.parseInt(supplierIDField.getText()));
//                int rowsAffected = pstmt.executeUpdate();
//                if (rowsAffected > 0) {
//                    JOptionPane.showMessageDialog(this, "Supplier deleted successfully!");
//                } else {
//                    JOptionPane.showMessageDialog(this, "Supplier not found.");
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Error deleting supplier.");
//        }
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new SupplierFrame().setVisible(true);
//            }
//        });
//    }
//}
package com.infobeans;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SupplierFrame extends JFrame {

    private JTextField supplierIDField;
    private JTextField supplierNameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JButton createButton;
    private JButton readButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable supplierTable;
    private DefaultTableModel tableModel;

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/InventoryDB";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public SupplierFrame() {
        setTitle("Supplier Frame");
        setSize(1000, 600); // Adjusted size to accommodate JTable and details panel
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create panels
        JPanel detailsPanel = createDetailsPanel();
        JPanel tablePanel = createTablePanel();

        // Add panels to the frame using JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, detailsPanel, tablePanel);
        splitPane.setDividerLocation(400); // Set initial divider location
        add(splitPane, BorderLayout.CENTER);

        // Load initial data into the table
        loadTableData();
    }

    private JPanel createDetailsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components

        // Set background color
        panel.setBackground(new Color(240, 248, 255)); // AliceBlue
        
        JLabel headingLabel = new JLabel("SUPPLIER PAGE", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setForeground(Color.BLACK);
        headingLabel.setPreferredSize(new Dimension(getWidth(), 50)); // Set preferred size for consistent appearance
        add(headingLabel, BorderLayout.NORTH);


        // Heading Label
//        JLabel headingLabel = new JLabel("SUPPLIER PAGE");
//        headingLabel.setFont(new Font("Verdana", Font.BOLD, 40));
//        headingLabel.setForeground(new Color(220, 20, 60)); // Crimson Red
//        headingLabel.setBackground(new Color(255, 255, 255)); // White background
//        headingLabel.setOpaque(true); // Make background color visible
//        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        headingLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, new Color(0, 102, 204))); // Bottom border
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.gridwidth = 2; // Span across columns
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        panel.add(headingLabel, gbc);

        // Supplier ID Label
        JLabel supplierIDLabel = new JLabel("Supplier ID:");
        supplierIDLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(supplierIDLabel, gbc);

        // Supplier ID TextField
        supplierIDField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(supplierIDField, gbc);

        // Supplier Name Label
        JLabel supplierNameLabel = new JLabel("Supplier Name:");
        supplierNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(supplierNameLabel, gbc);

        // Supplier Name TextField
        supplierNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(supplierNameField, gbc);

        // Phone Label
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(phoneLabel, gbc);

        // Phone TextField
        phoneField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(phoneField, gbc);

        // Email Label
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(emailLabel, gbc);

        // Email TextField
        emailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(emailField, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4, 10, 10)); // 1 row, 4 columns, 10px gap

        createButton = new JButton("Create");
        createButton.setBackground(new Color(144, 238, 144)); // LightGreen
        createButton.setForeground(Color.BLACK);
        createButton.setFont(new Font("Arial", Font.BOLD, 14));
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createSupplier();
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
                readSupplier();
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
                updateSupplier();
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
                deleteSupplier();
            }
        });
        buttonPanel.add(deleteButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // Span across columns
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255)); // AliceBlue

        // Create JTable and JScrollPane
        String[] columnNames = {"Supplier ID", "Supplier Name", "Phone", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0);
        supplierTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(supplierTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private void clearFields() {
        supplierIDField.setText("");
        supplierNameField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    private void createSupplier() {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO newsuppliers (SupplierID, SupplierName, Phone, Email) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, Integer.parseInt(supplierIDField.getText()));
                pstmt.setString(2, supplierNameField.getText());
                pstmt.setString(3, phoneField.getText());
                pstmt.setString(4, emailField.getText());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Supplier created successfully!");
                clearFields(); // Clear fields after operation
                loadTableData(); // Refresh table data
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error creating supplier.");
        }
    }

    private void readSupplier() {
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM newsuppliers WHERE SupplierID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, Integer.parseInt(supplierIDField.getText()));
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        supplierNameField.setText(rs.getString("SupplierName"));
                        phoneField.setText(rs.getString("Phone"));
                        emailField.setText(rs.getString("Email"));
                    } else {
                        JOptionPane.showMessageDialog(this, "Supplier not found.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading supplier.");
        }
    }

    private void updateSupplier() {
        try (Connection conn = getConnection()) {
            String sql = "UPDATE newsuppliers SET SupplierName = ?, Phone = ?, Email = ? WHERE SupplierID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, supplierNameField.getText());
                pstmt.setString(2, phoneField.getText());
                pstmt.setString(3, emailField.getText());
                pstmt.setInt(4, Integer.parseInt(supplierIDField.getText()));
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Supplier updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Supplier not found.");
                }
                clearFields(); // Clear fields after operation
                loadTableData(); // Refresh table data
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating supplier.");
        }
    }

    private void deleteSupplier() {
        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM newsuppliers WHERE SupplierID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, Integer.parseInt(supplierIDField.getText()));
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Supplier deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Supplier not found.");
                }
                clearFields(); // Clear fields after operation
                loadTableData(); // Refresh table data
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting supplier.");
        }
    }

    private void loadTableData() {
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM newsuppliers";
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                // Clear existing rows
                tableModel.setRowCount(0);

                // Add new rows
                while (rs.next()) {
                    Object[] rowData = {
                        rs.getInt("SupplierID"),
                        rs.getString("SupplierName"),
                        rs.getString("Phone"),
                        rs.getString("Email")
                    };
                    tableModel.addRow(rowData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading table data.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SupplierFrame().setVisible(true);
            }
        });
    }
}
