package shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class GeneralizationLine extends Line{
    public GeneralizationLine(Port startPort, Port endPort) {
        super(startPort, endPort);
    }

    @Override
    public void draw(Graphics g) {
        int x1 = startPort.getX();
        int y1 = startPort.getY();
        int x2 = endPort.getX();
        int y2 = endPort.getY();

        int arrowWidth = 10;
        int arrowLength = 15;

        double angle = Math.atan2(y2 - y1, x2 - x1);

        int x3 = (int) (x2 - arrowLength * Math.cos(angle) - arrowWidth * Math.sin(angle));
        int y3 = (int) (y2 - arrowLength * Math.sin(angle) + arrowWidth * Math.cos(angle));
        int x4 = (int) (x2 - arrowLength * Math.cos(angle) + arrowWidth * Math.sin(angle));
        int y4 = (int) (y2 - arrowLength * Math.sin(angle) - arrowWidth * Math.cos(angle));

        g.setColor(Color.BLACK);
        g.drawLine(x1, y1, (int)(x2 - arrowLength * Math.cos(angle)), (int)(y2 - arrowLength * Math.sin(angle)));

        Polygon arrow = new Polygon();
        arrow.addPoint(x2, y2);
        arrow.addPoint(x3, y3);
        arrow.addPoint(x4, y4);

        g.setColor(Color.WHITE);
        g.fillPolygon(arrow);
        g.setColor(Color.BLACK);
        g.drawPolygon(arrow);
    }
}
