package com.alkon.weasellistconsole.cli.commands;

import com.alkon.weasellistconsole.application.ApplicationContext;
import com.alkon.weasellistconsole.application.model.ItemList;
import com.alkon.weasellistconsole.application.model.User;
import com.alkon.weasellistconsole.cli.ReturnCode;

import static com.alkon.weasellistconsole.cli.Constants.NO_LIST_FOUND;
import static com.alkon.weasellistconsole.cli.Constants.getMessage;

public class Unarchive extends Command {

    public Unarchive() {
        super("unarchive", "Unarchives the specified list.");
    }

    @Override
    public ReturnCode execute(final String input) {
        final User user = (User) ApplicationContext.getParam(ApplicationContext.USER);

        final ItemList list = user.getItemList(input);
        if (list == null) {
            getCli().println(getMessage(NO_LIST_FOUND, input));
        } else {
            list.setArchived(false);
            this.updateUser(user);
        }

        return ReturnCode.CONTINUE;
    }

}
