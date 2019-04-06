package io.lotharkatt.cooky.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import io.lotharkatt.cooky.R;
import io.lotharkatt.cooky.models.Recipe;

public class RecipeOverview extends AppCompatActivity {


    TextView textViewName, textViewAuthor, textViewDescription, textViewTime, textViewTag, textViewIngredient, textViewStep ;

    public RecipeOverview(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_overview);


       Intent intent = getIntent();
       Recipe recpItem = intent.getParcelableExtra("Item");
       String lol = recpItem.getAuthor();


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

        List<Recipe.Ingredient> losl = recpItem.getIngredients();
        losl.get(2);

        textViewIngredient = (TextView) findViewById(R.id.textViewIngredient);
        textViewIngredient.setText(losl);

    /*
        textViewStep = (TextView) findViewById(R.id.textViewStep);
        textViewStep.setText(recpItem.getIngredients().toString());
*/

    }
}
