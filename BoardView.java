
// BoardView.java
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;

// Class tho handle anything related to board 
public class BoardView extends JFrame {
    JPanel buttonsContainer = new JPanel(new GridLayout(12, 1));
    JPanel boardContainer = new JPanel(new GridLayout(6, 7));
    JPanel mainContainer = new JPanel(new FlowLayout());
    JPanel centerPanel = new JPanel();
    JPanel secondPanel = new JPanel();
    JPanel mainPanel = new JPanel();

    private TileClickController tileClick;
    private PieceController pieceControl;
    private ButtonsController buttons;
    private boolean isFlipped = true;
    public int mainPanelHeight;
    public int mainPanelWidth;
    public int buttonsHeight;
    public int buttonsWidth;
    public int boardHeight;
    public int boardWidth;

    // Constructor
    public BoardView(PieceController pieceControl, TileClickController tileClick, ButtonsController buttons) {
        this.pieceControl = pieceControl;
        this.tileClick = tileClick;
        this.buttons = buttons;

        // Set the board in ButtonsController
        buttons.setBoardView(this);

        mainPanelHeight = 700;
        mainPanelWidth = 850;
        buttonsHeight = 100;
        buttonsWidth = 80;
        boardHeight = 600;
        boardWidth = 700;

        // Initialize board display
        displayBoard();
    }

