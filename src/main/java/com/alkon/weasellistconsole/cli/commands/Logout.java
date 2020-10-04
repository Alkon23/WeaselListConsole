package com.alkon.weasellistconsole.cli.commands;

import com.alkon.weasellistconsole.application.ApplicationContext;
import com.alkon.weasellistconsole.application.PropertyFile;
import com.alkon.weasellistconsole.cli.ReturnCode;

import java.io.IOException;

import static com.alkon.weasellistconsole.application.PropertyFile.CACHED_PASS;
import static com.alkon.weasellistconsole.application.PropertyFile.CACHED_USER;

/**
 * Class for "exit" command.
 * Exits the application
 */
public class Logout extends Command {

    public Logout() {
        super("logout", "Removes the cached user in properties if there is one and exits the application.");
    }

    @Override
    public ReturnCode execute(final String input) {
        if (ApplicationContext.getParam(CACHED_USER) != null) {
            try {
                PropertyFile.removeProperty(CACHED_USER);
                PropertyFile.removeProperty(CACHED_PASS);
            } catch (IOException e) {
                ApplicationContext.setParam(ApplicationContext.EXIT_ERROR, e.getMessage());
                return ReturnCode.ERROR;
            }
        }
        return ReturnCode.EXIT;
    }

}
