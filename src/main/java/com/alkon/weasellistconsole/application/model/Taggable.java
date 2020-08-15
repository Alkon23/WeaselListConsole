package com.alkon.weasellistconsole.application.model;

import java.util.List;

public interface Taggable {

    void setTagsFromList(List<String> tags);

    void addTag(final String tag);

    void removeTag(final String tag);

    boolean hasTag(final String tag);

    void editTag(final String oldValue, final String newValue);

    List<String> getTagsAsList();

}
