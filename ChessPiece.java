
// ChessPiece.java
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

// Abstract class to represent chess piece and manage chess piece movement rules & highlight
public abstract class ChessPiece {
    private String imagePath;
    private String color;
    private String name;
    public boolean turnBackToggleYellow;
    public boolean turnBackToggleBlue;
    public boolean shouldRotateYellow;
    public boolean shouldRotateBlue;
    public boolean shouldRotateALL;

    // Constructor
    public ChessPiece(String name, String imagePath, String color, PieceController pieceControl) {
        this.imagePath = imagePath;
        this.color = color;
        this.name = name;
        this.turnBackToggleYellow = false;
        this.turnBackToggleBlue = false;
        this.shouldRotateYellow = false;
        this.shouldRotateBlue = false;
        this.shouldRotateALL = true;
    }

    // Contributor : Farid, Afiq
    // Getter to get shouldRotateYellow
    public boolean shouldRotateYellow() {
        return shouldRotateYellow;
    }

    // Contributor : Farid, Afiq
    // Getter to get shouldRotateBlue
    public boolean shouldRotateBlue() {
        return shouldRotateBlue;
    }

    // Contributor : Farid, Afiq
    // Method to toggle turnBackToggleYellow
    public void turnBackToggleYellow() {
        turnBackToggleYellow = !turnBackToggleYellow;
    }

    // Contributor : Farid, Afiq
    // Method to toggle turnBackToggleBlue
    public void turnBackToggleBlue() {
        turnBackToggleBlue = !turnBackToggleBlue;
    }

    // Contributor : Farid, Afiq
    // Setter to set shouldRotateYellow
    public void setShouldRotateYellow(boolean shouldRotate) {
        this.shouldRotateYellow = shouldRotate;
    }

    // Contributor : Farid, Afiq
    // Setter to set shouldRotateBlue
    public void setShouldRotateBlue(boolean shouldRotate) {
        this.shouldRotateBlue = shouldRotate;
    }

    // Contributor : Farid, Afiq
    // Setter to set shouldRotateBlue
    public void toggleRotationALL() {
        shouldRotateALL = !shouldRotateALL;
    }

    // Contributor : Farid
    // Getter to get piece imagepath
    public String getImagePath() {
        return imagePath;
    }

    // Contributor : Farid
    // Getter to get piece color
    public String getColor() {
        return color;
    }

    // Contributor : Farid
    // Getter to get piece type
    public String getType() {
        return name;
    }

    // Contributor : Afiq
    // Method to rotate the image by 180 degrees
    public Image rotateImage180Degrees(Image originalImage) {
        if (shouldRotateYellow || shouldRotateBlue) {
            int width = originalImage.getWidth(null);
            int height = originalImage.getHeight(null);

            BufferedImage rotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = rotatedImage.createGraphics();

            AffineTransform at = new AffineTransform();
            at.translate(width / 2, height / 2);
            at.rotate(Math.PI); // Rotate by 180 degrees
            at.translate(-width / 2, -height / 2);

            g2d.setTransform(at);
            g2d.drawImage(originalImage, 0, 0, null);
            g2d.dispose();

            return rotatedImage;
        } else {
            return originalImage;
        }
    }

    // Contributor : Farid, Afiq
    // Method to rotate the image by 180 degrees for ALL pieces
    public Image rotateImage180DegreesALL(Image originalImage) {
        if (shouldRotateALL) {
            int width = originalImage.getWidth(null);
            int height = originalImage.getHeight(null);

            BufferedImage rotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = rotatedImage.createGraphics();

            AffineTransform at = new AffineTransform();
            at.translate(width / 2, height / 2);
            at.rotate(Math.PI); // Rotate by 180 degrees
            at.translate(-width / 2, -height / 2);

            g2d.setTransform(at);
            g2d.drawImage(originalImage, 0, 0, null);
            g2d.dispose();

            return rotatedImage;
        } else {
            return originalImage;
        }
    }

    // Contributor : Farid
    // Abstract method for checking the validity of a move
    public abstract boolean isValidMove(int startX, int startY, int destX, int destY);

    // Contributor : Farid
    // Abstract method for highlighting valid moves
    public abstract boolean highlightMove(int startX, int startY, int destX, int destY);

