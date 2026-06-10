package mode;

import core.ShapeManager;
import core.UMLController;

public class BasicObjectMode implements Mode {
    private final String shapeType;

    public BasicObjectMode(String shapeType) {
        this.shapeType = shapeType;
    }

    @Override
    public void mousePressed(int x, int y, ShapeManager sm, UMLController controller) { }

    @Override
    public void mouseDragged(int x, int y, ShapeManager sm, UMLController controller) { }

    @Override
    public void mouseReleased(int x, int y, ShapeManager sm, UMLController controller) {
        controller.handleDropShape(shapeType, x, y);
    }
}