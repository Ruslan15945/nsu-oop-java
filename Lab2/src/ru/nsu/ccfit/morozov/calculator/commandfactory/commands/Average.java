package ru.nsu.ccfit.morozov.calculator.commandfactory.commands;

import ru.nsu.ccfit.morozov.calculator.CalcData;
import ru.nsu.ccfit.morozov.calculator.commandfactory.Command;
import ru.nsu.ccfit.morozov.calculator.exceptions.EmptyStackException;
import ru.nsu.ccfit.morozov.calculator.exceptions.WrongArgumentsExpression;

public class Average implements Command {

    @Override
    public void execute(CalcData data, String parameters) {

        int n = 0;

        if (0 < parameters.length()) {
            try {
                n = Integer.parseInt(parameters.replaceAll("[^0-9]", ""));
            }
            catch (NumberFormatException e){
                throw new WrongArgumentsExpression("Average", parameters);
            }
        }
        if (n <= 0)
            throw new WrongArgumentsExpression("Average", parameters);

        if (data.size() < n)
            throw new EmptyStackException("Average");

        double sum = 0;

        for (int i = 0; i < n; ++i){
            sum += data.pop();
        }

        data.push(sum / n);

    }
}
