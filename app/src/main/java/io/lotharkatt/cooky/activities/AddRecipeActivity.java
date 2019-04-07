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

import java.util.ArrayList;
import java.util.List;

import io.lotharkatt.cooky.R;
import io.lotharkatt.cooky.models.Recipe;

public class AddRecipeActivity extends AppCompatActivity {
    Button buttonSubmit, buttonAdd;
    EditText editTextName, editTextAuthor, editTextDecription, editTextTags, textIn;
    FirebaseFirestore db;

    List<String> tags = new ArrayList<>();
    List<Recipe.Ingredient> ingredients = new ArrayList<>();
    List<Recipe.Step> steps = new ArrayList<>();
    LinearLayout container;
    List<String> test = new ArrayList<>();

    String content;


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
        editTextTags = (EditText) findViewById(R.id.editTextTag);
        // TODO: separate tags from one long string to list
        String tagString = editTextTags.getText().toString();


        textIn = (EditText) findViewById(R.id.textin);
        buttonAdd = (Button) findViewById(R.id.add);
        container = (LinearLayout) findViewById(R.id.container);


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row, null);
                EditText textOut = (EditText) addView.findViewById(R.id.textout);
                textOut.setText(textIn.getText().toString());


                content = textOut.getText().toString();
                final Recipe.Ingredient ingredient = new Recipe.Ingredient(content, "kg", 30);


                Button buttonRemove = (Button) addView.findViewById(R.id.remove);


                final View.OnClickListener thisListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("thisListener called:\t" + this + "\n");
                        System.out.println("Remove addView: " + addView + "\n\n");
                        ((LinearLayout) addView.getParent()).removeView(addView);
                        ingredients.remove(ingredient);


                    }
                };

                buttonRemove.setOnClickListener(thisListener);
                container.addView(addView);
                ingredients.add(ingredient);


            }


        });
        tags.add("karel");


        // TODO: Remove when will be UI ready
        Recipe.Step step = new Recipe.Step("Nakup", 5, false);
        Recipe.Step step1 = new Recipe.Step("Nakrajej", 5, true);
        Recipe.Step step2 = new Recipe.Step("Uvar", 5, false);
        Recipe.Step step3 = new Recipe.Step("Profit", 5, false);


        steps.add(step);
        steps.add(step1);
        steps.add(step2);
        steps.add(step3);


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String author = editTextAuthor.getText().toString().trim();
                String description = editTextDecription.getText().toString().trim();


                int time = 12;

                CollectionReference dbRec = db.collection("recipes");
                // step bude taky mapa potrebuju casy
                Recipe recipe = new Recipe(name, author, description, "Dinner", time, tags, ingredients, steps);

                // TODO: Validation
                dbRec.add(recipe)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(AddRecipeActivity.this, "Product added" + test, Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddRecipeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();


                    }
                });

            }
        });
    }


    private void dataValidation() {
    }

    private void separtateString(String tags) {

    }

}
