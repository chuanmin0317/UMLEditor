package shape;

import java.awt.*;

public class CompositionLine extends Line {
    public CompositionLine(Port startPort, Port endPort) {
        super(startPort, endPort);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new java.awt.BasicStroke((2.0f)));
        g2d.setColor(Color.GRAY);

        int x1 = startPort.getX();
        int y1 = startPort.getY();
        int x2 = endPort.getX();
        int y2 = endPort.getY();

        int diamondWidth = 10;
        int diamondLength = 15;

        double angle = Math.atan2(y2 - y1, x2 - x1);

        int x3 = (int) (x2 - diamondLength * Math.cos(angle) - diamondWidth * Math.sin(angle));
        int y3 = (int) (y2 - diamondLength * Math.sin(angle) + diamondWidth * Math.cos(angle));

        int x4 = (int) (x2 - 2 * diamondLength * Math.cos(angle));
        int y4 = (int) (y2 - 2 * diamondLength * Math.sin(angle));

        int x5 = (int) (x2 - diamondLength * Math.cos(angle) + diamondWidth * Math.sin(angle));
        int y5 = (int) (y2 - diamondLength * Math.sin(angle) - diamondWidth * Math.cos(angle));

        // 畫主線段
        g2d.drawLine(x1, y1, x4, y4);

        // 畫菱形
        Polygon diamond = new Polygon();
        diamond.addPoint(x2, y2);
        diamond.addPoint(x3, y3);
        diamond.addPoint(x4, y4);
        diamond.addPoint(x5, y5);

        // 畫空心菱形
        g2d.setColor(Color.WHITE);
        g2d.fillPolygon(diamond);
        g2d.setColor(Color.GRAY);
        g.drawPolygon(diamond);

        g2d.setStroke(new java.awt.BasicStroke((1.0f)));
    }
}
