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

    protected abstract void updatePorts();

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

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);

        if (ports != null) {
            updatePorts();
        }
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        if (ports != null) {
            updatePorts();
        }
    }

    public Port getClosestPort(int mx, int my) {
        updatePorts();

        Port closest = null;
        double minDistance = Double.MAX_VALUE;

        for (Port p : ports) {
            double dist = Math.pow(p.getX() - mx, 2) + Math.pow(p.getY() - my, 2);
            if (dist < minDistance) {
                minDistance = dist;
                closest = p;
            }
        }

        return closest;
    }

    public boolean isOnPort(int mx, int my) {
        if (!isSelected || ports == null) return false;

        int size = 14;
        int half = size / 2;

        for (Port p : ports) {
            if (mx >= p.getX() - half && mx <= p.getX() + half &&
                    my >= p.getY() - half && my <= p.getY() + half) {
                return true;
            }
        }
        return false;
    }

    protected abstract void drawShape(Graphics g);
}
