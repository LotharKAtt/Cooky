package io.lotharkatt.cooky.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<String> getTags() {
        return tags;
    }

    String name;
    String author;
    String description;
    int globalTime;
    // TODO: add timestamp for recipes
    //private @ServerTimestamp Date timestamp
    List<Ingredient> ingredients;
    List<String> tags;
    List<Step> steps;




    public Recipe(){

    }



    public Recipe(String name, String author, String description, int globalTime, List<String> tags, List<Ingredient> ingredients, List<Step> steps) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.globalTime = globalTime;
        this.tags = tags;
        this.steps = steps;
        this.ingredients = ingredients;

    }

    public int getGlobalTime() {
        return globalTime;
    }

    public void setGlobalTime(int globalTime) {
        this.globalTime = globalTime;
    }

    protected Recipe(Parcel in) {
        name = in.readString();
        author = in.readString();
        description = in.readString();
        globalTime = in.readInt();
        tags = in.createStringArrayList();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }

    };

    public String getName() {
        return name;
    }


    public String getAuthor() {
        return author;
    }


    public String getDescription() {
        return description;
    }

    public List<Step> getSteps() {
        return steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(author);
        dest.writeString(description);
        dest.writeInt(globalTime);
        dest.writeStringList(tags);

    }


    public static class Step {
        String stepDescription;
        int stepTime;
        Boolean stepTimer;

        public Step(){}

        public Step(String description, int time, Boolean timer) {
            this.stepDescription = description;
            this.stepTime = time;
            this.stepTimer = timer;
        }

        public String getStepDescription() {
            return stepDescription;
        }

        public int getStepTime() {
            return stepTime;
        }

        public Boolean getStepTimer() {
            return stepTimer;
        }
    }
    public static class Ingredient {
        String ingredientName;
        String ingredientUnit;
        int ingredientQuantity;

        public Ingredient(){
        }

        public Ingredient(String name, String unit, int qunatity) {
            this.ingredientName = name;
            this.ingredientUnit = unit;
            this.ingredientQuantity = qunatity;
        }

        public String getIngredientName() {
            return ingredientName;
        }

        public String getIngredientUnit() {
            return ingredientUnit;
        }

        public int getIngredientQuantity() {
            return ingredientQuantity;
        }
    }
}
