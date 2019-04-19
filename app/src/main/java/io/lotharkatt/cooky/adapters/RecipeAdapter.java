package io.lotharkatt.cooky.adapters;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;


import java.util.List;

import io.lotharkatt.cooky.R;
import io.lotharkatt.cooky.models.Recipe;

public class RecipeAdapter extends FirestoreRecyclerAdapter<Recipe, RecipeAdapter.RecipeViewHolder> {
    private OnItemClickListener listener;


    public RecipeAdapter(@NonNull FirestoreRecyclerOptions<Recipe> options) {
        super(options);
    }


    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe,
                parent, false);
        return new RecipeViewHolder(v);
    }



    @Override
    protected void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int position, @NonNull Recipe recipe) {

        int totalStepTime = 0;
        List<Recipe.Step> steps = recipe.getSteps();
        for (Recipe.Step step : steps) {
            totalStepTime = totalStepTime + step.getStepTime();
        }



         recipe.setGlobalTime(totalStepTime);

        recipeViewHolder.textViewName.setText(recipe.getName());
        recipeViewHolder.textViewAuthor.setText(recipe.getAuthor());
        recipeViewHolder.textViewDescription.setText(recipe.getDescription());
        recipeViewHolder.textViewTime.setText(String.valueOf(totalStepTime) + ":00");


    }

    class RecipeViewHolder extends RecyclerView.ViewHolder{
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
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null){
                        listener.OnItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });



        }




    }
    public interface OnItemClickListener {
        void OnItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public  void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;

    }

}
