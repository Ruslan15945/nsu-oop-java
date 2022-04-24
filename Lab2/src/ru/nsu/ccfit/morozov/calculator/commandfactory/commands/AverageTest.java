package ru.nsu.ccfit.morozov.calculator.commandfactory.commands;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.ccfit.morozov.calculator.CalcData;
import ru.nsu.ccfit.morozov.calculator.commandfactory.Command;
import ru.nsu.ccfit.morozov.calculator.commandfactory.CommandFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AverageTest {

    @Test
    public void execute() {

        CalcData data = new CalcData();

        Command command = null;
        try {
            command = CommandFactory.getInstance().createCommand("AVG");
        } catch (IOException e) {
            Assert.fail("Cannot create command");
        }
        data.push(999.);
        data.push(1.);
        data.push(-1.);
        data.push(2.);
        data.push(-2.);
        command.execute(data, "4");
        data.push(0.2);
        command.execute(data, "2");

        Assert.assertEquals(new ArrayList<>(List.of(999., 0.1)), data.getStack());


    }
}