package ru.nsu.ccfit.morozov.wormio.server;

import ru.nsu.ccfit.morozov.wormio.network.ServerMessage;
import ru.nsu.ccfit.morozov.wormio.server.model.Player;
import ru.nsu.ccfit.morozov.wormio.util.StreamUtil;

import java.io.IOException;
import java.io.OutputStream;

public class Sender implements Runnable{

    private OutputStream stream;
    private Player player;

    public Sender( Player player, OutputStream stream){
        this.stream = stream;
        this.player = player;
        try {
            StreamUtil.writeString(stream,player.getToken());
            stream.flush();
        } catch (IOException e) {

        }
    }

    @Override
    public synchronized void run() {
        try {
            ServerMessage.UpdateWorld message = new ServerMessage.UpdateWorld(player.getPlayerView());
            message.writeTo(stream);
            stream.flush();
            //System.out.println("server.sender" + player.getPlayerView().getPositionX() + player.getPlayerView().getPositionY());
        } catch (IOException e) {

        }

    }

}
