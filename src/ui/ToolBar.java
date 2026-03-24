package ui;

import mode.CreateObjectMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBar extends JPanel {
    private JButton[] buttons;
    private String[] buttonNames = {
            "Select", "Association", "Generalization", "Composition", "Rect", "Oval"
    };

    private Canvas canvas;

    public ToolBar(Canvas canvas) {
        this.canvas = canvas;

        setLayout(new GridLayout(6, 1, 0, 0));
        setBackground(Color.DARK_GRAY);

        buttons = new JButton[6];

        for (int i = 0; i < buttonNames.length; i++) {
            JButton btn = new JButton(buttonNames[i]);
            btn.setBackground(Color.WHITE);
            btn.setFocusPainted(false);

            btn.addActionListener(new ButtonClickListener());
            buttons[i] = btn;
            add(btn);
        }
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();

            for (JButton btn : buttons) {
                btn.setBackground(Color.WHITE);
                btn.setForeground(Color.BLACK);
            }

            clickedButton.setBackground(Color.BLACK);
            clickedButton.setForeground(Color.WHITE);

            String modeName = clickedButton.getText();
            if (modeName.equals("Rect") || modeName.equals("Oval")) {
                canvas.setCurrentMode(new CreateObjectMode(modeName, canvas));
            } else {
                canvas.setCurrentMode(null);
            }

        }
    }
}
