package io.lotharkatt.cooky.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.lotharkatt.cooky.R;
import io.lotharkatt.cooky.models.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {


    private Context mCtx;
    private List<Recipe> recipeList;
    private static final String TAG = "RecipeAdapter";

    public RecipeAdapter(Context mCtx, List<Recipe> recipeList) {
        this.mCtx = mCtx;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new RecipeViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_recipe, parent, false)


        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");
        Recipe recipe = recipeList.get(position);

        holder.textViewName.setText(recipe.getName());
        holder.textViewAuthor.setText(recipe.getAuthor());
        holder.textViewDescription.setText(recipe.getDescription());



    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewName, textViewAuthor, textViewDescription;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
     //       Toast.makeText(RecipeViewHolder.this,"Test Click lissener", Toast.LENGTH_LONG).show();
            //Toast.makeText(AddRecipeActivity.this, "Product added", Toast.LENGTH_LONG).show();


        }


    }
    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
