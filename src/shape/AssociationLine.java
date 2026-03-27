package shape;

import java.awt.Color;
import java.awt.Graphics;

public class AssociationLine extends Line {
    public AssociationLine(Port startPort, Port endPort) {
        super(startPort, endPort);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(startPort.getX(), startPort.getY(), endPort.getX(), endPort.getY());
    }
}
