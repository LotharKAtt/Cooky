package io.lotharkatt.cooky.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.lotharkatt.cooky.R;
import io.lotharkatt.cooky.models.Recipe;

public class RecipeActivity extends AppCompatActivity {
    private Button buttonSubmit;
    private EditText editTextName, editTextAuthor, editTextDecription;
    private FirebaseFirestore db;
    private int cas = 10;
    private List<String> steps;
    private Map<String, Object> ingredients = new HashMap<>();
    private LinearLayout parentLinearLayout;
    EditText edittext_var ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);


        setContentView(R.layout.activity_add_recipe);

        db = FirebaseFirestore.getInstance();


        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        editTextAuthor = (EditText) findViewById(R.id.editTextAuthor);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextDecription = (EditText) findViewById(R.id.editTextDescription);
        parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);



        ingredients.put("20kg","brambor");
        ingredients.put("30dk","salamu");
        ingredients.put("30dk", 11);


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String author = editTextAuthor.getText().toString().trim();
                String description = editTextDecription.getText().toString().trim();



                CollectionReference dbRec = db.collection("recipes");
                // step bude taky mapa potrebuju casy
                Recipe recipe = new Recipe(name, author, description, cas, Arrays.asList("west_coast", "sorcal"), ingredients);

                // TODO: Validation
                dbRec.add(recipe)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(RecipeActivity.this, "Product added", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RecipeActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();


                    }
                });

            }
        });
    }
    public void onAddField(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View rowView = inflater.inflate(R.layout.field, null);
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
    }
    public void onDelete(View v) {
        parentLinearLayout.removeView((View) v.getParent());
    }
    // TODO: DATA VALIDATOIN


    public void onSelect(View v) { // on click event for a SELECT button

        edittext_var = (EditText)((View) v.getParent()).findViewById(R.id.number_edit_text) ;

        System.out.println("The text is = "+ edittext_var.getText());
    }

    private void dataValidation(){}


}
