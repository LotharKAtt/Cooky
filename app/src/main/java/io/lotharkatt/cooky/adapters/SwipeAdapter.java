package io.lotharkatt.cooky.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import io.lotharkatt.cooky.activities.StepActivity;
import io.lotharkatt.cooky.activities.StepFragment;
import io.lotharkatt.cooky.models.Recipe;

public class SwipeAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_ITEMS = 10;
    private ArrayList<Integer> page_indexes;
    StepActivity stepActivity = new StepActivity();


    String lol = "karel got";


    public SwipeAdapter(FragmentManager fm) {
        super(fm);

        page_indexes = new ArrayList<>();
        for (int i = 0; i < NUM_ITEMS; i++) {
            page_indexes.add(i);
        }
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
        }

        Integer index = page_indexes.get(position);
        return StepFragment.newInstance(index, lol);
//        return StepFragment.newInstance(index, stepActivity.getRecept(position));

    }

    @Override
    public int getCount() {
        return page_indexes.size();
    }
}
