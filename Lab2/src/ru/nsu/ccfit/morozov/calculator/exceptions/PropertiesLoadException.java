package ru.nsu.ccfit.morozov.calculator.exceptions;

public class PropertiesLoadException extends FactoryException{

    public PropertiesLoadException(){
        super("Cannot load properties file");
    }

}
