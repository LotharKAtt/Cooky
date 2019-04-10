package io.lotharkatt.cooky.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
    Button buttonSubmit, buttonAdd, buttonStepAdd;
    EditText editTextName, editTextAuthor, editTextDescription, editTextTags, ingredientNameIn, ingredientQuantityIn, stepDescriptionIn, stepTimeIn;
    Spinner spinnerCourse, spinnerIn;
    FirebaseFirestore db;

    CheckBox stepTimerIn, stepTimerOut;
    List<String> tags = new ArrayList<>();
    List<Recipe.Ingredient> ingredients = new ArrayList<>();
    List<Recipe.Step> steps = new ArrayList<>();
    LinearLayout container, containerStep;
    String[] unitsResources;
    Boolean timerAllowed = false;
    String ingredientUnit, course;
    int ingPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);


        setContentView(R.layout.activity_add_recipe);

        db = FirebaseFirestore.getInstance();
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        editTextAuthor = (EditText) findViewById(R.id.editTextAuthor);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        editTextTags = (EditText) findViewById(R.id.editTextTag);
        // TODO: separate tags from one long string to list
        String tagString = editTextTags.getText().toString();


        spinnerCourse = (Spinner) findViewById(R.id.spinnerCourse);
        String[] courseResource = getResources().getStringArray(R.array.course);
        final ArrayAdapter<String> courseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, courseResource);
        spinnerCourse.setAdapter(courseAdapter);
        spinnerCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course = parent.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final Spinner  spinnerOut = (Spinner) findViewById(R.id.spinnerin);
        unitsResources = getResources().getStringArray(R.array.unit);

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(AddRecipeActivity.this, android.R.layout.simple_spinner_dropdown_item, unitsResources);

        spinnerOut.setAdapter(unitAdapter);
        spinnerOut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ingredientUnit = parent.getSelectedItem().toString();
                ingPos = parent.getSelectedItemPosition();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        spinnerIn = (Spinner) findViewById(R.id.spinnerin);
        ingredientNameIn = (EditText) findViewById(R.id.ingredientnamein);
        ingredientQuantityIn = (EditText) findViewById(R.id.ingredientquantityin);

        buttonAdd = (Button) findViewById(R.id.add);
        container = (LinearLayout) findViewById(R.id.container);


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row, null);


                // TODO: Validation on empty entries
                EditText ingredientNameOut = (EditText) addView.findViewById(R.id.ingredientnameout);
                ingredientNameOut.setText(ingredientNameIn.getText().toString());
                ingredientNameIn.setText("");



                EditText ingredientQuantityOut = (EditText) addView.findViewById(R.id.ingredientquantityout);
                ingredientQuantityOut.setText(ingredientQuantityIn.getText().toString());
                ingredientQuantityIn.setText("");


                Spinner  spinnerOut2 = (Spinner) addView.findViewById(R.id.ingredientunitout);

                ArrayAdapter<String> unitAdapter2 = new ArrayAdapter<String>(AddRecipeActivity.this, android.R.layout.simple_spinner_dropdown_item, unitsResources);

                spinnerOut2.setAdapter(unitAdapter2);
                spinnerOut2.setSelection(ingPos);



                final Recipe.Ingredient ingredient = new Recipe.Ingredient(ingredientNameOut.getText().toString(), spinnerOut2.getSelectedItem().toString(), Integer.parseInt(ingredientQuantityOut.getText().toString()));


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


        stepDescriptionIn = (EditText) findViewById(R.id.stepdescriptionin);
        stepTimeIn = (EditText) findViewById(R.id.steptimein);
        stepTimerIn = (CheckBox) findViewById(R.id.steptimerin);


        buttonStepAdd = (Button) findViewById(R.id.btnstepadd);
        containerStep = (LinearLayout) findViewById(R.id.containerSteps);


        stepTimerIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(stepTimerIn.isChecked()){
                    timerAllowed = true;
                }
                else {
                    timerAllowed = false;
                }

            }
        });









        buttonStepAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row_step, null);


                // TODO: Validation on empty entries
                EditText stepDescriptionOut = (EditText) addView.findViewById(R.id.stepdescriptionout);
                stepDescriptionOut.setText(stepDescriptionIn.getText().toString());
                stepDescriptionIn.setText("");


                final EditText stepTimeOut = (EditText) addView.findViewById(R.id.steptimeout);
                stepTimeOut.setText(stepTimeIn.getText().toString());
                stepTimeIn.setText("");


                stepTimerOut = (CheckBox) addView.findViewById(R.id.steptimerout);
                stepTimerOut.setChecked(timerAllowed);

                stepTimerOut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(stepTimerOut.isChecked()){
                            timerAllowed = true;
                        }
                        else {
                            timerAllowed = false;
                        }
                    }
                });





                final Recipe.Step step = new Recipe.Step(stepDescriptionOut.getText().toString(), Integer.parseInt(stepTimeOut.getText().toString()), timerAllowed);



                Button buttonStepRemove = (Button) addView.findViewById(R.id.btnstepdelete);

                final View.OnClickListener thisListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("thisListener called:\t" + this + "\n");
                        System.out.println("Remove addView: " + addView + "\n\n");
                        ((LinearLayout) addView.getParent()).removeView(addView);
                        steps.remove(step);
                    }
                };
                buttonStepRemove.setOnClickListener(thisListener);
                containerStep.addView(addView);
                steps.add(step);
            }
        });




        tags.add("karel");


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String author = editTextAuthor.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();

                int time = 12;

                CollectionReference dbRec = db.collection("recipes");
                Recipe recipe = new Recipe(name, author, description, course, time, tags, ingredients, steps);

                // TODO: Validation
                dbRec.add(recipe)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(AddRecipeActivity.this, "Product added", Toast.LENGTH_LONG).show();
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
