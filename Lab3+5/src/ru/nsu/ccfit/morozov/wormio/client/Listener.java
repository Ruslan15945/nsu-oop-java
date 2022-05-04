package ru.nsu.ccfit.morozov.wormio.client;

import ru.nsu.ccfit.morozov.wormio.network.ServerMessage;
import ru.nsu.ccfit.morozov.wormio.util.Observer;
import ru.nsu.ccfit.morozov.wormio.util.StreamUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Listener extends Thread {

    Client client;
    private final InputStream inputStream;
    Socket socket;
    ArrayList<Observer> observers;

    public Listener(Client client, InputStream inputStream, Socket socket){
        this.socket = socket;
        this.client = client;
        this.inputStream = inputStream;
        observers = new ArrayList<>();
    }

    public void getToken(){
        try {
            String token = StreamUtil.readString(inputStream);
            System.out.println("Read token:" + token);
            client.setToken(token);
        } catch (IOException e) {
            client.stop();
        }
    }

    @Override
    public void run() {

        while (true) {
            try {
                ServerMessage message = ServerMessage.readFrom(inputStream);
                if (message instanceof ServerMessage.UpdateWorld) {
                    ServerMessage.UpdateWorld updateWorldMessage = (ServerMessage.UpdateWorld) message;
                    client.setPlayerView(updateWorldMessage.getPlayerView());
                    //System.out.println("client.listener" + updateWorldMessage.getPlayerView().getPositionX() + updateWorldMessage.getPlayerView().getPositionY());
                    for (Observer observer:observers){
                        observer.update();
                    }
                }
                else{
                    System.out.println("unexpected message");
                    throw new IOException("unexpected message");
                }
                if (!client.isInit())
                    client.initSender();
            }
            catch (IOException e){
                break;
            }
        }
        client.stop();
        for (Observer observer:observers){
            observer.finalUpdate();
        }
    }

    public void register(Observer observer) {
        observers.add(observer);
    }
}
