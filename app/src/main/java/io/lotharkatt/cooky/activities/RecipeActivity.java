package io.lotharkatt.cooky.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import io.lotharkatt.cooky.R;
import io.lotharkatt.cooky.models.Recipe;

public class RecipeActivity extends AppCompatActivity {
    private Button buttonSubmit;
    private EditText editTextName, editTextAuthor, editTextDecripton;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);


        setContentView(R.layout.activity_recipe);

        db = FirebaseFirestore.getInstance();

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        editTextAuthor = (EditText) findViewById(R.id.editTextAuthor);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextDecripton = (EditText) findViewById(R.id.editTextDescription);


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String author = editTextAuthor.getText().toString().trim();
                String desc = editTextDecripton.getText().toString().trim();

                CollectionReference dbRec = db.collection("recipes");
                Recipe recipe = new Recipe(name, author, desc);
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


}
