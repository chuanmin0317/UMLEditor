package mode;

public class ModeFactory {
    public static Mode createMode(String modeName) {
        return switch (modeName) {
            case "Select" -> new SelectMode();
            case "Rect" -> new BasicObjectMode("Rect");
            case "Oval" -> new BasicObjectMode("Oval");
            case "Association" -> new ConnectionMode("Association");
            case "Composition" -> new ConnectionMode("Composition");
            case "Generalization" -> new ConnectionMode("Generalization");
            default -> new SelectMode();
        };
    }
}