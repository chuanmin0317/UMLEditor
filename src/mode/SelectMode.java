package mode;

import shape.Shape;
import ui.Canvas;

import java.awt.*;
import java.util.List;

public class SelectMode implements Mode {
    private boolean isSelectingArea = false;
    private boolean isMoving = false;

    private int startX, startY;
    private int lastX, lastY;

    @Override
    public void mousePressed(int x, int y, Canvas canvas) {
        startX = x; startY = y;
        lastX = x; lastY = y;

        isSelectingArea = false;
        isMoving = false;

        List<Shape> shapes = canvas.getShapes();
        Shape clickShape = null;

        for (int i = shapes.size() - 1; i >= 0; i--) {
            if (shapes.get(i).contains(x, y)) {
                clickShape = shapes.get(i);
                break;
            }
        }

        if (clickShape != null) {
            isMoving = true;

            // 如果本來沒被選取則取消所有選取，並選取所選的Shape，反之則不做動作
            if (!clickShape.isSelected()) {
                for (Shape s : shapes) {
                    s.setSelected(false);
                }
                clickShape.setSelected(true);
            }

            isSelectingArea = false;
        } else {
            for (Shape s : shapes) {
                s.setSelected(false);
            }

            isSelectingArea = true;
        }

        canvas.repaint();
    }

    @Override
    public void mouseDragged(int x, int y, Canvas canvas) {
        if (isMoving) {
            int dx = x - lastX;
            int dy = y - lastY;

            for (Shape s : canvas.getShapes()) {
                if (s.isSelected()) {
                    s.setLocation(s.getX() + dx, s.getY() + dy);
                }
            }

            lastX = x;
            lastY = y;
            canvas.repaint();

        } else if (isSelectingArea) {
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

            // 檢查那些Shape完全落於此處
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

        isMoving = false;
    }
}
