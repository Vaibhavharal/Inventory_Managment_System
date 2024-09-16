package com.infobeans;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ForgotPasswordFrame extends JFrame {
    
    ForgotPasswordFrame() {
        setTitle("Forgot Password");
        setSize(400, 300);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Create components
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20); // Adjust width as needed
        JLabel newPasswordLabel = new JLabel("New Password:");
        JPasswordField newPasswordField = new JPasswordField(20); // Adjust width as needed
        JButton submitButton = new JButton("Submit");
        
        // Set GridBagConstraints for components
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(emailLabel, gbc);
        
        gbc.gridx = 1;
        add(emailField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(newPasswordLabel, gbc);
        
        gbc.gridx = 1;
        add(newPasswordField, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gbc);
        
        // Add ActionListener to the Submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String newPassword = new String(newPasswordField.getPassword());
                
                if (email.isEmpty() || newPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Email and new password must be provided.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                try {
                    String url = "jdbc:mysql://localhost:3306/InventoryDB"; 
                    String user = "root"; 
                    String pass = "root";
                    
                    Connection connection = DriverManager.getConnection(url, user, pass);
                    
                    // Check if the email exists
                    String checkEmailSql = "SELECT * FROM signup WHERE email = ?";
                    PreparedStatement checkEmailStmt = connection.prepareStatement(checkEmailSql);
                    checkEmailStmt.setString(1, email);
                    
                    ResultSet resultSet = checkEmailStmt.executeQuery();
                    
                    if (resultSet.next()) {
                        // Email exists; Update the password
                        String updatePasswordSql = "UPDATE signup SET password = ? WHERE email = ?";
                        PreparedStatement updatePasswordStmt = connection.prepareStatement(updatePasswordSql);
                        updatePasswordStmt.setString(1, newPassword);
                        updatePasswordStmt.setString(2, email);
                        
                        int rowsUpdated = updatePasswordStmt.executeUpdate();
                        
                        if (rowsUpdated > 0) {
                            JOptionPane.showMessageDialog(null, "Password updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to update password.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Email not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An error occurred while processing the request.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                // Close the forgot password frame
                dispose();
            }
        });
        
        // Center the frame on screen
        setLocationRelativeTo(null);
        
        setVisible(true);
    }
}
