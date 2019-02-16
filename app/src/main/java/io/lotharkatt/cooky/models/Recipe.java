package io.lotharkatt.cooky.models;

import java.util.List;

public class Recipe {
    private String name;
    private String author;
    private String description;
    private int preperationTime;
    List<String> tags;


    public Recipe(){

    }

    public Recipe(String name, String author, String description) {
        this.name = name;
        this.author = author;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
