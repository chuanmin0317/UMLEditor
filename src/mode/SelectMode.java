package mode;

import core.ShapeManager;
import core.UMLController;
import shape.BasicObject;
import shape.Port;
import shape.Shape;

import java.awt.Rectangle;
import java.util.List;

public class SelectMode implements Mode {
    private boolean isSelectingArea = false;
    private boolean isMoving = false;
    private boolean isResizing = false;
    private boolean resizeX = false;
    private boolean resizeY = false;
    private BasicObject resizingShape = null;
    private int startX, startY, lastX, lastY, fixedX = 0, fixedY = 0;

    @Override
    public void mousePressed(int x, int y, ShapeManager sm, UMLController controller) {
        startX = x; startY = y; lastX = x; lastY = y;
        isSelectingArea = false; isMoving = false; isResizing = false;
        resizingShape = null;
        List<Shape> shapes = sm.getShapes();

        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape s = shapes.get(i);
            if (s.isSelected() && s instanceof BasicObject) {
                Port port = ((BasicObject) s).isOnPort(x, y);
                if (port != null) {
                    isResizing = true;
                    resizingShape = (BasicObject) s;
                    int sx = s.getX(), sy = s.getY(), sw = s.getWidth(), sh = s.getHeight();
                    int cx = sx + sw / 2, cy = sy + sh / 2;
                    if (port.getX() < cx - 5) { resizeX = true; fixedX = sx + sw; } else if (port.getX() > cx + 5) { resizeX = true; fixedX = sx; } else { resizeX = false; }
                    if (port.getY() < cy - 5) { resizeY = true; fixedY = sy + sh; } else if (port.getY() > cy + 5) { resizeY = true; fixedY = sy; } else { resizeY = false; }
                    return;
                }
            }
        }

        Shape clickShape = null;
        for (int i = shapes.size() - 1; i >= 0; i--) {
            if (shapes.get(i).contains(x, y)) {
                clickShape = shapes.get(i);
                break;
            }
        }

        if (clickShape != null) {
            isMoving = true;
            sm.bringToFront(clickShape);
            if (!clickShape.isSelected()) {
                sm.clearAllSelection();
                clickShape.setSelected(true);
            }
        } else {
            sm.clearAllSelection();
            isSelectingArea = true;
        }
        controller.requestRepaint();
    }

    @Override
    public void mouseDragged(int x, int y, ShapeManager sm, UMLController controller) {
        if (isResizing && resizingShape != null) {
            int newWidth = resizingShape.getWidth(), newHeight = resizingShape.getHeight();
            int newX = resizingShape.getX(), newY = resizingShape.getY();

            if(resizeX) {
                newWidth = Math.abs(x - fixedX);
                newX = Math.min(x, fixedX);
                if (newWidth < 40) { newWidth = 40; newX = (x < fixedX) ? fixedX - 40 : fixedX; }
            }
            if (resizeY) {
                newHeight = Math.abs(y - fixedY);
                newY = Math.min(y, fixedY);
                if (newHeight < 40) { newHeight = 40; newY = (y < fixedY) ? fixedY - 40 : fixedY; }
            }
            resizingShape.setLocation(newX, newY);
            resizingShape.setSize(newWidth, newHeight);
        } else if (isMoving) {
            int dx = x - lastX;
            int dy = y - lastY;
            for (Shape s : sm.getShapes()) {
                if (s.isSelected()) s.setLocation(s.getX() + dx, s.getY() + dy);
            }
            lastX = x;
            lastY = y;
        } else if (isSelectingArea) {
            sm.setSelectionBox(new Rectangle(Math.min(startX, x), Math.min(startY, y), Math.abs(x - startX), Math.abs(y - startY)));
        }
        controller.requestRepaint();
    }

    @Override
    public void mouseReleased(int x, int y, ShapeManager sm, UMLController controller) {
        if (isSelectingArea) {
            Rectangle selectionRect = sm.getSelectionBox();
            if (selectionRect != null) {
                for (Shape s : sm.getShapes()) {
                    Rectangle shapeRect = new Rectangle(s.getX(), s.getY(), s.getWidth(), s.getHeight());
                    if (selectionRect.contains(shapeRect)) s.setSelected(true);
                }
            }
            sm.setSelectionBox(null);
        }
        isSelectingArea = false;
        isMoving = false;
        isResizing = false;
        resizingShape = null;
        controller.requestRepaint();
    }
}