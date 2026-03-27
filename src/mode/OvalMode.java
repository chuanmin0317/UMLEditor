package mode;

import shape.Oval;
import ui.Canvas;

public class OvalMode implements Mode {
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

            if (width == 0) width = 100;
            if (height == 0) height = 80;

            Oval oval = new Oval(drawX, drawY, width, height);
            canvas.addShape(oval);

            isDragging = false;
        }

        canvas.repaint();
        canvas.restorePreviousMode();
    }
}