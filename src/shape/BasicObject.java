package shape;

import java.awt.*;

public abstract class BasicObject extends Shape {
    // 把座標和大小移到這裡
    protected int x, y, width, height;
    protected Port[] ports;
    protected String name = "";
    protected Color bgColor = Color.LIGHT_GRAY;

    public BasicObject(int x, int y, int width, int height) {
        super(); // 呼叫無參數的 Shape 建構子
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // 實作 Shape 要求的 Getter
    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    @Override public int getWidth() { return width; }
    @Override public int getHeight() { return height; }

    @Override
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
        if (ports != null) updatePorts();
    }

    // BasicObject 專屬的改變大小方法
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        if (ports != null) updatePorts();
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
        if ((isSelected || isHovered) && ports != null) {
            updatePorts();
            for (Port p : ports) {
                p.draw(g);
            }
        }
    }

    @Override
    public boolean contains(int mx, int my) {
        return mx >= x && mx <= x + width && my >= y && my <= y + height;
    }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }
    public void setBgColor(Color bgColor) { this.bgColor = bgColor; }
    public Color getBgColor() { return bgColor; }

    public void drawName(java.awt.Graphics g) {
        if (name != null && !name.isEmpty()) {
            java.awt.FontMetrics fm = g.getFontMetrics();
            int stringWidth = fm.stringWidth(name);
            int stringAscent = fm.getAscent();
            int testX = x + (width - stringWidth) / 2;
            int textY = y + (height + stringAscent) / 2 - 2;
            g.setColor(Color.BLACK);
            g.drawString(name, testX, textY);
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