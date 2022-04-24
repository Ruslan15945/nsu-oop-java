package ru.nsu.ccfit.morozov.companyemulator;




import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.LinkedList;

public class Storage{

    private static final Logger logger = LogManager.getLogger(Storage.class);

    private static int count = 0;

    private int id;
    private volatile LinkedList<Product> products;
    private int capacity;
    private final String productName;

    public Storage(String productName, int capacity){
        products = new LinkedList<>();
        this.productName = productName;
        this.capacity = capacity;
        synchronized (Storage.class){
            this.id = ++count;
        }
    }

    public synchronized void put(Product item) throws InterruptedException {
        while (products.size() >= capacity)
            wait();
        products.add(item);
        logger.info("Storage " + this.productName +id+": put ->" + products.size());
        notify();
    }



    public synchronized Product get() throws InterruptedException {
        while (products.isEmpty()){
            wait();
        }
        Product item = products.getFirst();
        products.removeFirst();
        logger.info("Storage " + this.productName +id+": get ->" + products.size());
        notify();
        return item;

    }

    public int getCapacity(){
        return capacity;
    }

    public String getProductName() {
        return productName;
    }
}
