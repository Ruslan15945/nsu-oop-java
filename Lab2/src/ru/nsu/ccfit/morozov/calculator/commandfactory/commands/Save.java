package ru.nsu.ccfit.morozov.calculator.commandfactory.commands;

import ru.nsu.ccfit.morozov.calculator.CalcData;
import ru.nsu.ccfit.morozov.calculator.commandfactory.Command;
import ru.nsu.ccfit.morozov.calculator.exceptions.WrongArgumentsExpression;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Save implements Command {

    @Override
    public void execute(CalcData data, String parameters) {

        int currentSymbolIndex = 0;

        while ((currentSymbolIndex < parameters.length()) && !Character.isWhitespace(parameters.charAt(currentSymbolIndex)))
            currentSymbolIndex++;

        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(parameters.substring(0,currentSymbolIndex)));
            for ( Double i : data.getStack()) {
                writer.write(i.toString() + System.lineSeparator());
            }
        }
        catch (IOException e)
        {
            throw  new WrongArgumentsExpression("Save", parameters);
        }
        finally
        {
            if (null != writer)
            {
                try
                {
                    writer.close();
                }
                catch (IOException e)
                {

                }
            }
        }

    }
}
