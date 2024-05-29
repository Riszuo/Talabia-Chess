
// LoadGame.java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

// Class to handle Load Game Function
public class LoadGame {
    private ChessPieceFactory pieceFactory;
    private PieceController pieceControl;
    private ChessPieceLogic pieceLogic;
    private BoardView board;

    // Constructor
    public LoadGame(PieceController pieceControl, ChessPieceLogic pieceLogic, ChessPieceFactory pieceFactory) {
        this.pieceControl = pieceControl;
        this.pieceLogic = pieceLogic;
        this.pieceFactory = pieceFactory;
    }

    // Contributor : Farid, Afiq
    // Setter for BoardView
    public void setPieceBoard(BoardView board) {
        this.board = board;
    }

    // Contributor : Farid, Afiq
    // Method to Load Game
    public void loadGame() {
        // Initialize File Chooser
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
        fileChooser.setDialogTitle("Choose a file to load");

        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String fileName = fileChooser.getSelectedFile().getAbsolutePath();
            loadGameFromFile(fileName);
        }
    }

    // Contributor : Farid, Afiq
    // Method to Load Game from file
    public void loadGameFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            // Read currentPlayerColor, turnCounter, and isFlipped
            pieceLogic.setCurrentPlayerColor(reader.readLine());
            pieceLogic.setTurnCounter(Integer.parseInt(reader.readLine()));
            board.setIsFlipped(Boolean.parseBoolean(reader.readLine()));

            // Clear the board
            pieceControl.clearBoard();

            // Read and place pieces on the board
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String pieceType = parts[0];
                String color = parts[1];
                int x = Integer.parseInt(parts[2]);
                int y = Integer.parseInt(parts[3]);

                // Initialize new piece and create new piece
                ChessPiece piece = pieceFactory.createChessPiece(pieceType, color);
                pieceControl.setPiece(x, y, piece);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
