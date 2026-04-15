package shape;

import java.awt.*;

public abstract class BasicObject extends Shape {
    protected  Port[] ports;
    protected String name = "";
    protected Color bgColor = Color.LIGHT_GRAY;

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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public void drawName(java.awt.Graphics g) {
        if (name != null && !name.isEmpty()) {
            // 取得字體的量測工具
            java.awt.FontMetrics fm = g.getFontMetrics();
            int stringWidth = fm.stringWidth(name);
            int stringAscent = fm.getAscent();

            int testX = x + (width - stringWidth) / 2;
            int textY = y + (height + stringAscent) / 2 - 2;

            g.setColor(Color.BLACK);
            g.drawString(name, testX, textY);

        }
    }

    // 用於找到最近的Port 來連接線條
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

    // 用於確保是滑鼠在Port 上要進行圖形縮放
    public Port isOnPort(int mx, int my) {
        if (!isSelected || ports == null) return null;

        int size = 14;
        int half = size / 2;

        for (Port p : ports) {
            if (mx >= p.getX() - half && mx <= p.getX() + half &&
                    my >= p.getY() - half && my <= p.getY() + half) {
                return p;
            }
        }
        return null;
    }

    protected abstract void drawShape(Graphics g);
}