    // =======================================================================================================
    // Contributor : Farid, Afiq
    // Point Piece
    // Yellow Point Piece
    public static class YellowPointPiece extends ChessPiece {
        private PieceController pieceControl;
        private boolean shouldMoveBackward;

        public YellowPointPiece(String imagePath, String color, PieceController pieceControl) {
            super("YellowPoint", imagePath, color, pieceControl);
            this.pieceControl = pieceControl;
            this.shouldMoveBackward = false;
        }

        @Override
        public boolean isValidMove(int startX, int startY, int destX, int destY) {

            // Check if the move is valid for a PointPiece
            if (getColor().equals("Yellow")) {
                // Check if the move is either 1 or 2 steps forward or backward
                if (((destY == startY - 1 || destY == startY - 2) && destX == startX && !shouldMoveBackward) ||
                        ((destY == startY + 1 || destY == startY + 2) && destX == startX && shouldMoveBackward)) {

                    // Check if the destination tile is within bounds
                    if (destY >= 0 && destY < 6) {

                        // Check if there is any piece in the path
                        int step = destY > startY ? 1 : -1;
                        for (int y = startY + step; y != destY; y += step) {
                            if (y >= 0 && y < 6 && pieceControl.getPiece(startX, y) != null) {
                                // There is a piece in the path, invalid move
                                return false;
                            }
                        }

                        // Check if the destination tile is empty or contains a piece of a different
                        // color
                        ChessPiece destinationPiece = pieceControl.getPiece(destX, destY);
                        if (destinationPiece == null || !destinationPiece.getColor().equals(getColor())) {
                            // If the piece reaches the first row, set shouldMoveBackward to true
                            if (destY == 0) {
                                shouldMoveBackward = true;
                                setShouldRotateYellow(true);
                            }
                            // If the piece reaches the last row, set shouldMoveBackward to true
                            else if (destY == 5) {
                                shouldMoveBackward = false;
                                setShouldRotateYellow(false);
                            }
                            return true;
                        } else {
                            // If the destination tile contains a piece of the same color
                            return false;
                        }
                    } else {
                        // If the destination tile is out of bounds or contains a piece of the same
                        // color
                        return false;
                    }
                } else {
                    // Invalid move: does not meet the conditions for a PointPiece move
                    return false;
                }
            }
            // Invalid color for the piece
            return false;
        }

        @Override
        public boolean highlightMove(int startX, int startY, int destX, int destY) {

            // Check if the move is valid for a PointPiece
            if (getColor().equals("Yellow")) {
                // Check if the move is either 1 or 2 steps forward or backward
                if (((destY == startY - 1 || destY == startY - 2) && destX == startX && !turnBackToggleYellow) ||
                        ((destY == startY + 1 || destY == startY + 2) && destX == startX && turnBackToggleYellow)) {

                    // Check if the destination tile is within bounds
                    if (destY >= 0 && destY < 6) {

                        // Check if there is any piece in the path
                        int step = destY > startY ? 1 : -1;
                        for (int y = startY + step; y != destY; y += step) {
                            if (y >= 0 && y < 6 && pieceControl.getPiece(startX, y) != null) {
                                // There is a piece in the path, invalid move
                                return false;
                            }
                        }

                        // Check if the destination tile is empty or contains a piece of a different
                        // color
                        ChessPiece destinationPiece = pieceControl.getPiece(destX, destY);

                        if (destinationPiece == null || !destinationPiece.getColor().equals(getColor())) {
                            return true;
                        } else {
                            // If the destination tile contains a piece of the same color
                            return false;
                        }

                    } else {
                        // If the destination tile is out of bounds or contains a piece of the same
                        // color
                        return false;
                    }
                } else {
                    // Invalid move: does not meet the conditions for a PointPiece move
                    return false;
                }
            }
            // Invalid color for the piece
            return false;
        }
    }

    // Contributor : Farid, Afiq
    // Blue Point Piece
    public static class BluePointPiece extends ChessPiece {
        private PieceController pieceControl;
        private boolean shouldMoveForward;

        public BluePointPiece(String imagePath, String color, PieceController pieceControl) {
            super("BluePoint", imagePath, color, pieceControl);
            this.shouldMoveForward = false;
            this.pieceControl = pieceControl;
        }

