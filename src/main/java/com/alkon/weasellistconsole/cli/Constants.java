package com.alkon.weasellistconsole.cli;

import org.apache.commons.lang3.StringUtils;

public final class Constants {

    // Utils
//    public static final String ANSI_RESET = "\u001B[0m";
//    public static final String ANSI_BLACK = "\u001B[30m";
//    public static final String ANSI_RED = "\u001B[31m";
//    public static final String ANSI_GREEN = "\u001B[32m";
//    public static final String ANSI_YELLOW = "\u001B[33m";
//    public static final String ANSI_BLUE = "\u001B[34m";
//    public static final String ANSI_PURPLE = "\u001B[35m";
//    public static final String ANSI_CYAN = "\u001B[36m";
//    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String CLI_PREFIX = "> ";
    public static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    public static final String COLOR_REGEX = "#?([0-9A-F]{6})";
    public static final String PROPERTIES_FILE = "C:\\weasellist\\weasellist.properties";
    public static final String WELCOME = "Welcome %s to Weasel List CLI!";
    public static final char YES = 'y';

    public static final String USER = "user";
    public static final String LIST = "list";
    public static final String ITEM = "item";
    public static final String TAG = "tag";
    public static final String TAGS = "tags";
    public static final String OPTIONAL = "(optional) ";
    public static final String DELETED = "deleted";
    public static final String ADDED = "added";
    public static final String EDITED = "edited";
    public static final String MOVED = "moved";

    // Fields
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String INFO = "information";
    public static final String TYPE = "type";
    public static final String RATING = "rating";
    public static final String COLOR = "color";
    public static final String NUMBER = "number";

    public static final String ENTER_NAME = NAME + ": ";
    public static final String ENTER_TYPE = TYPE + " (book/game/film/series/manga/comic/anime/other): ";
    public static final String ENTER_RATING = RATING + ": ";
    public static final String ENTER_COLOR = COLOR + ": ";
    public static final String ENTER_USERNAME = USERNAME + ": ";
    public static final String ENTER_PASSWORD = PASSWORD + ": ";
    public static final String ENTER_TAGS = TAGS + ": ";
    public static final String ENTER_EMAIL = EMAIL + ": ";
    public static final String ENTER_INFO = INFO + ": ";

    // Shared messages
    public static final String LOGIN_REGISTER = "Please login or register a new account (login/register/exit): ";
    public static final String FILL_INFO = "Please fill the following information in order to create a new %s:";
    public static final String CONFIRM_CREATION = "Do you want to create the %s '%s'? (Y/N)";
    public static final String ALREADY_USE_ENTER = "The %s you entered is already in use, please enter another one.";
    public static final String ALREADY_USE = "The %s you entered is already in use.";
    public static final String DOESNT_EXIST = "The %s you entered doesn't exist.";
    public static final String INVALID_OPTION = "The option you entered is invalid.";
    public static final String NOT_A = "The value you entered is not a %s";
    public static final String OPERATION_SUCCESSFUL = "%s successfully %s\n";
    // Shared error messages
    public static final String COMMAND_NOT_FOUND = "'%s' command not found";
    public static final String UNEXPECTED_ERROR = "[ERROR] Unexpected error:";
    public static final String EXIT_ERROR = "Exiting application due to an error: %s";
    public static final String EXIT_UNEXPECTED_ERROR = "Exiting application due to an unexpected error.";
    public static final String MISSING_ARGUMENTS = "There are missing arguments. Use \"help %s\" to see more.";
    // Login command
    public static final String INVALID_USER = "Invalid user and password combination.";
    public static final String RETRY_LOGIN = "Do you want to retry the login (%d tries left)? (Y/N): ";
    public static final String STORE_USER = "Do you want to keep the user logged in? (Y/N): ";
    // Register command
    public static final String REWRITE_PASSWORD = "Rewrite the password: ";
    public static final String PASSWORD_NOT_EQUAL = "The passwords doesn't match, please write it again.";
    public static final String INVALID_MAIL_FORMAT = "Invalid email format.";
    // Help command
    public static final String COMMAND_INFO = "%s -> %s";
    // View command
    public static final String NO_LIST_FOUND = "There is no lists named '%s'";
    public static final String ITEM_LISTS = "Item Lists";
    public static final String ITEM_NAME = "Item Name";
    public static final String LABELS = "Labels";
    public static final String AVAILABLE_TAGS = "Available Tags";
    // Add command
    public static final String ADD_ALSO = "Do you want to add any %s? (Y/N)";
    public static final String ADD_ANOTHER = "Do you want to add another %s? (Y/N)";
    public static final String WHICH_LIST_ADD = "To which list do you want to add the new item?";
    // Delete command
    public static final String WHICH_LIST_DELETE = "From which list do you want to delete the %s?";
    public static final String WHICH_ITEM_DELETE = "From which item do you want to delete the %s?";
    public static final String TAG_FROM_WHERE = "From where do you want to delete the tag? (list/item/user)";
    public static final String CONFIRM_DELETION = "Do you want to delete the %s '%s'? (Y/N)";
    // Edit command
    public static final String CURRENT_VALUES = "These are the current values:";
    public static final String EDIT_VALUES = "Introduce the new values (leave it empty to maintain current value): ";
    public static final String WHICH_EDIT = "Which %s do you want to edit? ";
    public static final String EDIT_PASSWORD = "Do you want to change the password? ";
    public static final String EDIT_TAG = "Do you want to add a new tag (%d) or edit an existing one (%d-%d) ?";
    public static final String RENAME_DELETE = "Do you want to rename or delete it (rename/delete)?";
    public static final String CANNOT_BE_EMPTY = "The %s cannot be empty.";
    public static final String FROM_WHICH_LIST = "From which list do you want to edit the item? ";
    public static final String ADD_TAGS = "The tag list is empty, do you want to add a new one (Y/N)?";
    // Move command
    public static final String ENTER_SOURCE_LIST = "From: ";
    public static final String ENTER_TARGET_LIST = "To: ";
    public static final String ENTER_ITEM = ITEM + ": ";
    // Mongo command
    public static final String PROPERTIES_NOT_FOUND = "Database connection not found. Please enter a new one:";
    public static final String ENTER_VALUES = "To create a new connection enter the following values or leave them empty for default values:";
    public static final String ENTER_HOST = "Host (default localhost): ";
    public static final String ENTER_PORT = "Port (default 27017): ";
    public static final String ENTER_DATABASE = "Database (default weaseldb): ";
    public static final String IS_CORRECT = "Is this connection uri correct? (y/n) ";

    public static String getMessage(String template, Object... params) {
        return StringUtils.capitalize(String.format(template, params));
    }

}
