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

import com.alkon.weasellistconsole.application.ApplicationContext;
import com.alkon.weasellistconsole.cli.commands.Command;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.alkon.weasellistconsole.application.Utils.listToString;
import static com.alkon.weasellistconsole.cli.CommandUtils.*;
import static com.alkon.weasellistconsole.cli.Constants.*;

public class CommandLineInterpreter {

    private static CommandLineInterpreter instance;
    private Scanner scanner;
    @Getter
    private List<Command> existingCommands;

    private CommandLineInterpreter() {
        this.scanner = new Scanner(System.in);
    }

    public static CommandLineInterpreter getInstance() {
        if (instance == null) {
            instance = new CommandLineInterpreter();
            instance.existingCommands = CommandUtils.loadCommands();
        }
        return instance;
    }

    /**
     * Reads a command entered by the user and executes it
     *
     * @param context Application Context
     * @return The {@link ReturnCode} returned by the command execution. If there is any unexpected error in the execution, a {@link ReturnCode#ERROR} is returned
     */
    public ReturnCode readCommand(final ApplicationContext context) {
        try {
            final String[] input = this.read().split(" ");

            final Command command = getCommand(this.existingCommands, input[0]);
            if (command == null) {
                this.println(getMessage(COMMAND_NOT_FOUND, input[0]));
                return ReturnCode.CONTINUE;
            }

            String inputText = "";

            if (input.length > 1) {
                inputText = listToString(Arrays.asList(input).subList(1, input.length), " ");
            }

            return command.execute(inputText, context);
        } catch (Exception e) {
            this.println(getMessage(UNEXPECTED_ERROR));
            e.printStackTrace();
            return ReturnCode.ERROR;
        }
    }

    /**
     * Reads user input
     *
     * @return user input
     */
    public String read() {
        return this.read("", false);
    }

    public boolean readBoolean(String message) {
        return this.read(message, false).toLowerCase().charAt(0) == YES;
    }


    /**
     * Writes the given message and reads user input (in the same line)
     *
     * @param message Message to display
     * @return User input
     */
    public String read(String message) {
        return this.read(message, false);
    }

    /**
     * Writes the given message and reads user input
     *
     * @param message Message to display
     * @param newLine Boolean flag to check whether to read user input in a new line
     * @return User input
     */
    public String read(String message, boolean newLine) {
        if (!message.endsWith(" ")) {
            message = message.concat(" ");
        }

        if (newLine) {
            this.println(message);
            this.print("");
        } else {
            this.print(message);
        }

        return this.scanner.nextLine();
    }

    public void println(String text) {
        System.out.println(CLI_PREFIX + text);
    }

    public void printSpace() {
        System.out.println();
    }

    public void print(String text) {
        System.out.print(CLI_PREFIX + text);
    }

}
