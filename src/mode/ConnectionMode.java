package mode;

import core.ShapeManager;
import core.UMLController;
import shape.*;

import java.util.List;

public class ConnectionMode implements Mode {
    private final String lineType;
    private BasicObject startShape = null;
    private Port startPort = null;

    public ConnectionMode(String lineType) {
        this.lineType = lineType;
    }

    @Override
    public void mousePressed(int x, int y, ShapeManager sm, UMLController controller) {
        List<Shape> shapes = sm.getShapes();
        startShape = null;
        startPort = null;
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape shape = shapes.get(i);
            if (shape.contains(x, y) && shape instanceof BasicObject) {
                startShape = (BasicObject) shape;
                startPort = startShape.getClosestPort(x, y);
                break;
            }
        }
    }

    @Override
    public void mouseDragged(int x, int y, ShapeManager sm, UMLController controller) {}

    @Override
    public void mouseReleased(int x, int y, ShapeManager sm, UMLController controller) {
        if (startShape != null && startPort != null) {
            BasicObject endShape = null;
            Port endPort = null;
            List<Shape> shapes = sm.getShapes();
            for (int i = shapes.size() -1; i >= 0; i--) {
                Shape shape = shapes.get(i);
                if (shape.contains(x, y) && shape instanceof BasicObject) {
                    endShape = (BasicObject) shape;
                    break;
                }
            }

            if (endShape != null && endShape != startShape) {
                endPort = endShape.getClosestPort(x, y);
                Line newLine = switch (lineType) {
                    case "Association" -> new AssociationLine(startPort, endPort);
                    case "Generalization" -> new GeneralizationLine(startPort, endPort);
                    case "Composition" -> new CompositionLine(startPort, endPort);
                    default -> null;
                };
                if (newLine != null) sm.addShape(newLine);
            }
        }
        controller.requestRepaint();
    }
}