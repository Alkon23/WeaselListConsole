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
import static com.alkon.weasellistconsole.application.PropertyFile.CACHED_PASS;
import static com.alkon.weasellistconsole.application.PropertyFile.CACHED_USER;
import static com.alkon.weasellistconsole.cli.Constants.*;
import static com.alkon.weasellistconsole.cli.ReturnCode.CONTINUE;

@SpringBootApplication
public class WeaselListConsoleApplication implements CommandLineRunner {

    @Autowired
    private MongoWrapper wrapper;

    public static void main(String[] args) {
        SpringApplication.run(WeaselListConsoleApplication.class, args);
    }

    @Override
    public void run(String... args) {
        ApplicationContext.setParam(ApplicationContext.MONGO_WRAPPER, this.wrapper);
        final CommandLineInterpreter interpreter = CommandLineInterpreter.getInstance();

        // Logs the user into the application
        ReturnCode status = this.loginOrRegister(interpreter);

        if (status == CONTINUE) {
            interpreter.printSpace();
            interpreter.println(getMessage(WELCOME, ((User) ApplicationContext.getParam(USER)).getNick()));
        }

        while (status == CONTINUE) {
            status = interpreter.readCommand();
        }

        if (status == ReturnCode.ERROR) {
            interpreter.printSpace();
            if (ApplicationContext.hasParam(ApplicationContext.EXIT_ERROR)) {
                interpreter.println(getMessage(EXIT_ERROR, ApplicationContext.getParam(ApplicationContext.EXIT_ERROR)));
            } else {
                interpreter.println(EXIT_UNEXPECTED_ERROR);
            }
        }
    }

    private ReturnCode loginOrRegister(final CommandLineInterpreter cli) {
        ReturnCode status = ReturnCode.ERROR;
        String option;

        if (checkCachedUser()) {
            return ReturnCode.CONTINUE;
        }

        do {
            option = cli.read(LOGIN_REGISTER).toLowerCase();

            switch (option) {
                case "login":
                    status = CommandUtils.getCommand(cli.getExistingCommands(), "login")
                            .execute(null);
                    break;
                case "register":
                    status = CommandUtils.getCommand(cli.getExistingCommands(), "register")
                            .execute(null);
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

    private boolean checkCachedUser() {
        final MongoWrapper mongoWrapper = (MongoWrapper) ApplicationContext.getParam(ApplicationContext.MONGO_WRAPPER);
        final String username = (String) ApplicationContext.getParam(CACHED_USER);
        final String pass = (String) ApplicationContext.getParam(CACHED_PASS);
        if (username == null || pass == null) {
            return false;
        }

        User user = mongoWrapper.getUser(username, pass);
        if (user == null) {
            return false;
        }

        ApplicationContext.setParam(ApplicationContext.USER, user);
        return true;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
