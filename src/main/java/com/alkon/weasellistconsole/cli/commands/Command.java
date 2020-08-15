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
import com.alkon.weasellistconsole.application.repo.MongoWrapper;
import com.alkon.weasellistconsole.cli.CommandLineInterpreter;
import com.alkon.weasellistconsole.cli.ReturnCode;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public abstract class Command {

    @Autowired
    private MongoWrapper mongoWrapper;
    private String command;
    private String description;
    private CommandLineInterpreter cli;

    public Command(String command, String description) {
        this.command = command;
        this.description = description;
        this.cli = CommandLineInterpreter.getInstance();
    }

    /**
     * Executes the command
     * @return A valid ReturnCode depending on the execution result
     */
    public abstract ReturnCode execute(final String input, final ApplicationContext context);

}
