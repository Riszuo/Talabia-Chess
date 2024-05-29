
// TileClickController.java
import java.awt.Component;

// Class to handle tile function and control
public class TileClickController {
    private PieceController pieceControl;
    private ChessPieceLogic pieceLogic;
    private ButtonsController buttons;
    private ChessTile selectedTile;
    private BoardView board;

    // Contributor : Farid
    // Setter to set ChessPieceLogic
    public void setPieceLogic(ChessPieceLogic pieceLogic) {
        this.pieceLogic = pieceLogic;
    }

    // Contributor : Farid
    // Setter to set PieceController
    public void setPieceControl(PieceController pieceControl) {
        this.pieceControl = pieceControl;
    }

    // Contributor : Farid
    // Setter to set BoardView
    public void setPieceBoard(BoardView board) {
        this.board = board;
    }

    // Contributor : Farid
    // Setter to set ButtonsController
    public void setButtonsController(ButtonsController buttons) {
        this.buttons = buttons;
    }

    // Contributor : Farid, Rui
    // Handle a tile click event
    public void handleTileClick(ChessTile tile) {

        // Declare x and y from the selected piece coordinate
        int x = tile.getXCoordinate();
        int y = tile.getYCoordinate();

        // Initialize piece
        ChessPiece piece = pieceControl.getPiece(x, y);

        // No piece is selected
        if (pieceControl.getSelectedX() == -1 && pieceControl.getSelectedY() == -1) {
            // This will always be executed since instantiated SelectedCoordinate = [-1,-1]

            // Set the current tile coordinate as 'Selected Piece' ( Originally [-1 ,-1] )
            pieceControl.setSelectedPiece(x, y);

            // Print selected tile coordinate for debugging
            pieceControl.printPieceInfo(x, y);

            // Highlight the selected tile
            tile.setTileColor(Resource.highlightColor());

            // Stores a reference to the clicked tile in the
            selectedTile = tile;

            // If the piece that is selected is at the final row
            if (piece instanceof ChessPiece.YellowPointPiece && (y == 0 || y == 5)) {
                piece.turnBackToggleYellow();
            } else if (piece instanceof ChessPiece.BluePointPiece && (y == 0 || y == 5)) {
                piece.turnBackToggleBlue();
            }

            // Highlight the available moves of the selected piece
            if (piece != null) {
                highlightValidMoves(selectedTile);
            }

            // This else statement will execute after a piece already been selected and the
            // player click on another tile afterwards
        } else {

            // Print selected tile coordinate for debugging
            pieceControl.printPieceInfo(x, y);

            // Declaring the coordinate of the original position of the piece
            int startX = pieceControl.getSelectedX();
            int startY = pieceControl.getSelectedY();

            // Move the piece
            pieceControl.movePiece(startX, startY, x, y); // Move the piece (from where we click to where we want to go)

            // Reset back the tile colors to normal
            resetTileColors();

            // Check if the SunPiece has been captured
            if (pieceLogic.isSunPieceCaptured()) {
                // Reset the game if one of the sun piece is missing
                buttons.resetGame();

                // Reset the selected piece and tile color to default
                pieceControl.setSelectedPiece(-1, -1);
                selectedTile.setTileColor(Resource.changeColorWhite());
            } else {
                // Reset the selected piece and tile color to default
                pieceControl.setSelectedPiece(-1, -1);
                selectedTile.setTileColor(Resource.changeColorWhite());

                // Set back current player
                pieceLogic.setCurrentPlayer();
            }
        }
    }

    // Contributor : Rui
    // Method to highlight valid moves for the selected piece
    public void highlightValidMoves(ChessTile tile) {
        int startX = pieceControl.getSelectedX();
        int startY = pieceControl.getSelectedY();
        ChessPiece selectedPiece = pieceControl.getPiece(startX, startY);

        for (int x = 0; x <= 6; x++) {
            for (int y = 0; y <= 5; y++) {
                if (selectedPiece instanceof ChessPiece.YellowPointPiece
                        || selectedPiece instanceof ChessPiece.BluePointPiece) {
                    if (selectedPiece.highlightMove(startX, startY, x, y)
                            && pieceLogic.isCorrectPlayersTurn(selectedPiece)) {
                        ChessTile validMoveTile = getTileAtCoordinates(x, y);
                        validMoveTile.setTileColor(Resource.highlightColor());
                    }
                } else if (!(selectedPiece instanceof ChessPiece.YellowPointPiece)
                        || !(selectedPiece instanceof ChessPiece.BluePointPiece)) {
                    if (selectedPiece.isValidMove(startX, startY, x, y)
                            && pieceLogic.isCorrectPlayersTurn(selectedPiece)) {
                        ChessTile validMoveTile = getTileAtCoordinates(x, y);
                        validMoveTile.setTileColor(Resource.highlightColor());
                    }
                }
            }
        }
    }

    // Contributor : Rui
    // Method to highlight valid moves for the selected piece
    public ChessTile getTileAtCoordinates(int x, int y) {
        Component[] components = board.getFrame().getComponents();
        for (Component component : components) {
            if (component instanceof ChessTile) {
                ChessTile tile = (ChessTile) component;
                if (tile.getXCoordinate() == x && tile.getYCoordinate() == y) {
                    return tile;
                }
            }
        }
        return null; // Tile not found
    }

    // Contributor : Rui
    // Method to reset tile colors
    public void resetTileColors() {
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 5; j++) {
                ChessTile tile = getTileAtCoordinates(i, j);
                if (tile != null) {
                    tile.setTileColor(Resource.changeColorWhite());
                }
            }
        }
    }
}
