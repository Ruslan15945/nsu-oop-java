package ru.nsu.ccfit.morozov.wormio.view;


import ru.nsu.ccfit.morozov.wormio.client.Client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class HumanClient extends JFrame{
    private MenuPanel menuPanel;
    private ConnectionPanel connectionPanel;

    public HumanClient(){
        super("Worm.io");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        menuPanel = new MenuPanel(this);
        connectionPanel = new ConnectionPanel(this);
        setMenuView();
        setVisible(true);
    }

    public void setGameView(String host, String port){

        try {
            Client client = new ru.nsu.ccfit.morozov.wormio.client.HumanClient(host, Integer.parseInt(port));

            client.init();
            GameView game = new GameView(client, this);
            this.setVisible(false);
        } catch (IOException e) {
            System.out.println("Cannot join" + host +":" + port);
            setConnectionView();
        }
    }

    public void setMenuView(){

        this.setContentPane(menuPanel);
        menuPanel.revalidate();
        pack();
        setVisible(true);
    }

    public void setConnectionView(){
        this.setContentPane(connectionPanel);
        //this.getContentPane().remove(menuPanel);
        //this.getContentPane().add(connectionPanel, BorderLayout.CENTER);
        connectionPanel.revalidate();
        pack();
        setVisible(true);
    }

    public void setScoreView(int score){
        this.setContentPane(new ScorePanel(this, score));
        pack();
        setVisible(true);
    }

}
