//package com.infobeans;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.sql.*;
//
//public class CategoryFrame extends JFrame {
//
//    private JTextField categoryidField;
//    private JTextField categorynameField;
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
//    public CategoryFrame() {
//        setTitle("Category Frame");
//        setSize(1650, 1080);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(null);
//        
//        getContentPane().setBackground(new Color(240, 248, 255)); // AliceBlue background
//
//        // Create and position heading label
//        JLabel headingLabel = new JLabel("CATEGORY PAGE");
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
//        // Create labels
//        JLabel categoryidLabel = new JLabel("Category ID:");
//        categoryidLabel.setBounds(675, 250, 100, 30);
//        categoryidLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        add(categoryidLabel);
//
//        JLabel categorynameLabel = new JLabel("Category Name:");
//        categorynameLabel.setBounds(675, 340, 150, 30);
//        categorynameLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        add(categorynameLabel);
//
//        // Create text fields
//        categoryidField = new JTextField();
//        categoryidField.setBounds(675, 280, 300, 35);
//        categoryidField.setBackground(Color.WHITE);
//        add(categoryidField);
//
//        categorynameField = new JTextField();
//        categorynameField.setBounds(675, 370, 300, 35);
//        categorynameField.setBackground(Color.WHITE);
//        add(categorynameField);
//
//        // Create buttons
//        createButton = new JButton("Create");
//        createButton.setBounds(560, 500, 120, 35);
//        createButton.setBackground(new Color(144, 238, 144)); // LightGreen
//        createButton.setForeground(Color.BLACK);
//        createButton.setFont(new Font("Arial", Font.BOLD, 14));
//        createButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                createCategory();
//            }
//        });
//        add(createButton);
//
//        readButton = new JButton("Read");
//        readButton.setBounds(700, 500, 120, 35);
//        readButton.setBackground(new Color(173, 216, 230)); // LightBlue
//        readButton.setForeground(Color.BLACK);
//        readButton.setFont(new Font("Arial", Font.BOLD, 14));
//        readButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                readCategory();
//            }
//        });
//        add(readButton);
//
//        updateButton = new JButton("Update");
//        updateButton.setBounds(840, 500, 120, 35);
//        updateButton.setBackground(new Color(255, 228, 225)); // MistyRose
//        updateButton.setForeground(Color.BLACK);
//        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
//        updateButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                updateCategory();
//            }
//        });
//        add(updateButton);
//
//        deleteButton = new JButton("Delete");
//        deleteButton.setBounds(980, 500, 120, 35);
//        deleteButton.setBackground(new Color(255, 99, 71)); // Tomato
//        deleteButton.setForeground(Color.BLACK);
//        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
//        deleteButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                deleteCategory();
//            }
//        });
//        add(deleteButton);
//    }
//
//    private Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(URL, USER, PASSWORD);
//    }
//
//    private void createCategory() {
//        try (Connection conn = getConnection()) {
//            String sql = "INSERT INTO Categories (CategoryID, CategoryName) VALUES (?, ?)";
//            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//                pstmt.setInt(1, Integer.parseInt(categoryidField.getText()));
//                pstmt.setString(2, categorynameField.getText());
//                pstmt.executeUpdate();
//                JOptionPane.showMessageDialog(this, "Category created successfully!");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Error creating category.");
//        }
//    }
//
//    private void readCategory() {
//        try (Connection conn = getConnection()) {
//            String sql = "SELECT * FROM Categories WHERE CategoryID = ?";
//            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//                pstmt.setInt(1, Integer.parseInt(categoryidField.getText()));
//                try (ResultSet rs = pstmt.executeQuery()) {
//                    if (rs.next()) {
//                        categorynameField.setText(rs.getString("CategoryName"));
//                    } else {
//                        JOptionPane.showMessageDialog(this, "Category not found.");
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Error reading category.");
//        }
//    }
//
//    private void updateCategory() {
//        try (Connection conn = getConnection()) {
//            String sql = "UPDATE Categories SET CategoryName = ? WHERE CategoryID = ?";
//            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//                pstmt.setString(1, categorynameField.getText());
//                pstmt.setInt(2, Integer.parseInt(categoryidField.getText()));
//                int rowsAffected = pstmt.executeUpdate();
//                if (rowsAffected > 0) {
//                    JOptionPane.showMessageDialog(this, "Category updated successfully!");
//                } else {
//                    JOptionPane.showMessageDialog(this, "Category not found.");
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Error updating category.");
//        }
//    }
//
//    private void deleteCategory() {
//        try (Connection conn = getConnection()) {
//            String sql = "DELETE FROM Categories WHERE CategoryID = ?";
//            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//                pstmt.setInt(1, Integer.parseInt(categoryidField.getText()));
//                int rowsAffected = pstmt.executeUpdate();
//                if (rowsAffected > 0) {
//                    JOptionPane.showMessageDialog(this, "Category deleted successfully!");
//                } else {
//                    JOptionPane.showMessageDialog(this, "Category not found.");
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Error deleting category.");
//        }
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new CategoryFrame().setVisible(true);
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

