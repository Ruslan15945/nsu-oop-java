package ru.nsu.ccfit.morozov.calculator.commandfactory.commands;

import ru.nsu.ccfit.morozov.calculator.CalcData;
import ru.nsu.ccfit.morozov.calculator.commandfactory.Command;
import ru.nsu.ccfit.morozov.calculator.exceptions.EmptyStackException;

public class Plus implements Command {

    @Override
    public void execute(CalcData data, String parameters) {

        if (data.size() < 2)
            throw new EmptyStackException("Plus");

        Double value2 = data.pop();

        Double value1 = data.pop();

        Double result = value1 + value2;

        data.push(result);

    }
}
