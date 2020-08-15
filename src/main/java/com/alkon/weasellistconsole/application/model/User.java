package com.alkon.weasellistconsole.application.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "User")
public class User {

    @Id
    private String id;
    private String nick;
    private String password;
    private String email;
    private List<Tag> tags;
    private List<ItemList> itemLists;
    private String lastLoginToken;

    public User() {
        this.itemLists = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    public ItemList getItemList(final String name) {
        for (ItemList list : itemLists) {
            if (list.getName().equalsIgnoreCase(name)) {
                return list;
            }
        }
        return null;
    }

    public void setItemList(final ItemList list) {
        for (int i = 0; i < this.itemLists.size(); i++) {
            ItemList il = this.itemLists.get(i);
            if (il.equals(list)) {
                this.itemLists.set(i, list);
            }
        }
    }

    public Tag getTag(final String name) {
        for (Tag tag : tags) {
            if (tag.getName().equalsIgnoreCase(name)) {
                return tag;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nick='" + nick + '\'' +
                ", email='" + email + '\'' +
                ", tags=" + tags +
                ", itemLists=" + itemLists +
                ", lastLoginToken='" + lastLoginToken + '\'' +
                '}';
    }
}
