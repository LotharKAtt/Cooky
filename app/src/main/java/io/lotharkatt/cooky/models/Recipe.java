package io.lotharkatt.cooky.models;


import java.util.ArrayList;
import java.util.List;

public class Recipe {

    String name;
    String author;
    String description;
    // TODO: add timestamp for recipes
    //private @ServerTimestamp Date timestamp
    List<Ingredient> ingredients;
    List <String> tags;
    List<Step> steps;




    public Recipe(){

    }

    public Recipe(String name, String author, String description, List<String> tags, List<Ingredient> ingredients, List<Step> steps) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.tags = tags;
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

    public static class Step {
        String stepDescription;
        int stepTime;
        Boolean stepTimer;

        public Step(String description, int time, Boolean timer) {
            this.stepDescription = description;
            this.stepTime = time;
            this.stepTimer = timer;
        }
    }
    public static class Ingredient{
        String ingredientName;
        String ingredientUnit;
        int ingredientQuantity;

        public Ingredient(String name, String unit, int qunatity) {
            this.ingredientName = name;
            this.ingredientUnit = unit;
            this.ingredientQuantity = qunatity;
        }
    }
}
