package ui;

import mode.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBar extends JPanel {
    private final JButton[] buttons;
    private final ToolbarListener listener;
    private JButton currentButton, previousButton;

    public ToolBar(ToolbarListener listener) {
        this.listener = listener;

        setLayout(new GridLayout(6, 1, 0, 0));
        setBackground(Color.DARK_GRAY);

        buttons = new JButton[6];

        String[] buttonNames = {
                "Select", "Association", "Generalization", "Composition", "Rect", "Oval"
        };
        for (int i = 0; i < buttonNames.length; i++) {
            JButton btn = new JButton(buttonNames[i]);
            btn.setBackground(Color.WHITE);
            btn.setFocusPainted(false);

            btn.addActionListener(new ButtonClickListener());
            buttons[i] = btn;
            add(btn);
        }

        currentButton = buttons[0];
        previousButton = buttons[0];
        updateButtonUI(currentButton);

        listener.onModeSelected(new SelectMode());
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();

            if (currentButton != clickedButton) {
                previousButton = currentButton;
                currentButton = clickedButton;
            }

            updateButtonUI(currentButton);

            String modeName = currentButton.getText();
            switch (modeName) {
                case "Rect" -> listener.onModeSelected(new RectMode());
                case "Oval" -> listener.onModeSelected(new OvalMode());
                case "Select" -> listener.onModeSelected(new SelectMode());
                case "Association" -> listener.onModeSelected(new ConnectionMode("Association"));
                case "Composition" -> listener.onModeSelected(new ConnectionMode("Composition"));
                case "Generalization" -> listener.onModeSelected(new ConnectionMode("Generalization"));
            }
        }
    }

    private void updateButtonUI(JButton activeBtn) {
        for (JButton btn : buttons) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
        }
        if (activeBtn != null) {
            activeBtn.setBackground(Color.BLACK);
            activeBtn.setForeground(Color.WHITE);
        }
    }

    public void resetButtonColor() {
        if (previousButton != null) {
            currentButton = previousButton;
        }

        updateButtonUI(currentButton);

        if (listener != null && previousButton != null) {
            String modeName = previousButton.getText();

            Mode previousMode = switch (modeName) {
                case "Select" -> new SelectMode();
                case "Rect" -> new RectMode();
                case "Oval" -> new OvalMode();
                case "Association" -> new ConnectionMode("Association");
                case "Composition" -> new ConnectionMode("Composition");
                case "Generalization" -> new ConnectionMode("Generalization");
                default -> null;
            };

            listener.onModeSelected(previousMode);
        }
    }
}
