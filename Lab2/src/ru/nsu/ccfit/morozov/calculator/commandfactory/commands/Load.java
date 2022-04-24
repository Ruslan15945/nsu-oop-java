package ru.nsu.ccfit.morozov.calculator.commandfactory.commands;

import ru.nsu.ccfit.morozov.calculator.CalcData;
import ru.nsu.ccfit.morozov.calculator.commandfactory.Command;
import ru.nsu.ccfit.morozov.calculator.exceptions.WrongArgumentsExpression;

import java.io.*;
import java.util.Locale;
import java.util.Scanner;

public class Load implements Command {

    @Override
    public void execute(CalcData data, String parameters) {

        int currentSymbolIndex = 0;

        while ((currentSymbolIndex < parameters.length()) && !Character.isWhitespace(parameters.charAt(currentSymbolIndex)))
            currentSymbolIndex++;

        Scanner reader = null;
        try
        {
            reader = new Scanner(new File(parameters.substring(0,currentSymbolIndex))).useDelimiter("\\s").useLocale(Locale.US);
            while (reader.hasNext()) {
                if (reader.hasNextDouble())
                    data.push(reader.nextDouble());
                else
                    reader.next();
            }

        }
        catch (IOException e)
        {
            throw  new WrongArgumentsExpression("Load", parameters);
        }
        finally
        {
            if (null != reader)
            {
                reader.close();
            }
        }

    }
}
