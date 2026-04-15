package mode;

import shape.BasicObject;
import shape.Port;
import shape.Shape;
import ui.Canvas;

import java.awt.*;
import java.awt.image.renderable.RenderableImage;
import java.util.List;

public class SelectMode implements Mode {
    private boolean isSelectingArea = false;
    private boolean isMoving = false;
    private boolean isResizing = false;
    private boolean resizeX = false;
    private boolean resizeY = false;

    private Shape resizingShape = null;

    private int startX, startY;
    private int lastX, lastY;
    private int fixedX = 0;
    private int fixedY = 0;

    @Override
    public void mousePressed(int x, int y, Canvas canvas) {
        startX = x; startY = y;
        lastX = x; lastY = y;

        isSelectingArea = false;
        isMoving = false;
        isResizing = false;

        List<Shape> shapes = canvas.getShapes();

        resizingShape = null;

        // resize obj
        for (int i = shapes.size() - 1; i >=0; i--) {
            Shape s = shapes.get(i);
            if (s.isSelected() && s instanceof BasicObject) {
                Port port = ((BasicObject) s).isOnPort(x, y);
                if (port != null) {
                    isResizing = true;
                    resizingShape = s;

                    // 找出固定不動的對角點
                    int sx = s.getX(), sy = s.getY();
                    int sw = s.getWidth(), sh = s.getHeight();
                    int cx = sx + sw / 2, cy = sy + sh / 2;

                    // 判斷X軸
                    if (port.getX() < cx - 5) {
                        resizeX = true; fixedX = sx + sw;
                    } else if (port.getX() > cx + 5) {
                        resizeX = true; fixedX = sx;
                    } else {
                        resizeX = false;
                    }

                    // 判斷Y軸
                    if (port.getY() < cy - 5) {
                        resizeY = true; fixedY = sy + sh;
                    } else if (port.getY() > cy + 5) {
                        resizeY = true; fixedY = sy;
                    } else {
                        resizeY = false;
                    }

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

        // moving obj or selection Box
        if (clickShape != null) {
            isMoving = true;

            canvas.bringToFront(clickShape);
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
        if (isResizing && resizingShape != null) {
            int newX = resizingShape.getX();
            int newY = resizingShape.getY();
            int newWidth = resizingShape.getWidth();
            int newHeight = resizingShape.getHeight();

            if(resizeX) {
                newWidth = Math.abs(x - fixedX);
                newX = Math.min(x, fixedX);
                if (newWidth < 40) {
                    newWidth = 40;
                    newX = (x < fixedX) ? fixedX - 40 : fixedX;
                }
            }

            if (resizeY) {
                newHeight = Math.abs(y - fixedY);
                newY = Math.min(y, fixedY);
                if (newHeight < 40) {
                    newHeight = 40;
                    newY = (y < fixedY) ? fixedY - 40 : fixedY;
                }
            }
            resizingShape.setLocation(newX, newY);
            resizingShape.setSize(newWidth, newHeight);
            canvas.repaint();

        } else if (isMoving) {
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
            canvas.repaint();
        }

        isSelectingArea = false;
        isMoving = false;
        isResizing = false;
        resizingShape = null;
    }
}