        @Override
        public boolean isValidMove(int startX, int startY, int destX, int destY) {

            // Check if the move is valid for the blue PointPiece
            if (getColor().equals("Blue")) {
                // Check if the move is either 1 or 2 steps forward or backward
                if (((destY == startY - 1 || destY == startY - 2) && destX == startX && shouldMoveForward) ||
                        ((destY == startY + 1 || destY == startY + 2) && destX == startX && !shouldMoveForward)) {

                    // Check if the destination tile is within bounds
                    if (destY >= 0 && destY < 6) {

                        // Check if there is any piece in the path
                        int step = destY > startY ? 1 : -1;
                        for (int y = startY + step; y != destY; y += step) {
                            if (y >= 0 && y < 6 && pieceControl.getPiece(startX, y) != null) {

                                // There is a piece in the path, invalid move
                                return false;
                            }
                        }

                        // Check if the destination tile is empty or contains a piece of a different
                        // color
                        ChessPiece destinationPiece = pieceControl.getPiece(destX, destY);
                        if (destinationPiece == null || !destinationPiece.getColor().equals(getColor())) {
                            // If the piece reaches the last row of , set shouldMoveForward to true
                            if (destY == 5) {
                                shouldMoveForward = true;
                                setShouldRotateBlue(true);
                            }
                            // If the piece reaches the first row of , set shouldMoveForward to false
                            else if (destY == 0) {
                                shouldMoveForward = false;
                                setShouldRotateBlue(false);
                            }
                            return true;
                        } else {
                            // If the destination tile contains a piece of the same color
                            return false;
                        }
                    } else {
                        // If the destination tile is out of bounds or contains a piece of the same
                        // color
                        return false;
                    }
                } else {
                    return false;
                }
            }
            // Invalid color for the piece
            return false;
        }

        @Override
        public boolean highlightMove(int startX, int startY, int destX, int destY) {

            // Check if the move is valid for the blue PointPiece
            if (getColor().equals("Blue")) {
                // Check if the move is either 1 or 2 steps forward or backward
                if (((destY == startY - 1 || destY == startY - 2) && destX == startX && turnBackToggleBlue) ||
                        ((destY == startY + 1 || destY == startY + 2) && destX == startX && !turnBackToggleBlue)) {

                    // Check if the destination tile is within bounds
                    if (destY >= 0 && destY < 6) {

                        // Check if there is any piece in the path
                        int step = destY > startY ? 1 : -1;
                        for (int y = startY + step; y != destY; y += step) {
                            if (y >= 0 && y < 6 && pieceControl.getPiece(startX, y) != null) {

                                // There is a piece in the path, invalid move
                                return false;
                            }
                        }

                        // Check if the destination tile is empty or contains a piece of a different
                        // color
                        ChessPiece destinationPiece = pieceControl.getPiece(destX, destY);
                        if (destinationPiece == null || !destinationPiece.getColor().equals(getColor())) {
                            return true;
                        } else {
                            // If the destination tile contains a piece of the same color
                            return false;
                        }

                    } else {
                        // If the destination tile is out of bounds or contains a piece of the same
                        // color
                        return false;
                    }
                } else {
                    // Invalid move: does not meet the conditions for a PointPiece move
                    return false;
                }
            }
            // Invalid color for the piece
            return false;
        }
    }

    // =======================================================================================================
    // Point Piece

    // =======================================================================================================
    // Contributor : Jia
    // Plus Piece
    // Yellow Plus Piece
    public static class YellowPlusPiece extends ChessPiece {
        private PieceController pieceControl;

        public YellowPlusPiece(String imagePath, String color, PieceController pieceControl) {
            super("YellowPlus", imagePath, color, pieceControl);
            this.pieceControl = pieceControl;
        }

        public boolean highlightMove(int startX, int startY, int destX, int destY) {
            return false;
        }

