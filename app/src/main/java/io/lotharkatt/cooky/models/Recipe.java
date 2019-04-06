package io.lotharkatt.cooky.models;


import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * Recipe class store definition for Recipe object
 */
public class Recipe implements Parcelable {


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
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<String> getTags() {
        return tags;
    }

    public int getGlobalTime() {
        return globalTime;
    }

    public void setGlobalTime(int globalTime) {
        this.globalTime = globalTime;
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

    public List<Step> getSteps() {
        return steps;
    }


    public static class Step implements Parcelable{
        String stepDescription;
        int stepTime;
        Boolean stepTimer;

        public Step(){}

        public Step(String description, int time, Boolean timer) {
            this.stepDescription = description;
            this.stepTime = time;
            this.stepTimer = timer;
        }

        protected Step(Parcel in) {
            stepDescription = in.readString();
            stepTime = in.readInt();
            byte tmpStepTimer = in.readByte();
            stepTimer = tmpStepTimer == 0 ? null : tmpStepTimer == 1;
        }

        public static final Creator<Step> CREATOR = new Creator<Step>() {
            @Override
            public Step createFromParcel(Parcel in) {
                return new Step(in);
            }

            @Override
            public Step[] newArray(int size) {
                return new Step[size];
            }
        };

        public String getStepDescription() {
            return stepDescription;
        }

        public int getStepTime() {
            return stepTime;
        }

        public Boolean getStepTimer() {
            return stepTimer;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(stepDescription);
            dest.writeInt(stepTime);
            dest.writeByte((byte) (stepTimer == null ? 0 : stepTimer ? 1 : 2));
        }
    }
    public static class Ingredient implements Parcelable {
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

        protected Ingredient(Parcel in) {
            ingredientName = in.readString();
            ingredientUnit = in.readString();
            ingredientQuantity = in.readInt();
        }

        public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
            @Override
            public Ingredient createFromParcel(Parcel in) {
                return new Ingredient(in);
            }

            @Override
            public Ingredient[] newArray(int size) {
                return new Ingredient[size];
            }
        };

        public String getIngredientName() {
            return ingredientName;
        }

        public String getIngredientUnit() {
            return ingredientUnit;
        }

        public int getIngredientQuantity() {
            return ingredientQuantity;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(ingredientName);
            dest.writeString(ingredientUnit);
            dest.writeInt(ingredientQuantity);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.author);
        dest.writeString(this.description);
        dest.writeInt(this.globalTime);
        dest.writeTypedList(this.ingredients);
        dest.writeStringList(this.tags);
        dest.writeTypedList(this.steps);
    }

    protected Recipe(Parcel in) {
        this.name = in.readString();
        this.author = in.readString();
        this.description = in.readString();
        this.globalTime = in.readInt();
        this.ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        this.tags = in.createStringArrayList();
        this.steps = in.createTypedArrayList(Step.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
