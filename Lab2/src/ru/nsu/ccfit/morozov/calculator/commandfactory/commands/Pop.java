package ru.nsu.ccfit.morozov.calculator.commandfactory.commands;

import ru.nsu.ccfit.morozov.calculator.CalcData;
import ru.nsu.ccfit.morozov.calculator.commandfactory.Command;

public class Pop implements Command {

    @Override
    public void execute(CalcData data, String parameters) {

        if (!data.empty())
            data.pop();

    }
}
