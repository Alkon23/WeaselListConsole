package com.alkon.weasellistconsole.cli.commands;

import com.alkon.weasellistconsole.application.ApplicationContext;
import com.alkon.weasellistconsole.cli.CommandUtils;
import com.alkon.weasellistconsole.cli.ReturnCode;

import java.util.List;

import static com.alkon.weasellistconsole.cli.Constants.*;

/**
 * Class for "help" command.
 * Displays info about the available commands
 */
public class Help extends Command {

    public Help() {
        super("help", "Displays info about the available commands.\n\t" +
                "If a command name is provided it will display info about that command");
    }

    @Override
    public ReturnCode execute(final String input) {

        if (input != null && !input.isEmpty()) {
            this.printSingle(input);
        } else {
            this.printAll();
        }

        return ReturnCode.CONTINUE;
    }

    private void printSingle(String commandName) {
        Command command = CommandUtils.getCommand(this.getCli().getExistingCommands(), commandName);

        if (command == null) {
            this.getCli().println(getMessage(COMMAND_NOT_FOUND, commandName));
            return;
        }

        this.getCli().println(getMessage(COMMAND_INFO, commandName, command.getDescription()));
    }

    private void printAll() {
        List<Command> commands = this.getCli().getExistingCommands();
        for (Command command : commands) {
            this.getCli().println(getMessage(COMMAND_INFO, command.getCommand(), command.getDescription()));
        }
    }

}
