// PieceController.java

// Class to handle everything related to piece management and control
public class PieceController {
    private ChessPieceFactory pieceFactory;
    private ChessPieceLogic pieceLogic;
    private ChessPiece[][] pieces;
    private BoardView board;

    // Store the coordinates of the currently selected piece
    // (default value = no selected piece)
    private int selectedX = -1;
    private int selectedY = -1;

    // Constructor
    public PieceController(ChessPieceFactory pieceFactory, ChessPieceLogic pieceLogic) {
        this.pieceFactory = pieceFactory;
        this.pieceLogic = pieceLogic;

        // Initialize pieces from Chess Piece Array
        pieces = new ChessPiece[6][7];
    }

    // Contributor : Farid
    // Setter for ChessPieceLogic
    public void setPieceLogic(ChessPieceLogic pieceLogic) {
        this.pieceLogic = pieceLogic;
    }

    // Contributor : Farid
    // Setter for BoardView
    public void setBoard(BoardView board) {
        this.board = board;
    }

    // Contributor : Farid
    // Method to Initialize Pieces at it's default position
    public void initializePieces() {
        // Yellow ====================================================================
        // Hourglass
        setPiece(1, 5, pieceFactory.createChessPiece("YellowHourglass", "Yellow"));
        setPiece(5, 5, pieceFactory.createChessPiece("YellowHourglass", "Yellow"));

        // Time
        setPiece(2, 5, pieceFactory.createChessPiece("YellowTime", "Yellow"));
        setPiece(4, 5, pieceFactory.createChessPiece("YellowTime", "Yellow"));

        // Plus
        setPiece(0, 5, pieceFactory.createChessPiece("YellowPlus", "Yellow"));
        setPiece(6, 5, pieceFactory.createChessPiece("YellowPlus", "Yellow"));

        // Sun
        setPiece(3, 5, pieceFactory.createChessPiece("YellowSun", "Yellow"));

        // Point
        setPiece(0, 4, pieceFactory.createChessPiece("YellowPoint", "Yellow"));
        setPiece(1, 4, pieceFactory.createChessPiece("YellowPoint", "Yellow"));
        setPiece(2, 4, pieceFactory.createChessPiece("YellowPoint", "Yellow"));
        setPiece(3, 4, pieceFactory.createChessPiece("YellowPoint", "Yellow"));
        setPiece(4, 4, pieceFactory.createChessPiece("YellowPoint", "Yellow"));
        setPiece(5, 4, pieceFactory.createChessPiece("YellowPoint", "Yellow"));
        setPiece(6, 4, pieceFactory.createChessPiece("YellowPoint", "Yellow"));

        // Blue ====================================================================
        // Hourglass
        setPiece(1, 0, pieceFactory.createChessPiece("BlueHourglass", "Blue"));
        setPiece(5, 0, pieceFactory.createChessPiece("BlueHourglass", "Blue"));

        // Time
        setPiece(2, 0, pieceFactory.createChessPiece("BlueTime", "Blue"));
        setPiece(4, 0, pieceFactory.createChessPiece("BlueTime", "Blue"));

        // Plus
        setPiece(0, 0, pieceFactory.createChessPiece("BluePlus", "Blue"));
        setPiece(6, 0, pieceFactory.createChessPiece("BluePlus", "Blue"));

        // Sun
        setPiece(3, 0, pieceFactory.createChessPiece("BlueSun", "Blue"));

        // Point
        setPiece(0, 1, pieceFactory.createChessPiece("BluePoint", "Blue"));
        setPiece(1, 1, pieceFactory.createChessPiece("BluePoint", "Blue"));
        setPiece(2, 1, pieceFactory.createChessPiece("BluePoint", "Blue"));
        setPiece(3, 1, pieceFactory.createChessPiece("BluePoint", "Blue"));
        setPiece(4, 1, pieceFactory.createChessPiece("BluePoint", "Blue"));
        setPiece(5, 1, pieceFactory.createChessPiece("BluePoint", "Blue"));
        setPiece(6, 1, pieceFactory.createChessPiece("BluePoint", "Blue"));
    }

