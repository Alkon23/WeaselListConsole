package com.alkon.weasellistconsole.cli.commands;

import com.alkon.weasellistconsole.application.ApplicationContext;
import com.alkon.weasellistconsole.application.model.*;
import com.alkon.weasellistconsole.application.repo.MongoWrapper;
import com.alkon.weasellistconsole.cli.CommandLineInterpreter;
import com.alkon.weasellistconsole.cli.ReturnCode;

import java.util.Arrays;

import static com.alkon.weasellistconsole.application.Utils.*;
import static com.alkon.weasellistconsole.cli.Constants.*;

public class Move extends Command {

    public Move() {
        super("move", "Moves an item to another list.\n\t" +
                "Additional arguments are 'item' 'from' 'to' in that order.");
    }

    @Override
    public ReturnCode execute(final String input) {
        final CommandLineInterpreter cli = getCli();
        final String[] args = isEmpty(input) ? new String[0] : input.split(" ");
        String item, from, to;

        switch (args.length) {
            case 0:
                item = cli.read(ENTER_ITEM);
                from = cli.read(ENTER_SOURCE_LIST);
                to = cli.read(ENTER_TARGET_LIST);
                break;
            case 1:
                item = args[0];
                from = cli.read(ENTER_SOURCE_LIST);
                to = cli.read(ENTER_TARGET_LIST);
                break;
            case 2:
                item = args[0];
                from = args[1];
                to = cli.read(ENTER_TARGET_LIST);
                break;
            default: // 3 or more
                item = args[0];
                from = args[1];
                to = listToString(Arrays.asList(Arrays.copyOfRange(args, 2, args.length)), " ");
                break;
        }

        this.move(item, from, to);

        return ReturnCode.CONTINUE;
    }

    private void move(final String itemName,
                      final String sourceListName, final String targetListName) {
        final User user = (User) ApplicationContext.getParam(ApplicationContext.USER);
        final CommandLineInterpreter cli = getCli();

        ItemList targetList = user.getItemList(targetListName);
        ItemList sourceList = user.getItemList(sourceListName);
        if (targetList == null || sourceList == null) {
            cli.println(getMessage(DOESNT_EXIST, LIST));
            return;
        }

        Item item = sourceList.getItem(itemName);
        if (item == null) {
            cli.println(getMessage(DOESNT_EXIST, ITEM));
            return;
        } else if (targetList.getItem(itemName) != null) {
            cli.println(getMessage(ALREADY_USE, ITEM));
        }

        targetList.getItems().add(item);
        sourceList.getItems().remove(item);
        cli.println(getMessage(OPERATION_SUCCESSFUL, ITEM, MOVED));

        this.updateUser(user);
    }

}
