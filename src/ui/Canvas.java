package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import mode.Mode;
import shape.Shape;

public class Canvas extends JPanel implements ToolbarListener {
    private List<Shape> shapes = new ArrayList<>();
    private Mode currentMode;
    private ToolBar toolBar;
    private Rectangle selectionBox = null;

    public Canvas() {
        setBackground(Color.WHITE);

        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (currentMode != null) {
                    int x = e.getX();
                    int y = e.getY();
                    currentMode.mousePressed(x, y, Canvas.this);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentMode != null) {
                    int x = e.getX();
                    int y = e.getY();
                    currentMode.mouseDragged(x, y, Canvas.this);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentMode != null) {
                    int x = e.getX();
                    int y = e.getY();
                    currentMode.mouseReleased(x, y, Canvas.this);
                }
            }
        };

        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);

    }

    public void setToolBar(ToolBar toolBar) {
        this.toolBar = toolBar;
    }

    // Mode
    @Override
    public void onModeSelected(Mode mode) {
        this.currentMode = mode;
    }

    public void restorePreviousMode() {
        if (toolBar != null) {
            toolBar.resetButtonColor();
        }
    }

    public void setSelectionBox(int x1, int y1, int x2, int y2) {
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);
        int w = Math.abs(x1 - x2);
        int h = Math.abs(y1 - y2);
        selectionBox = new Rectangle(x, y, w, h);
    }

    public void removeSelectionBox() {
        selectionBox = null;
    }

    // Shape
    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape shape : shapes) {
            shape.draw(g);
        }

        if (selectionBox != null) {
            g.setColor(new Color(100, 149, 237, 50));
            g.fillRect(selectionBox.x, selectionBox.y, selectionBox.width, selectionBox.height);
            g.setColor((new Color(100, 149, 237)));
            g.drawRect(selectionBox.x, selectionBox.y, selectionBox.width, selectionBox.height);
        }
    }
}