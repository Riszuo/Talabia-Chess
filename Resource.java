
// Resource.java
import java.awt.*;

// Contributor : Farid
// Class to handle the resources of the game to make everything simpler and more tidy
public class Resource {
    private static final String IMAGE_PATH_PREFIX = "images/";

    public static String getYellowPlusImagePath() {
        return IMAGE_PATH_PREFIX + "PlusYellow.png";
    }

    public static String getYellowSunImagePath() {
        return IMAGE_PATH_PREFIX + "SunYellow.png";
    }

    public static String getYellowHourglassImagePath() {
        return IMAGE_PATH_PREFIX + "HourglassYellow.png";
    }

    public static String getYellowTimeImagePath() {
        return IMAGE_PATH_PREFIX + "TimeYellow.png";
    }

    public static String getYellowPointImagePath() {
        return IMAGE_PATH_PREFIX + "PointYellow.png";
    }

    public static String getBluePlusImagePath() {
        return IMAGE_PATH_PREFIX + "PlusBlue.png";
    }

    public static String getBlueSunImagePath() {
        return IMAGE_PATH_PREFIX + "SunBlue.png";
    }

    public static String getBlueHourglassImagePath() {
        return IMAGE_PATH_PREFIX + "HourglassBlue.png";
    }

    public static String getBlueTimeImagePath() {
        return IMAGE_PATH_PREFIX + "TimeBlue.png";
    }

    public static String getBluePointImagePath() {
        return IMAGE_PATH_PREFIX + "PointBlue.png";
    }

    // Get yellow color
    public static String YellowColor() {
        return "Yellow";
    }

    // Get blue color
    public static String BlueColor() {
        return "Blue";
    }

    // Method to get the highlight color
    public static Color highlightColor() {
        return Color.CYAN;
    }

    // Method to get the color white
    public static Color changeColorWhite() {
        return Color.WHITE;
    }
}
