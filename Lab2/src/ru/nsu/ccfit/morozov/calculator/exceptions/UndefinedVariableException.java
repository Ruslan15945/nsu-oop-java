package ru.nsu.ccfit.morozov.calculator.exceptions;

public class UndefinedVariableException extends CommandException {

    public UndefinedVariableException(String name) {
        super("Variable" + name + " not defined");
    }
}
