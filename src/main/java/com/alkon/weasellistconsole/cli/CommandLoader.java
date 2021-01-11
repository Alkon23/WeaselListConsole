package com.alkon.weasellistconsole.cli;

import com.alkon.weasellistconsole.cli.commands.*;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandLoader {

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
        return Stream.of(
                new Exit(),
                new Help(),
                new Login(),
                new Logout(),
                new Register(),
                new View(),
                new Add(),
                new Remove(),
                new Edit(),
                new Move(),
                new Mongo(),
                new Archive(),
                new Unarchive()
        ).collect(Collectors.toList());
    }

}
