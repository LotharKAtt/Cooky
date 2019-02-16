package io.lotharkatt.cooky.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.lotharkatt.cooky.R;

public class MainActivity extends AppCompatActivity {

    private Button addRecipe, recipeList;
    private Vibrator v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        addRecipe = (Button) findViewById(R.id.buttonAddRecipe);
        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecipeActivity();

            }
        });


        recipeList = (Button) findViewById(R.id.buttonRecipeList);
        recipeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecipeListActivity();


            }
        });

    }

    private void openRecipeListActivity() {
        Intent intent = new Intent(this, RecipesActivity.class);
        startActivity(intent);
    }

    private void openRecipeActivity() {
        Intent intent = new Intent(this, RecipeActivity.class);
        startActivity(intent);
        v.vibrate(400);

    }

}
