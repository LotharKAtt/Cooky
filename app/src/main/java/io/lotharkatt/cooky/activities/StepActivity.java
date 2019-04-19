package io.lotharkatt.cooky.activities;


import android.os.Bundle;
import android.util.Log;

import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.FragmentActivity;

import io.lotharkatt.cooky.R;
import io.lotharkatt.cooky.adapters.SwipeAdapter;
import io.lotharkatt.cooky.models.Recipe;

public class StepActivity extends FragmentActivity {

    SwipeAdapter swipeAdapter;
    ViewPager swipePager;
    Recipe recipe;
    String id;

    public StepActivity() {

    }

    public StepActivity(String id) {
        this.id = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        recipe = getIntent().getExtras().getParcelable("Item");
        id = getIntent().getExtras().getString("id");

        swipePager = findViewById(R.id.pager);
        swipeAdapter = new SwipeAdapter(getSupportFragmentManager(), this);
        swipePager.setAdapter(swipeAdapter);
    }


    public String getStep(int position) {
        return recipe.getSteps().get(position).getStepDescription();
    }

    public String getId() {
        return id;
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
