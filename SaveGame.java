
// SaveGame.java
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// Class to handle the save game function
public class SaveGame {
    private PieceController pieceControl;
    private ChessPieceLogic pieceLogic;
    private BoardView board;

    // Constructor
    public SaveGame(PieceController pieceControl, ChessPieceLogic pieceLogic) {
        this.pieceControl = pieceControl;
        this.pieceLogic = pieceLogic;
    }

    // Contributor : Farid
    // Setter to set BoardView
    public void setPieceBoard(BoardView board) {
        this.board = board;
    }

    // Contributor : Farid, Afiq
    // Method to save game
    public void saveGame(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write currentPlayerColor, turnCounter, and isFlipped
            writer.write(pieceLogic.getCurrentPlayerColor() + "\n");
            writer.write(pieceLogic.getTurnCounter() + "\n");

            // Invert isFlipped before saving
            writer.write(!board.getIsFlipped() + "\n");

            // Iterate through tiles and write piece information
            for (int y = 0; y < 6; y++) {
                for (int x = 0; x < 7; x++) {
                    ChessPiece piece = pieceControl.getPiece(x, y);
                    if (piece != null) {
                        // Write piece type, color, and position
                        writer.write(piece.getType() + "," + piece.getColor() + "," + x + "," + y + "\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
