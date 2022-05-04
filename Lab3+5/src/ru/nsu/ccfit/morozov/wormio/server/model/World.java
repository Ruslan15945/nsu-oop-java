package ru.nsu.ccfit.morozov.wormio.server.model;


import java.util.ArrayList;
import java.util.LinkedList;

public class World {
    Chunk[][] chunks;
    int x;
    int y;
    int chunkSize = 500;

    public World(int x, int y){
        this.chunks = new Chunk[x][y];
        for (int i = 0; i < x; ++i){
            for (int j = 0; j < y; ++j){
                this.chunks[i][j] = new Chunk();
            }
        }
        this.x = x;
        this.y = y;
    }

    public ArrayList<ChunkPosition> getCloseChunks(double positionX, double positionY){

        ArrayList<ChunkPosition> viewChunks = new ArrayList<>();
        for (int i = -1; i <= 1; ++i)
            for (int j = -1; j <= 1; ++j){
                double shiftX = 0, shiftY = 0;
                if (positionX/chunkSize + i < 0)
                    shiftX = - x * chunkSize;
                else if (positionX/chunkSize + i > x)
                    shiftX = x * chunkSize;

                if (positionY/chunkSize + j < 0)
                    shiftY = - y * chunkSize;
                else if (positionY/chunkSize + j > y)
                    shiftY = y * chunkSize;
                viewChunks.add(new ChunkPosition(chunks[((int)Math.floor(positionX/chunkSize+i+x)) % x][((int)Math.floor(positionY/chunkSize+j+y)) % y] , shiftX, shiftY));
            }
        return viewChunks;
    }


    class Chunk{
        volatile LinkedList<Food> food = new LinkedList<>();
        volatile LinkedList<Worm> worms = new LinkedList<>();
    }

    class ChunkPosition{
        Chunk chunk;
        double shiftX;
        double shiftY;
        ChunkPosition(Chunk chunk, double shiftX, double shiftY){
            this.chunk = chunk;
            this.shiftX = shiftX;
            this.shiftY = shiftY;
        }
    }

    public void remove(Worm worm) {
        Chunk chunk = chunks[(int)Math.floor(worm.getPosition().x/chunkSize)][(int)Math.floor(worm.getPosition().y/chunkSize)];
        synchronized (chunk.worms) {
            chunk.worms.remove(worm);
        }
    }

    public void remove(Food food) {
        Chunk chunk = chunks[(int)Math.floor(food.getPosition().x/chunkSize)][(int)Math.floor(food.getPosition().y/chunkSize)];
        synchronized (chunk.food) {
            chunk.food.remove(food);
        }
    }

    public void add(Food food){
        Chunk chunk = chunks[(int)Math.floor(food.getPosition().x/chunkSize)][(int)Math.floor(food.getPosition().y/chunkSize)];
        synchronized (chunk.food) {
            chunk.food.push(food);
        }
    }



    public void add(Worm worm){
        Chunk chunk = chunks[(int)Math.floor(worm.getPosition().x/chunkSize)][(int)Math.floor(worm.getPosition().y/chunkSize)];
        synchronized (chunk.worms) {
            chunk.worms.push(worm);
        }
    }

    public void moveWorm(Worm worm){

        Chunk chunkBefore = chunks[(int)Math.floor(worm.getPosition().x/chunkSize)][(int)Math.floor(worm.getPosition().y/chunkSize)];
        worm.move();
        double x = worm.getPosition().x;
        double y = worm.getPosition().y;
        while(x >= this.x * chunkSize)
            x -= this.x * chunkSize;
        while(y >= this.y * chunkSize)
            y -= this.y * chunkSize;
        while (x < 0)
            x += this.x * chunkSize;
        while (y < 0)
            y += this.y * chunkSize;
        worm.setPosition(new GameObject.Point(x,y));

        Chunk chunkAfter = chunks[(int)Math.floor(worm.getPosition().x/chunkSize)][(int)Math.floor(worm.getPosition().y/chunkSize)];
        synchronized (chunkBefore.worms) {
            chunkBefore.worms.remove(worm);
        }
        synchronized (chunkAfter.worms) {
            chunkAfter.worms.push(worm);
        }
    }

}
