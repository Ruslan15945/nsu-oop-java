package ru.nsu.ccfit.morozov.wormio.view;

import ru.nsu.ccfit.morozov.wormio.view.HumanClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ConnectionPanel extends JPanel {

    private HumanClient window;
    ConnectionPanel(HumanClient window){
        this.window = window;
        setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JButton joinButton = new JButton("Join");
        joinButton.setFont(new Font(Font.DIALOG, Font.BOLD, 50));
        joinButton.setFocusPainted(false);
        joinButton.setContentAreaFilled(false);
        joinButton.setBorder(null);
        joinButton.setMargin(new Insets(10,100,10,100));
        joinButton.setAlignmentX(CENTER_ALIGNMENT);


        JButton backButton = new JButton("Back");
        backButton.setFont(new Font(Font.DIALOG, Font.BOLD, 50));
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorder(null);
        backButton.setMargin(new Insets(10,100,10,100));
        backButton.setAlignmentX(CENTER_ALIGNMENT);

        JTextField hostField = new JTextField("127.0.0.1");
        hostField.setFont(new Font(Font.DIALOG, Font.PLAIN, 50));
        hostField.setBackground(null);
        hostField.setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 50));
        hostField.setHorizontalAlignment(JTextField.CENTER);
        hostField.setBorder(null);

        JTextField portField = new JTextField("54353");
        portField.setFont(new Font(Font.DIALOG, Font.PLAIN, 50));
        portField.setBackground(null);
        portField.setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,50));
        portField.setHorizontalAlignment(JTextField.CENTER);
        portField.setBorder(null);

        JLabel hostLabel = new JLabel("Enter host:");
        hostLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
        JLabel portLabel = new JLabel("Enter port:");
        portLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));


        hostField.setAlignmentX(CENTER_ALIGNMENT);
        portField.setAlignmentX(CENTER_ALIGNMENT);
        hostLabel.setAlignmentX(CENTER_ALIGNMENT);
        portLabel.setAlignmentX(CENTER_ALIGNMENT);
        backButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setMenuView();
            }
        });

        joinButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setGameView(hostField.getText(), portField.getText());
            }
        });


        add(Box.createVerticalGlue());
        add(hostLabel);
        add(hostField);
        add(Box.createVerticalGlue());
        add(portLabel);
        add(portField);
        add(Box.createVerticalGlue());
        add(joinButton);
        add(Box.createVerticalGlue());
        add(backButton);
        add(Box.createVerticalGlue());
        setFocusable(true);
        requestFocus();

        hostField.requestFocus();

    }

}
