package com.alkon.weasellistconsole.cli.commands;

import com.alkon.weasellistconsole.application.ApplicationContext;
import com.alkon.weasellistconsole.application.model.ItemList;
import com.alkon.weasellistconsole.application.model.User;
import com.alkon.weasellistconsole.cli.ReturnCode;

import static com.alkon.weasellistconsole.cli.Constants.NO_LIST_FOUND;
import static com.alkon.weasellistconsole.cli.Constants.getMessage;

public class Archive extends Command {

    public Archive() {
        super("archive", "Archives the specified list. " +
                "The list won't show up in the generic 'view' list. \n\t" +
                "To view the archived list's use 'view archived' command.");
    }

    @Override
    public ReturnCode execute(final String input) {
        final User user = (User) ApplicationContext.getParam(ApplicationContext.USER);

        final ItemList list = user.getItemList(input);
        if (list == null) {
            getCli().println(getMessage(NO_LIST_FOUND, input));
        } else {
            list.setArchived(true);
            this.updateUser(user);
        }

        return ReturnCode.CONTINUE;
    }

}
