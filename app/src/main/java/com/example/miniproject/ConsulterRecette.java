package com.example.miniproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class ConsulterRecette extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private SharedPreferences sharedPreferences;
    private ArrayList<Recipe> recipeList = new ArrayList<>();
    private EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_recette);


        recyclerView = findViewById(R.id.recyclerView);
        searchInput = findViewById(R.id.searchInput);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sharedPreferences = getSharedPreferences("RecipeApp", MODE_PRIVATE);

        adapter = new RecipeAdapter(recipeList, this, new RecipeAdapter.OnRecipeClickListener() {
            @Override
            public void onRecipeClick(Recipe recipe) {

                Intent intent = new Intent(ConsulterRecette.this, DetailsRecette.class);
                intent.putExtra("name", recipe.getName());
                intent.putExtra("ingredients", recipe.getIngredients());
                intent.putExtra("steps", recipe.getSteps());
                intent.putExtra("imageUri", recipe.getImageUri());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);

        loadRecipes();

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterRecipes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadRecipes() {
        Map<String, ?> allEntries = sharedPreferences.getAll();
        recipeList.clear();

        if (allEntries.isEmpty()) {
            Toast.makeText(this, "Aucune recette trouvée.", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean validDataFound = false;

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String recipeName = entry.getKey();
            String recipeData = entry.getValue().toString();
            String[] data = recipeData.split("\\|\\|");


            if (data.length >= 3) {
                String ingredients = data[0];
                String steps = data[1];
                String imageUri = data.length == 3 ? "" : data[2];
                recipeList.add(new Recipe(recipeName, "", ingredients, steps, imageUri));



                validDataFound = true;
            } else {

                Log.e("ConsulterRecette", "Données mal formatées pour la recette : " + recipeName);
            }
        }

        if (!validDataFound) {
            Toast.makeText(this, "Aucune recette valide trouvée.", Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged();
    }

    private void filterRecipes(String query) {
        ArrayList<Recipe> filteredList = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            // Filtrer par nom, ingrédients ou catégorie (si ces informations sont présentes)
            if (recipe.getName().toLowerCase().contains(query.toLowerCase())
                    || recipe.getIngredients().toLowerCase().contains(query.toLowerCase())
                    || recipe.getCategory().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(recipe);
            }
        }


        adapter.updateList(filteredList);
    }
}