        @Override
        public boolean isValidMove(int startX, int startY, int destX, int destY) {
            // Check if the move is valid for the yellow PlusPiece
            if (getColor().equals("Yellow") && (destX == startX || destY == startY)) {

                // Check if the destination tile is within boundary
                if (destX >= 0 && destX < 7 && destY >= 0 && destY < 6) {

                    // Determine direction
                    int xDirection;
                    if (destX - startX > 0) {
                        xDirection = 1;
                    } else if (destX - startX < 0) {
                        xDirection = -1;
                    } else {
                        xDirection = 0;
                    }

                    int yDirection;
                    if (destY - startY > 0) {
                        yDirection = 1;
                    } else if (destY - startY < 0) {
                        yDirection = -1;
                    } else {
                        yDirection = 0;
                    }

                    // Check if there is any piece obstructing the path
                    for (int x = startX + xDirection, y = startY + yDirection; x != destX
                            || y != destY; x += xDirection, y += yDirection) {
                        if (x >= 0 && x < 7 && y >= 0 && y < 6
                                && pieceControl.getPiece(x, y) != null) {

                            // Piece is on the path, invalid move
                            return false;
                        }
                    }

                    // Check if the destination tile is empty or contains different color piece
                    ChessPiece destinationPiece = pieceControl.getPiece(destX, destY);
                    if (destinationPiece == null || !destinationPiece.getColor().equals(getColor())) {
                        return true;
                    } else {
                        // If the destination tile contains same color piece
                        return false;
                    }
                } else {
                    // If the destination tile is out of bounds or contains same color piece
                    return false;
                }
            }
            // Does not meet the conditions for a PlusPiece move
            return false;
        }
    }

    // Contributor : Jia
    // Blue Plus Piece
    public static class BluePlusPiece extends ChessPiece {
        private PieceController pieceControl;

        public BluePlusPiece(String imagePath, String color, PieceController pieceControl) {
            super("BluePlus", imagePath, color, pieceControl);
            this.pieceControl = pieceControl;
        }

        public boolean highlightMove(int startX, int startY, int destX, int destY) {
            return false;
        }

        @Override
        public boolean isValidMove(int startX, int startY, int destX, int destY) {
            // Check if the move is valid for the blue PlusPiece
            if (getColor().equals("Blue") && (destX == startX || destY == startY)) {

                // Check if the destination tile is within boundary
                if (destX >= 0 && destX < 7 && destY >= 0 && destY < 6) {

                    // Determine direction
                    int xDirection;
                    if (destX - startX > 0) {
                        xDirection = 1;
                    } else if (destX - startX < 0) {
                        xDirection = -1;
                    } else {
                        xDirection = 0;
                    }

                    int yDirection;
                    if (destY - startY > 0) {
                        yDirection = 1;
                    } else if (destY - startY < 0) {
                        yDirection = -1;
                    } else {
                        yDirection = 0;
                    }

                    // Check if there is any piece obstructing the path
                    for (int x = startX + xDirection, y = startY + yDirection; x != destX
                            || y != destY; x += xDirection, y += yDirection) {
                        if (x >= 0 && x < 7 && y >= 0 && y < 6
                                && pieceControl.getPiece(x, y) != null) {

                            // Piece is on the path, invalid move
                            return false;
                        }
                    }

                    // Check if the destination tile is empty or contains different color piece
                    ChessPiece destinationPiece = pieceControl.getPiece(destX, destY);
                    if (destinationPiece == null || !destinationPiece.getColor().equals(getColor())) {
                        return true;
                    } else {
                        // If the destination tile contains same color piece
                        return false;
                    }
                } else {
                    // If the destination tile is out of bounds or contains same color piece
                    return false;
                }
            }
            // Does not meet the conditions for a PlusPiece move
            return false;
        }
    }
    // =======================================================================================================
    // Plus Piece

    // =======================================================================================================
    // Contributor : Jia
    // Sun Piece
    // Yellow Sun Piece
    public static class YellowSunPiece extends ChessPiece {
        private PieceController pieceControl;

        public YellowSunPiece(String imagePath, String color, PieceController pieceControl) {
            super("YellowSun", imagePath, color, pieceControl);
            this.pieceControl = pieceControl;
        }

        public boolean highlightMove(int startX, int startY, int destX, int destY) {
            return false;
        }

        @Override
        public boolean isValidMove(int startX, int startY, int destX, int destY) {
            // Check if the move is valid for the yellow SunPiece
            if (getColor().equals("Yellow") && isOneStepMove(startX, startY, destX, destY)) {

                // Check if the destination tile is within boundary
                if (destX >= 0 && destX < 7 && destY >= 0 && destY < 6) {

                    // Check if the destination tile is empty or contains different color piece
                    ChessPiece destinationPiece = pieceControl.getPiece(destX, destY);
                    if (destinationPiece == null || !destinationPiece.getColor().equals(getColor())) {
                        return true;
                    } else {
                        // If the destination tile contains same color piece
                        return false;
                    }
                } else {
                    // If the destination tile is out of boundary
                    return false;
                }
            }
            // Does not meet the conditions for a SunPiece move
            return false;
        }

