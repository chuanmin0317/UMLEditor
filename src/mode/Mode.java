package mode;
import core.ShapeManager;
import core.UMLController;

public interface Mode {
    void mousePressed(int x, int y, ShapeManager sm, UMLController controller);
    void mouseDragged(int x, int y, ShapeManager sm, UMLController controller);
    void mouseReleased(int x, int y, ShapeManager sm, UMLController controller);
}