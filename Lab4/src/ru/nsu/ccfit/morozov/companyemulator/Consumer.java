package ru.nsu.ccfit.morozov.companyemulator;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Consumer implements Runnable{

    private static final Logger logger = LogManager.getLogger(Consumer.class);

    private int id;
    private Storage storage;
    private String productName;
    private int delay;

    public Consumer(int id, Product.Config config, Storage storage){

        this.id = id;

        this.productName = config.name;
        this.delay = config.timeToConsume;

        this.storage = storage;

    }

    @Override
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            try{
                Product item = storage.get();
                logger.info("Consumed (by " + this.id + ")" + "Product name: " + item.getName() + "; id:" + item.getId());
                Thread.sleep(delay);
            }
            catch (Exception e){
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

}
