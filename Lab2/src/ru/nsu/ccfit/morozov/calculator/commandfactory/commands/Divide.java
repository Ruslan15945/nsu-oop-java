package ru.nsu.ccfit.morozov.calculator.commandfactory.commands;

import ru.nsu.ccfit.morozov.calculator.CalcData;
import ru.nsu.ccfit.morozov.calculator.commandfactory.Command;
import ru.nsu.ccfit.morozov.calculator.exceptions.DivisionByZeroException;
import ru.nsu.ccfit.morozov.calculator.exceptions.EmptyStackException;

public class Divide implements Command {

    @Override
    public void execute(CalcData data, String parameters) {

        if (data.size() < 2)
            throw new EmptyStackException("Divide");

        if (data.peek() == 0.)
            throw new DivisionByZeroException();

        Double value2 = data.pop();

        Double value1 = data.pop();

        Double result = value1 / value2;

        data.push(result);

    }
}
