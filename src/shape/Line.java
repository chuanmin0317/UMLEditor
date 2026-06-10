package shape;

import java.awt.*;
import java.awt.geom.Line2D;

public abstract class Line extends Shape {
    protected Port startPort;
    protected Port endPort;
    private static final double TOLERANCE = 5.0;

    public Line(Port startPort, Port endPort) {
        super();
        this.startPort = startPort;
        this.endPort = endPort;
    }

    // Line 的座標與寬高是由連線兩端動態決定的
    @Override
    public int getX() { return Math.min(startPort.getX(), endPort.getX()); }

    @Override
    public int getY() { return Math.min(startPort.getY(), endPort.getY()); }

    @Override
    public int getWidth() { return Math.abs(startPort.getX() - endPort.getX()); }

    @Override
    public int getHeight() { return Math.abs(startPort.getY() - endPort.getY()); }

    @Override
    public void setLocation(int x, int y) {
    }

    @Override
    public boolean contains(int mx, int my) {
        if (startPort == null || endPort == null) return false;
        double distance = Line2D.ptSegDist(
                startPort.getX(), startPort.getY(),
                endPort.getX(), endPort.getY(),
                mx, my
        );
        return distance <= TOLERANCE;
    }

    protected void drawPorts(Graphics g) {
        if (isHovered) {
            if (startPort != null) startPort.draw(g);
            if (endPort != null) endPort.draw(g);
        }
    }
}