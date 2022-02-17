package ru.nsu.ccfit.morozov.calculator.commandfactory.commands;

import ru.nsu.ccfit.morozov.calculator.CalcData;
import ru.nsu.ccfit.morozov.calculator.commandfactory.Command;
import ru.nsu.ccfit.morozov.calculator.exceptions.EmptyStackException;
import ru.nsu.ccfit.morozov.calculator.exceptions.SqrtOfNegativeValueException;

public class Sqrt implements Command {

    @Override
    public void execute(CalcData data, String parameters) {

        if (data.empty())
            throw new EmptyStackException("Sqrt");

        if (data.peek() < 0)
            throw new SqrtOfNegativeValueException();

        Double value = data.pop();

        Double result = Math.sqrt(value);

        data.push(result);

    }
}
