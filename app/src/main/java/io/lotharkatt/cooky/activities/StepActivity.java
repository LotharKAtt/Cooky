package io.lotharkatt.cooky.activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

import io.lotharkatt.cooky.R;
import io.lotharkatt.cooky.adapters.SwipeAdapter;
import io.lotharkatt.cooky.models.Recipe;

public class StepActivity extends FragmentActivity {

    SwipeAdapter swipeAdapter;
    ViewPager swipePager;
    Recipe recipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("Item2");


        swipePager = findViewById(R.id.pager);
        swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
        swipePager.setAdapter(swipeAdapter);

        findViewById(R.id.goto_first).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        findViewById(R.id.delete_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        findViewById(R.id.goto_last).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //              swipeAdapter.setCurrentItem(SwipeAdapter.getCount() - 1);
            }
        });


    }

    public String getRecept(int position) {
        List<Recipe.Step> recipes = recipe.getSteps();
        String description = recipes.get(position).getStepDescription();


        return description;

    }


}
