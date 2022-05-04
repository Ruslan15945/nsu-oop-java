package ru.nsu.ccfit.morozov.wormio.model;

import ru.nsu.ccfit.morozov.wormio.util.StreamUtil;

public class Action {
    private double direction;
    private double speed;
    private String token;


    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Action(double direction, double speed, String token){
        this.direction = direction;
        this.speed = speed;
        this.token = token;

    }

    public static Action readFrom(java.io.InputStream stream) throws java.io.IOException {
        String token = StreamUtil.readString(stream);
        double direction = StreamUtil.readDouble(stream);
        double speed = StreamUtil.readDouble(stream);

        return new Action(direction,speed,token);
    }

    public void writeTo(java.io.OutputStream stream) throws java.io.IOException {
        StreamUtil.writeString(stream, token);
        StreamUtil.writeDouble(stream, this.direction);
        StreamUtil.writeDouble(stream, this.speed);
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
