package ru.nsu.ccfit.morozov.calculator.commandfactory.commands;

import org.junit.Assert;
import ru.nsu.ccfit.morozov.calculator.CalcData;
import ru.nsu.ccfit.morozov.calculator.commandfactory.Command;
import ru.nsu.ccfit.morozov.calculator.commandfactory.CommandFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AbsoluteTest {

    @org.junit.Test
    public void execute() {

        CalcData data = new CalcData();

        Command command = null;
        try {
            command = CommandFactory.getInstance().createCommand("ABS");
        } catch (IOException e) {
            Assert.fail("Cannot create command");
        }
        data.push(-189.189);
        command.execute(data, "");
        data.push(981.981);
        command.execute(data, "");
        Assert.assertEquals(new ArrayList<>(List.of(189.189, 981.981)), data.getStack());

    }
}