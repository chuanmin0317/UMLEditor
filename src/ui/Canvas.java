package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import mode.Mode;
import shape.Shape;
import shape.Rect;
import shape.Oval;


public class Canvas extends JPanel {
    private List<Shape> shapes = new ArrayList<>();

    private Mode currentMode;

    public Canvas() {
        setBackground(Color.WHITE);

        setOpaque(true);

        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (currentMode != null) {
                    currentMode.mousePressed(e);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentMode != null) {
                    currentMode.mouseDragged(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentMode != null) {
                    currentMode.mouseReleased(e);
                }
            }
        };

        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);

    }

    public void setCurrentMode(Mode mode) {
        this.currentMode = mode;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape shape : shapes) {
            shape.draw(g);
        }
    }
}