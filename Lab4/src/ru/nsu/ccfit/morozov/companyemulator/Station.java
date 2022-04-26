package ru.nsu.ccfit.morozov.companyemulator;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Station {

    public static class Config{
        int distance;
        int capacityA;
        int capacityB;
        int waysA;
        int waysB;
        Config(int distance, int capacityA, int capacityB, int waysA, int waysB){
            this.distance = distance;
            this.capacityA = capacityA;
            this.capacityB = capacityB;
            this.waysA = waysA;
            this.waysB = waysB;
        }
    }

    private final int capacity;
    private int taken = 0;
    private final Boolean docking = false;


    private final BlockingQueue<Storage> storagesDeque;
    private final Map<String, Storage> storagesMap;

    public Station(int capacity, Map<String, Storage> storagesMap){
        this.capacity = capacity;
        storagesDeque = new LinkedBlockingQueue<>();
        this.storagesMap = storagesMap;
        for (var item : storagesMap.entrySet()){
            storagesDeque.add(item.getValue());
        }
    }

    public void dock() throws InterruptedException {
        synchronized (docking){
            while(taken == capacity){
                docking.wait();
            }
            ++taken;
        }
    }

    public void undock(){
        synchronized (docking) {
            if (taken == 0) {
                return;
            }
            --taken;
            docking.notify();
        }
    }

    public synchronized Storage getStorage() throws InterruptedException {
        if (storagesDeque.isEmpty())
            return null;
        storagesDeque.put(storagesDeque.peek());
        return storagesDeque.take();
    }

    public Storage getStorage(String productName) {
        return storagesMap.get(productName);
    }

}
