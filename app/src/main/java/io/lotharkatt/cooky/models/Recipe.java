package io.lotharkatt.cooky.models;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Recipe {

    //@Exclude
    //private String id;
    private String name;
    private String author;
    private String description;
    int preparationTime;
    Map<String, Object> ingredients;
    List<String> tags;
    List<String> steps = new ArrayList<String>();



    public Recipe(){

    }

    public Recipe(String name, String author, String description, int preparationTime, List<String> steps, Map<String, Object> ingredients) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.preparationTime = preparationTime;
        this.steps = steps;
        this.ingredients = ingredients;

    }



    public String getName() {
        return name;
    }


    public String getAuthor() {
        return author;
    }


    public String getDescription() {
        return description;
    }

}
