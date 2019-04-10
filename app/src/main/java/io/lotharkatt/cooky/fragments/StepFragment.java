package io.lotharkatt.cooky.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.lotharkatt.cooky.R;
import io.lotharkatt.cooky.activities.AddRecipeActivity;


public class StepFragment extends Fragment {
    int mNum;
    String stepContent;



    public StepFragment() {
        // Required empty public constructor
    }

    public static StepFragment newInstance(int num, String step) {
        StepFragment fragment = new StepFragment();
        Bundle args = new Bundle();
        args.putInt("num", num);
        args.putString("stepContent", step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
            stepContent = getArguments() != null ? getArguments().getString("stepContent") : "ERROR";


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_step, container, false);
        View title = v.findViewById(R.id.textViewStepTitle);
        ((TextView) title).setText(String.format("Step #%d", mNum + 1));
        View step = v.findViewById(R.id.textViewStepDescription);
        ((TextView) step).setText(String.format(stepContent));
        View btnAlarm = v.findViewById(R.id.btnAlarm);
        ((Button) btnAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.print("==========================");
            }
        });

        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


}
