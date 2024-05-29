// MainMenu.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Class to handle everything related to Main Menu
public class MainMenu extends JFrame {
    private ButtonsController buttons;

    // Constructor
    public MainMenu(ButtonsController buttons) {
        this.buttons = buttons;

        // Initialize the main menu display
        displayMainMenu();
    }

    // Contributor : Jacelyn
    // Method to display the main menu
    public void displayMainMenu() {
        setTitle("Talabia Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null); // Pop up the menu in the middle of the screen
        getContentPane().setBackground(Color.WHITE);

        // Create and configure components
        JLabel gameName = new JLabel("Talabia Chess Game");
        gameName.setFont(new Font("Helvetica", Font.BOLD, 24));
        gameName.setHorizontalAlignment(SwingConstants.CENTER);

        // Setup layout for logo image
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new BorderLayout());

        // Load chess logo image
        ImageIcon chessLogoIcon = new ImageIcon("images/chessLogo.png");
        JLabel chessLogoLabel = new JLabel(chessLogoIcon);
        logoPanel.add(chessLogoLabel, BorderLayout.CENTER);

        // Buttons and Layout
        Panel buttonPanel = new Panel(new GridLayout(2, 1));
        JButton startButton = new JButton("Start Game");
        JButton exitButton = new JButton("Exit Game");

        // Add styling to buttons
        startButton.setFont(new Font("Helvetica", Font.PLAIN, 15));
        exitButton.setFont(new Font("Helvetica", Font.PLAIN, 15));

        // Start Button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start Button clicked...MainMenu");
                buttons.onStart();
                setVisible(false);
            }
        });

        // Exit Button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exit Button clicked...MainMenu");
                buttons.exitGame();
            }
        });

        // Add components to the frame
        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);

        // Use GridBagLayout to align components
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.2;
        add(gameName, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.4;
        add(logoPanel, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.4;
        add(buttonPanel, gbc);

        setVisible(true);
    }
}
