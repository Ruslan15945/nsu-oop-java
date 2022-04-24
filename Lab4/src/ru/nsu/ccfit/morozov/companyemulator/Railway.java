package ru.nsu.ccfit.morozov.companyemulator;



import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Railway{


    private static final Logger logger = LogManager.getLogger(Railway.class);

    private BlockingDeque<Road> ABRoads = new LinkedBlockingDeque<>();
    private BlockingDeque<Road> BARoads = new LinkedBlockingDeque<>();


    private final int roadsLength;

    private void openRoads(int roadsLength, int waysA, int waysB){
        for (int i = 0; i < waysA; ++i){
            ABRoads.add(new Road(roadsLength));
        }
        for (int i = 0; i < waysB; ++i){
            BARoads.add(new Road(roadsLength));
        }
    }

    public Railway(int roadsLength, int waysA, int waysB){
        openRoads(roadsLength, waysA, waysB);
        this.roadsLength = roadsLength;
    }

    public Road getABRoad() throws InterruptedException {
        Road r = ABRoads.take();
        logger.info("Railway: AB road taken, " + ABRoads.size() + " left");
        return r;
    }
    public Road getBARoad() throws InterruptedException {
        Road r = BARoads.take();
        logger.info("Railway: BA road taken, " + BARoads.size() + " left");
        return r;
    }

    public int getRoadsLength() {
        return roadsLength;
    }

    public void freeABRoad(Road road) throws InterruptedException {
        ABRoads.put(road);
        logger.info("Railway: AB road added, " + ABRoads.size() + " left");
    }
    public void freeBARoad(Road road) throws InterruptedException {
        BARoads.put(road);
        logger.info("Railway: BA road added, " + BARoads.size() + " left");
    }

}
