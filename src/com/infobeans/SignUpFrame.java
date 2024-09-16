package com.infobeans;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

public class SignUpFrame extends JFrame {

    SignUpFrame() {
        // Create the frame
        JFrame frame = new JFrame("SignUp Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1650, 1080);
        frame.setLayout(null); // Use null layout for absolute positioning
        frame.setVisible(true);

        // Create components
        JLabel emailLabel = new JLabel("Your Email");
        emailLabel.setForeground(Color.BLACK);
        JTextField emailField = new JTextField(20);

        JLabel nameLabel = new JLabel("Your Name");
        nameLabel.setForeground(Color.BLACK);
        JTextField nameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.BLACK);
        JPasswordField passwordField = new JPasswordField(20);

        JButton signUpButton = new JButton("Sign Up");
        JButton signIn = new JButton("Sign In");

        // Add checkbox for showing/hiding password
        JCheckBox showPasswordCheckBox = new JCheckBox("");
        showPasswordCheckBox.setBounds(655, 440, 20, 20);

        // Set bounds for each component
        emailLabel.setBounds(655, 210, 100, 30);
        emailField.setBounds(655, 235, 200, 35);

        nameLabel.setBounds(655, 290, 100, 30);
        nameField.setBounds(655, 320, 200, 35);

        passwordLabel.setBounds(655, 370, 100, 30);
        passwordField.setBounds(655, 400, 200, 35);

        signUpButton.setBounds(680, 470, 150, 35);
        signIn.setBounds(705, 530, 100, 20);

        signUpButton.setBackground(new Color(255, 255, 255));
        signIn.setBackground(new Color(255, 255, 255));

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
        JPanel signupPanel = new JPanel();
        signupPanel.setLayout(null);
        signupPanel.setSize(700, 350);
        signupPanel.setBackground(new Color(0, 0, 0, 50));
        signupPanel.setBounds(430, 145, 650, 550);

        background.add(signupPanel);
        background.add(emailLabel);
        background.add(emailField);
        background.add(nameLabel);
        background.add(nameField);
        background.add(passwordLabel);
        background.add(passwordField);
        background.add(showPasswordCheckBox); // Add the checkbox
        background.add(signUpButton);
        background.add(signIn);

        // Add ActionListener to the Sign Up button
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // JDBC
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
                    String name = nameField.getText();
                    String password = new String(passwordField.getPassword());

                    // Validation
                    if (email.isEmpty() || name.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "All fields must be filled out.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                        return; // Exit the method if validation fails
                    }

                    String sql = "INSERT INTO signup (email, name, password) VALUES (?, ?, ?)";

                    PreparedStatement preparedStatement = connection.prepareStatement(sql);

                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, name);
                    preparedStatement.setString(3, password);

                    // Execute the insert operation
                    int rowsAffected = preparedStatement.executeUpdate();

                    emailField.setText("");
                    passwordField.setText("");
                    nameField.setText("");

                    // Registration success
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Registration completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }

                    // Print success message
                    System.out.println("Rows inserted: " + rowsAffected);

                } catch (Exception e1) {
                    // Handle case where the JDBC driver class is not found
                    System.err.println("JDBC Driver not found. Include the JDBC driver in your classpath.");
                    e1.printStackTrace();
                }
            }
        });

        // Add ActionListener to the Sign In button
        signIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open LoginFrame
                LoginFrame l = new LoginFrame();
                l.setVisible(true);
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

    public static void main(String[] args) {
        new SignUpFrame();
    }
}
