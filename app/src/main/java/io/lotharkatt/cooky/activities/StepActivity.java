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
    Button buttonGoBack, buttonGoNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Intent intent = getIntent();
        recipe = (Recipe) intent.getParcelableExtra("Recipe");


        swipePager = findViewById(R.id.pager);
        swipeAdapter = new SwipeAdapter(getSupportFragmentManager(), this);
        swipePager.setAdapter(swipeAdapter);



        buttonGoBack = findViewById(R.id.goto_back);

        buttonGoBack.setVisibility(View.INVISIBLE);
        buttonGoBack.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    swipeAdapter.getItem(swipePager.getCurrentItem() -1);
                }
            });

        buttonGoNext = findViewById(R.id.goto_next);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(swipePager.getCurrentItem() == 1){
            buttonGoBack.setVisibility(View.VISIBLE);

        }
        buttonGoNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                swipeAdapter.getItem(swipePager.getCurrentItem() +1);

            }
        });
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
