package ru.nsu.ccfit.morozov.companyemulator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Config {

    private final HashMap<String, Product.Config> productsConfig;
    private final HashMap<String, Train.Config> trainsConfig;
    private Station.Config stationsConfig;

    Config(){
        productsConfig = new HashMap<>();
        trainsConfig = new HashMap<>();
    }

    public void parse(InputStream stream) throws IOException, ParseException {

        if (stream == null)
            throw new IOException();

        JSONObject conf = (JSONObject) new JSONParser().parse(new InputStreamReader(stream));
        JSONArray products = (JSONArray) conf.get("products");
        JSONArray trains = (JSONArray) conf.get("trains");
        JSONObject stations = (JSONObject) conf.get("stations");



        for (Object productObject : products){
            JSONObject productRecord = (JSONObject) productObject;
            String name = (String)productRecord.get("name");
            int capacity = ((Long) productRecord.get("capacity")).intValue();
            int factoryCount = ((Long) productRecord.get("factoryCount")).intValue();
            int consumerCount = ((Long) productRecord.get("consumerCount")).intValue();
            int storageCapacityA = ((Long) productRecord.get("storageCapacityA")).intValue();
            int storageCapacityB = ((Long) productRecord.get("storageCapacityB")).intValue();
            int timeToCreate = ((Long) productRecord.get("timeToCreate")).intValue();
            int timeToConsume = ((Long) productRecord.get("timeToConsume")).intValue();
            int timeToLoad = ((Long) productRecord.get("timeToLoad")).intValue();
            int timeToUnload = ((Long) productRecord.get("timeToUnload")).intValue();
            productsConfig.put(name,
                    new Product.Config(name, factoryCount, consumerCount,
                            capacity, storageCapacityA, storageCapacityB,
                            timeToCreate, timeToConsume, timeToLoad, timeToUnload
                    ));

        }

        for (Object trainObject : trains){
            JSONObject productRecord = (JSONObject) trainObject;
            String name = (String)productRecord.get("name");
            int capacity = ((Long) productRecord.get("capacity")).intValue();
            int timeToCreate = ((Long) productRecord.get("timeToCreate")).intValue();
            int speed = ((Long) productRecord.get("speed")).intValue();
            int distanceToGo = ((Long) productRecord.get("distanceToGo")).intValue();
            trainsConfig.put(name,
                    new Train.Config(name, capacity, speed,
                            timeToCreate, distanceToGo
                    ));

        }

        int distance = ((Long) stations.get("distance")).intValue();
        int capacityA = ((Long) stations.get("capacityA")).intValue();
        int capacityB = ((Long) stations.get("capacityB")).intValue();
        int waysA = ((Long) stations.get("waysA")).intValue();
        int waysB = ((Long) stations.get("waysB")).intValue();

        stationsConfig = new Station.Config(distance, capacityA, capacityB, waysA, waysB);

    }

    HashMap<String, Product.Config> getProductsConfig(){
        return productsConfig;
    }

    HashMap<String, Train.Config> getTrainsConfig(){
        return trainsConfig;
    }

    Station.Config getStationsConfig(){
        return stationsConfig;
    }

}
