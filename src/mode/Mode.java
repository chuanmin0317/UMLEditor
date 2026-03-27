package mode;

import ui.Canvas;

import java.awt.event.MouseEvent;

public interface Mode {
    void mousePressed(int x, int y, Canvas canvas);
    void mouseDragged(int x, int y, Canvas canvas);
    void mouseReleased(int x, int y, Canvas canvas);
}