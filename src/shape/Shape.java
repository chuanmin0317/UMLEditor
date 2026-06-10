package shape;

import java.awt.*;

public abstract class Shape {
    protected int depth;
    protected boolean isSelected = false;
    protected boolean isHovered = false;

    public Shape() {
        this.depth = 0;
    }

    // 核心行為：繪圖與點擊偵測
    public abstract void draw(Graphics g);
    public abstract boolean contains(int mx, int my);

    // 抽象化座標與尺寸
    public abstract int getX();
    public abstract int getY();
    public abstract int getWidth();
    public abstract int getHeight();

    // 讓圖形可以被移動
    public abstract void setLocation(int x, int y);

    // 共用的狀態 Getter/Setter
    public void setSelected(boolean selected) { this.isSelected = selected; }
    public boolean isSelected() { return isSelected; }

    public void setHovered(boolean hovered) { this.isHovered = hovered; }
    public boolean isHovered() { return isHovered; }

    public void setDepth(int depth) { this.depth = depth; }
    public int getDepth() { return depth; }
}