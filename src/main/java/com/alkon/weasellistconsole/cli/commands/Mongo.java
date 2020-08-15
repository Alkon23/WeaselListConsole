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
