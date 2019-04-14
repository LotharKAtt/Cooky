package io.lotharkatt.cooky.activities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.lotharkatt.cooky.R;
import io.lotharkatt.cooky.models.Recipe;

public class RecipeOverview extends AppCompatActivity {

    String ingredientName, getIngredientUnit, stepDescription;
    int ingredientQuantity, stepTime;
    boolean stepTimer;
    TextView textViewName, textViewAuthor, textViewDescription, textViewTime, textViewTag, textViewIngredient, textViewStep;
    Button btnCook;
    ImageView imageViewRecipe;
    Recipe recipeItem;

    public RecipeOverview() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_overview);


        Intent intent = getIntent();
        recipeItem = intent.getParcelableExtra("Item");

        imageViewRecipe = (ImageView) findViewById(R.id.imageViewRecipe);
        Picasso.get().load(recipeItem.getImageUrl()).fit().centerCrop().into(imageViewRecipe);


        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewName.setText(recipeItem.getName());


        textViewAuthor = (TextView) findViewById(R.id.textViewAuthor);
        textViewAuthor.setText(recipeItem.getAuthor());

        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        textViewDescription.setText(recipeItem.getDescription());

        textViewTime = (TextView) findViewById(R.id.textViewTime);
        textViewTime.setText("Required time: " + Integer.toString(recipeItem.getGlobalTime()) + ":00");

        textViewTag = (TextView) findViewById(R.id.textViewTags);
        textViewTag.setText(recipeItem.getTags().toString());

        textViewIngredient = (TextView) findViewById(R.id.textViewIngredient);

        List<Recipe.Ingredient> ingredients = recipeItem.getIngredients();
        for (Recipe.Ingredient ingredient : ingredients) {
            ingredientName = ingredient.getIngredientName();
            ingredientQuantity = ingredient.getIngredientQuantity();
            getIngredientUnit = ingredient.getIngredientUnit();
            textViewIngredient.append(ingredient.getIngredientQuantity() + " " + ingredient.getIngredientUnit() + " ==" + ingredient.getIngredientName());
            textViewIngredient.append("\n");


        }

        textViewStep = (TextView) findViewById(R.id.textViewStep);

        List<Recipe.Step> steps = recipeItem.getSteps();
        int stepNumber=1;
        for (Recipe.Step step : steps) {
            stepDescription = step.getStepDescription();
            stepTime = step.getStepTime();
            stepTimer = step.getStepTimer();
            textViewStep.append(stepNumber + ".)" + step.getStepDescription() + " ");
            textViewStep.append("\n");
            textViewStep.append("Time for step:" + step.getStepTime());
            textViewStep.append("\n");
            stepNumber++;

        }

        btnCook = (Button) findViewById(R.id.btnStartCook);
        btnCook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStepActivity();
            }
        });

    }

    private void openStepActivity() {
        Intent intent = new Intent(this, StepActivity.class);
        intent.putExtra("Recipe", recipeItem);

        startActivity(intent);
    }
}
