
// ChessTile.java
import javax.swing.JButton;

// Class to representing a tile/button on the chessboard
public class ChessTile extends JButton {
    private int xCoordinate, yCoordinate;

    // Constructor
    public ChessTile(int x, int y) {
        // Initialize the coordinates of the tile
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    // Contributor : Farid
    // Getter for the x-coordinate of the tile
    public int getXCoordinate() {
        return xCoordinate;
    }

    // Contributor : Farid
    // Getter for the y-coordinate of the tile
    public int getYCoordinate() {
        return yCoordinate;
    }

    // Contributor : Farid
    // Method to set the background color of the tile
    public void setTileColor(java.awt.Color color) {
        setBackground(color);
    }
}
