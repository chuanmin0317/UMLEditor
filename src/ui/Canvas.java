package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import mode.Mode;
import shape.Shape;
import shape.Group;

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

    // Group
    public void groupSelectedShapes() {
        Group newGroup = new Group();
        List<Shape> selectedShapes = new ArrayList<>();

        for (Shape s : shapes) {
            if (s.isSelected()) {
                selectedShapes.add(s);
            }
        }

        if (selectedShapes.size() > 1) {
            for (Shape s : selectedShapes) {
                s.setSelected(false);
                newGroup.addShape(s);
                shapes.remove(s);
            }
            newGroup.setSelected(true);
            shapes.add(newGroup);
            repaint();
        }
    }

    public void ungroupSelectedShape() {
        List<Shape> groupsToUngroup = new ArrayList<>();

        for (Shape s : shapes) {
            if (s.isSelected() && s instanceof Group) {
                groupsToUngroup.add(s);
            }
        }

        if (groupsToUngroup.size() != 1) return;

        for (Shape g : groupsToUngroup) {
            Group group = (Group) g;
            shapes.remove(group);

            shapes.addAll(group.getChildShapes());
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        for (Shape shape : shapes) {
            shape.draw(g2d);
        }

        if (selectionBox != null) {
            g2d.setColor((new Color(20, 40, 80)));

            float[] dashPattern = {5f, 5f};
            g2d.setStroke(new java.awt.BasicStroke(
                    1.5f,
                    java.awt.BasicStroke.CAP_BUTT,
                    java.awt.BasicStroke.JOIN_MITER,
                    10.0f,
                    dashPattern,
                    0.0f
            ));

            g2d.drawRect(selectionBox.x, selectionBox.y, selectionBox.width, selectionBox.height);
            g2d.setStroke(new java.awt.BasicStroke());
        }
    }
}