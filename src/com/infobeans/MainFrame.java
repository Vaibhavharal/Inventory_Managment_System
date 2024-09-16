package com.infobeans;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private JButton itemButton;
    private JButton categoryButton;
    private JButton supplierButton;
    private JButton orderButton;

    private OrderFrame orderFrame;
    private SupplierFrame supplierFrame;
    private CategoryFrame categoryFrame;
    private ItemFrame1 itemFrame;

    public MainFrame() {
        setTitle("Inventory Management System");
        setSize(800, 600); // Adjusted size for better view
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set background color
        getContentPane().setBackground(new Color(135, 206, 235)); // Sky blue background

        // Use GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding around components

        // Initialize the frames
        orderFrame = new OrderFrame();
        supplierFrame = new SupplierFrame();
        categoryFrame = new CategoryFrame();
        itemFrame = new ItemFrame1();

        // Create heading label
        JLabel headingLabel = new JLabel("INVENTORY MANAGEMENT SYSTEM", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font size and style
        headingLabel.setForeground(Color.BLACK); // Set text color to black

        // Add heading label to the main frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Span all columns
        add(headingLabel, gbc);

        // Create buttons
        itemButton = new JButton("Item Management");
        categoryButton = new JButton("Category Management");
        supplierButton = new JButton("Supplier Management");
        orderButton = new JButton("Order Management");

        // Set button sizes
        Dimension buttonSize = new Dimension(220, 60); // Standard button size
        itemButton.setPreferredSize(buttonSize);
        categoryButton.setPreferredSize(buttonSize);
        supplierButton.setPreferredSize(buttonSize);
        orderButton.setPreferredSize(buttonSize);

        // Set button text colors
        itemButton.setForeground(Color.BLACK);
        categoryButton.setForeground(Color.BLACK);
        supplierButton.setForeground(Color.BLACK);
        orderButton.setForeground(Color.BLACK);

        // Add action listeners to the buttons using anonymous inner classes
        itemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFrame(itemFrame);
            }
        });

        categoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFrame(categoryFrame);
            }
        });

        supplierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFrame(supplierFrame);
            }
        });

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFrame(orderFrame);
            }
        });

        // Create and add logout button with smaller size
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(0, 0, 50, 30); // Reduced size for the logout button
        logoutButton.setForeground(Color.BLACK); // Optional: Set logout button color

        // Add action listener to the logout button using anonymous inner class
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current frame
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true); // Show the login frame
                //loginFrame.setLocationRelativeTo(null); // Center the login frame on the screen
            }
        });

        // Add buttons to the main frame using GridBagLayout
        gbc.gridy = 1; // Start at the second row

        gbc.gridx = 0; // Column 0
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Span all columns
        add(itemButton, gbc);

        gbc.gridy = 2; // Move to the next row
        add(categoryButton, gbc);

        gbc.gridy = 3; // Move to the next row
        add(supplierButton, gbc);

        gbc.gridy = 4; // Move to the next row
        add(orderButton, gbc);

        // Add logout button to the bottom-right corner
        gbc.gridy = 5; // Move to the last row (bottom)
        gbc.gridx = GridBagConstraints.RELATIVE; // Last column
        gbc.anchor = GridBagConstraints.SOUTHEAST; // Anchor to the bottom-right corner
        gbc.gridwidth = GridBagConstraints.NONE; // Reset to default grid width
        add(logoutButton, gbc);
    }

    private void showFrame(JFrame frame) {
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
