package shape;

import java.awt.*;

public class AssociationLine extends Line {
    public AssociationLine(Port startPort, Port endPort) {
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

        // 畫主線段
        g.drawLine(x1, y1, x2, y2);

        int arrowWidth = 7;
        int arrowLength = 15;

        double angle = Math.atan2(y2 - y1, x2 - x1);

        int x3 = (int) (x2 - arrowLength * Math.cos(angle) - arrowWidth * Math.sin(angle));
        int y3 = (int) (y2 - arrowLength * Math.sin(angle) + arrowWidth * Math.cos(angle));
        int x4 = (int) (x2 - arrowLength * Math.cos(angle) + arrowWidth * Math.sin(angle));
        int y4 = (int) (y2 - arrowLength * Math.sin(angle) - arrowWidth * Math.cos(angle));

        // 畫出箭頭的兩端
        g.drawLine(x2, y2, x3, y3);
        g.drawLine(x2, y2, x4, y4);

        g2d.setStroke(new java.awt.BasicStroke((1.0f)));
    }
}
