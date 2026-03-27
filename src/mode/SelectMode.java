package mode;

import shape.Shape;
import ui.Canvas;

import java.awt.*;
import java.util.List;

public class SelectMode implements Mode {
    private boolean isSelectingArea = false;
    private int startX, startY;

    @Override
    public void mousePressed(int x, int y, Canvas canvas) {
        List<Shape> shapes = canvas.getShapes();
        Shape clickShape = null;

        for (int i = shapes.size() - 1; i >= 0; i--) {
            if (shapes.get(i).contains(x, y)) {
                clickShape = shapes.get(i);
                break;
            }
        }

        if (clickShape != null) {
            for (Shape s : shapes) {
                s.setSelected(false);
            }

            clickShape.setSelected(true);
            isSelectingArea = false;
        } else {
            for (Shape s : shapes) {
                s.setSelected(false);
            }

            isSelectingArea = true;
            startX = x;
            startY = y;
        }

        canvas.repaint();
    }

    @Override
    public void mouseDragged(int x, int y, Canvas canvas) {
        if (isSelectingArea) {
            canvas.setSelectionBox(startX, startY, x, y);
            canvas.repaint();
        }
    }

    @Override
    public void mouseReleased(int x, int y, Canvas canvas) {
        if (isSelectingArea) {
            int minX = Math.min(startX, x);
            int minY = Math.min(startY, y);
            int width = Math.abs(x - startX);
            int height = Math.abs(y - startY);

            Rectangle selectionRect = new Rectangle(minX, minY, width, height);

            for (Shape s : canvas.getShapes()) {
                Rectangle shapeRect = new Rectangle(s.getX(), s.getY(), s.getWidth(), s.getHeight());

                if (selectionRect.contains(shapeRect)) {
                    s.setSelected(true);
                }
            }

            canvas.removeSelectionBox();
            isSelectingArea = false;
            canvas.repaint();
        }
    }
}
