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
