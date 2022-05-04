package ru.nsu.ccfit.morozov.wormio.server.model;

public class Worm extends GameObject{

    private Point velocity;
    public double speed;
    private int score;
    boolean alive;

    Worm(double x, double y, double size) {
        super(x, y, size);
        velocity = new Point(0,0);
        score = 0;
        alive = true;
    }

    public synchronized void move() {
        this.setPosition(this.getPosition().add(velocity.scale(speed)));
    }

    public Point getVelocity() {
        return velocity;
    }

    public synchronized void setVelocity(Point velocity) {
        this.velocity = velocity;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
        setSize(10 + Math.sqrt(this.score));
    }


}