    // Contributor: Jia
    // Method to update resolution from player's selection in
    // option(ButtonsController)
    public void changeResolution() {
        // Set Layout Dimension
        buttonsContainer.setPreferredSize(new Dimension(buttonsWidth, buttonsHeight));
        mainPanel.setPreferredSize(new Dimension(mainPanelWidth, mainPanelHeight));
        boardContainer.setPreferredSize(new Dimension(boardWidth, boardHeight));

        // Update the layout manager
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));

        // Remove existing components
        centerPanel.removeAll();

        // Add the board and buttons with the new sizes
        centerPanel.add(boardContainer);
        centerPanel.add(buttonsContainer);

        // Remove existing components
        secondPanel.removeAll();

        // Add the board and buttons with the new sizes
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        secondPanel.add(centerPanel);

        // Update the layout manager
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainPanel.add(secondPanel);

        // Repaint and revalidate
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // Contributor : Farid, Jia
    // Method to display board
    public void displayBoard() {
        setTitle("Talabia Chess Game"); // Window Title
        setMinimumSize(new Dimension(850, 700)); // Board Dimension
        setResizable(false); // Make the board not resizable

        pieceControl.initializePieces(); // Initialize chess pieces
        pieceControl.identifyPlayerTurn(); // Identifying player turn before displaying board
        updateBoard();

        // Initialize Buttons on Board
        JButton loadBtn = new JButton("Load");
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttons.loadGame();
            }
        });
        buttonsContainer.add(loadBtn);
        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttons.saveGame();
            }
        });
        buttonsContainer.add(saveBtn);
        JButton optionsBtn = new JButton("Options");
        optionsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttons.options();
            }
        });
        buttonsContainer.add(optionsBtn);
        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttons.backGame();
            }
        });
        buttonsContainer.add(backBtn);

        // Setting Main Panel Border
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Setting Center Panel Layout
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));

        // Inserting board and buttons layout inside center panel
        centerPanel.add(boardContainer);
        centerPanel.add(buttonsContainer);

        // Inserting center panel to second panel
        secondPanel.add(centerPanel);

        // Inserting second panel to main panel
        mainPanel.add(secondPanel);

        // Insert mainPanel to screen
        add(mainPanel);

        // Set Layout Dimension
        buttonsContainer.setPreferredSize(new Dimension(buttonsWidth, buttonsHeight));
        mainPanel.setPreferredSize(new Dimension(mainPanelWidth, mainPanelHeight));
        boardContainer.setPreferredSize(new Dimension(boardWidth, boardHeight));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    // Contributor : Farid
    // Method to initialize board
    private void initializeBoard() { // Yellow POV

        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 7; x++) {
                // Initialize tile
                ChessTile tile = new ChessTile(x, y);

                // Initialize tile 'space' on grid
                tile.setBackground(Color.WHITE);

                // Acquiring piece from a specific coordinate from 2D Array
                ChessPiece piece = pieceControl.getPiece(x, y);

                // If the coordinate has a piece allocated on it
                if (piece != null) {
                    // If this piece is a yellow point piece
                    if (piece instanceof ChessPiece.YellowPointPiece) { // If it is a yellow point piece
                        // Initialize Icon
                        ImageIcon icon = new ImageIcon(piece.getImagePath());

                        // If should Rotate Yellow is true
                        if (piece.shouldRotateYellow) {
                            // Initialize rotated point piece image
                            Image rotatedPointer = piece.rotateImage180Degrees(icon.getImage());
                            tile.setIcon(new ImageIcon(rotatedPointer));
                        } else {
                            tile.setIcon(icon);
                        }

                        // If this piece is a blue point piece
                    } else if (piece instanceof ChessPiece.BluePointPiece) { // If it is a blue point piece
                        // Initialize Icon
                        ImageIcon icon = new ImageIcon(piece.getImagePath());

                        // If should Rotate Blue is true
                        if (piece.shouldRotateBlue) {
                            // Initialize rotated point piece image
                            Image rotatedPointer = piece.rotateImage180Degrees(icon.getImage());
                            tile.setIcon(new ImageIcon(rotatedPointer));
                        } else {
                            tile.setIcon(icon);
                        }

                        // If this piece is not point piece
                    } else {
                        // Initialize Icon
                        ImageIcon icon = new ImageIcon(piece.getImagePath());
                        tile.setIcon(icon);
                    }
                }

                // Initialize action listener
                tile.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tileClick.handleTileClick(tile);
                    }
                });

                // Add the tile to board layout
                boardContainer.add(tile);
            }
        }
    }

    // Contributor : Rui, Farid
    // Method to initialize flipped chessboard
    private void initializeFlippedBoard() { // Blue POV

        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 7; x++) {
                // Initialize flipped tile
                ChessTile tile = new ChessTile(6 - x, 5 - y);

                // Initialize tile 'space' on grid
                tile.setBackground(Color.WHITE);

                // Acquiring piece from a specific coordinate from 2D Array
                ChessPiece piece = pieceControl.getPiece(6 - x, 5 - y);

                // If the coordinate has a piece allocated on it
                if (piece != null) {
                    // // If this piece is a blue point piece
                    if (piece instanceof ChessPiece.BluePointPiece) {
                        // Initialize rotated point piece icon
                        ImageIcon icon = new ImageIcon(piece.getImagePath());

                        // If should Rotate Blue is true
                        if (piece.shouldRotateBlue) {
                            tile.setIcon(icon);
                        } else {
                            Image rotatedPointer = piece.rotateImage180DegreesALL(icon.getImage());
                            tile.setIcon(new ImageIcon(rotatedPointer));
                        }

                        // If this piece is a yellow point piece
                    } else if (piece instanceof ChessPiece.YellowPointPiece) {
                        ImageIcon icon = new ImageIcon(piece.getImagePath());

                        // If should Rotate Yellow is true
                        if (piece.shouldRotateYellow) {
                            tile.setIcon(icon);
                        } else {
                            Image rotatedPointer = piece.rotateImage180DegreesALL(icon.getImage());
                            tile.setIcon(new ImageIcon(rotatedPointer));
                        }

                        // If this piece is not point piece
                    } else {
                        // Initialize Icon
                        ImageIcon icon = new ImageIcon(piece.getImagePath());
                        tile.setIcon(icon);
                    }
                }

                // Initialize action listener
                tile.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tileClick.handleTileClick(tile);
                    }
                });

                // Add the tile to board layout
                boardContainer.add(tile);
            }
        }
    }

    // Contributor : Farid, Rui
    // Method to update the board
    public void updateBoard() {
        // Toggle isFlipped
        isFlipped = !isFlipped;

        // Reset Board First
        boardContainer.removeAll();

        if (isFlipped) {
            initializeFlippedBoard();
        } else {
            initializeBoard();
        }

        // Identify player turn again
        pieceControl.identifyPlayerTurn();

        // Repaint and revalidate
        boardContainer.revalidate();
        boardContainer.repaint();
    }

    // Contributor : Jacelyn, Farid
    // Method to display msg pop up for yellow's victory
    public void displayYellowVictoryMsg() {
        System.out.println("JPane: Yellow Wins!");
        JOptionPane.showMessageDialog(this, "Yellow Wins!", "", JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(this, "Restarting Game...", "", JOptionPane.INFORMATION_MESSAGE);
    }

    // Contributor : Jacelyn, Farid
    // Method to display msg pop up for blue's victory
    public void displayBlueVictoryMsg() {
        System.out.println("JPane: Blue Wins!");
        JOptionPane.showMessageDialog(this, "Blue Wins!", "", JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(this, "Restarting Game...", "", JOptionPane.INFORMATION_MESSAGE);
    }

    // Contributor : Jacelyn, Farid
    // Method to display warning msg for invalid move
    public void displayInvalidTurnMsg(String playerColor) {
        System.out.println("You cannot move, it is not your turn.");
        JOptionPane.showMessageDialog(this, "It is currently " + playerColor + "'s turn!", "",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Contributor : Rui, Farid
    // Setter for isFlipped boolean
    public void setIsFlipped(boolean flipBoardState) {
        isFlipped = flipBoardState;
    }

    // Contributor : Rui, Farid
    // Getter for isFlipped boolean
    public boolean getIsFlipped() {
        return this.isFlipped;
    }

    // Contributor : Rui, Farid
    // Getter for board frame
    public JPanel getFrame() {
        return this.boardContainer;
    }
}