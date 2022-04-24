package ru.nsu.ccfit.morozov.companyemulator;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Factory implements Runnable{

    private static final Logger logger = LogManager.getLogger(Factory.class);

    private final int id;
    private final Storage storage;
    private final String productName;
    private int pCount = 0;
    private int delay;

    public Factory (int id, Product.Config config, Storage storage){

        this.id = id;

        productName = config.name;
        delay = config.timeToCreate;

        this.storage = storage;
    }


    @Override
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            try{
                Product item = new Product(productName, newId());
                storage.put(item);
                logger.info("Produced (by " + this.id + ") Product name: " + item.getName() + "; id:" + item.getId());
                Thread.sleep(delay);
            }
            catch (Exception e){
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    private String newId(){
        return productName + id + ++pCount;
    }
}
