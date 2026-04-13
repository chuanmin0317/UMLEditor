package main;

import ui.ToolBar;
import ui.Canvas;

import javax.swing.*;
import java.awt.*;

public class UMLEditor extends JFrame {

    private final Canvas canvas;

    // constructor
    public UMLEditor() {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createMenuBar();

        canvas = new Canvas();
        add(canvas, BorderLayout.CENTER);

        ToolBar toolBar = new ToolBar(canvas);
        toolBar.setPreferredSize(new Dimension(120, 600));
        add(toolBar, BorderLayout.WEST);

        canvas.setToolBar(toolBar);
    }

    private void createMenuBar() {
        JMenuBar mb = new JMenuBar();

        JMenu file = new JMenu("File");

        JMenu edit = new JMenu("Edit");
        JMenuItem groupItem = new JMenuItem("Group");
        JMenuItem ungroupIdem = new JMenuItem("Ungroup");
        JMenuItem labelItem = new JMenuItem("Label");

        groupItem.addActionListener(e -> canvas.groupSelectedShapes());
        ungroupIdem.addActionListener(e -> canvas.ungroupSelectedShape());

        labelItem.addActionListener(e -> canvas.customizeLabelStyle());

        edit.add(groupItem);
        edit.add(ungroupIdem);
        edit.add(labelItem);

        mb.add(file);
        mb.add(edit);

        setJMenuBar(mb);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UMLEditor editor = new UMLEditor();
            editor.setVisible(true);
        });
    }
}