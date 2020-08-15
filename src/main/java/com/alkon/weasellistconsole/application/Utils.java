package com.alkon.weasellistconsole.application;

import com.alkon.weasellistconsole.application.model.ItemType;

import java.util.List;

import static com.alkon.weasellistconsole.cli.Constants.COLOR_REGEX;


public final class Utils {

    public static String listToString(final List<String> list, final String glue) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            // Check if last item
            if (i != list.size() - 1) {
                sb.append(glue);
            }
        }
        return sb.toString();
    }

    public static boolean isEmpty(final String s) {
        return s == null || s.isEmpty();
    }

    public static double getRatingFromString(final String s) {
        try {
            double rating = Math.abs(Double.parseDouble(s));
            return rating > 5 ? 5 : rating;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static ItemType getTypeFromString(final String s) {
        try {
            return ItemType.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ItemType.OTHER;
        }
    }

    public static boolean isValidType(final String s) {
        try {
            ItemType.valueOf(s);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean isColor(final String s) {
        return s.matches(COLOR_REGEX);
    }

}
