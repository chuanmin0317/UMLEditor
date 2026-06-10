package ui;

import core.UMLController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ToolBar extends JPanel {
    private final JButton[] buttons;
    private final UMLController controller;
    private JButton currentButton;
    private JButton previousButton;

    public ToolBar(UMLController controller) {
        this.controller = controller;
        setLayout(new GridLayout(6, 1, 0, 0));
        setBackground(Color.DARK_GRAY);

        String[] buttonNames = {"Select", "Association", "Generalization", "Composition", "Rect", "Oval"};
        buttons = new JButton[buttonNames.length];

        for (int i = 0; i < buttonNames.length; i++) {
            JButton btn = new JButton(buttonNames[i]);
            btn.setBackground(Color.WHITE);
            btn.setFocusPainted(false);

            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    JButton clickedButton = (JButton) e.getSource();
                    String modeName = clickedButton.getText();

                    // 如果不是 Rect 或 Oval，則記住這個穩定模式
                    if (!modeName.equals("Rect") && !modeName.equals("Oval")) {
                        previousButton = clickedButton;
                    }
                    updateButtonUI(clickedButton);
                    controller.setMode(modeName);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    JButton btn = (JButton) e.getSource();
                    String modeName = btn.getText();

                    if (modeName.equals("Rect") || modeName.equals("Oval")) {
                        Canvas canvas = controller.getCanvas();
                        Point pt = SwingUtilities.convertPoint(btn, e.getPoint(), canvas);

                        // 有拖曳到畫布內就建立圖形
                        if (pt.x >= 0 && pt.y >= 0 && pt.x < canvas.getWidth() && pt.y < canvas.getHeight()) {
                            controller.handleDropShape(modeName, pt.x, pt.y);
                        }

                        // 無論如何，放開後自動彈回前一個穩定模式
                        if (previousButton != null) {
                            updateButtonUI(previousButton);
                            controller.setMode(previousButton.getText());
                        }
                    }
                }
            });
            buttons[i] = btn;
            add(btn);
        }

        currentButton = buttons[0];
        previousButton = buttons[0];
        updateButtonUI(currentButton);
    }

    private void updateButtonUI(JButton activeBtn) {
        for (JButton btn : buttons) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
        }
        if (activeBtn != null) {
            activeBtn.setBackground(Color.BLACK);
            activeBtn.setForeground(Color.WHITE);
            currentButton = activeBtn;
        }
    }
}