package ru.nsu.ccfit.morozov.calculator.commandfactory.commands;

import ru.nsu.ccfit.morozov.calculator.CalcData;
import ru.nsu.ccfit.morozov.calculator.commandfactory.Command;
import ru.nsu.ccfit.morozov.calculator.exceptions.EmptyStackException;

public class Absolute implements Command {

    @Override
    public void execute(CalcData data, String parameters) {

        if (data.empty())
            throw new EmptyStackException("absolute");

        Double value = data.pop();

        Double result = Math.abs(value);

        data.push(result);

    }
}
