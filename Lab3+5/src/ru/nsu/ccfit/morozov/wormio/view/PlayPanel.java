package ru.nsu.ccfit.morozov.wormio.view;

import ru.nsu.ccfit.morozov.wormio.client.Client;
import ru.nsu.ccfit.morozov.wormio.model.PlayerView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class PlayPanel extends JPanel {

    JLabel scoreText;
    JPanel viewPanel;
    GameView window;
    private BufferedImage image;
    private Graphics2D g;
    private int windowWidth;
    private int windowHeight;
    private double scale;
    PlayMouseHandler mouseHandler;

    PlayPanel(GameView window){
        super();
        this.window = window;
        this.windowWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.windowHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        setPreferredSize(new Dimension(windowWidth,windowHeight));
        this.scale = (double)windowHeight / 500;
        this.setLayout(new BorderLayout());

        image = new BufferedImage(windowHeight, windowHeight, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(windowWidth-windowHeight,windowHeight));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        scoreText = new JLabel();
        scoreText.setFont(new Font(Font.DIALOG, Font.PLAIN, 50));
        scoreText.setMaximumSize(new Dimension(windowWidth-windowHeight,100));
        scoreText.setAlignmentX(CENTER_ALIGNMENT);

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font(Font.DIALOG, Font.BOLD, 50));
        exitButton.setFocusPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setAlignmentX(CENTER_ALIGNMENT);
        exitButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.finalUpdate();
            }
        });

        leftPanel.add(scoreText);
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(exitButton);

        viewPanel = new JPanel();
        viewPanel.setPreferredSize(new Dimension(windowHeight,windowHeight));

        this.add(leftPanel, BorderLayout.WEST);
        this.add(viewPanel, BorderLayout.EAST);
        revalidate();
        setFocusable(true);
        requestFocus();

    }


    public void gameRender(PlayerView view){
        scoreText.setText(" Score: " + view.getScore());
        g.setColor(Color.WHITE);
        g.fillRect(0,0,windowHeight, windowHeight);
        g.setColor(Color.GRAY);
        int ownRadius = (int)view.getSize();
        g.fillOval((int)(scale*(view.getPositionX() - ownRadius)),(int)(scale*(view.getPositionY() - ownRadius)), (int)(2*ownRadius*scale),(int)(2*ownRadius*scale));

        for(int i = 0; i< view.getFoodCount(); ++i){
            g.setColor(new Color(view.getFoodColors().get(i)));
            double radius = view.getFoodSizes().get(i);
            g.fillOval( (int)(scale*(view.getFoodPositionsX().get(i) - radius)),(int)(scale*(view.getFoodPositionsY().get(i) - radius)), (int)(2*radius*scale),(int)(2*radius*scale));
        }

        g.setColor(Color.BLACK);
        for(int i = 0; i< view.getPlayersCount(); ++i){
            double radius = view.getSizes().get(i);
            g.fillOval( (int)(scale*(view.getPositionsX().get(i) - radius)),(int)(scale*(view.getPositionsY().get(i) - radius)), (int)(2*radius*scale),(int)(2*radius*scale));
        }
    }

    public void gameDraw(){
        Graphics g2 = viewPanel.getGraphics();
        g2.drawImage(image,0,0,null);
        g2.dispose();
    }

    public void setHandler(Client client) {

        mouseHandler = new PlayMouseHandler(client, viewPanel);
        viewPanel.addMouseListener(mouseHandler);
        viewPanel.addMouseMotionListener(mouseHandler);
    }

}
