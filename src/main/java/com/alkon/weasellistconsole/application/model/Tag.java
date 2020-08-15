package com.alkon.weasellistconsole.application.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tag {

    private String name;
    private String color;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
        this.color = "#A2A2A2";
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
