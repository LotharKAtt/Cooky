package io.lotharkatt.cooky.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.lotharkatt.cooky.R;
import io.lotharkatt.cooky.adapters.RecipeAdapter;
import io.lotharkatt.cooky.models.Recipe;

public class RecipeListActivity extends AppCompatActivity implements RecipeAdapter.OnClickListener {

    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private List<Recipe> recipeList;
    private ProgressBar progressBar;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference recipesRef = db.collection("recipes");


    public RecipeListActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_recipe_list);
        
        progressBar = findViewById(R.id.progressbar);
        
        recyclerView = findViewById(R.id.recyclerview_recipes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recipeList = new ArrayList<>();



        adapter = new RecipeAdapter(this, recipeList);

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickLIstener(new RecipeAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(RecipeListActivity.this, RecipeOverview.class);
                intent.putExtra("Item", recipeList.get(position));
                startActivity(intent);
            }


        });


        FloatingActionButton fabAddRecipe = (FloatingActionButton) findViewById(R.id.fabAddRecipe);
        fabAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddRecipeActivity();

            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        recipesRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(RecipeListActivity.this, "Error during fetching", Toast.LENGTH_SHORT).show();

                    return;
                }
                progressBar.setVisibility(View.GONE);
                // FIXME: There must be better opition
                recipeList.clear();

                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        Recipe r = d.toObject(Recipe.class);
                        recipeList.add(r);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void openAddRecipeActivity() {
        Intent intent = new Intent(this, AddRecipeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {

    }

//
//    @Override
//    public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
//
//    }
}