    // Contributor : Farid
    // Method to handle the movement of a chess piece
    public void movePiece(int startX, int startY, int destX, int destY) {

        ChessPiece piece = getPiece(startX, startY);
        System.out.println("The Piece that is moving: " + piece);

        if (piece != null && piece.getColor() == pieceLogic.getCurrentPlayerColor()) {
            if (piece.isValidMove(startX, startY, destX, destY)) {

                setPiece(destX, destY, piece); // Move the selected piece to the selected destination
                setPiece(startX, startY, null); // Remove the piece from the starting location

                // Round Counter for piece changes
                counterPieceSwap();

                // Update the view
                board.updateBoard();
            }
        } else if (piece != null && piece.getColor() != pieceLogic.getCurrentPlayerColor()) {
            board.displayInvalidTurnMsg(pieceLogic.getCurrentPlayerColor());
        }
    }

    // Contributor : Jia
    // Switch Time and Plus Pieces
    public void switchTimeAndPlus() {
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 6; y++) {
                ChessPiece thisPiece = getPiece(x, y);

                if (thisPiece instanceof ChessPiece.YellowTimePiece) {
                    setPiece(x, y, new ChessPiece.YellowPlusPiece(Resource.getYellowPlusImagePath(),
                            Resource.YellowColor(), this));
                } else if (thisPiece instanceof ChessPiece.BlueTimePiece) {
                    setPiece(x, y, new ChessPiece.BluePlusPiece(Resource.getBluePlusImagePath(),
                            Resource.BlueColor(), this));
                } else if (thisPiece instanceof ChessPiece.YellowPlusPiece) {
                    setPiece(x, y, new ChessPiece.YellowTimePiece(Resource.getYellowTimeImagePath(),
                            Resource.YellowColor(), this));
                } else if (thisPiece instanceof ChessPiece.BluePlusPiece) {
                    setPiece(x, y, new ChessPiece.BlueTimePiece(Resource.getBlueTimeImagePath(),
                            Resource.BlueColor(), this));
                }
            }
        }
    }

    // Contributor : Farid
    // Method to clear the pieces on the board
    public void clearBoard() {
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 7; x++) {
                setPiece(x, y, null);
            }
        }
    }

    // Contributor : Jia, Farid
    // Round Counter for piece changes
    public void counterPieceSwap() {
        // Increase turn counter every turn
        pieceLogic.increaseTurnCounter();

        // If turn counter is equal to 4 (indicating it is already 2 round)
        if (pieceLogic.getTurnCounter() == 4) {
            // Switch the pieces
            switchTimeAndPlus();

            // Reset the turn counter
            pieceLogic.resetTurnCounter();
        }
    }

    // Contributor : Farid
    // Getter to get piece at a certain coordinate from 2D Array
    public ChessPiece getPiece(int x, int y) {
        return pieces[y][x];
    }

    // Contributor : Farid
    // Setter to set a piece in a specific array coordinate (x , y , piece type)
    public void setPiece(int x, int y, ChessPiece piece) {
        pieces[y][x] = piece;
    }

    // Contributor : Farid
    // Get piece type and color at a specific position
    public String getPieceTypeAndColor(int x, int y) {
        ChessPiece piece = getPiece(x, y);
        if (piece != null) {
            return piece.getType() + " (" + piece.getColor() + ")";
        } else {
            return "No piece at this position";
        }
    }

    // Contributor : Farid
    // Method for debugging and checking piece and it's coordinate
    public void printPieceInfo(int x, int y) {
        System.out.println("Clicked on tile: " + x + ", " + y);
        System.out.println("Piece info: " + getPieceTypeAndColor(x, y));
    }

    // Contributor : Farid
    // Setter to set the selected piece coordinates
    public void setSelectedPiece(int x, int y) {
        selectedX = x;
        selectedY = y;
    }

    // Contributor : Farid
    // Getter to get the x-coordinate of the selected piece
    public int getSelectedX() {
        return selectedX;
    }

    // Contributor : Farid
    // Getter to get the y-coordinate of the selected piece
    public int getSelectedY() {
        return selectedY;
    }

    // Contributor : Farid
    // Getter to get victory msg for yellow
    public void getVictoryYellow() {
        board.displayYellowVictoryMsg();
    }

    // Contributor : Farid
    // Getter to get victory msg for blue
    public void getVictoryBlue() {
        board.displayBlueVictoryMsg();
    }

    // Contributor : Farid
    // Getter to get the identifier for player turn
    public void identifyPlayerTurn() {
        pieceLogic.setCurrentPlayer();
    }
}