        // Check if the move is one step in any direction
        private boolean isOneStepMove(int startX, int startY, int destX, int destY) {
            return Math.abs(destX - startX) <= 1 && Math.abs(destY - startY) <= 1;
        }
    }

    // Contributor : Jia
    // Blue Sun Piece
    public static class BlueSunPiece extends ChessPiece {
        private PieceController pieceControl;

        public BlueSunPiece(String imagePath, String color, PieceController pieceControl) {
            super("BlueSun", imagePath, color, pieceControl);
            this.pieceControl = pieceControl;
        }

        public boolean highlightMove(int startX, int startY, int destX, int destY) {
            return false;
        }

        @Override
        public boolean isValidMove(int startX, int startY, int destX, int destY) {
            // Check if the move is valid for the blue SunPiece
            if (getColor().equals("Blue") && isOneStepMove(startX, startY, destX, destY)) {

                // Check if the destination tile is within boundary
                if (destX >= 0 && destX < 7 && destY >= 0 && destY < 6) {

                    // Check if the destination tile is empty or contains different color piece
                    ChessPiece destinationPiece = pieceControl.getPiece(destX, destY);
                    if (destinationPiece == null || !destinationPiece.getColor().equals(getColor())) {
                        return true;
                    } else {
                        // If the destination tile contains same color piece
                        return false;
                    }
                } else {
                    // If the destination tile is out of boundary
                    return false;
                }
            }
            // Does not meet the conditions for a SunPiece move
            return false;
        }

        // Check if the move is one step in any direction
        private boolean isOneStepMove(int startX, int startY, int destX, int destY) {
            return Math.abs(destX - startX) <= 1 && Math.abs(destY - startY) <= 1;
        }
    }
    // =======================================================================================================
    // Sun Piece

    // =======================================================================================================
    // Contributor : Jia
    // Hourglass Piece
    // Yellow Hourglass Piece
    public static class YellowHourglassPiece extends ChessPiece {
        private PieceController pieceControl;

        public YellowHourglassPiece(String imagePath, String color, PieceController pieceControl) {
            super("YellowHourglass", imagePath, color, pieceControl);
            this.pieceControl = pieceControl;
        }

        public boolean highlightMove(int startX, int startY, int destX, int destY) {
            return false;
        }

        @Override
        public boolean isValidMove(int startX, int startY, int destX, int destY) {
            // Check if the move is valid for a Yellow Hourglass Piece
            if (getColor().equals("Yellow")) {

                // Check if the destination tile is within boundary
                if (destX >= 0 && destX < 7 && destY >= 0 && destY < 6) {

                    // Check if the destination tile is empty or contains different color piece
                    ChessPiece destinationPiece = pieceControl.getPiece(destX, destY);
                    if (destinationPiece == null || !destinationPiece.getColor().equals(getColor())) {

                        int dx = Math.abs(destX - startX);
                        int dy = Math.abs(destY - startY);

                        // Check if the move is a 3x2 L shape
                        return (dx == 1 && dy == 2) || (dx == 2 && dy == 1);
                    } else {
                        // If the destination tile contains same color piece
                        return false;
                    }
                } else {
                    // If the destination tile is out of boundary
                    return false;
                }
            }
            // Does not meet the conditions for a Yellow Hourglass Piece move
            return false;
        }
    }

    // Contributor : Jia
    // Blue Hourglass Piece
    public static class BlueHourglassPiece extends ChessPiece {
        private PieceController pieceControl;

        public BlueHourglassPiece(String imagePath, String color, PieceController pieceControl) {
            super("BlueHourglass", imagePath, color, pieceControl);
            this.pieceControl = pieceControl;
        }

        public boolean highlightMove(int startX, int startY, int destX, int destY) {
            return false;
        }

