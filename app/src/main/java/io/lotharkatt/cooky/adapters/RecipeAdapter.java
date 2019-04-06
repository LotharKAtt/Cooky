package io.lotharkatt.cooky.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

import io.lotharkatt.cooky.R;
import io.lotharkatt.cooky.activities.RecipeOverview;
import io.lotharkatt.cooky.models.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {


    private Context mCtx;
    private List<Recipe> recipeList;
    private static final String TAG = "RecipeAdapter";
    private OnClickListener mListner;


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
        List<Recipe.Step> steps = recipe.getSteps();
        int totalStepTime=0;

        for (Recipe.Step step : steps){
            totalStepTime = totalStepTime + step.getStepTime();
        }
        recipe.setGlobalTime(totalStepTime);



        holder.textViewName.setText(recipe.getName());
        holder.textViewAuthor.setText(recipe.getAuthor());
        holder.textViewDescription.setText(recipe.getDescription());
        holder.textViewTime.setText(String.valueOf(totalStepTime));



    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewName, textViewAuthor, textViewDescription, textViewTime;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewTime = itemView.findViewById(R.id.textViewTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListner != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListner.onItemClick(position);

                        }
                    }


                }
            });


        }

        @Override
        public void onClick(View v) {}


    }
    public interface OnClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickLIstener(OnClickListener listener){
        mListner = listener;

    }
}
