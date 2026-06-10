package core;

import shape.Shape;
import shape.Group;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class ShapeManager {
    private final List<Shape> shapes = new ArrayList<>();
    private Rectangle selectionBox = null;

    public void addShape(Shape shape) {
        shapes.add(shape);
        updateDepths();
    }

    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void clearAllSelection() {
        for (Shape s : shapes) {
            s.setSelected(false);
        }
    }

    public void bringToFront(Shape shape) {
        if (shapes.remove(shape)) {
            shapes.add(shape);
            updateDepths();
        }
    }

    public void updateDepths() {
        int n = shapes.size();
        for (int i = 0; i < n; i++) {
            shapes.get(i).setDepth(n - 1 - i);
        }
    }

    public Rectangle getSelectionBox() { return selectionBox; }
    public void setSelectionBox(Rectangle box) { this.selectionBox = box; }

    public void groupSelectedShapes() {
        Group newGroup = new Group();
        List<Shape> selectedShapes = new ArrayList<>();
        for (Shape s : shapes) {
            if (s.isSelected()) selectedShapes.add(s);
        }
        if (selectedShapes.size() > 1) {
            for (Shape s : selectedShapes) {
                s.setSelected(false);
                newGroup.addShape(s);
                shapes.remove(s);
            }
            newGroup.setSelected(true);
            shapes.add(newGroup);
        }
    }

    public void ungroupSelectedShape() {
        List<Shape> groupsToUngroup = new ArrayList<>();
        for (Shape s : shapes) {
            if (s.isSelected() && s instanceof Group) groupsToUngroup.add(s);
        }
        if (groupsToUngroup.size() != 1) return;
        for (Shape g : groupsToUngroup) {
            Group group = (Group) g;
            shapes.remove(group);
            shapes.addAll(group.getChildShapes());
        }
    }
}