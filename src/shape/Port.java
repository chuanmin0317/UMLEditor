package shape;

import java.awt.Color;
import java.awt.Graphics;

public class Port {
    private int x, y;
    private static final int SIZE = 14;

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x - SIZE/2, y - SIZE/2, SIZE, SIZE);
    }

    public int getX() { return x; }
    public int getY() { return y; }
}