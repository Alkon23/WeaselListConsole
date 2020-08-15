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
import com.alkon.weasellistconsole.application.model.User;
import com.alkon.weasellistconsole.application.repo.MongoWrapper;
import com.alkon.weasellistconsole.cli.CommandLineInterpreter;
import com.alkon.weasellistconsole.cli.ReturnCode;

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
    public ReturnCode execute(final String input, final ApplicationContext context) {
        final MongoWrapper mongoWrapper = (MongoWrapper) context.getParam(ApplicationContext.MONGO_WRAPPER);
        final CommandLineInterpreter cli = getCli();

        User user;
        boolean retry;
        int triesLeft = 3;
        do {
            retry = false;

            cli.printSpace();
            final String username = cli.read(ENTER_USERNAME);
            final String password = cli.read(ENTER_PASSWORD);

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

        context.setParam(ApplicationContext.USER, user);
        return ReturnCode.CONTINUE;
    }

}
