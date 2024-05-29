
// ButtonsController.java
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.awt.Dimension;

// Class to handle everything related to buttons
public class ButtonsController {
    private ChessPieceFactory pieceFactory;
    private TileClickController tileClick;
    private PieceController pieceControl;
    private ChessPieceLogic pieceLogic;
    private BoardView board;

    // Constructor
    public ButtonsController(PieceController pieceControl) {
        // Initialize PieceController
        this.pieceControl = pieceControl;
    }

    // Contributor : Farid
    // Setter for ChessPieceLogic
    public void setPieceLogic(ChessPieceLogic pieceLogic) {
        this.pieceLogic = pieceLogic;
    }

    // Contributor : Farid
    // Setter for ChessPieceFactory
    public void setPieceFactory(ChessPieceFactory pieceFactory) {
        this.pieceFactory = pieceFactory;
    }

    // Contributor : Farid
    // Setter for BoardView
    public void setBoardView(BoardView board) {
        this.board = board;
    }

    // Contributor : Farid
    // Setter for TileClickController
    public void setTileClick(TileClickController tileClick) {
        this.tileClick = tileClick;
    }

    // Contributor : Farid, Jacelyn
    // Method for Starting game (go to board)
    public void onStart() {
        // Initialize Board
        BoardView board = new BoardView(pieceControl, tileClick, this);
        tileClick.setPieceBoard(board);
        pieceControl.setBoard(board);
    }

    // Contributor : Farid, Afiq
    // Method to Load Game
    public void loadGame() {
        // Initialize LoadGame
        LoadGame loadGame = new LoadGame(pieceControl, pieceLogic, pieceFactory);
        loadGame.setPieceBoard(board);
        loadGame.loadGame();

        // Update board after new pieces loaded in
        board.updateBoard();
    }

    // Contributor : Farid, Afiq
    // Method to Save Game
    public void saveGame() {
        // Saved file naming format
        String fileName = "save_file.txt";

        // Initialize SaveGame
        SaveGame saveGame = new SaveGame(pieceControl, pieceLogic);
        saveGame.setPieceBoard(board);
        saveGame.saveGame(fileName);
    }

    // Contributor : Farid, Afiq
    // Method to terminate game
    public void exitGame() {
        System.exit(0);
    }

    // Contributor : Farid, Jacelyn
    // Method to go back to Main Menu
    public void backGame() {
        // Reset the game back
        resetGame();

        // Clear the current board
        board.dispose();

        // Initialize Main Menu
        new MainMenu(this);
    }

    // Contributor : Jia
    // Method for window resolution option
    public void options() {
        Object[] options = { "850x700", "1024x790", "Full Screen" };
        String selectedOption = (String) JOptionPane.showInputDialog(null,
                "Choose a resolution:", "Set Window Resolution",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (selectedOption != null) {
            switch (selectedOption) {
                case "850x700":
                    setWindowSize(850, 700);
                    setMainPanelSize(850, 700);
                    setBoardSize(700, 600);
                    setButtonsSize(80, 100);
                    break;
                case "1024x790":
                    setWindowSize(1024, 790);
                    setMainPanelSize(1024, 748);
                    setBoardSize(840, 700);
                    setButtonsSize(120, 100);
                    break;
                case "Full Screen":
                    setFullScreen();
                    setMainPanelSize(1920, 1080);
                    setBoardSize(900, 740);
                    setButtonsSize(140, 100);
                    ;
                    break;
            }
            // Implement the new changes from information acquired from this method
            board.changeResolution();
        }
    }

    // Contributor : Jia
    // Method to set the window size
    private void setWindowSize(int width, int height) {
        board.setSize(new Dimension(width, height));
        board.setLocationRelativeTo(null); // Center the window
    }

    // Contributor : Jia
    // Method to set the main panel size
    private void setMainPanelSize(int width, int height) {
        board.mainPanelHeight = height;
        board.mainPanelWidth = width;
    }

    // Contributor : Jia
    // Method to set the board size
    private void setBoardSize(int width, int height) {
        board.boardHeight = height;
        board.boardWidth = width;
    }

    // Contributor : Jia
    // Method to set the buttons size
    private void setButtonsSize(int width, int height) {
        board.buttonsHeight = height;
        board.buttonsWidth = width;
    }

    // Contributor : Jia
    // Method to set the window to full screen
    private void setFullScreen() {
        board.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window to cover entire screen
        board.setLocationRelativeTo(null); // Center the window on screen
    }

    // Contributor : Farid
    // Method to Reset Game (when Sun piece captured)
    public void resetGame() {
        // Clear pieces in rows 2 and 3
        for (int x = 0; x < 7; x++) {
            pieceControl.setPiece(x, 2, null);
            pieceControl.setPiece(x, 3, null);
        }

        // Reset counter
        pieceLogic.resetTurnCounter();

        // Reset first current player
        pieceLogic.setCurrentPlayer();

        // Reset pieces to initial positions
        pieceControl.initializePieces();

        // Reset Board Flip
        board.setIsFlipped(true);

        // Notify the view to update the board
        board.updateBoard();
    }
}