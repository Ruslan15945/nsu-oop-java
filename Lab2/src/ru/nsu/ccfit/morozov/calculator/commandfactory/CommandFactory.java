package ru.nsu.ccfit.morozov.calculator.commandfactory;


import ru.nsu.ccfit.morozov.calculator.exceptions.CommandNotFoundException;
import ru.nsu.ccfit.morozov.calculator.exceptions.PropertiesLoadException;

import java.io.IOException;
import java.util.Properties;

public class CommandFactory {

    private static CommandFactory instance = null;

    private final Properties properties = new Properties();



    private CommandFactory() {

        try {
            properties.load(CommandFactory.class.getResourceAsStream("config.properties"));
        }
        catch (Exception e) {
            throw new PropertiesLoadException();
        }
    }

    public static CommandFactory getInstance() throws IOException {
        CommandFactory localInstance = instance;

        if (localInstance == null) {
            synchronized (CommandFactory.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CommandFactory();
                }
            }
        }
        return localInstance;
    }

    public Command createCommand(String commandName)
    {

        String commandClassName = properties.getProperty(commandName);
        if (commandClassName == null)
            throw new CommandNotFoundException(commandName);

        Command command;
        try {
            command = (Command) Class.forName(commandClassName).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new CommandNotFoundException(commandName);
        }

        return command;
    }

}
