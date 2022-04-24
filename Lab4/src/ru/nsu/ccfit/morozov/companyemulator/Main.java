package ru.nsu.ccfit.morozov.companyemulator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {

            Logger.getRootLogger().setLevel(Level.INFO);

            Company world = new Company(Main.class.getResourceAsStream("/config.json"));

            world.start();

        } catch (IOException e) {
            System.out.println("Cannot find config file");
        } catch (ParseException e) {
            System.out.println("Wrong config");
        }
    }
}
