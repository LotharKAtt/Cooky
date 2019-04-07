package io.lotharkatt.cooky.activities;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.lotharkatt.cooky.R;

public class MainActivity extends AppCompatActivity {

    private Button addRecipe, recipeList, btnAbout;
    private Vibrator v;
    CountDownTimer countDownTimer;
    private TextView tvTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        tvTimer = (TextView) findViewById(R.id.tvTimer);
        addRecipe = (Button) findViewById(R.id.buttonAddRecipe);
        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("lol");
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

        btnAbout = (Button) findViewById(R.id.buttonAbout);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.start();
            }
        });

        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText("seconds remaining: " + millisUntilFinished / 1000);

            }

            @Override
            public void onFinish() {
                tvTimer.setText("done!");
                v.vibrate(400);


            }
        };
    }

    private void openRecipeListActivity() {
        Intent intent = new Intent(this, RecipesListActivity.class);
        startActivity(intent);
    }

    private void openRecipeActivity() {
        Intent intent = new Intent(this, AddRecipeActivity.class);
        startActivity(intent);
        v.vibrate(400);

    }

}
