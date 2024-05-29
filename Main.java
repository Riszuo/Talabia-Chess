// Main.java

// The main class to handle the initialization of every other classes
public class Main {
    public static void main(String[] args) {

        ChessPieceFactory pieceFactory = new ChessPieceFactory(); // Initialize Piece Factory

        ChessPieceLogic pieceLogic = new ChessPieceLogic(); // Initialize Piece Logic

        PieceController pieceControl = new PieceController(pieceFactory, pieceLogic); // Initialize Piece Control

        ButtonsController buttons = new ButtonsController(pieceControl); // Initialize Buttons Control

        TileClickController tileClick = new TileClickController(); // Initialize Tile Control

        pieceFactory.setPieceControl(pieceControl);
        pieceLogic.setPieceControl(pieceControl);
        pieceControl.setPieceLogic(pieceLogic);

        tileClick.setPieceControl(pieceControl);
        tileClick.setButtonsController(buttons);
        tileClick.setPieceLogic(pieceLogic);

        buttons.setPieceFactory(pieceFactory);
        buttons.setPieceLogic(pieceLogic);
        buttons.setTileClick(tileClick);

        new MainMenu(buttons); // Initialize Main Menu
    }
}
