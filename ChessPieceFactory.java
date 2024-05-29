
// ChessPieceFactory.java
// Model ( Factory Design Pattern )
import java.util.function.Supplier;
import java.util.HashMap;
import java.util.Map;

// Class to handle creation of pieces
public class ChessPieceFactory {
    private final Map<String, Supplier<ChessPiece>> yellowPieceCreators = new HashMap<>();
    private final Map<String, Supplier<ChessPiece>> bluePieceCreators = new HashMap<>();
    private PieceController pieceControl;

    // Constructor
    public ChessPieceFactory() {
        // Initialize piece creators
        initializePieceCreators();
    }

    // Contributor : Farid
    // Setter to inject the PieceController
    public void setPieceControl(PieceController pieceControl) {
        this.pieceControl = pieceControl;
    }

    // Contributor : Farid
    // Method to initialize piece creators for yellow and blue pieces
    private void initializePieceCreators() {
        // Initialize yellow piece creators
        yellowPieceCreators.put("YellowHourglass",
                () -> new ChessPiece.YellowHourglassPiece(Resource.getYellowHourglassImagePath(),
                        Resource.YellowColor(), pieceControl));
        yellowPieceCreators.put("YellowPoint", () -> new ChessPiece.YellowPointPiece(Resource.getYellowPointImagePath(),
                Resource.YellowColor(), pieceControl));
        yellowPieceCreators.put("YellowPlus", () -> new ChessPiece.YellowPlusPiece(Resource.getYellowPlusImagePath(),
                Resource.YellowColor(), pieceControl));
        yellowPieceCreators.put("YellowTime", () -> new ChessPiece.YellowTimePiece(Resource.getYellowTimeImagePath(),
                Resource.YellowColor(), pieceControl));
        yellowPieceCreators.put("YellowSun", () -> new ChessPiece.YellowSunPiece(Resource.getYellowSunImagePath(),
                Resource.YellowColor(), pieceControl));

        // Initialize blue piece creators
        bluePieceCreators.put("BlueHourglass",
                () -> new ChessPiece.BlueHourglassPiece(Resource.getBlueHourglassImagePath(), Resource.BlueColor(),
                        pieceControl));
        bluePieceCreators.put("BluePoint", () -> new ChessPiece.BluePointPiece(Resource.getBluePointImagePath(),
                Resource.BlueColor(), pieceControl));
        bluePieceCreators.put("BluePlus", () -> new ChessPiece.BluePlusPiece(Resource.getBluePlusImagePath(),
                Resource.BlueColor(), pieceControl));
        bluePieceCreators.put("BlueTime", () -> new ChessPiece.BlueTimePiece(Resource.getBlueTimeImagePath(),
                Resource.BlueColor(), pieceControl));
        bluePieceCreators.put("BlueSun",
                () -> new ChessPiece.BlueSunPiece(Resource.getBlueSunImagePath(), Resource.BlueColor(), pieceControl));
    }

    // Contributor : Farid
    // Method to create chess pieces based on type and color
    public ChessPiece createChessPiece(String type, String color) {
        // Initialize piece creator
        Supplier<ChessPiece> creator = getPieceCreator(color, type);

        if (creator != null) {
            return creator.get();
        } else {
            return null;
        }
    }

    // Contributor : Farid
    // Method to get the appropriate piece creator based on color and type
    private Supplier<ChessPiece> getPieceCreator(String color, String type) {
        // Convert the first letter to uppercase
        color = color.substring(0, 1).toUpperCase() + color.substring(1);

        // Call yellow piece creator if it's "Yellow", else call the blue piece creator
        Map<String, Supplier<ChessPiece>> pieceCreators = "Yellow".equals(color) ? yellowPieceCreators
                : bluePieceCreators;
        return pieceCreators.get(type);
    }
}
