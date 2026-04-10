package shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Group extends Shape {
    private List<Shape> childShapes = new ArrayList<>();

    public Group() {
        super(0, 0, 0, 0);
    }

    public void addShape(Shape shape) {
        childShapes.add(shape);
        updateBounds();
    }

    public List<Shape> getChildShapes() {
        return childShapes;
    }

    private void updateBounds() {
        if (childShapes.isEmpty()) return;

        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        for (Shape s : childShapes) {
            minX = Math.min(minX, s.getX());
            minY = Math.min(minY, s.getY());
            maxX = Math.max(maxX, s.getX() + s.getWidth());
            maxY = Math.max(maxY, s.getY() + s.getHeight());
        }

        this.x = minX;
        this.y = minY;
        this.width = maxX - minX;
        this.height = maxY - minY;
    }

    // move shape
    @Override
    public void setLocation(int newX, int newY) {
        int dx = newX - this.x;
        int dy = newY - this.y;

        this.x = newX;
        this.y = newY;

        for (Shape s : childShapes) {
            s.setLocation(s.getX() + dx, s.getY() + dy);
        }
    }

    @Override
    public void draw(Graphics g) {
        for (Shape s : childShapes) {
            s.draw(g);
        }

        if (isSelected) {
            Graphics2D g2d = (Graphics2D) g;
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
            g2d.drawRect(x, y, width, height);
            g2d.setStroke(new java.awt.BasicStroke());
        }
    }
}