public class CategoryFrame extends JFrame {

    private JTextField categoryidField;
    private JTextField categorynameField;
    private JButton createButton;
    private JButton readButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable categoryTable;
    private DefaultTableModel tableModel;

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/InventoryDB";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public CategoryFrame() {
        setTitle("Category Frame");
        setSize(1000, 600); // Adjusted size for better view
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create and set up the panels
        JPanel detailPanel = createDetailPanel();
        JPanel tablePanel = createTablePanel();

        // Add panels to the frame using JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, detailPanel, tablePanel);
        splitPane.setDividerLocation(400); // Set initial divider location
        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createDetailPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components

        panel.setBackground(new Color(240, 248, 255)); // AliceBlue background
        
        JLabel headingLabel = new JLabel("CATEGORY PAGE", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setForeground(Color.BLACK);
        headingLabel.setPreferredSize(new Dimension(getWidth(), 50)); // Set preferred size for consistent appearance
        add(headingLabel, BorderLayout.NORTH);


        // Create and position heading label
//        JLabel headingLabel = new JLabel("CATEGORY PAGE");
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

        // Create and position labels and text fields
        gbc.gridwidth = 1; // Reset to default
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Category ID:"), gbc);

        gbc.gridx = 1;
        categoryidField = new JTextField(20);
        panel.add(categoryidField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Category Name:"), gbc);

        gbc.gridx = 1;
        categorynameField = new JTextField(20);
        panel.add(categorynameField, gbc);

        // Create and position buttons
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        createButton = new JButton("Create");
        createButton.setBackground(new Color(144, 238, 144)); // LightGreen
        createButton.setForeground(Color.BLACK);
        createButton.setFont(new Font("Arial", Font.BOLD, 14));
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCategory();
            }
        });
        panel.add(createButton, gbc);

        gbc.gridx = 1;
        readButton = new JButton("Read");
        readButton.setBackground(new Color(173, 216, 230)); // LightBlue
        readButton.setForeground(Color.BLACK);
        readButton.setFont(new Font("Arial", Font.BOLD, 14));
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readCategory();
            }
        });
        panel.add(readButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        updateButton = new JButton("Update");
        updateButton.setBackground(new Color(255, 228, 225)); // MistyRose
        updateButton.setForeground(Color.BLACK);
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCategory();
            }
        });
        panel.add(updateButton, gbc);

        gbc.gridx = 1;
        deleteButton = new JButton("Delete");
        deleteButton.setBackground(new Color(255, 99, 71)); // Tomato
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCategory();
            }
        });
        panel.add(deleteButton, gbc);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255)); // AliceBlue background

        // Create JTable and JScrollPane
        String[] columnNames = {"Category ID", "Category Name"};
        tableModel = new DefaultTableModel(columnNames, 0);
        categoryTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(categoryTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private void createCategory() {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO Categories (CategoryID, CategoryName) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, Integer.parseInt(categoryidField.getText()));
                pstmt.setString(2, categorynameField.getText());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Category created successfully!");
                clearTable();
                loadCategories();
                clearFields(); // Clear fields after operation
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error creating category.");
        }
    }

    private void readCategory() {
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM Categories WHERE CategoryID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, Integer.parseInt(categoryidField.getText()));
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        categorynameField.setText(rs.getString("CategoryName"));
                    } else {
                        JOptionPane.showMessageDialog(this, "Category not found.");
                        clearFields(); // Clear fields if no data found
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading category.");
        }
    }

    private void updateCategory() {
        try (Connection conn = getConnection()) {
            String sql = "UPDATE Categories SET CategoryName = ? WHERE CategoryID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, categorynameField.getText());
                pstmt.setInt(2, Integer.parseInt(categoryidField.getText()));
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Category updated successfully!");
                    clearTable();
                    loadCategories();
                    clearFields(); // Clear fields after operation
                } else {
                    JOptionPane.showMessageDialog(this, "Category not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating category.");
        }
    }

    private void deleteCategory() {
        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM Categories WHERE CategoryID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, Integer.parseInt(categoryidField.getText()));
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Category deleted successfully!");
                    clearTable();
                    loadCategories();
                    clearFields(); // Clear fields after operation
                } else {
                    JOptionPane.showMessageDialog(this, "Category not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting category.");
        }
    }

    private void clearTable() {
        tableModel.setRowCount(0); // Clear all rows in the table
    }

    private void loadCategories() {
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM Categories";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Object[] rowData = {
                        rs.getInt("CategoryID"),
                        rs.getString("CategoryName")
                    };
                    tableModel.addRow(rowData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading categories.");
        }
    }

    private void clearFields() {
        categoryidField.setText("");
        categorynameField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CategoryFrame frame = new CategoryFrame();
                frame.setVisible(true);
                frame.loadCategories(); // Load categories when the frame is made visible
            }
        });
    }
}
