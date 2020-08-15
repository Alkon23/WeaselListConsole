package com.alkon.weasellistconsole.application.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static com.alkon.weasellistconsole.application.Utils.isEmpty;
import static com.alkon.weasellistconsole.application.Utils.listToString;

@Getter
@Setter
public class Item implements Taggable {
    private String name;
    private String info;
    private ItemType type;
    private Double rating;
    private String tags;

    public Item() {
        this.tags = "";
    }

    @Override
    public boolean hasTag(String tag) {
        return this.tags.contains(tag);
    }

    @Override
    public void setTagsFromList(final List<String> tags) {
        this.tags = listToString(tags, ";");
    }

    @Override
    public void addTag(final String tag) {
        if (isEmpty(this.tags)) {
            this.tags = tag;
        } else if (!this.tags.contains(tag)) {
            this.tags = this.tags + ";" + tag;
        }
    }

    @Override
    public void editTag(String oldValue, String newValue) {
        this.tags = this.tags.replace(oldValue, newValue);
    }

    @Override
    public List<String> getTagsAsList() {
        String[] tags = this.tags.split(";");
        if (tags.length == 1 && tags[0].isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList());
    }

    @Override
    public void removeTag(final String tag) {
        if (!isEmpty(this.tags)) {
            List<String> tags = new ArrayList<>(Arrays.asList(this.tags.split(";")));
            tags.remove(tag);
            this.tags = listToString(tags, ";");
        }
    }

    @Override
    public String toString() {
        return "Item{" +
            "name='" + name + '\'' +
            ", info='" + info + '\'' +
            ", type=" + type +
            ", rating=" + rating +
            ", tags='" + tags + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name);
    }

}
