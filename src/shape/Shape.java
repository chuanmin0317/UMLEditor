package shape;

import java.awt.*;

public abstract class Shape {
    protected int x, y, width, height;
    protected int depth;
    protected boolean isSelected = false;

    public Shape(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.depth = 0;
    }

    public abstract void draw(Graphics g);

    public int getX() { return x; }

    public int getY() { return  y; }

    public int getWidth() { return width; }

    public int getHeight() {return height; }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    public boolean contains(int mx, int my) {
        return mx >= x && mx <= x + width && my >= y && my <= y + height;
    }
}