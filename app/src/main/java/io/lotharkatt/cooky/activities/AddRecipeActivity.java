package io.lotharkatt.cooky.activities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.lotharkatt.cooky.R;
import io.lotharkatt.cooky.models.Recipe;

public class AddRecipeActivity extends AppCompatActivity {
    Button buttonChosseImage, buttonUploadImage, buttonSubmit, buttonAddIngredient, buttonAddStep;
    EditText editTextName, editTextAuthor, editTextDescription, editTextTags, editTextIngredientNameIn, editTextIngredientQuantityIn, editTextStepDescriptionIn, editTextStepTimeIn;
    Spinner spinnerCourse, spinnerIngredientIn;
    ImageView imageViewUpload;
    FirebaseFirestore db;
    StorageReference storageReference;
    StorageTask storageTask;
    Uri imageUri;

    CheckBox checkBoxStepTimerIn, checkBoxStepTimerOut;
    List<String> tags = new ArrayList<>();
    List<Recipe.Ingredient> ingredients = new ArrayList<>();
    List<Recipe.Step> steps = new ArrayList<>();
    LinearLayout containerIngredient, containerStep;
    String[] unitsResources;
    Boolean timerAllowed = false;
    String ingredientUnit, course, imageUrl;
    int ingredientPosition;
    int globalTime;


    static final int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);


        setContentView(R.layout.activity_add_recipe);

        // TODO ADD BACK BUTTONS
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        db = FirebaseFirestore.getInstance();
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        editTextAuthor = (EditText) findViewById(R.id.editTextAuthor);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        editTextTags = (EditText) findViewById(R.id.editTextTag);
        // TODO: separate tags from one long string to list
        String tagString = editTextTags.getText().toString();


        buttonChosseImage = (Button) findViewById(R.id.buttonChooseFromGalery);
        buttonUploadImage = (Button) findViewById(R.id.buttonUploadToFirebase);
        imageViewUpload = (ImageView) findViewById(R.id.imageViewUploadImage);

        storageReference = FirebaseStorage.getInstance().getReference("recipes");


        buttonChosseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        buttonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storageTask != null && storageTask.isInProgress()) {
                    Toast.makeText(AddRecipeActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }

            }
        });

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


        final Spinner spinnerIngredientOut = (Spinner) findViewById(R.id.spinnerIngredientIn);
        unitsResources = getResources().getStringArray(R.array.ingredientUnit);

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(AddRecipeActivity.this, android.R.layout.simple_spinner_dropdown_item, unitsResources);

        spinnerIngredientOut.setAdapter(unitAdapter);
        spinnerIngredientOut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ingredientUnit = parent.getSelectedItem().toString();
                ingredientPosition = parent.getSelectedItemPosition();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerIngredientIn = (Spinner) findViewById(R.id.spinnerIngredientIn);
        editTextIngredientNameIn = (EditText) findViewById(R.id.editTextIngredientNameIn);
        editTextIngredientQuantityIn = (EditText) findViewById(R.id.editTextIngredientQuantityIn);

        buttonAddIngredient = (Button) findViewById(R.id.add);
        containerIngredient = (LinearLayout) findViewById(R.id.ingredientContainer);


        buttonAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row, null);


                // TODO: Validation on empty entries
                EditText ingredientNameOut = (EditText) addView.findViewById(R.id.ingredientnameout);
                ingredientNameOut.setText(editTextIngredientNameIn.getText().toString());
                editTextIngredientNameIn.setText("");


                EditText ingredientQuantityOut = (EditText) addView.findViewById(R.id.ingredientquantityout);
                ingredientQuantityOut.setText(editTextIngredientQuantityIn.getText().toString());
                editTextIngredientQuantityIn.setText("");


                Spinner spinnerOut2 = (Spinner) addView.findViewById(R.id.ingredientunitout);

                ArrayAdapter<String> unitAdapter2 = new ArrayAdapter<String>(AddRecipeActivity.this, android.R.layout.simple_spinner_dropdown_item, unitsResources);

                spinnerOut2.setAdapter(unitAdapter2);
                spinnerOut2.setSelection(ingredientPosition);


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
                containerIngredient.addView(addView);
                ingredients.add(ingredient);
            }
        });


        editTextStepDescriptionIn = (EditText) findViewById(R.id.stepdescriptionin);
        editTextStepTimeIn = (EditText) findViewById(R.id.steptimein);
        checkBoxStepTimerIn = (CheckBox) findViewById(R.id.steptimerin);


        buttonAddStep = (Button) findViewById(R.id.btnstepadd);
        containerStep = (LinearLayout) findViewById(R.id.containerSteps);


        checkBoxStepTimerIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxStepTimerIn.isChecked()) {
                    timerAllowed = true;
                } else {
                    timerAllowed = false;
                }

            }
        });


        buttonAddStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row_step, null);


                // TODO: Validation on empty entries
                EditText editTextStepDescriptionOut = (EditText) addView.findViewById(R.id.stepdescriptionout);
                editTextStepDescriptionOut.setText(editTextStepDescriptionIn.getText().toString());
                editTextStepDescriptionIn.setText("");


                final EditText editTextStepTimeOut = (EditText) addView.findViewById(R.id.steptimeout);
                editTextStepTimeOut.setText(editTextStepTimeIn.getText().toString());
                editTextStepTimeIn.setText("");


                checkBoxStepTimerOut = (CheckBox) addView.findViewById(R.id.steptimerout);
                checkBoxStepTimerOut.setChecked(timerAllowed);

                checkBoxStepTimerOut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (checkBoxStepTimerOut.isChecked()) {
                            timerAllowed = true;
                        } else {
                            timerAllowed = false;
                        }
                    }
                });

                globalTime = globalTime + Integer.parseInt(editTextStepTimeOut.getText().toString());
                final Recipe.Step step = new Recipe.Step(editTextStepDescriptionOut.getText().toString(), Integer.parseInt(editTextStepTimeOut.getText().toString()), timerAllowed);


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

        // TODO add tag system
        tags.add("karel");


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String author = editTextAuthor.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();

                CollectionReference dbRec = db.collection("recipes");
                Recipe recipe = new Recipe(name, author, description, course, imageUrl, globalTime, tags, ingredients, steps);

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
                finish();
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadFile() {
        if (imageUri != null) {

            StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

            storageTask = fileRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imageUrl = uri.toString();
                                }
                            });                            Toast.makeText(AddRecipeActivity.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddRecipeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            imageUrl = "Not Found";
                        }
                    })
                    // TODO: If time
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).fit().into(imageViewUpload);
        }
    }
}
