package com.alkon.weasellistconsole.cli.commands;

import com.alkon.weasellistconsole.application.ApplicationContext;
import com.alkon.weasellistconsole.cli.ReturnCode;

/**
 * Class for "exit" command.
 * Exits the application
 */
public class Exit extends Command {

    public Exit() {
        super("exit", "Exits the application.");
    }

    @Override
    public ReturnCode execute(final String input, final ApplicationContext context) {
        return ReturnCode.EXIT;
    }

}
