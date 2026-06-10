package main;

import core.ShapeManager;
import core.UMLController;
import ui.Canvas;
import ui.ToolBar;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        setTitle("UML Editor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 1. 初始化核心邏輯與資料 (Model & Mediator)
        ShapeManager shapeManager = new ShapeManager();
        Canvas canvas = new Canvas();
        UMLController controller = new UMLController(shapeManager, canvas);

        // 2. 將 Controller 注入 Canvas
        canvas.setup(controller, shapeManager);

        // 3. 建立並配置 UI 元件
        createMenuBar(controller);
        add(canvas, BorderLayout.CENTER);

        ToolBar toolBar = new ToolBar(controller);
        toolBar.setPreferredSize(new Dimension(120, 600));
        add(toolBar, BorderLayout.WEST);
    }

    private void createMenuBar(UMLController controller) {
        JMenuBar mb = new JMenuBar();
        JMenu edit = new JMenu("Edit");

        JMenuItem groupItem = new JMenuItem("Group");
        JMenuItem ungroupItem = new JMenuItem("Ungroup");
        JMenuItem labelItem = new JMenuItem("Label");

        // 將選單事件交給 Controller 處理
        groupItem.addActionListener(e -> controller.handleGroup());
        ungroupItem.addActionListener(e -> controller.handleUngroup());
        labelItem.addActionListener(e -> controller.handleCustomizeLabel());

        edit.add(groupItem);
        edit.add(ungroupItem);
        edit.add(labelItem);
        mb.add(edit);

        setJMenuBar(mb);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main editor = new Main();
            editor.setVisible(true);
        });
    }
}