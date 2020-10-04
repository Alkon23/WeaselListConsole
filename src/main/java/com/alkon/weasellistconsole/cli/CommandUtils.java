package com.alkon.weasellistconsole.cli;

import com.alkon.weasellistconsole.cli.commands.*;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CommandUtils {

    /**
     * Searches the given command in the given command list
     *
     * @param commandList List of commands to search in
     * @param command     Name of the command to search
     * @return The found command. Null if no command found
     */
    public static Command getCommand(@NonNull final List<Command> commandList, @NonNull final String command) {
        for (Command c : commandList) {
            if (c.getCommand().equalsIgnoreCase(command)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Load all the available commands into a List
     *
     * @return A List containing all the available commands
     */
    public static List<Command> loadCommands() {
        List<Command> commands = new ArrayList<>();
        commands.add(new Exit());
        commands.add(new Help());
        commands.add(new Login());
        commands.add(new Logout());
        commands.add(new Register());
        commands.add(new View());
        commands.add(new Add());
        commands.add(new Remove());
        commands.add(new Edit());
        commands.add(new Move());
        commands.add(new Mongo());

        return commands;
    }

}
