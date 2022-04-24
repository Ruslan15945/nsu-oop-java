package ru.nsu.ccfit.morozov.calculator.exceptions;

public class EmptyStackException extends CommandException {

    public EmptyStackException(String command) {
        super("There is not enough arguments to calculate " + command);
    }
}
