package com.alkon.weasellistconsole.cli.commands;

import com.alkon.weasellistconsole.application.ApplicationContext;
import com.alkon.weasellistconsole.application.model.User;
import com.alkon.weasellistconsole.application.repo.MongoWrapper;
import com.alkon.weasellistconsole.cli.CommandLineInterpreter;
import com.alkon.weasellistconsole.cli.ReturnCode;

import static com.alkon.weasellistconsole.cli.Constants.*;

/**
 * Class for Register command.
 * Registers a new user in the database and logs it in. If valid, it stores the {@link User} in the {@link ApplicationContext}.
 */
public class Register extends Command {

    public Register() {
        super("register", "Registers a new user in the database and logs it in");
    }

    @Override
    public ReturnCode execute(final String input) {
        final MongoWrapper mongoWrapper = (MongoWrapper) ApplicationContext.getParam(ApplicationContext.MONGO_WRAPPER);
        final CommandLineInterpreter cli = getCli();

        cli.println(getMessage(FILL_INFO, USER));
        String nick, email, password;

        do {
            nick = cli.read(ENTER_USERNAME);
            if (mongoWrapper.usernameExists(nick)) {
                nick = null;
                cli.println(getMessage(ALREADY_USE_ENTER, USERNAME));
            }
        } while (nick == null);

        do {
            email = cli.read(ENTER_EMAIL);
            if (email.matches(EMAIL_REGEX)) {
                if (mongoWrapper.emailExists(email)) {
                    email = null;
                    cli.println(getMessage(ALREADY_USE_ENTER, EMAIL));
                }
            } else {
                email = null;
                cli.println(INVALID_MAIL_FORMAT);
            }
        } while (email == null);

        do {
            password = cli.read(ENTER_PASSWORD);
            if (!password.equals(cli.read(REWRITE_PASSWORD))) {
                password = null;
                cli.println(PASSWORD_NOT_EQUAL);
            }
        } while (password == null);

        User user = new User();
        user.setNick(nick);
        user.setEmail(email);
        user.setPassword(password);

        boolean confirmation = cli.read(getMessage(CONFIRM_CREATION, USER, nick)).toLowerCase().charAt(0) == YES;
        if (!confirmation) {
            return ReturnCode.EXIT;
        }

        user = mongoWrapper.registerUser(user);
        ApplicationContext.setParam(ApplicationContext.USER, user);
        return ReturnCode.CONTINUE;
    }

}
