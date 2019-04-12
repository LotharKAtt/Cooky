package io.lotharkatt.cooky.activities;
import android.content.Intent;
import android.os.Bundle;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

import androidx.fragment.app.Fragment;
import io.lotharkatt.cooky.R;

public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: ASK ABOUT PERMITION, RUN ONLY ONCE
        // TODO SPLASHER?

        SliderPage selectPage = new SliderPage();
        selectPage.setTitle("Select recipe");
        selectPage.setDescription("Decripiton");
        selectPage.setImageDrawable(R.drawable.ic_appintro_navigate_before_white);
        selectPage.setBgColor(getColor(R.color.colorPrimary));
        addSlide(AppIntroFragment.newInstance(selectPage));

        SliderPage preparePage = new SliderPage();
        preparePage.setTitle("Prepare ingredients");
        preparePage.setDescription("Decripiton");
        preparePage.setImageDrawable(R.drawable.ic_appintro_navigate_before_white);
        preparePage.setBgColor(getColor(R.color.colorPrimary));
        addSlide(AppIntroFragment.newInstance(preparePage));

        SliderPage eatPage = new SliderPage();
        eatPage.setTitle("Eat Food");
        eatPage.setDescription("Decripiton");
        eatPage.setImageDrawable(R.drawable.ic_appintro_navigate_before_white);
        eatPage.setBgColor(getColor(R.color.colorPrimary));
        addSlide(AppIntroFragment.newInstance(eatPage));


        showSkipButton(true);
        setProgressButtonEnabled(true);
        setFadeAnimation();


    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent = new Intent(this, RecipeListActivity.class);
        startActivity(intent);

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(this, RecipeListActivity.class);
        startActivity(intent);    }
}
