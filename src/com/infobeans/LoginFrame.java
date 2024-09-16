package com.infobeans;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginFrame extends JFrame {

    LoginFrame() {
        // Create the frame
        JFrame frame = new JFrame("Login Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1650, 1080);
        frame.setLayout(null); // Use null layout for absolute positioning
        frame.setVisible(true);
        
        

        // Create components
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(Color.BLACK);
        JTextField emailField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.BLACK);
        JPasswordField passwordField = new JPasswordField(20);
        JButton signInButton = new JButton("Sign In");
        JButton signUp = new JButton("Sign Up");
        JButton reset = new JButton("Forgot");
        JCheckBox showPasswordCheckBox = new JCheckBox("");

        // Set bounds for each component
        emailLabel.setBounds(655, 210, 100, 30);
        emailField.setBounds(655, 235, 200, 35);
        passwordLabel.setBounds(655, 290, 100, 30);
        passwordField.setBounds(655, 320, 200, 35);
        showPasswordCheckBox.setBounds(655, 360, 20, 20);
        signInButton.setBounds(680, 410, 150, 35);
        signUp.setBounds(655, 470, 100, 20);
        reset.setBounds(760, 470, 100, 20);

        signInButton.setBackground(new Color(255, 255, 255));
        signUp.setBackground(new Color(255, 255, 255));
        reset.setBackground(new Color(255, 255, 255));

        // Add components to the frame
        //background image
        ImageIcon background_image = new ImageIcon("background4.jpg");
        Image img = background_image.getImage();
        Image temp_img = img.getScaledInstance(1650, 1080, Image.SCALE_SMOOTH);
        background_image = new ImageIcon(temp_img);
        JLabel background = new JLabel("", background_image, JLabel.CENTER);
        background.setBounds(0, 0, 1650, 1080);
        frame.add(background);

        //panel
        JPanel login = new JPanel();
        login.setLayout(null);
        login.setSize(700, 350);
        login.setBackground(new Color(0, 0, 0, 50));
        login.setBounds(430, 145, 650, 550);

        background.add(login);
        background.add(emailLabel);
        background.add(emailField);
        background.add(passwordLabel);
        background.add(passwordField);
        background.add(showPasswordCheckBox);
        background.add(signInButton);
        background.add(reset);
        background.add(signUp);
        

        // Add ActionListener to the Sign In button
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String url = "jdbc:mysql://localhost:3306/InventoryDB";
                    String user = "root";
                    String pass = "root";

                    // Declare Connection object
                    Connection connection = null;

                    try {
                        // Load MySQL JDBC driver (optional for recent versions)
                        Class.forName("com.mysql.cj.jdbc.Driver");

                        // Establish the connection
                        connection = DriverManager.getConnection(url, user, pass);

                        // Print a success message
                        System.out.println("Connection established successfully!");

                    } catch (Exception e1) {
                        // Handle case where the JDBC driver class is not found
                        System.err.println("JDBC Driver not found. Include the JDBC driver in your classpath.");
                        e1.printStackTrace();
                    }

                    // Retrieve user input
                    String email = emailField.getText();
                    String password = new String(passwordField.getPassword());

                    if (email.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Both email and password must be provided.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                        return; // Exit the method if validation fails
                    }

                    String sql = "SELECT * FROM signup WHERE email = ? AND password = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, password);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    emailField.setText("");
                    passwordField.setText("");

                    //validation
                    if (resultSet.next()) {
                        // Record found
                        MainFrame mainFrame = new MainFrame();
                        mainFrame.setVisible(true);
                        dispose(); // Close LoginFrame
                    } else {
                        // No record found
                        JOptionPane.showMessageDialog(null, "Invalid email or password.", "Authentication Error", JOptionPane.ERROR_MESSAGE);
                    }

                    // Perform login logic (for demonstration purposes, just printing values)
                    System.out.println("Email: " + email);
                    System.out.println("Password: " + password);

                } catch (Exception e1) {
                    // Handle case where the JDBC driver class is not found
                    System.err.println("JDBC Driver not found. Include the JDBC driver in your classpath.");
                    e1.printStackTrace();
                }
            }
        });

        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open SignUpFrame
                SignUpFrame signUpFrame = new SignUpFrame();
                signUpFrame.setVisible(true);
            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open ForgotPasswordFrame
                new ForgotPasswordFrame();
            }
        });

        // Add ActionListener to the Show Password checkbox
        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckBox.isSelected()) {
                    // Show password
                    passwordField.setEchoChar((char) 0);
                } else {
                    // Hide password
                    passwordField.setEchoChar('*');
                }
            }
        });
    }

    // Make the frame visible
    public static void main(String[] args) {
        new LoginFrame();
    }
}