package com.alkon.weasellistconsole.cli.commands;

import com.alkon.weasellistconsole.application.ApplicationContext;
import com.alkon.weasellistconsole.application.model.*;
import com.alkon.weasellistconsole.application.repo.MongoWrapper;
import com.alkon.weasellistconsole.cli.CommandLineInterpreter;
import com.alkon.weasellistconsole.cli.ReturnCode;

import static com.alkon.weasellistconsole.application.Utils.*;
import static com.alkon.weasellistconsole.cli.Constants.*;

public class Add extends Command {

    public Add() {
        super("add", "Adds a new element into the database. " +
                "This element can be either a list, an item in a list, or a tag. \n\t" +
                "It must be specified the element type in the command (list/item/tag).");
    }

    @Override
    public ReturnCode execute(final String input, final ApplicationContext context) {

        switch (input.toLowerCase()) {
            case "list":
                addList(context);
                break;
            case "item":
                addItem(context);
                break;
            case "tag":
                addTag(context);
                break;
            default:
                getCli().println(getMessage(MISSING_ARGUMENTS, "add"));
                break;
        }

        return ReturnCode.CONTINUE;
    }

    private void updateUser(final ApplicationContext context, User user) {
        user = ((MongoWrapper) context.getParam(ApplicationContext.MONGO_WRAPPER)).saveUser(user);
        context.setParam(ApplicationContext.USER, user);
    }

    private void addTag(final ApplicationContext context) {
        final User user = (User) context.getParam(ApplicationContext.USER);
        final CommandLineInterpreter cli = getCli();

        cli.println(getMessage(FILL_INFO, TAG));

        boolean addOther;
        do {
            String tagName = cli.read(ENTER_NAME);
            if (user.getTag(tagName) == null) {
                Tag tag = new Tag(tagName);

                String color = cli.read(ENTER_COLOR).toUpperCase();
                if (isColor(color)) {
                    tag.setColor(color);
                }

                if (cli.readBoolean(getMessage(CONFIRM_CREATION, TAG, tagName))) {
                    user.getTags().add(tag);
                }

                // Add another?
                addOther = cli.readBoolean(getMessage(ADD_ANOTHER, TAG));
            } else {
                cli.println(getMessage(ALREADY_USE_ENTER, TAG));
                addOther = true;
            }
        } while (addOther);

        this.updateUser(context, user);
    }

    private void addTagsToObject(final User user, Taggable obj) {
        final CommandLineInterpreter cli = getCli();
        boolean addOther;

        do {
            addOther = true;
            String tagName = cli.read(TAG + " " + ENTER_NAME);
            if (!tagName.isEmpty()) {
                if (user.getTag(tagName) == null) {
                    cli.println(getMessage(DOESNT_EXIST, TAG));
                    if (cli.readBoolean(getMessage(CONFIRM_CREATION, TAG, tagName))) {
                        user.getTags().add(new Tag(tagName));
                        obj.addTag(tagName);
                    }
                } else {
                    obj.addTag(tagName);
                }
                // Add another?
                addOther = cli.readBoolean(getMessage(ADD_ANOTHER, TAG));
            }
        } while (addOther);
    }

    private void addItemToList(final User user, ItemList list) {
        final CommandLineInterpreter cli = getCli();
        boolean addOther;
        do {
            Item item = new Item();
            String value = cli.read(ENTER_NAME);
            item.setName(value);

            value = cli.read(OPTIONAL + ENTER_TYPE);
            item.setType(getTypeFromString(value));

            value = cli.read(OPTIONAL + ENTER_RATING);
            item.setRating(getRatingFromString(value));

            if (cli.readBoolean(getMessage(ADD_ALSO, TAG))) {
                addTagsToObject(user, item);
            }
            list.getItems().add(item);
            // Add another?
            addOther = cli.readBoolean(getMessage(ADD_ANOTHER, ITEM));
        } while (addOther);
    }

    private void addItem(final ApplicationContext context) {
        User user = (User) context.getParam(ApplicationContext.USER);
        final CommandLineInterpreter cli = getCli();

        ItemList list;
        do {
            String listName = cli.read(WHICH_LIST_ADD);
            list = user.getItemList(listName);
            if (list == null) {
                cli.println(getMessage(DOESNT_EXIST, LIST));
            }
        } while (list == null);

        cli.println(getMessage(FILL_INFO, ITEM));
        this.addItemToList(user, list);

        this.updateUser(context, user);
    }

    private void addList(final ApplicationContext context) {
        User user = (User) context.getParam(ApplicationContext.USER);
        final CommandLineInterpreter cli = getCli();

        cli.println(getMessage(FILL_INFO, LIST));

        final ItemList list = new ItemList();

        // Get list name
        String listName;
        do {
            listName = cli.read(ENTER_NAME);

            if (user.getItemList(listName) != null) {
                cli.println(getMessage(ALREADY_USE_ENTER, LIST));
                listName = null;
            }
        } while (listName == null);
        list.setName(listName);

        // Add tags
        if (cli.readBoolean(getMessage(ADD_ALSO, TAG))) {
            addTagsToObject(user, list);
        }

        // Add items
        if (cli.readBoolean(getMessage(ADD_ALSO, ITEM))) {
            addItemToList(user, list);
        }

        user.getItemLists().add(list);
        this.updateUser(context, user);
    }

}
