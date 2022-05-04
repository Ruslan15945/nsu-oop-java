package ru.nsu.ccfit.morozov.wormio.view;

import ru.nsu.ccfit.morozov.wormio.view.HumanClient;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MenuPanel extends JPanel {

    private HumanClient window;
    MenuPanel(HumanClient window){
        this.window = window;
        setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JButton connectButton = new JButton("Connect");
        connectButton.setFont(new Font(Font.DIALOG, Font.BOLD, 50));
        connectButton.setFocusPainted(false);
        connectButton.setContentAreaFilled(false);
        connectButton.setBorder(null);
        connectButton.setMargin(new Insets(10,100,10,100));
        connectButton.setAlignmentX(CENTER_ALIGNMENT);

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font(Font.DIALOG, Font.BOLD, 50));
        exitButton.setFocusPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorder(null);
        exitButton.setMargin(new Insets(10,100,10,100));
        exitButton.setAlignmentX(CENTER_ALIGNMENT);
        exitButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        connectButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setConnectionView();
            }
        });
        add(Box.createVerticalGlue());
        add(connectButton);
        add(Box.createVerticalStrut(100));
        add(exitButton);
        add(Box.createVerticalGlue());
        setFocusable(true);
        requestFocus();

    }

}
