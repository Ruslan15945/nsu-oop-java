package ru.nsu.ccfit.morozov.wormio.client;

import ru.nsu.ccfit.morozov.wormio.model.Action;
import ru.nsu.ccfit.morozov.wormio.network.ClientMessage;

import java.io.IOException;
import java.io.OutputStream;
import java.util.TimerTask;

public class Sender extends TimerTask {

    Client client;
    OutputStream outputStream;

    public Sender(Client client, OutputStream outputStream) {
        this.client = client;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        Action action = client.getAction();
        ClientMessage.ActionMessage message = new ClientMessage.ActionMessage(action);

        try {
            message.writeTo(outputStream);
            outputStream.flush();
        } catch (IOException e) {
        }
    }
}
