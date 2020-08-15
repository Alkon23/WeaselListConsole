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
