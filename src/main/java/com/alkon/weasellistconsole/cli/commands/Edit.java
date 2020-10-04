package com.alkon.weasellistconsole.cli.commands;

import com.alkon.weasellistconsole.application.ApplicationContext;
import com.alkon.weasellistconsole.application.model.*;
import com.alkon.weasellistconsole.application.repo.MongoWrapper;
import com.alkon.weasellistconsole.cli.CommandLineInterpreter;
import com.alkon.weasellistconsole.cli.ReturnCode;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.alkon.weasellistconsole.application.Utils.*;
import static com.alkon.weasellistconsole.cli.Constants.*;

public class Edit extends Command {

    public Edit() {
        super("edit", "Edits an element of the database. " +
                "This element can be either a list, an item in a list, a tag or the user. \n\t" +
                "It must be specified the element type in the command (list/item/tag/user).");
    }

    @Override
    public ReturnCode execute(final String input) {

        switch (input.toLowerCase()) {
            case "list":
                editList();
                break;
            case "item":
                editItem();
                break;
            case "tag":
                editTag();
                break;
            case "user":
                editUser();
                break;
            default:
                getCli().println(getMessage(MISSING_ARGUMENTS, "edit"));
                break;
        }

        return ReturnCode.CONTINUE;
    }

    private void updateUser(User user) {
        user = ((MongoWrapper) ApplicationContext.getParam(ApplicationContext.MONGO_WRAPPER)).saveUser(user);
        ApplicationContext.setParam(ApplicationContext.USER, user);
    }

    private void updateUserAndPass(User user) {
        user = ((MongoWrapper) ApplicationContext.getParam(ApplicationContext.MONGO_WRAPPER)).saveUserAndPass(user);
        ApplicationContext.setParam(ApplicationContext.USER, user);
    }

    private void editUser() {
        final User user = (User) ApplicationContext.getParam(ApplicationContext.USER);
        final MongoWrapper mongoWrapper = (MongoWrapper) ApplicationContext.getParam(ApplicationContext.MONGO_WRAPPER);
        final CommandLineInterpreter cli = getCli();

        boolean updatePassword = false;

        cli.println(CURRENT_VALUES);
        cli.println(ENTER_USERNAME + user.getNick());
        cli.println(ENTER_EMAIL + user.getEmail());

        cli.println(EDIT_VALUES);
        String newValue = cli.read(ENTER_USERNAME).trim();
        if (!newValue.isEmpty()) {
            if (mongoWrapper.usernameExists(newValue)) {
                cli.println(getMessage(ALREADY_USE, USERNAME));
            } else {
                user.setNick(newValue);
            }
        }

        newValue = cli.read(ENTER_EMAIL).trim();
        if (!newValue.isEmpty()) {
            user.setEmail(newValue);
            if (newValue.matches(EMAIL_REGEX)) {
                user.setEmail(newValue);
            } else {
                cli.println(INVALID_MAIL_FORMAT);
            }
        }

        if (cli.readBoolean(EDIT_PASSWORD)) {
            newValue = cli.read(ENTER_PASSWORD);
            if (!newValue.equals(cli.read(REWRITE_PASSWORD))) {
                cli.println(PASSWORD_NOT_EQUAL);
            } else {
                user.setPassword(newValue);
                updatePassword = true;
            }
        }

        if (updatePassword) {
            this.updateUserAndPass(user);
        } else {
            this.updateUser(user);
        }
    }

    private void editTag() {
        final User user = (User) ApplicationContext.getParam(ApplicationContext.USER);
        final CommandLineInterpreter cli = getCli();

        final String tagName = cli.read(getMessage(WHICH_EDIT, TAG));
        final Tag tag = user.getTag(tagName);

        cli.println(CURRENT_VALUES);
        cli.println(ENTER_NAME + tag.getName());
        cli.println(ENTER_COLOR + tag.getColor());

        cli.println(EDIT_VALUES);
        String newValue = cli.read(ENTER_NAME).trim();
        if (!newValue.isEmpty()) {
            if (user.getTag(newValue) != null) {
                cli.println(getMessage(ALREADY_USE, NAME));
            } else {
                tag.setName(newValue);

                for (ItemList list : user.getItemLists()) {
                    list.editTag(tagName, newValue);

                    for (Item item : list.getItems()) {
                        item.editTag(tagName, newValue);
                    }
                }
            }
        }

        newValue = cli.read(ENTER_COLOR).trim();
        if (!newValue.isEmpty()) {
            if (isColor(newValue)) {
                tag.setColor(newValue);
            } else {
                cli.println(getMessage(NOT_A, COLOR));
            }
        }

        this.updateUser(user);
    }

