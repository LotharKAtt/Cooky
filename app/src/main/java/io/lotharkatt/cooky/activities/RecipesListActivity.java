package io.lotharkatt.cooky.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.content.Context;

import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    private LinearLayout linearLayoutCardRecp;

    public RecipesListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        linearLayoutCardRecp = (LinearLayout) findViewById(R.id.LinerLayoutCardRecp);

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
                // TODO: Switch to cooky slide


                Intent intent = new Intent(RecipesListActivity.this, RecipeOverview.class);
                intent.putExtra("Item", recipeList.get(position));


                startActivity(intent);

                Recipe recipe = recipeList.get(2);

                //DEBUG
                Toast.makeText(RecipesListActivity.this,"Test Click lissener", Toast.LENGTH_LONG).show();


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
//                        r.setId(d.getId());
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
