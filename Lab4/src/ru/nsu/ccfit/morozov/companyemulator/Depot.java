package ru.nsu.ccfit.morozov.companyemulator;

import java.util.HashMap;

public class Depot {

    private final Config config;
    private final Station stationA;
    private final Station stationB;
    private final Railway railway;
    private final HashMap<String,Thread> trains;

    Depot(Config config, Station stationA, Station stationB){
        trains = new HashMap<>();
        this.config = config;
        this.stationA = stationA;
        this.stationB = stationB;
        this.railway = new Railway(config.getStationsConfig().distance, config.getStationsConfig().waysA, config.getStationsConfig().waysB);
    }

    public void newTrain(Train train) {
        Thread thread = new Thread(new Train(this, train.getConfig()));
        thread.start();
        trains.put(train.getConfig().name, thread);
    }

    public void createTrains() {
        for (var item : config.getTrainsConfig().entrySet()){
            Thread thread = new Thread(new Train(this, item.getValue()));
            thread.start();
            trains.put(item.getKey(),thread);
        }
    }

    public void stop() {
        for (var thread : trains.entrySet()){
            thread.getValue().interrupt();
        }
        trains.clear();
    }

    public Station getStationA() {
        return stationA;
    }

    public Station getStationB() {
        return stationB;
    }

    public Railway getRailway() {
        return railway;
    }

    public Product.Config getProductInfo(String name){
        return config.getProductsConfig().get(name);
    }

}
