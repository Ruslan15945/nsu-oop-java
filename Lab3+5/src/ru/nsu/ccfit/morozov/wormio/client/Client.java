package ru.nsu.ccfit.morozov.wormio.client;

import ru.nsu.ccfit.morozov.wormio.model.Action;
import ru.nsu.ccfit.morozov.wormio.model.PlayerView;

import java.io.*;
import java.net.Socket;
import java.util.Timer;

public abstract class Client {

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    private PlayerView playerView;
    private Timer timer;
    private final Listener listener;
    private final Sender sender;
    private String token;
    private boolean isInit = false;

    public Listener getListener(){
        return listener;
    }

    public Sender getSender(){
        return sender;
    }

    public Client(String host, int port) throws IOException {
        socket = new Socket(host, port);
        socket.setTcpNoDelay(true);
        inputStream = new BufferedInputStream(socket.getInputStream());
        outputStream = new BufferedOutputStream(socket.getOutputStream());

        listener = new Listener(this, inputStream, socket);
        listener.getToken();
        sender = new Sender(this, outputStream);
        playerView = new PlayerView(0,0,0,0);
    }

    public void init(){
        new Thread(this.listener).start();
    }

    public void initSender(){
        setInit(true);
        timer = new Timer();
        timer.scheduleAtFixedRate(sender, 10, 10);
    }


    public synchronized PlayerView getPlayerView() {
        return playerView;
    }

    public synchronized void setPlayerView(PlayerView playerView) {
        this.playerView = playerView;
    }

    public abstract Action getAction();
    public abstract void setAction(Action action);

    public void stop() {
        if (timer != null)
            this.timer.cancel();
        if (socket.isClosed())
                return;
        try {
            this.socket.close();
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

    public synchronized boolean isInit() {
        return isInit;
    }

    public synchronized void setInit(boolean init) {
        isInit = init;
    }
}
