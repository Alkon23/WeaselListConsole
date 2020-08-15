/*
 * WeaselList Console. Copyright (c) 2020.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
    public ReturnCode execute(final String input, final ApplicationContext context) {

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
