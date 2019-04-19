package io.lotharkatt.cooky.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import io.lotharkatt.cooky.R;
import io.lotharkatt.cooky.activities.RecipeListActivity;
import io.lotharkatt.cooky.models.Review;


public class ReviewFragment extends Fragment {

    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private Button buttonSendReview, buttonSkipFeedBack;
    private EditText editTextFeedbackMsg;
    private FirebaseFirestore db;
    private String id;

    public ReviewFragment() {
    }

    public static ReviewFragment newInstance(String id) {
        ReviewFragment fragment = new ReviewFragment();
        Bundle args = new Bundle();
        args.putString("id", id);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString("id");

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();

        View v = inflater.inflate(R.layout.fragment_review, container, false);
        editTextFeedbackMsg = v.findViewById(R.id.editTextFeedBack);


        buttonSendReview = v.findViewById(R.id.btnSendFeedback);
        buttonSendReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Review review = new Review(FirebaseAuth.getInstance().getCurrentUser().getEmail(), editTextFeedbackMsg.getText().toString(), id);

                CollectionReference reviewsRec = db.collection("reviews");
                reviewsRec.add(review).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "Review was added!", Toast.LENGTH_SHORT).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                Toast.makeText(getActivity(), "Thank you for a feedback!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), RecipeListActivity.class));

            }
        });
        buttonSkipFeedBack = v.findViewById(R.id.buttonSkipFeedback);
        buttonSkipFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RecipeListActivity.class));
            }
        });

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
