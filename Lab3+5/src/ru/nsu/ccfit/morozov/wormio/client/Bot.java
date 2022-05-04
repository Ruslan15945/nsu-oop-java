package ru.nsu.ccfit.morozov.wormio.client;

import ru.nsu.ccfit.morozov.wormio.model.Action;
import ru.nsu.ccfit.morozov.wormio.model.PlayerView;

import java.io.IOException;
import java.util.Random;

public class Bot extends Client{

    Random random;

    public Bot(String host, int port) throws IOException {
        super(host, port);
        random = new Random();

    }

    private Action action;
    private int count = 50;

    @Override
    public Action getAction(){
        if(++count > 50){
            PlayerView view = getPlayerView();
            double x = view.getPositionX();
            double y = view.getPositionY();
            double direction = random.nextDouble(-10, 10);
            boolean out = true;
            if (view.getFoodCount()>0){
                double dx = view.getFoodPositionsX().get(0) - x;
                double dy = view.getFoodPositionsY().get(0) - y;
                direction = Math.atan2(dy,dx);
            }
            else {
                out = false;
                if (x > 6000) {
                    out = true;
                    x = -1;
                } else if (x < 0) {
                    out = true;
                    x = 1;
                }
                if (y > 6000) {
                    out = true;
                    y = -1;
                } else if (y < 0) {
                    out = true;
                    y = 1;
                }
                if (out)
                    direction = Math.atan2(y, x);
            }
            setAction(new Action(direction, out?0.9:random.nextDouble(0.3,0.7), getToken()));
            count = 0;
        }
        return action;
    }

    @Override
    public void setAction(Action action) {
        this.action = action;
    }

}
