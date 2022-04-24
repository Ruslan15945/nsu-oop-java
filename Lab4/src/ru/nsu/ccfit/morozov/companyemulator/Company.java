package ru.nsu.ccfit.morozov.companyemulator;


import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;


public class Company {

    Config config;
    Station AStation;
    Station BStation;
    Depot depot;
    Map<String, Storage> AStorages = new HashMap<>();
    Map<String, Storage> BStorages = new HashMap<>();

    private final LinkedList<Thread> factories = new LinkedList<>();
    private final LinkedList<Thread> consumers = new LinkedList<>();

    public Company(InputStream configStream) throws IOException, ParseException {


        config = new Config();
        config.parse(configStream);

        createStorages();
        createDepot();

    }


    public void start(){
        try{

            createThreads();
            depot.createTrains();

            Scanner in = new Scanner(System.in);
            while(true) {
                String cmd = in.nextLine();
                String[] parsedCmd = cmd.split(" ");
                if (parsedCmd[0].equals("exit")) {
                    terminate();
                    break;
                }
            }
            in.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void terminate() {
        depot.stop();
        for (var thread : factories){
            thread.interrupt();
        }
        factories.clear();
        for (var thread : consumers){
            thread.interrupt();
        }
        consumers.clear();
    }

    private void createStorages(){
        for (var item : config.getProductsConfig().entrySet()){
            Storage AStorage = new Storage(item.getKey(), item.getValue().storageCapacityA);
            Storage BStorage = new Storage(item.getKey(), item.getValue().storageCapacityB);
            AStorages.put(item.getKey(), AStorage);
            BStorages.put(item.getKey(), BStorage);
        }
    }

    private void createDepot() {
        AStation = new Station(config.getStationsConfig().capacityA, AStorages);
        BStation = new Station(config.getStationsConfig().capacityB, BStorages);
        depot = new Depot(config, AStation, BStation);

    }

    private void createThreads(){
        for (var item : config.getProductsConfig().entrySet()) {
            String productName = item.getValue().name;
            for (int i = 0; i < item.getValue().factoryCount; ++i) {

                Thread thread = new Thread(new Factory(i, item.getValue(), AStorages.get(productName)));
                thread.start();
                factories.add(thread);
            }
            for (int i = 0; i < item.getValue().consumerCount; ++i) {
                Thread thread = new Thread(new Consumer(i, item.getValue(), BStorages.get(productName)));
                thread.start();
                consumers.add(thread);
            }
        }
    }

}
