package ru.nsu.ccfit.morozov.wormio.server.model;

import ru.nsu.ccfit.morozov.wormio.model.PlayerView;
import ru.nsu.ccfit.morozov.wormio.server.Listener;
import ru.nsu.ccfit.morozov.wormio.server.Sender;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class Player {
    private final Socket socket;
    private Controller controller;

    private int id;
    private String token;
    private PlayerView playerView;

    private Sender sender;
    private Listener listener;


    Player(Controller controller, Socket socket, int id){
        this.id = id;
        Random random = new Random();
        int tokenLength = random.nextInt(5,10);
        StringBuffer token = new StringBuffer();
        for (int i = 0; i < tokenLength; i++)
        {
            token.append(Character.toString(random.nextInt(33, 128)));
        }

        this.token = token.toString();
        this.controller = controller;
        this.socket = socket;
        try {
            this.sender = new Sender(this, new BufferedOutputStream(socket.getOutputStream()));
            this.listener = new Listener(controller, new BufferedInputStream(socket.getInputStream()), id);
            new Thread(listener).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public void setPlayerView(PlayerView playerView) {
        this.playerView = playerView;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sender getSender(){
        return this.sender;
    }

    public Listener getListener(){
        return this.listener;
    }

    public Socket getSocket() {
        return socket;
    }

    public void disconnect() {
        try {
            this.socket.close();
        } catch (IOException e) {
        }
    }
}
