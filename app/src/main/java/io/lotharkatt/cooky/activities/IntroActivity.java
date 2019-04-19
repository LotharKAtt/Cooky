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
        // TODO: ASK ABOUT PERMISSION, RUN ONLY ONCE
        // TODO SPLASHER

        SliderPage selectPage = new SliderPage();
        selectPage.setTitle("Select recipe");
        selectPage.setDescription("Find food what you are like.");
        selectPage.setImageDrawable(R.mipmap.ic_launcher);
        selectPage.setBgColor(getColor(R.color.colorPrimary));
        addSlide(AppIntroFragment.newInstance(selectPage));

        SliderPage preparePage = new SliderPage();
        preparePage.setTitle("Prepare ingredients");
        preparePage.setDescription("Prepare all ingredients what are necessary for recipe.");
        preparePage.setImageDrawable(R.mipmap.ic_launcher);
        preparePage.setBgColor(getColor(R.color.colorPrimaryDark));
        addSlide(AppIntroFragment.newInstance(preparePage));

        SliderPage eatPage = new SliderPage();
        eatPage.setTitle("Eat Food");
        eatPage.setDescription("... and Profit!");
        eatPage.setImageDrawable(R.mipmap.ic_launcher);
        eatPage.setBgColor(getColor(R.color.colorPrimary));
        addSlide(AppIntroFragment.newInstance(eatPage));


        showSkipButton(true);
        setProgressButtonEnabled(true);
        setFadeAnimation();


    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        startActivity(new Intent(this, MainActivity.class));

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(this, MainActivity.class));
    }
}
