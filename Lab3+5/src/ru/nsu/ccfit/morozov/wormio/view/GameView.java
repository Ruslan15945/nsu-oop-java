package ru.nsu.ccfit.morozov.wormio.view;

import ru.nsu.ccfit.morozov.wormio.client.Client;
import ru.nsu.ccfit.morozov.wormio.model.PlayerView;
import ru.nsu.ccfit.morozov.wormio.util.Observer;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameView implements Observer {
    JFrame window;
    PlayPanel playPanel;
    Client client;
    HumanClient mainApplication;
    PlayerView view;

    GameView(Client client, HumanClient mainApplication){
        this.mainApplication = mainApplication;
        this.client = client;
        window = new JFrame("Worm.io");
        window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                finalUpdate();
            }
        });
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setUndecorated(true);
        playPanel = new PlayPanel(this);
        window.setContentPane(playPanel);
        window.pack();
        playPanel.setHandler(client);
        window.setVisible(true);
        client.getListener().register(this);
    }

    public void close() {
        this.window.dispose();
        mainApplication.setMenuView();
    }

    @Override
    public synchronized void update(){
        view = client.getPlayerView();
        playPanel.gameRender(view);
        playPanel.gameDraw();
    }

    @Override
    public void finalUpdate() {
        client.stop();
        this.window.dispose();
        mainApplication.setScoreView(view.getScore());

    }
}
