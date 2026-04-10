package shape;

import java.awt.Graphics;

public abstract class Line extends Shape {
    protected Port startPort;
    protected Port endPort;

    public Line(Port startPort, Port endPort) {
        super(0, 0, 0, 0);
        this.startPort = startPort;
        this.endPort = endPort;
    }

    @Override
    public boolean contains(int mx, int my) {
        return false;
    }
}
