package io.lotharkatt.cooky.activities;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.lotharkatt.cooky.R;
import io.lotharkatt.cooky.models.Recipe;
import io.lotharkatt.cooky.models.Review;

public class RecipeOverview extends AppCompatActivity {

    private String ingredientName, getIngredientUnit, stepDescription;
    private int ingredientQuantity, stepTime;
    private boolean stepTimer;
    private TextView textViewName, textViewAuthor, textViewDescription, textViewTime, textViewTag, textViewIngredient, textViewStep, textViewReview;
    private Button btnCook;
    private ImageView imageViewRecipe;
    private Recipe recipeItem;
    private String id;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference reviewsRef = db.collection("reviews");


    public RecipeOverview() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_overview);

        recipeItem = getIntent().getExtras().getParcelable("Item");
        id = getIntent().getExtras().getString("id");

        imageViewRecipe = (ImageView) findViewById(R.id.imageViewRecipe);
        Picasso.get().load(recipeItem.getImageUrl()).fit().centerCrop().into(imageViewRecipe);

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewName.setText(recipeItem.getName());

        textViewAuthor = (TextView) findViewById(R.id.textViewAuthor);
        textViewAuthor.setText(recipeItem.getAuthor());

        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        textViewDescription.setText(recipeItem.getDescription());

        textViewTime = (TextView) findViewById(R.id.textViewTime);
        textViewTime.setText("Required time: " + Integer.toString(recipeItem.getGlobalTime()) + ":00");

        textViewTag = (TextView) findViewById(R.id.textViewTags);
        textViewTag.setText(recipeItem.getTags().toString());

        textViewIngredient = (TextView) findViewById(R.id.textViewIngredient);

        List<Recipe.Ingredient> ingredients = recipeItem.getIngredients();
        for (Recipe.Ingredient ingredient : ingredients) {
            ingredientName = ingredient.getIngredientName();
            ingredientQuantity = ingredient.getIngredientQuantity();
            getIngredientUnit = ingredient.getIngredientUnit();
            textViewIngredient.append(ingredient.getIngredientQuantity() + " " + ingredient.getIngredientUnit() + " " + ingredient.getIngredientName());
            textViewIngredient.append("\n");


        }

        textViewStep = (TextView) findViewById(R.id.textViewStep);

        List<Recipe.Step> steps = recipeItem.getSteps();
        int stepNumber=1;
        for (Recipe.Step step : steps) {
            stepDescription = step.getStepDescription();
            stepTime = step.getStepTime();
            stepTimer = step.getStepTimer();
            textViewStep.append(stepNumber + ".)" + step.getStepDescription() + " ");
            textViewStep.append("\n");
            textViewStep.append("Time for step:" + step.getStepTime());
            textViewStep.append("\n");
            stepNumber++;

        }

        textViewReview = (TextView) findViewById(R.id.textViewReview);
        btnCook = (Button) findViewById(R.id.btnStartCook);
        btnCook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStepActivity();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        reviewsRef.whereEqualTo("recipeID", id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";
                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            Review review = documentSnapshot.toObject(Review.class);
                            String author = review.getAuthor();
                            String feedback = review.getFeedBackMessage();
                            data += "\n" + "<b>" + author + "</b>: " + feedback  + "\n";

                        }
                        textViewReview.setText(Html.fromHtml(data));

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    private void openStepActivity() {
        Intent intent = new Intent(this, StepActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable("Item", recipeItem);
        extras.putString("id", id);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
