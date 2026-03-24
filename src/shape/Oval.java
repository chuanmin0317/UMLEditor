package shape;

import java.awt.*;

public class Oval extends BasicObject {

    public Oval(int x, int y, int width, int height) {
        super(x, y, width, height);
        createPorts(4);
        updatePorts();
    }

    @Override
    public void updatePorts() {
        ports[0].setLocation(x + width / 2, y);
        ports[1].setLocation(x + width, y + height / 2);
        ports[2].setLocation(x + width / 2, y + height);
        ports[3].setLocation(x, y + height / 2);
    }

    @Override
    public void drawShape(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawOval(x, y, width, height);
    }
}
