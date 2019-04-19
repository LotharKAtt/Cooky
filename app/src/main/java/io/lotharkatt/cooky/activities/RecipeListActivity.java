package io.lotharkatt.cooky.activities;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.lotharkatt.cooky.R;
import io.lotharkatt.cooky.adapters.RecipeAdapter;
import io.lotharkatt.cooky.models.Recipe;

public class RecipeListActivity extends AppCompatActivity {

    private RecipeAdapter adapter;
    private ProgressBar progressBar;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference recipesRef = db.collection("recipes");


    public RecipeListActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_recipe_list);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        setUpRecyclerView();

        FloatingActionButton fabAddRecipe = (FloatingActionButton) findViewById(R.id.fabAddRecipe);
        fabAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecipeListActivity.this, AddRecipeActivity.class));
            }
        });
    }

    private void setUpRecyclerView() {
        Query query = recipesRef.orderBy("name", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Recipe> options = new FirestoreRecyclerOptions.Builder<Recipe>()
                .setQuery(query, Recipe.class)
                .build();

        adapter = new RecipeAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_recipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
        adapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(DocumentSnapshot documentSnapshot, int position) {
                Recipe recipe = documentSnapshot.toObject(Recipe.class);
                String id = documentSnapshot.getId();

                Intent intent = new Intent(RecipeListActivity.this, RecipeOverview.class);
                Bundle extras = new Bundle();
                extras.putParcelable("Item", recipe);
                extras.putString("id", id);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }




}
