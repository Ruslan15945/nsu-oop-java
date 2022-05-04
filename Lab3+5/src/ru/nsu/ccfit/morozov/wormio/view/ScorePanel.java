package ru.nsu.ccfit.morozov.wormio.view;

import javax.swing.*;
import javax.swing.text.Style;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ScorePanel extends JPanel {

    private HumanClient window;
    ScorePanel(HumanClient window, int score){
        this.window = window;
        setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Your final score: " + score);
        label.setFont(new Font(Font.DIALOG, Font.PLAIN, 50));
        JButton exitButton = new JButton("Menu");
        exitButton.setFont(new Font(Font.DIALOG, Font.BOLD, 50));
        exitButton.setFocusPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorder(null);
        exitButton.setMargin(new Insets(10,100,10,100));
        exitButton.setAlignmentX(CENTER_ALIGNMENT);


        label.setAlignmentX(CENTER_ALIGNMENT);
        exitButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setMenuView();
            }
        });

        add(Box.createVerticalGlue());
        add(label);
        add(Box.createVerticalStrut(50));
        add(exitButton);
        add(Box.createVerticalGlue());
        setFocusable(true);
        requestFocus();

    }

}