        @Override
        public boolean isValidMove(int startX, int startY, int destX, int destY) {
            // Check if the move is valid for a Yellow Hourglass Piece
            if (getColor().equals("Blue")) {

                // Check if the destination tile is within boundary
                if (destX >= 0 && destX < 7 && destY >= 0 && destY < 6) {

                    // Check if the destination tile is empty or contains different color piece
                    ChessPiece destinationPiece = pieceControl.getPiece(destX, destY);
                    if (destinationPiece == null || !destinationPiece.getColor().equals(getColor())) {

                        int dx = Math.abs(destX - startX);
                        int dy = Math.abs(destY - startY);

                        // Check if the move is a 3x2 L shape
                        return (dx == 1 && dy == 2) || (dx == 2 && dy == 1);
                    } else {
                        // If the destination tile contains same color piece
                        return false;
                    }
                } else {
                    // If the destination tile is out of boundary
                    return false;
                }
            }
            // Does not meet the conditions for a Blue Hourglass Piece move
            return false;
        }
    }
    // =======================================================================================================
    // Hourglass Piece

    // =======================================================================================================
    // Contributor : Jia
    // Time Piece
    // Yellow Time Piece
    public static class YellowTimePiece extends ChessPiece {
        private PieceController pieceControl;

        public YellowTimePiece(String imagePath, String color, PieceController pieceControl) {
            super("YellowTime", imagePath, color, pieceControl);
            this.pieceControl = pieceControl;
        }

        public boolean highlightMove(int startX, int startY, int destX, int destY) {
            return false;
        }

        @Override
        public boolean isValidMove(int startX, int startY, int destX, int destY) {
            if (Math.abs(destX - startX) == Math.abs(destY - startY)) {

                // Check if the destination tile is within boundary
                if (destX >= 0 && destX < 7 && destY >= 0 && destY < 6) {

                    // Set xDirection and yDirection
                    int xDirection = Integer.compare(destX, startX);
                    int yDirection = Integer.compare(destY, startY);

                    // Check if there is any piece obstructing the path
                    for (int x = startX + xDirection, y = startY + yDirection; x != destX
                            || y != destY; x += xDirection, y += yDirection) {
                        if (x >= 0 && x < 7 && y >= 0 && y < 6 && pieceControl.getPiece(x, y) != null) {
                            // Piece is on the path, invalid move
                            return false;
                        }
                    }

                    // Check if the destination tile is empty or contains a different color piece
                    ChessPiece destinationPiece = pieceControl.getPiece(destX, destY);
                    if (destinationPiece == null || !destinationPiece.getColor().equals(getColor())) {
                        return true;
                    } else {
                        // If the destination tile contains a piece of the same color
                        return false;
                    }
                } else {
                    // If the destination tile is out of bounds
                    return false;
                }
            }
            // Does not meet the conditions for a diagonal move
            return false;
        }
    }

    // Contributor : Jia
    // Blue Time Piece
    public static class BlueTimePiece extends ChessPiece {
        private PieceController pieceControl;

        public BlueTimePiece(String imagePath, String color, PieceController pieceControl) {
            super("BlueTime", imagePath, color, pieceControl);
            this.pieceControl = pieceControl;
        }

        public boolean highlightMove(int startX, int startY, int destX, int destY) {
            return false;
        }

        @Override
        public boolean isValidMove(int startX, int startY, int destX, int destY) {
            // Check if the move is valid for the blue TimePiece
            if (Math.abs(destX - startX) == Math.abs(destY - startY)) {

                // Check if the destination tile is within boundary
                if (destX >= 0 && destX < 7 && destY >= 0 && destY < 6) {

                    // Set xDirection and yDirection
                    int xDirection = Integer.compare(destX, startX);
                    int yDirection = Integer.compare(destY, startY);

                    // Check if there is any piece obstructing the path
                    for (int x = startX + xDirection, y = startY + yDirection; x != destX
                            || y != destY; x += xDirection, y += yDirection) {
                        if (x >= 0 && x < 7 && y >= 0 && y < 6 && pieceControl.getPiece(x, y) != null) {
                            // Piece is on the path, invalid move
                            return false;
                        }
                    }
                    // Check if the destination tile is empty or contains a different color piece
                    ChessPiece destinationPiece = pieceControl.getPiece(destX, destY);
                    if (destinationPiece == null || !destinationPiece.getColor().equals(getColor())) {
                        return true;
                    } else {
                        // If the destination tile contains a piece of the same color
                        return false;
                    }
                } else {
                    // If the destination tile is out of bounds
                    return false;
                }
            }
            // Does not meet the conditions for a diagonal move
            return false;
        }
    }
    // =======================================================================================================
    // Time Piece
}