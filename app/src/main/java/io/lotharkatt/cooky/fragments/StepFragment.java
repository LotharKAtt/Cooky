package io.lotharkatt.cooky.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.lotharkatt.cooky.R;
import io.lotharkatt.cooky.activities.AddRecipeActivity;


public class StepFragment extends Fragment implements View.OnClickListener {
    int mNum;
    String stepContent;
    boolean stepTimer;
    int stepTime;
    Button btnAlarm;
    CountDownTimer countDownTimer;
    TextView alarm;
    Vibrator vibrator;



    public StepFragment() {
        // Required empty public constructor
    }

    public static StepFragment newInstance(int num, String step, int time, boolean timer) {
        StepFragment fragment = new StepFragment();
        Bundle args = new Bundle();
        args.putInt("num", num);
        args.putString("stepContent", step);
        args.putInt("stepTime", time);
        args.putBoolean("stepTimer", timer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
            stepContent = getArguments() != null ? getArguments().getString("stepContent") : "ERROR";
            stepTime = getArguments().getInt("stepTime");
            stepTimer = getArguments().getBoolean("stepTimer");



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

        alarm = (TextView) v.findViewById(R.id.textViewAlarm);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);


        btnAlarm = (Button) v.findViewById(R.id.btnAlarm);
        btnAlarm.setOnClickListener(this);

        if(stepTimer == false) {
            btnAlarm.setVisibility(View.GONE);
            alarm.setVisibility(View.GONE);

        }
        countDownTimer = new CountDownTimer((stepTime * 1000), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                alarm.setText("Remaining: \n" + millisUntilFinished / 1000);

            }

            @Override
            public void onFinish() {
                alarm.setText("done!");
                // TODO add sound notification, push notification
                vibrator.vibrate(400);


            }
        };

        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAlarm:
                countDownTimer.start();
                setupAlarmForTimer();
                System.out.print("===========================");
                Toast.makeText(getActivity(), "sss", Toast.LENGTH_SHORT).show();
                break;
        }


    }

    private void setupAlarmForTimer() {
    }
}
