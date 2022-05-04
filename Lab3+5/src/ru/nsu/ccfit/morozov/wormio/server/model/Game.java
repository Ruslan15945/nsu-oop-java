package ru.nsu.ccfit.morozov.wormio.server.model;


import ru.nsu.ccfit.morozov.wormio.model.PlayerView;

import java.util.ArrayList;
import java.util.Random;

public class Game{
    Random random;
    private World world;
    private Worm[] worms;
    private Food[] food;
    private ArrayList<Food> extraFood;
    private final int maxFoodCount = 3000;
    private final int frequency = 1000/30;
    private final Controller controller;
    private boolean isPlaying;
    private final double fieldSize = 3000;
    private final double viewSize = 500;

    Game (Controller controller){
        this.controller = controller;
        random = new Random();
    }

    void changeVelocity(int id, double angle, double speed){

        if (speed > 1.0 || speed < 0.) speed = 1;
        worms[id].setVelocity(new GameObject.Point(Math.cos(angle)*speed, Math.sin(angle)*speed));


    }


    public void tick() {
        if (isPlaying){
            spawnFood();
            movePlayers();
            checkCollision();
        }
    }

    private void checkCollision() {
        for(int j = 0; j < controller.maxPlayers; ++j) {
            Worm worm = worms[j];
            if (worm == null || worm.alive == false)
                continue;

            double positionX = worm.getPosition().x;
            double positionY = worm.getPosition().y;

            ArrayList<World.ChunkPosition> chunks = world.getCloseChunks(positionX, positionY);

            for (World.ChunkPosition chunk :chunks) {
                for (Food f : chunk.chunk.food) {
                    if (!f.eaten && worm.collides(f, chunk.shiftX, chunk.shiftY)) {
                        worm.addScore(f.getScore());
                        f.eaten = true;
                    }

                }
                for (Worm otherWorm : chunk.chunk.worms) {
                    if (otherWorm.alive && otherWorm != worm) {
                        int enters = worm.enters(otherWorm, chunk.shiftX, chunk.shiftY);
                        if (enters == 1) {
                            otherWorm.alive = false;
                        } else if (enters == -1) {
                            worm.alive = false;
                        }
                    }
                }
            }
        }
    }

    private void spawnFood() {
        int probability = frequency * maxFoodCount;
        for (int i = 0; i < extraFood.size(); ++i){
            Food f = extraFood.get(i);
            if (f.eaten){
                world.remove(f);
                extraFood.remove(i);
            }
        }
        for (int i = 0; i < maxFoodCount; ++i){
            if (food[i] != null && food[i].eaten){
                world.remove(food[i]);
                food[i] = null;
            }
            if (food[i] == null){
                if(random.nextInt(probability) == 0) {
                    int color = random.nextInt(1<<24);
                    food[i] = new Food(random.nextDouble(fieldSize), random.nextDouble(fieldSize), 5+random.nextInt(0, 5), color);
                    world.add(food[i]);
                }
            }
            else{
                if (!food[i].grow()) {
                    world.remove(food[i]);
                    food[i] = null;
                }
            }
        }

    }


    public PlayerView getPlayerView(int id){
        Worm worm = getPlayer(id);
        PlayerView view = new PlayerView(viewSize/2, viewSize/2, worm.getSize(), worm.getScore());

        double positionX = worm.getPosition().x;
        double positionY = worm.getPosition().y;

        ArrayList<World.ChunkPosition> chunks = world.getCloseChunks(positionX, positionY);

        for (World.ChunkPosition c : chunks) {
            for (Worm otherWorm : c.chunk.worms) {
                if (otherWorm != worm) {
                    double oPositionX = otherWorm.getPosition().x + c.shiftX;
                    double oPositionY = otherWorm.getPosition().y + c.shiftY;
                    double dx = Math.abs(oPositionX - positionX) - otherWorm.getSize();
                    double dy = Math.abs(oPositionY - positionY) - otherWorm.getSize();
                    if (dx < viewSize/2 && dy < viewSize/2) {
                        view.addPosition(viewSize / 2 + oPositionX - positionX, viewSize / 2 + oPositionY - positionY, otherWorm.getSize());
                    }
                }
            }
            for (Food f : c.chunk.food) {
                double fPositionX = f.getPosition().x + c.shiftX;
                double fPositionY = f.getPosition().y + c.shiftY;
                double dx = Math.abs(fPositionX - positionX) - f.getSize();
                double dy = Math.abs(fPositionY - positionY) - f.getSize();
                if (dx < viewSize/2 && dy < viewSize/2) {
                    view.addFoodPosition(viewSize / 2 + fPositionX - positionX, viewSize / 2 + fPositionY - positionY, f.getSize(), f.getColor());
                }
            }
        }

        return view;
    }

    private void movePlayers(){
        for( Worm worm: worms){
            if (worm!=null) {
                world.moveWorm(worm);
            }
        }
    }

    public void init(int maxWorms) {
        if (isPlaying)
            return;

        world = new World(6,6);
        worms = new Worm[maxWorms];
        extraFood = new ArrayList<>();
        food = new Food[maxFoodCount];
        isPlaying = true;

    }


    public void addPlayer(int id) {

        Worm worm = new Worm(random.nextDouble(0,fieldSize), random.nextDouble(0,fieldSize), 10);
        worm.speed = 5;
        worms[id] = worm;
        world.add(worm);
    }

    public void killPlayer(int id){
        if (worms[id] != null) {
            Food remains = new Food(worms[id].getPosition().x, worms[id].getPosition().y, worms[id].getScore(), 0x808080);
            extraFood.add(remains);
            world.add(remains);
            world.remove(worms[id]);
            worms[id] = null;
        }
    }

    public Worm getPlayer(int id) {
        return worms[id];
    }

    public Worm[] getPlayers(){
        return worms;
    }

    public Food[] getFood() {
        return food;
    }

    public void setFood(Food[] food) {
        this.food = food;
    }

    public ArrayList<Food> getExtraFood(){
        return this.extraFood;
    }
}
