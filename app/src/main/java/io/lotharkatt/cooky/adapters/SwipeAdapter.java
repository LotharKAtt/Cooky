package io.lotharkatt.cooky.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import io.lotharkatt.cooky.activities.StepActivity;
import io.lotharkatt.cooky.fragments.StepFragment;

public class SwipeAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Integer> page_indexes;
    StepActivity stepActivity;



    public SwipeAdapter(FragmentManager fm, StepActivity stepActivity) {
        super(fm);
        this.stepActivity = stepActivity;

        page_indexes = new ArrayList<>();
        for (int i = 0; i < stepActivity.getNumberOfStep(); i++) {
            page_indexes.add(i);
        }
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
        }

        Integer index = page_indexes.get(position);
        return StepFragment.newInstance(index, stepActivity.getStep(position));

    }

    @Override
    public int getCount() {
        return page_indexes.size();
    }
}
