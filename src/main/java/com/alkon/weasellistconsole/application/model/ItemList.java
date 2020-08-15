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

package com.alkon.weasellistconsole.application.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.alkon.weasellistconsole.application.Utils.isEmpty;
import static com.alkon.weasellistconsole.application.Utils.listToString;


@Getter
@Setter
public class ItemList implements Taggable {

    private String name;
    private List<Item> items;
    private String tags;

    public ItemList() {
        this.items = new ArrayList<>();
        this.tags = "";
    }

    public Item getItem(String name) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public void setItem(final Item item) {
        for (int i = 0; i < this.items.size(); i++) {
            Item il = this.items.get(i);
            if (il.equals(item)) {
                this.items.set(i, item);
            }
        }
    }

    @Override
    public void editTag(String oldValue, String newValue) {
        this.tags = this.tags.replace(oldValue, newValue);
    }

    @Override
    public boolean hasTag(String tag) {
        return this.tags.contains(tag);
    }

    @Override
    public void setTagsFromList(List<String> tags) {
        this.tags = listToString(tags, ";");
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
    public void addTag(final String tag) {
        if (isEmpty(this.tags)) {
            this.tags = tag;
        } else if (!this.tags.contains(tag)) {
            this.tags = this.tags + ";" + tag;
        }
    }

    @Override
    public String toString() {
        return "ItemList{" +
                "name='" + name + '\'' +
                ", items=" + items +
                ", tags='" + tags + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemList list = (ItemList) o;
        return Objects.equals(name, list.name);
    }

}
