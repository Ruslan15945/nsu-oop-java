package ru.nsu.ccfit.morozov.wormio.server;

import ru.nsu.ccfit.morozov.wormio.server.model.Controller;
import ru.nsu.ccfit.morozov.wormio.network.ClientMessage;

import java.io.IOException;
import java.io.InputStream;

public class Listener extends Thread{

    Controller controller;
    InputStream stream;
    private int id;

    public Listener(Controller controller, InputStream stream, int id){
        this.id = id;
        this.controller = controller;
        this.stream = stream;
    }

    @Override
    public void run() {
        boolean keepReading = true;
        while (keepReading) {
            try {
                ClientMessage message = ClientMessage.readFrom(stream);
                if (message instanceof ClientMessage.ActionMessage) {
                    ClientMessage.ActionMessage actionMessage = (ClientMessage.ActionMessage) message;
                    controller.movePlayer(id, actionMessage.getAction());
                }
            }
            catch (IOException e){
                controller.disconnect(id);
                keepReading = false;
            }
        }
    }
}
