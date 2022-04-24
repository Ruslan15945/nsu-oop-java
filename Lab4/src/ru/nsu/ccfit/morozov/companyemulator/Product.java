package ru.nsu.ccfit.morozov.companyemulator;

public class Product{

    private String id;
    private String name;

    public static class Config{

        String name;
        int capacity;
        int factoryCount;
        int consumerCount;
        int storageCapacityA;
        int storageCapacityB;
        int timeToCreate;
        int timeToConsume;
        int timeToLoad;
        int timeToUnload;

        public Config(String name, int factoryCount, int consumerCount, int capacity, int storageCapacityA, int storageCapacityB, int timeToCreate, int timeToConsume, int timeToLoad, int timeToUnload) {
            this.name = name;
            this.capacity = capacity;
            this.factoryCount = factoryCount;
            this.consumerCount = consumerCount;
            this.storageCapacityA = storageCapacityA;
            this.storageCapacityB = storageCapacityB;
            this.timeToCreate = timeToCreate;
            this.timeToConsume = timeToConsume;
            this.timeToLoad = timeToLoad;
            this.timeToUnload = timeToUnload;
        }
    }

    public Product(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public String getName() {
        return name;
    }

}
