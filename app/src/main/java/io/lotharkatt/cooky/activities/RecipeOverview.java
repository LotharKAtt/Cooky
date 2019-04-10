package io.lotharkatt.cooky.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.lotharkatt.cooky.R;
import io.lotharkatt.cooky.models.Recipe;

public class RecipeOverview extends AppCompatActivity {

    String ingredientName, getIngredientUnit, stepDescription;
    int ingredientQuantity, stepTime;
    boolean stepTimer;
    TextView textViewName, textViewAuthor, textViewDescription, textViewTime, textViewTag, textViewIngredient, textViewStep;
    Button btnCook;
    Recipe recpItem;

    public RecipeOverview() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_overview);


        Intent intent = getIntent();
        recpItem = intent.getParcelableExtra("Item");


        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewName.setText(recpItem.getName());


        textViewAuthor = (TextView) findViewById(R.id.textViewAuthor);
        textViewAuthor.setText(recpItem.getAuthor());

        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        textViewDescription.setText(recpItem.getDescription());

        textViewTime = (TextView) findViewById(R.id.textViewTime);
        textViewTime.setText(Integer.toString(recpItem.getGlobalTime()) + ":00");

        textViewTag = (TextView) findViewById(R.id.textViewTags);
        textViewTag.setText(recpItem.getTags().toString());

        List<Recipe.Ingredient> ingredients = recpItem.getIngredients();
        for (Recipe.Ingredient ingredient : ingredients) {
            ingredientName = ingredient.getIngredientName();
            ingredientQuantity = ingredient.getIngredientQuantity();
            getIngredientUnit = ingredient.getIngredientUnit();
        }

        //TODO: dinamicly render all ingredient
        textViewIngredient = (TextView) findViewById(R.id.textViewIngredient);
        textViewIngredient.setText(ingredientName);


        List<Recipe.Step> steps = recpItem.getSteps();
        int size = steps.size();

        textViewStep = (TextView) findViewById(R.id.textViewStep);
        for (Recipe.Step step : steps) {
            stepDescription = step.getStepDescription();
            stepTime = step.getStepTime();
            stepTimer = step.getStepTimer();
            textViewStep.setText(step.getStepDescription() + " ," );
            

        }



        btnCook = (Button) findViewById(R.id.btnStartCook);
        btnCook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecipeOverview.this, "Test Click", Toast.LENGTH_LONG).show();
                openStepActivity();
            }
        });

    }

    private void openStepActivity() {
        Intent intent = new Intent(this, StepActivity.class);
        intent.putExtra("Recipe", recpItem);

        startActivity(intent);
    }
}
