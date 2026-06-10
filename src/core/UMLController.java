package core;

import mode.Mode;
import mode.ModeFactory;
import mode.SelectMode;
import shape.BasicObject;
import shape.Oval;
import shape.Rect;
import shape.Shape;
import ui.Canvas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UMLController {
    private final ShapeManager shapeManager;
    private final Canvas canvas;
    private Mode currentMode;

    public UMLController(ShapeManager shapeManager, Canvas canvas) {
        this.shapeManager = shapeManager;
        this.canvas = canvas;
        this.currentMode = new SelectMode();
    }

    public Canvas getCanvas() { return canvas; }

    public void setMode(String modeName) {
        this.currentMode = ModeFactory.createMode(modeName);
        shapeManager.clearAllSelection();
        shapeManager.setSelectionBox(null);
        requestRepaint();
    }

    // 處理從 ToolBar 拖曳或點擊放開建立圖形的邏輯 (預設大小)
    public void handleDropShape(String shapeType, int centerX, int centerY) {
        int FIXED_WIDTH = 100;
        int FIXED_HEIGHT = 80;
        int x = centerX - FIXED_WIDTH / 2;
        int y = centerY - FIXED_HEIGHT / 2;

        Shape newShape = switch (shapeType) {
            case "Rect" -> new Rect(x, y, FIXED_WIDTH, FIXED_HEIGHT);
            case "Oval" -> new Oval(x, y, FIXED_WIDTH, FIXED_HEIGHT);
            default -> null;
        };

        if (newShape != null) {
            shapeManager.addShape(newShape);
            requestRepaint();
        }
    }

    // 滑鼠事件轉發給當前 Mode
    public void handleMousePressed(int x, int y) { currentMode.mousePressed(x, y, shapeManager, this); }
    public void handleMouseDragged(int x, int y) { currentMode.mouseDragged(x, y, shapeManager, this); }
    public void handleMouseReleased(int x, int y) { currentMode.mouseReleased(x, y, shapeManager, this); }

    // 選單功能
    public void handleGroup() { shapeManager.groupSelectedShapes(); requestRepaint(); }
    public void handleUngroup() { shapeManager.ungroupSelectedShape(); requestRepaint(); }

    public void handleCustomizeLabel() {
        List<Shape> selectedShapes = new ArrayList<>();
        for (Shape s : shapeManager.getShapes()) {
            if (s.isSelected()) selectedShapes.add(s);
        }

        if (selectedShapes.size() == 1 && selectedShapes.getFirst() instanceof BasicObject obj) {

            JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
            panel.add(new JLabel("Name"));
            JTextField nameField = new JTextField(obj.getName());
            panel.add(nameField);

            panel.add(new JLabel("Color"));
            String[] colorOptions = {"gray", "yellow", "red", "green", "blue", "white"};
            JComboBox<String> colorBox = new JComboBox<>(colorOptions);
            panel.add(colorBox);

            int result = JOptionPane.showConfirmDialog(canvas, panel, "Customize Label Style", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                obj.setName(nameField.getText());
                String selectedColor = (String) colorBox.getSelectedItem();
                switch (selectedColor) {
                    case "yellow" -> obj.setBgColor(Color.YELLOW);
                    case "red" -> obj.setBgColor(Color.RED);
                    case "green" -> obj.setBgColor(Color.GREEN);
                    case "blue" -> obj.setBgColor(Color.BLUE);
                    case "white" -> obj.setBgColor(Color.WHITE);
                    case null -> {}
                    default -> obj.setBgColor(Color.LIGHT_GRAY);
                }
                requestRepaint();
            }
        }
    }

    public void requestRepaint() { canvas.repaint(); }
}