    private void editItem() {
        final User user = (User) ApplicationContext.getParam(ApplicationContext.USER);
        final CommandLineInterpreter cli = getCli();

        final String itemName = cli.read(getMessage(WHICH_EDIT, ITEM));
        final String listName = cli.read(FROM_WHICH_LIST);

        final ItemList list = user.getItemList(listName);
        if (list == null || list.getItem(itemName) == null) {
            cli.println(getMessage(DOESNT_EXIST, ITEM));
            return;
        }
        final Item item = list.getItem(itemName);

        cli.println(CURRENT_VALUES);
        cli.println(ENTER_NAME + item.getName());
        cli.println(ENTER_INFO + item.getInfo());
        cli.println(ENTER_TYPE + item.getType().toString());
        cli.println(ENTER_RATING + item.getRating());
        cli.println(ENTER_TAGS + item.getTags());

        cli.println(EDIT_VALUES);
        String newValue = cli.read(ENTER_NAME).trim();
        if (!newValue.isEmpty()) {
            if (list.getItem(newValue) != null) {
                cli.println(getMessage(ALREADY_USE, NAME));
            } else {
                item.setName(newValue);
            }
        }

        newValue = cli.read(ENTER_INFO).trim();
        if (!newValue.isEmpty()) {
            item.setInfo(newValue);
        }

        newValue = cli.read(ENTER_TYPE).trim();
        if (!newValue.isEmpty()) {
            if (isValidType(newValue)) {
                item.setType(getTypeFromString(newValue));
            } else {
                cli.println(INVALID_OPTION);
            }
        }

        newValue = cli.read(ENTER_RATING).trim();
        if (!newValue.isEmpty()) {
            if (StringUtils.isNumeric(newValue)) {
                item.setRating(getRatingFromString(newValue));
            } else {
                cli.println(getMessage(NOT_A, NUMBER));
            }
        }

        this.editTagsFromTaggable(user, item);

        this.updateUser(user);
    }

    private void editList() {
        final User user = (User) ApplicationContext.getParam(ApplicationContext.USER);
        final CommandLineInterpreter cli = getCli();

        final String listName = cli.read(getMessage(WHICH_EDIT, LIST));
        final ItemList list = user.getItemList(listName);
        if (list == null) {
            cli.println(getMessage(DOESNT_EXIST, LIST));
            return;
        }

        cli.println(CURRENT_VALUES);
        cli.println(ENTER_NAME + list.getName());
        cli.println(ENTER_TAGS + list.getTags());

        cli.println(EDIT_VALUES);
        String newValue = cli.read(ENTER_NAME).trim();
        if (!newValue.isEmpty()) {
            if (user.getItemList(newValue) != null) {
                cli.println(getMessage(ALREADY_USE, NAME));
            } else {
                list.setName(newValue);
            }
        }

        this.editTagsFromTaggable(user, list);

        this.updateUser(user);
    }

    private void editTagsFromTaggable(final User user, final Taggable taggable) {
        final CommandLineInterpreter cli = getCli();
        List<String> tags = taggable.getTagsAsList();
        String newValue;

        int tagNumber;
        try {
            // Empty tag list should only add new ones
            if (tags.isEmpty()) {
                if (cli.readBoolean(ADD_TAGS)) {
                  tagNumber = 1; // Bigger than 0
                } else {
                    return;
                }
            } else {
                tagNumber = Integer.parseInt(cli.read(getMessage(EDIT_TAG, tags.size() + 1, 1, tags.size())));
                tagNumber = Math.abs(tagNumber);
            }
        } catch (NumberFormatException e) {
            cli.println(getMessage(NOT_A, NUMBER));
            return;
        }

        if (tagNumber > tags.size()) {
            newValue = cli.read(ENTER_TAGS).trim();
            if (!newValue.isEmpty()) {
                tags.add(newValue);
                user.getTags().add(new Tag(newValue));
            } else {
                cli.println(getMessage(CANNOT_BE_EMPTY, NAME));
            }
        } else {
            String option = cli.read(RENAME_DELETE);
            if (option.equalsIgnoreCase("rename")) {
                newValue = cli.read(ENTER_TAGS).trim();
                if (!newValue.isEmpty()) {
                    tags.set(tagNumber - 1, newValue);
                } else {
                    cli.println(getMessage(CANNOT_BE_EMPTY, NAME));
                }
            } else if (option.equalsIgnoreCase("delete")) {
                tags.remove(tagNumber);
            } else {
                cli.println(INVALID_OPTION);
            }
        }

        taggable.setTagsFromList(tags);
    }
}
