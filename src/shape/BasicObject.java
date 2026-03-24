package shape;

import java.awt.Graphics;

public abstract class BasicObject extends Shape {
    protected  Port[] ports;

    public BasicObject(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    protected void createPorts(int portCount) {
        ports = new Port[portCount];
        for (int i = 0; i < portCount; i++) {
            ports[i] = new Port();
        }
    }

    protected abstract  void updatePorts();

    @Override
    public void draw(Graphics g) {
        drawShape(g);

        if (isSelected && ports != null) {
            updatePorts();
            for (Port p : ports) {
                p.draw(g);
            }
        }
    }

    protected abstract void drawShape(Graphics g);
}
