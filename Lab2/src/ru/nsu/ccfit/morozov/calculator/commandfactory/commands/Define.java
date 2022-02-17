package ru.nsu.ccfit.morozov.calculator.commandfactory.commands;

import ru.nsu.ccfit.morozov.calculator.CalcData;
import ru.nsu.ccfit.morozov.calculator.commandfactory.Command;
import ru.nsu.ccfit.morozov.calculator.exceptions.WrongArgumentsExpression;

public class Define implements Command {

    @Override
    public void execute(CalcData data, String parameters) {
        int currentSymbolIndex = 0;

        while ((currentSymbolIndex < parameters.length()) && !Character.isWhitespace(parameters.charAt(currentSymbolIndex)))
            currentSymbolIndex++;

        String variableName = parameters.substring(0,currentSymbolIndex++);

        double variableValue;
        if (currentSymbolIndex < parameters.length()) {
            try{
                variableValue = Double.parseDouble(parameters.substring(currentSymbolIndex).replaceAll(",", ".").replaceAll("[^0-9.-]", ""));
            }
            catch (NumberFormatException e){
                throw new WrongArgumentsExpression("Define", parameters);
            }
        }
        else{
            throw new WrongArgumentsExpression("Define", parameters);
        }
        data.add(variableName, variableValue);

    }
}
