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

package com.alkon.weasellistconsole;

import com.alkon.weasellistconsole.application.ApplicationContext;
import com.alkon.weasellistconsole.application.model.User;
import com.alkon.weasellistconsole.application.repo.MongoWrapper;
import com.alkon.weasellistconsole.cli.CommandLineInterpreter;
import com.alkon.weasellistconsole.cli.CommandUtils;
import com.alkon.weasellistconsole.cli.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.alkon.weasellistconsole.application.ApplicationContext.USER;
import static com.alkon.weasellistconsole.cli.Constants.*;

@SpringBootApplication
public class WeaselListConsoleApplication implements CommandLineRunner {

    @Autowired
    private MongoWrapper wrapper;

    public static void main(String[] args) {
        SpringApplication.run(WeaselListConsoleApplication.class, args);
    }

    @Override
    public void run(String... args) {
        final ApplicationContext applicationContext = new ApplicationContext();
        applicationContext.setParam(ApplicationContext.MONGO_WRAPPER, this.wrapper);
        final CommandLineInterpreter interpreter = CommandLineInterpreter.getInstance();

        // Logs the user into the application
        ReturnCode status = this.loginOrRegister(interpreter, applicationContext);

        if (status == ReturnCode.CONTINUE) {
            interpreter.printSpace();
            interpreter.println(getMessage(WELCOME, ((User) applicationContext.getParam(USER)).getNick()));
        }

        while (status == ReturnCode.CONTINUE) {
            status = interpreter.readCommand(applicationContext);
        }

        if (status == ReturnCode.ERROR) {
            interpreter.printSpace();
            if (applicationContext.hasParam(ApplicationContext.EXIT_ERROR)) {
                interpreter.println(getMessage(EXIT_ERROR, applicationContext.getParam(ApplicationContext.EXIT_ERROR)));
            } else {
                interpreter.println(EXIT_UNEXPECTED_ERROR);
            }
        }
    }

    private ReturnCode loginOrRegister(final CommandLineInterpreter cli, final ApplicationContext context) {
        ReturnCode status = ReturnCode.ERROR;
        String option;

        do {
            option = cli.read(LOGIN_REGISTER).toLowerCase();

            switch (option) {
                case "login":
                    status = CommandUtils.getCommand(cli.getExistingCommands(), "login")
                            .execute(null, context);
                    break;
                case "register":
                    status = CommandUtils.getCommand(cli.getExistingCommands(), "register")
                            .execute(null, context);
                    if (status == ReturnCode.EXIT) {
                        option = null;
                    }
                    break;
                case "exit":
                    status = ReturnCode.EXIT;
                    break;
                default:
                    option = null;
                    break;
            }
        } while (option == null);

        return status;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
