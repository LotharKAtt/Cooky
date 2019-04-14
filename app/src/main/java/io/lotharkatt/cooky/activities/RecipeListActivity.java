package io.lotharkatt.cooky.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.lotharkatt.cooky.R;
import io.lotharkatt.cooky.adapters.RecipeAdapter;
import io.lotharkatt.cooky.models.Recipe;

public class RecipeListActivity extends AppCompatActivity implements RecipeAdapter.OnClickListener {

    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private List<Recipe> recipeList;
    private ProgressBar progressBar;
    private FirebaseFirestore db;

    public RecipeListActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                if (isFirstStart) {

                    final Intent intent = new Intent(RecipeListActivity.this, IntroActivity.class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(intent);
                        }
                    });

                    SharedPreferences.Editor e = getPrefs.edit();

                    e.putBoolean("firstStart", false);
                    e.apply();
                }
            }
        });

        t.start();

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
        db = FirebaseFirestore.getInstance();

        db.collection("recipes").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                progressBar.setVisibility(View.GONE);

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

        FloatingActionButton fabAddRecipe = (FloatingActionButton) findViewById(R.id.fabAddRecipe);
        fabAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddRecipeActivity();

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
}
