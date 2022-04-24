package ru.nsu.ccfit.morozov.companyemulator;



import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.LinkedList;

public class Train implements Runnable{

    private static final Logger logger = LogManager.getLogger(Train.class);

    public Config getConfig() {
        return config;
    }

    public static class Config{
        String name;
        int capacity;
        int speed;
        int timeToCreate;
        int distanceToGo;

        Config(String name, int capacity, int speed, int timeToCreate, int distanceToGo){
            this.name = name;
            this.capacity = capacity;
            this.speed = speed;
            this.timeToCreate = timeToCreate;
            this.distanceToGo = distanceToGo;
        }

    }

    private final Config config;
    private final String name;

    private final int capacity;
    private final int speed;
    private int amortization;

    private final Depot depot;
    private final int roadLength;
    private LinkedList<Product> products;
    private Product.Config productInfo;

    public Train(Depot depot, Config config){
        this.depot = depot;
        this.roadLength = depot.getRailway().getRoadsLength();

        this.config = config;
        name = config.name;
        capacity = config.capacity;
        speed = config.speed;
        amortization = config.distanceToGo - roadLength;
        if (this.amortization < 0)
            this.amortization = 0;

        products = new LinkedList<>();
    }

    private void load(Storage storage) throws InterruptedException {
        int productCapacity = productInfo.capacity;
        for (int i = 0; i < capacity; i+=productCapacity) {
            products.addLast(storage.get());
            Thread.sleep( productInfo.timeToLoad);
        }
    }
    private void unload(Storage storage) throws InterruptedException {
        while (!products.isEmpty()) {
            storage.put(products.removeFirst());
            Thread.sleep( productInfo.timeToUnload);
        }
    }

    @Override
    public void run() {
        logger.error("Train " + this.name + " is on");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                checkAmortization();
                Storage storage = depot.getStationA().getStorage();
                String productName = storage.getProductName();
                productInfo = depot.getProductInfo(productName);
                logger.info("Train " + name + ": storage of " + productName + " was chosen");

                depot.getStationA().dock();
                logger.info("Train " + name + ": station A docked");

                load(storage);
                depot.getStationA().undock();
                logger.info("Train " + name + ": station A undocked");

                Road road = depot.getRailway().getABRoad();
                travel(road);
                depot.getRailway().freeABRoad(road);
                logger.info("Train " + name + ": went to station B");

                depot.getStationB().dock();
                logger.info("Train " + name + ": station B docked");

                unload(depot.getStationB().getStorage(productName));
                depot.getStationB().undock();
                logger.info("Train " + name + ": station B undocked");

                checkAmortization();

                road = depot.getRailway().getBARoad();
                travel(road);
                depot.getRailway().freeBARoad(road);
                logger.info("Train " + name + ": went to station A");


            } catch (InterruptedException e) {
                return;
            }
        }
    }
    private void checkAmortization(){
        if (amortization < 0) {
            depot.newTrain(this);
            Thread.currentThread().interrupt();
        }
        else
            logger.info("Train " + name + ": amortization = " + amortization);

    }

    private void travel(Road road) throws InterruptedException {

        Thread.sleep( (int)(1000 * Math.ceil((double)roadLength /speed)));
        amortization -= roadLength;
    }
}

