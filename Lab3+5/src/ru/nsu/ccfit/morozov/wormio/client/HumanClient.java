package ru.nsu.ccfit.morozov.wormio.client;

import ru.nsu.ccfit.morozov.wormio.model.Action;

import java.io.IOException;

public class HumanClient extends Client{

    private volatile Action action;

    public HumanClient(String host, int port) throws IOException {
        super(host, port);
        this.action = new Action(0,0,super.getToken());
    }

    @Override
    public Action getAction() {
        return this.action;
    }

    @Override
    public void setAction(Action action) {
        this.action = action;
    }
}
