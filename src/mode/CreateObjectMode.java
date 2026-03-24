package mode;

import shape.Oval;
import shape.Rect;
import shape.Shape;
import ui.Canvas;

import java.awt.event.MouseEvent;

public class CreateObjectMode implements Mode {
    private String shapeType;
    private Canvas canvas;

    public CreateObjectMode(String shapeType, Canvas canvas) {
        this.shapeType = shapeType;
        this.canvas = canvas;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        Shape newShape = null;
        if (shapeType.equals("Rect")) {
            newShape = new Rect(x, y, 100, 80);
        } else if (shapeType.equals("Oval")) {
            newShape = new Oval(x, y, 100, 80);
        }

        if (newShape != null) {
            canvas.addShape(newShape);
            canvas.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }
}
