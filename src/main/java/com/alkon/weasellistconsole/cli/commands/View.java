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
import com.alkon.weasellistconsole.application.model.Item;
import com.alkon.weasellistconsole.application.model.ItemList;
import com.alkon.weasellistconsole.application.model.Tag;
import com.alkon.weasellistconsole.application.model.User;
import com.alkon.weasellistconsole.cli.CommandLineInterpreter;
import com.alkon.weasellistconsole.cli.ReturnCode;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.alkon.weasellistconsole.application.Utils.isEmpty;
import static com.alkon.weasellistconsole.cli.Constants.*;

public class View extends Command {

    public View() {
        super("view", "Shows the list content as a table.\n\t " +
                "If no list name specified it will show the list of available lists.\n\t" +
                "If the name specified is 'tags' it will show the available tags.");
    }

    @Override
    public ReturnCode execute(final String input, final ApplicationContext context) {
        final User user = (User) context.getParam(ApplicationContext.USER);

        if (isEmpty(input)) {
            this.printLists(user.getItemLists());
        } else if (input.equalsIgnoreCase("tags")) {
            this.printTags(user.getTags());
        } else {
            final ItemList list = user.getItemList(input);
            if (list == null) {
                getCli().println(getMessage(NO_LIST_FOUND, input));
            } else {
                this.printList(list);
            }
        }

        return ReturnCode.CONTINUE;
    }

    private void printList(final ItemList list) {
        final CommandLineInterpreter cli = getCli();
        final String fullLine = StringUtils.rightPad("+", 98, "-").concat("+");

        cli.println(fullLine);
        cli.println(String.format("|%s|", StringUtils.center(StringUtils.capitalize(list.getName()), 97)));
        cli.println(fullLine);
        cli.println(String.format("|%44s|  %s  | %s | %33s|",
                StringUtils.center(ITEM_NAME, 44),
                StringUtils.capitalize(TYPE),
                StringUtils.capitalize(RATING),
                StringUtils.center(LABELS, 33)));
        cli.println(fullLine);
        for (Item item : list.getItems()) {
            cli.println(String.format("| %-42s | %-6s | %-6s | %-32s |",
                    StringUtils.abbreviate(item.getName(), 42),
                    item.getType().toString(),
                    item.getRating() + "/5",
                    StringUtils.abbreviate(item.getTags().replaceAll(";", ", "), 32)
            ));
        }
        if (list.getItems().isEmpty()) {
            cli.println(StringUtils.rightPad("|", 98, " ").concat("|"));
        }
        cli.println(fullLine);
    }

    private void printLists(final List<ItemList> lists) {
        final CommandLineInterpreter cli = getCli();
        final String fullLine = StringUtils.rightPad("+", 98, "-").concat("+");

        cli.println(fullLine);
        cli.println(String.format("|%s|", StringUtils.center(ITEM_LISTS, 97)));
        cli.println(fullLine);
        cli.println(String.format("|%49s| %s | %39s|",
                StringUtils.center(ITEM_NAME, 49),
                StringUtils.capitalize(ITEM),
                StringUtils.center(LABELS, 39)));
        cli.println(fullLine);
        for (ItemList list : lists) {
            cli.println(String.format("| %-47s | %-4d | %-38s |",
                    StringUtils.abbreviate(list.getName(), 47),
                    list.getItems().size(),
                    StringUtils.abbreviate(list.getTags().replaceAll(";", ", "), 38)
            ));
        }
        if (lists.isEmpty()) {
            cli.println(StringUtils.rightPad("|", 98, " ").concat("|"));
        }
        cli.println(fullLine);
    }

    private void printTags(final List<Tag> tags) {
        final CommandLineInterpreter cli = getCli();
        final String fullLine = StringUtils.rightPad("+", 98, "-").concat("+");

        cli.println(fullLine);
        cli.println(String.format("|%s|", StringUtils.center(AVAILABLE_TAGS, 97)));
        cli.println(fullLine);
        cli.println(tagsToString(tags));
        cli.println(fullLine);
    }

    private String tagsToString(final List<Tag> tags) {
        String s = "| " + tags.get(0).getName();

        int lineSize = s.length();
        for (int i = 1; i < tags.size(); i++) {
            Tag tag = tags.get(i);

            lineSize = lineSize + tag.getName().length() + 2; // Added 2 because of the ', ' glue
            if (lineSize > 96) {
                s = StringUtils.rightPad(s, 98, ' ') + " |\n| " + tag.getName();
                lineSize = tag.getName().length() + 2; // Added 2 because of the '| ' prefix
            } else {
                s = s + ", " + tag.getName();
            }
        }

        s = StringUtils.rightPad(s, 97, ' ') + " |";
        return s;
    }

}
