package ru.nsu.ccfit.morozov.calculator.exceptions;

public class SqrtOfNegativeValueException extends ArithmeticalException {

    public SqrtOfNegativeValueException() {
        super("You cannot find square root of a negative number");
    }
}
