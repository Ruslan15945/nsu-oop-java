package ru.nsu.ccfit.morozov.calculator.exceptions;

public class WrongArgumentsExpression extends CommandException {

    public WrongArgumentsExpression(String command) {
        super("Command \"" + command + "\": cannot parse arguments");
    }

    public WrongArgumentsExpression(String command, String arguments) {
        super("Command \"" + command + "\": cannot parse arguments [ " + arguments + " ]");
    }
}
