package io.lotharkatt.cooky.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.LinearLayout;
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

public class RecipesListActivity extends AppCompatActivity implements RecipeAdapter.OnClickListener {

    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private List<Recipe> recipeList;
    private ProgressBar progressBar;
    private FirebaseFirestore db;

    public RecipesListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

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
                Intent intent = new Intent(RecipesListActivity.this, RecipeOverview.class);
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
    }

    @Override
    public void onItemClick(int position) {
    }

}
