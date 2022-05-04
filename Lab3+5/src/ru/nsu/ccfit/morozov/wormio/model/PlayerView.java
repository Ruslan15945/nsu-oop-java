package ru.nsu.ccfit.morozov.wormio.model;

import ru.nsu.ccfit.morozov.wormio.util.StreamUtil;

import java.util.ArrayList;

public class PlayerView {

    private double positionX;
    private double positionY;
    private int playersCount;
    private int foodCount;
    private ArrayList<Double> positionsX;
    private ArrayList<Double> positionsY;
    private ArrayList<Double> sizes;
    private ArrayList<Double> foodPositionsX;
    private ArrayList<Double> foodPositionsY;
    private ArrayList<Double> foodSizes;
    private ArrayList<Integer> foodColors;
    private int score;
    private double size;

    public PlayerView(double positionX, double positionY, double size, int score){
        this.positionX = positionX;
        this.positionY = positionY;
        this.size = size;
        this.playersCount = 0;
        this.foodCount = 0;
        this.positionsX = new ArrayList<>();
        this.positionsY = new ArrayList<>();
        this.sizes = new ArrayList<>();
        this.foodPositionsX = new ArrayList<>();
        this.foodPositionsY = new ArrayList<>();
        this.foodSizes = new ArrayList<>();
        this.foodColors = new ArrayList<>();
        this.score = score;
    }

    public static PlayerView readFrom(java.io.InputStream stream) throws java.io.IOException {
        double positionX = StreamUtil.readDouble(stream);
        double positionY = StreamUtil.readDouble(stream);
        double size = StreamUtil.readDouble(stream);
        int foodCount = StreamUtil.readInt(stream);
        int playersCount = StreamUtil.readInt(stream);
        int score = StreamUtil.readInt(stream);
        PlayerView pw = new PlayerView(positionX, positionY, size, score);
        for (int i = 0; i < foodCount; ++i){
            double posX = StreamUtil.readDouble(stream);
            double posY = StreamUtil.readDouble(stream);
            double fsize = StreamUtil.readDouble(stream);
            int color = StreamUtil.readInt(stream);
            pw.addFoodPosition(posX,posY,fsize, color);
        }
        for (int i = 0; i < playersCount; ++i){
            double posX = StreamUtil.readDouble(stream);
            double posY = StreamUtil.readDouble(stream);
            double psize = StreamUtil.readDouble(stream);
            pw.addPosition(posX,posY,psize);
        }

        return pw;
    }

    public void addPosition(double x, double y, double size) {
        this.playersCount++;
        this.positionsX.add(x);
        this.positionsY.add(y);
        this.sizes.add(size);
    }

    public void addFoodPosition(double x, double y, double size, int color) {
        this.foodCount++;
        this.foodPositionsX.add(x);
        this.foodPositionsY.add(y);
        this.foodSizes.add(size);
        this.foodColors.add(color);
    }

    public void writeTo(java.io.OutputStream stream) throws java.io.IOException{
        StreamUtil.writeDouble(stream, positionX);
        StreamUtil.writeDouble(stream, positionY);
        StreamUtil.writeDouble(stream, size);
        StreamUtil.writeInt(stream, foodCount);
        StreamUtil.writeInt(stream, playersCount);
        StreamUtil.writeInt(stream, score);
        for (int i = 0; i < foodCount; ++i){
            StreamUtil.writeDouble(stream, foodPositionsX.get(i));
            StreamUtil.writeDouble(stream, foodPositionsY.get(i));
            StreamUtil.writeDouble(stream, foodSizes.get(i));
            StreamUtil.writeInt(stream, foodColors.get(i));
        }
        for (int i = 0; i < playersCount; ++i){
            StreamUtil.writeDouble(stream, positionsX.get(i));
            StreamUtil.writeDouble(stream, positionsY.get(i));
            StreamUtil.writeDouble(stream, sizes.get(i));
        }

    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public ArrayList<Double> getPositionsX(){
        return this.positionsX;
    }

    public ArrayList<Double> getPositionsY(){
        return this.positionsY;
    }

    public int getPlayersCount(){
        return this.playersCount;
    }

    public int getScore(){
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public ArrayList<Double> getFoodPositionsY() {
        return foodPositionsY;
    }

    public void setFoodPositionsY(ArrayList<Double> foodPositionsY) {
        this.foodPositionsY = foodPositionsY;
    }

    public ArrayList<Double> getFoodPositionsX() {
        return foodPositionsX;
    }

    public void setFoodPositionsX(ArrayList<Double> foodPositionsX) {
        this.foodPositionsX = foodPositionsX;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }
    public ArrayList<Double> getFoodSizes(){
        return this.foodSizes;
    }
    public ArrayList<Double> getSizes(){
        return this.sizes;
    }

    public ArrayList<Integer> getFoodColors() {
        return foodColors;
    }

    public void setFoodColors(ArrayList<Integer> foodColors) {
        this.foodColors = foodColors;
    }
}
