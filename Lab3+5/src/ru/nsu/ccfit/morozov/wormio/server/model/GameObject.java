package ru.nsu.ccfit.morozov.wormio.server.model;

public class GameObject {

    GameObject(double x, double y, double size){
        this.position = new Point(x,y);
        this.size = size;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public static class Point{
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
        public Point(Point o) {
            this.x = o.x;
            this.y = o.y;
        }

        public Point add(Point o){
            this.y += o.y;
            this.x += o.x;
            return this;
        }

        public Point scale(double s){
            this.x *= s;
            this.y *= s;
            return this;
        }
    }
    private volatile Point position;
    private double size;

    public boolean collides(GameObject o){
        return collides(o,0,0);
    }

    public int enters(GameObject o){
        return enters(o,0,0);
    }


    public boolean collides(GameObject o, double shiftX, double shiftY) {
        double distance = Math.sqrt(Math.pow(this.getPosition().x - (o.getPosition().x + shiftX),2) + Math.pow(this.getPosition().y - (o.getPosition().y + shiftY),2));
        double collisionDistance = getSize() + o.getSize();
        if (distance <= collisionDistance)
            return true;
        return false;
    }

    public int enters(GameObject o, double shiftX, double shiftY){
        double distance = Math.sqrt(Math.pow(this.getPosition().x - (o.getPosition().x + shiftX),2) + Math.pow(this.getPosition().y - (o.getPosition().y + shiftY),2));
        if (this.size > o.size && distance < size){
            return 1;
        }
        else if (this.size < o.size && distance < o.size){
            return -1;
        }
        else{
            return 0;
        }
    }

}
