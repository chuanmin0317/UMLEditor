package mode;

import shape.Rect;
import ui.Canvas;

import javax.swing.*;

public class RectMode implements  Mode {
    private int startX, startY;
    private boolean isDragging = false;

    @Override
    public void mousePressed(int x, int y, Canvas canvas) {
        startX = x;
        startY = y;
        isDragging = true;
    }

    @Override
    public void mouseDragged(int x, int y, Canvas canvas) {
        if (isDragging) {

        }
    }

    @Override
    public void mouseReleased(int x, int y, Canvas canvas) {
        if (isDragging) {
            int drawX = Math.min(startX, x);
            int drawY = Math.min(startY, y);

            int width = Math.abs(x - startX);
            int height = Math.abs(y - startY);

            Rect rect = new Rect(drawX, drawY, width, height);
            canvas.addShape(rect);
            isDragging = false;
        }

        canvas.repaint();
        canvas.restorePreviousMode();
    }
}
