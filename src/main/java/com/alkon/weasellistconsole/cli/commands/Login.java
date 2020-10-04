package com.alkon.weasellistconsole.cli.commands;

import com.alkon.weasellistconsole.application.ApplicationContext;
import com.alkon.weasellistconsole.application.PropertyFile;
import com.alkon.weasellistconsole.application.model.User;
import com.alkon.weasellistconsole.application.repo.MongoWrapper;
import com.alkon.weasellistconsole.cli.CommandLineInterpreter;
import com.alkon.weasellistconsole.cli.ReturnCode;

import java.io.IOException;

import static com.alkon.weasellistconsole.application.PropertyFile.CACHED_PASS;
import static com.alkon.weasellistconsole.application.PropertyFile.CACHED_USER;
import static com.alkon.weasellistconsole.cli.Constants.*;

/**
 * Class for Login command.
 * Asks for a valid auth and logs into the application. If valid, it stores the {@link User} in the {@link ApplicationContext}.
 */
public class Login extends Command {

    public Login() {
        super("login", "Asks for a valid auth and logs into the application");
    }

    @Override
    public ReturnCode execute(final String input) {
        final MongoWrapper mongoWrapper = (MongoWrapper) ApplicationContext.getParam(ApplicationContext.MONGO_WRAPPER);
        final CommandLineInterpreter cli = getCli();

        User user;
        String username, password;
        boolean retry;
        int triesLeft = 3;
        do {
            retry = false;

            cli.printSpace();
            username = cli.read(ENTER_USERNAME);
            password = cli.read(ENTER_PASSWORD);

            user = mongoWrapper.getUser(username, password);

            if (user == null) {
                cli.println(INVALID_USER);
                if (triesLeft > 0) {
                    retry = cli.read(getMessage(RETRY_LOGIN, triesLeft)).toLowerCase().charAt(0) == YES;
                    triesLeft--;
                }
            }
        } while (retry);

        if (user == null) {
            return ReturnCode.EXIT; // Return EXIT instead of ERROR because error was previously displayed
        }

        ApplicationContext.setParam(ApplicationContext.USER, user);
        if (cli.readBoolean(STORE_USER)) {
            try {
                PropertyFile.writeProperty(CACHED_USER, username);
                PropertyFile.writeProperty(CACHED_PASS, password);
            } catch (IOException e) {
                ApplicationContext.setParam(EXIT_ERROR, e.getMessage());
                return ReturnCode.ERROR;
            }
        }

        return ReturnCode.CONTINUE;
    }

}
