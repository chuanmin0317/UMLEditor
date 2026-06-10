package ui;

import core.ShapeManager;
import core.UMLController;
import shape.Shape;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Canvas extends JPanel {
    private UMLController controller;
    private ShapeManager shapeManager;

    public Canvas() {
        setBackground(Color.WHITE);
    }

    public void setup(UMLController controller, ShapeManager shapeManager) {
        this.controller = controller;
        this.shapeManager = shapeManager;

        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { controller.handleMousePressed(e.getX(), e.getY()); }
            @Override
            public void mouseDragged(MouseEvent e) { controller.handleMouseDragged(e.getX(), e.getY()); }
            @Override
            public void mouseReleased(MouseEvent e) { controller.handleMouseReleased(e.getX(), e.getY()); }
            @Override
            public void mouseMoved(MouseEvent e) {
                boolean needsRepaint = false;
                Shape topHoveredShape = null;
                for (int i = shapeManager.getShapes().size() - 1; i >= 0; i--) {
                    Shape s = shapeManager.getShapes().get(i);
                    if (s.contains(e.getX(), e.getY())) {
                        topHoveredShape = s;
                        break;
                    }
                }
                for (Shape s : shapeManager.getShapes()) {
                    boolean wasHovered = s.isHovered();
                    boolean isNowHovered = (s == topHoveredShape);
                    if (wasHovered != isNowHovered) {
                        s.setHovered(isNowHovered);
                        needsRepaint = true;
                    }
                }
                if (needsRepaint) controller.requestRepaint();
            }
        };
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (shapeManager == null) return;
        Graphics2D g2d = (Graphics2D) g;

        for (Shape shape : shapeManager.getShapes()) {
            shape.draw(g2d);
        }

        Rectangle selectionBox = shapeManager.getSelectionBox();
        if (selectionBox != null) {
            g2d.setColor(new Color(20, 40, 80));
            float[] dashPattern = {5f, 5f};
            g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0.0f));
            g2d.drawRect(selectionBox.x, selectionBox.y, selectionBox.width, selectionBox.height);
            g2d.setStroke(new BasicStroke());
        }
    }
}