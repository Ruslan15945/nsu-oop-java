package ru.nsu.ccfit.morozov.wormio.server.model;

public class Food extends GameObject{

    private int color;
    private int score;
    private boolean growing;
    private double maxScore = 31;
    private double growSpeed = 0.4/30;
    private double dScore;
    boolean eaten;

    Food(double x, double y, int score, int color) {
        super(x, y, 2 + Math.sqrt(score));
        this.score = score;
        this.color = color;
        growing = false;
        dScore = 0;
        eaten = false;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getScore() {
        return score;
    }

    public boolean grow(){
        dScore+=growSpeed;
        if (dScore>1){
            ++score;
            dScore-=1.0;
        }
        setSize(2 + Math.sqrt(score+dScore));
        if (score > maxScore)
            return false;
        return true;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
