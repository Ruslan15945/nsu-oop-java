package ru.nsu.ccfit.morozov.calculator.commandfactory.commands;

import ru.nsu.ccfit.morozov.calculator.CalcData;
import ru.nsu.ccfit.morozov.calculator.commandfactory.Command;
import ru.nsu.ccfit.morozov.calculator.exceptions.UndefinedVariableException;
import ru.nsu.ccfit.morozov.calculator.exceptions.WrongArgumentsExpression;

public class Push implements Command {

    @Override
    public void execute(CalcData data, String parameters) {

        int currentSymbolIndex = 0;

        while ((currentSymbolIndex < parameters.length()) && !Character.isWhitespace(parameters.charAt(currentSymbolIndex)))
            currentSymbolIndex++;

        Double value;
        if (Character.isAlphabetic(parameters.charAt(0))) {
            value = data.get(parameters.substring(0, currentSymbolIndex));
            if (value == null)
                throw new UndefinedVariableException(parameters.substring(0, currentSymbolIndex));
        }
        else {
            try {

                value = Double.parseDouble(parameters.substring(0, currentSymbolIndex).replaceAll(",", ".").replaceAll("[^0-9.-]", ""));
            }
            catch (NumberFormatException e){
                throw new WrongArgumentsExpression(parameters.substring(0, currentSymbolIndex));
            }
        }

        data.push(value);

    }
}
