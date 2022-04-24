package ru.nsu.ccfit.morozov.calculator.commandfactory;

import ru.nsu.ccfit.morozov.calculator.CalcData;

public interface Command {

    void execute(CalcData data, String parameters);

}
