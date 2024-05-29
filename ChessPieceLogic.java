// ChessPieceLogic.java

// Class to handle everything related to the game's core logic
public class ChessPieceLogic {
    private PieceController pieceControl;
    private String currentPlayerColor;
    private boolean isYellowTurn;
    private boolean isBlueTurn;
    public int turnCounter;

    // Constructor
    public ChessPieceLogic() {
        // Initialize boolean for yellow first move
        isYellowTurn = true;
        isBlueTurn = false;

        // Initialize turn counter
        turnCounter = 0;

        // Initialize first player color
        currentPlayerColor = "Yellow";
    }

    // Contributor : Farid
    // Setter for PieceController
    public void setPieceControl(PieceController pieceControl) {
        this.pieceControl = pieceControl;
    }

    // Contributor : Farid
    // Method to check if any Sun Piece has been captured
    public boolean isSunPieceCaptured() {
        boolean yellowSunPieceFound = false;
        boolean blueSunPieceFound = false;

        // Iterate and check through every tile on the board
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 6; y++) {
                ChessPiece piece = pieceControl.getPiece(x, y);

                // If Yellow Sun Piece is present on the board
                if (piece instanceof ChessPiece.YellowSunPiece) {
                    yellowSunPieceFound = true;

                    // If Blue Sun Piece is present on the board
                } else if (piece instanceof ChessPiece.BlueSunPiece) {
                    blueSunPieceFound = true;

                }
            }
        }

        // If yellow sun piece is not found -> blue wins
        if (!yellowSunPieceFound) {
            pieceControl.getVictoryBlue();
            // If blue sun piece is not found -> yellow wins
        } else if (!blueSunPieceFound) {
            pieceControl.getVictoryYellow();
        }

        // Return true if either Yellow or Blue SunPiece is not found, indicating it has
        // been captured
        return !yellowSunPieceFound || !blueSunPieceFound;
    }

    // Contributor : Rui
    // Method to check who is the current player
    public boolean isCorrectPlayersTurn(ChessPiece selectedPiece) {
        // If it is Yellow's Turn and the selected piece is a yellow piece
        if (isYellowTurn && selectedPiece.getColor().equals(Resource.YellowColor())) {
            return true;
            // If it is Blue's Turn and the selected piece is a blue piece
        } else if (isBlueTurn && selectedPiece.getColor().equals(Resource.BlueColor())) {
            return true;
        } else {
            return false;
        }
    }

    // Contributor : Rui, Jia, Farid
    // Method to set current player by turn counter
    public void setCurrentPlayer() {
        // If turnCounter is even = Yellow's turn
        // If turnCounter is odd = Blue's turn

        if (turnCounter % 2 == 0) {
            isYellowTurn = true;
            currentPlayerColor = "Yellow";
        } else {
            isBlueTurn = true;
            currentPlayerColor = "Blue";
        }
    }

    // Contributor : Rui, Farid, Jia
    // Setter to set current player color
    public void setCurrentPlayerColor(String thisPlayerColor) {
        currentPlayerColor = thisPlayerColor;
    }

    // Contributor : Rui, Farid, Jia
    // Getter to get current player color
    public String getCurrentPlayerColor() {
        return this.currentPlayerColor;
    }

    // Contributor : Jia, Farid
    // Setter to set turn counter
    public void setTurnCounter(int currentTurnCount) {
        turnCounter = currentTurnCount;
    }

    // Contributor : Jia, Farid
    // Getter to get turn counter
    public int getTurnCounter() {
        return this.turnCounter;
    }

    // Contributor : Jia, Farid
    // Method to reset turn counter
    public void resetTurnCounter() {
        turnCounter = 0;
    }

    // Contributor : Jia, Farid
    // Method to increment turn counter
    public void increaseTurnCounter() {
        this.turnCounter++;
    }
}
