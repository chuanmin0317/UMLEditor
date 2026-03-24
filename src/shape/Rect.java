package shape;

import java.awt.*;

public class Rect extends BasicObject {

    public Rect(int x, int y, int width, int height) {
        super(x, y, width, height);
        createPorts(8);
        updatePorts();
    }

    @Override
    protected void updatePorts() {
        ports[0].setLocation(x, y);
        ports[1].setLocation(x + width / 2, y);
        ports[2].setLocation(x + width, y);
        ports[3].setLocation(x + width, y + height / 2);
        ports[4].setLocation(x + width, y + height);
        ports[5].setLocation(x + width / 2, y + height);
        ports[6].setLocation(x, y + height);
        ports[7].setLocation(x, y + height / 2);
    }

    @Override
    protected void drawShape(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
    }
}
