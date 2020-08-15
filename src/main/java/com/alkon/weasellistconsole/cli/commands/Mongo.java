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
import com.alkon.weasellistconsole.cli.CommandLineInterpreter;
import com.alkon.weasellistconsole.cli.ReturnCode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.alkon.weasellistconsole.application.Utils.isEmpty;
import static com.alkon.weasellistconsole.cli.Constants.*;

/**
 * Class for "mongo" command.
 * Changes the database connection uri
 */
public class Mongo extends Command {

    public Mongo() {
        super("mongo", "Changes the database connection uri.");
    }

    @Override
    public ReturnCode execute(final String input, final ApplicationContext context) {
        try {
            CommandLineInterpreter cli = getCli();
            File properties = new File(PROPERTIES_FILE);

            boolean correct;
            String userIn;
            String uri = "mongodb://";

            cli.println(PROPERTIES_NOT_FOUND);
            do {
                cli.println(ENTER_VALUES);

                userIn = cli.read(ENTER_HOST);
                uri = uri.concat(isEmpty(userIn) ? "localhost" : userIn);

                userIn = cli.read(ENTER_PORT);
                uri = uri.concat(":").concat(isEmpty(userIn) ? "27017" : userIn);

                userIn = cli.read(ENTER_DATABASE);
                uri = uri.concat("/").concat(isEmpty(userIn) ? "weaseldb" : userIn);

                cli.println("\n" + uri);
                correct = cli.readBoolean(IS_CORRECT);
            } while (!correct);

            FileWriter fw = new FileWriter(properties);
            fw.write(uri);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            context.setParam(EXIT_ERROR, e.getMessage());
            return ReturnCode.ERROR;
        }

        return ReturnCode.EXIT;
    }

}
