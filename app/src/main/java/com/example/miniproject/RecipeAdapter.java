package com.example.miniproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private ArrayList<Recipe> recipeList;
    private OnRecipeClickListener onRecipeClickListener;
    private Context context;

    public RecipeAdapter(ArrayList<Recipe> recipeList, Context context, OnRecipeClickListener listener) {
        this.recipeList = recipeList;
        this.context = context;
        this.onRecipeClickListener = listener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.nameText.setText(recipe.getName());
        holder.itemView.setOnClickListener(v -> onRecipeClickListener.onRecipeClick(recipe));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void updateList(ArrayList<Recipe> newList) {
        recipeList = newList;
        notifyDataSetChanged();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.recipeName);
        }
    }

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }
}
