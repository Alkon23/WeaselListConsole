package com.alkon.weasellistconsole.cli.commands;

import com.alkon.weasellistconsole.application.ApplicationContext;
import com.alkon.weasellistconsole.application.model.Item;
import com.alkon.weasellistconsole.application.model.ItemList;
import com.alkon.weasellistconsole.application.model.Tag;
import com.alkon.weasellistconsole.application.model.User;
import com.alkon.weasellistconsole.application.repo.MongoWrapper;
import com.alkon.weasellistconsole.cli.CommandLineInterpreter;
import com.alkon.weasellistconsole.cli.ReturnCode;

import static com.alkon.weasellistconsole.cli.Constants.*;

public class Remove extends Command {


    public Remove() {
        super("remove", "Removes an element from the database" +
                "This element can be either a list, an item in a list, or a tag. \n\t" +
                "It must be specified the element type in the command (list/item/tag).");
    }

    @Override
    public ReturnCode execute(final String input, final ApplicationContext context) {
        switch (input.toLowerCase()) {
            case "list":
                this.removeList(context);
                break;
            case "item":
                this.removeItem(context);
                break;
            case "tag":
                this.removeTag(context);
                break;
            default:
                this.getCli().println(getMessage(MISSING_ARGUMENTS, "remove"));
                break;
        }

        return ReturnCode.CONTINUE;
    }

    private void removeList(final ApplicationContext context) {
        final User user = (User) context.getParam(ApplicationContext.USER);
        final CommandLineInterpreter cli = getCli();

        String input = cli.read(LIST + " " + ENTER_NAME);
        ItemList list = user.getItemList(input);
        if (list == null) {
            cli.println(getMessage(DOESNT_EXIST, LIST));
            return;
        }

        if (cli.readBoolean(getMessage(CONFIRM_DELETION, LIST, input))) {
            user.getItemLists().remove(list);
            this.updateUser(context, user);
            cli.println(getMessage(OPERATION_SUCCESSFUL, LIST, DELETED));
        }
    }

    private void removeItem(final ApplicationContext context) {
        final User user = (User) context.getParam(ApplicationContext.USER);
        final CommandLineInterpreter cli = getCli();

        String listName = cli.read(getMessage(WHICH_LIST_DELETE, ITEM));
        ItemList list = user.getItemList(listName);
        if (list == null) {
            cli.println(getMessage(DOESNT_EXIST, LIST));
            return;
        }

        String input = cli.read(ITEM + " " + ENTER_NAME);
        if (list.getItem(input) == null) {
            cli.println(getMessage(DOESNT_EXIST, ITEM));
            return;
        }

        if (cli.readBoolean(getMessage(CONFIRM_DELETION, ITEM, input))) {
            list.getItems().remove(list.getItem(input));
            user.setItemList(list);
            this.updateUser(context, user);
            cli.println(getMessage(OPERATION_SUCCESSFUL, LIST, DELETED));
        }
    }

    private void removeTag(final ApplicationContext context) {
        final User user = (User) context.getParam(ApplicationContext.USER);
        final CommandLineInterpreter cli = getCli();

        Tag tag = readTag(user);
        if (tag == null) return;
        String tagName = tag.getName();

        switch (cli.read(TAG_FROM_WHERE)) {
            case LIST:
                String listName = cli.read(getMessage(WHICH_LIST_DELETE, TAG));
                ItemList list = user.getItemList(listName);
                if (list == null) {
                    cli.println(getMessage(DOESNT_EXIST, LIST));
                    return;
                }
                if (cli.readBoolean(getMessage(CONFIRM_DELETION, TAG, tagName))) {
                    list.removeTag(tagName);
                    user.setItemList(list);
                    this.updateUser(context, user);
                    cli.println(getMessage(OPERATION_SUCCESSFUL, TAG, DELETED));
                }
                break;
            case ITEM:
                listName = cli.read(getMessage(WHICH_LIST_DELETE, TAG));
                list = user.getItemList(listName);
                if (list == null) {
                    cli.println(getMessage(DOESNT_EXIST, LIST));
                    return;
                }
                String itemName = cli.read(getMessage(WHICH_ITEM_DELETE, TAG));
                Item item = list.getItem(itemName);
                if (item == null) {
                    cli.println(getMessage(DOESNT_EXIST, ITEM));
                    return;
                }
                if (cli.readBoolean(getMessage(CONFIRM_DELETION, TAG, tagName))) {
                    item.removeTag(tagName);
                    list.setItem(item);
                    user.setItemList(list);
                    this.updateUser(context, user);
                    cli.println(getMessage(OPERATION_SUCCESSFUL, TAG, DELETED));
                }
                break;
            case USER:
                if (cli.readBoolean(getMessage(CONFIRM_DELETION, TAG, tagName))) {
                    user.getTags().remove(tag);
                    for (ItemList itemList : user.getItemLists()) {
                        if (itemList.hasTag(tagName)) {
                            itemList.removeTag(tagName);
                        }
                        for (Item i : itemList.getItems()) {
                            if (i.hasTag(tagName)) {
                                i.removeTag(tagName);
                            }
                        }
                    }
                    this.updateUser(context, user);
                    cli.println(getMessage(OPERATION_SUCCESSFUL, TAG, DELETED));
                }
                break;
            default:
                cli.println(INVALID_OPTION);
                break;
        }
    }

    private Tag readTag(final User user) {
        final CommandLineInterpreter cli = getCli();
        String input = cli.read(TAG + " " + ENTER_NAME);
        Tag tag = user.getTag(input);
        if (tag == null) {
            cli.println(getMessage(DOESNT_EXIST, TAG));
            return null;
        }
        return tag;
    }

    private void updateUser(final ApplicationContext context, User user) {
        user = ((MongoWrapper) context.getParam(ApplicationContext.MONGO_WRAPPER)).saveUser(user);
        context.setParam(ApplicationContext.USER, user);
    }

}
