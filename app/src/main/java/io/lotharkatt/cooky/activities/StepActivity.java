package io.lotharkatt.cooky.activities;

import android.content.Intent;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.Button;

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
        recipe = (Recipe) intent.getParcelableExtra("Recipe");


        swipePager = findViewById(R.id.pager);
        swipeAdapter = new SwipeAdapter(getSupportFragmentManager(), this);
        swipePager.setAdapter(swipeAdapter);

    }


    public String getStep(int position) {
        return recipe.getSteps().get(position).getStepDescription();

    }

    public int getNumberOfStep() {
        return recipe.getSteps().size();
    }


    public boolean getTimer(int position) {
        return recipe.getSteps().get(position).getStepTimer();
    }

    public int getTime(int position) {
        return recipe.getSteps().get(position).getStepTime();

    }


}
