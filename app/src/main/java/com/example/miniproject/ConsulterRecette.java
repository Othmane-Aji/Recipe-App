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

        // Initialisation des vues
        recyclerView = findViewById(R.id.recyclerView);
        searchInput = findViewById(R.id.searchInput);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialisation des SharedPreferences
        sharedPreferences = getSharedPreferences("RecipeApp", MODE_PRIVATE);

        // Créer l'adaptateur avec un OnRecipeClickListener
        adapter = new RecipeAdapter(recipeList, this, new RecipeAdapter.OnRecipeClickListener() {
            @Override
            public void onRecipeClick(Recipe recipe) {
                // Créez un Intent pour passer les données de la recette à l'écran des détails
                Intent intent = new Intent(ConsulterRecette.this, DetailsRecette.class);
                intent.putExtra("name", recipe.getName());
                intent.putExtra("ingredients", recipe.getIngredients());
                intent.putExtra("steps", recipe.getSteps());
                startActivity(intent);  // Lancer l'activité de détail
            }
        });

        // Attacher l'adaptateur au RecyclerView
        recyclerView.setAdapter(adapter);

        // Charger les recettes sauvegardées
        loadRecipes();

        // Recherche en temps réel
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

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String recipeName = entry.getKey();
            String recipeData = entry.getValue().toString();

            Log.d("SharedPreferences", "Key: " + recipeName + " - Value: " + recipeData);

            // Vérification et extraction des données
            String[] data = recipeData.split("\\|\\|");

            if (data.length == 3) {
                String category = data[0];
                String ingredients = data[1];
                String steps = data[2];
                recipeList.add(new Recipe(recipeName, category, ingredients, steps));
            } else {
                Log.e("SharedPreferences", "Données mal formatées pour la recette : " + recipeName);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(recipeName);  // Supprime les données invalides
                editor.apply();
            }
        }

        Log.d("ConsulterRecette", "Nombre de recettes chargées : " + recipeList.size());

        adapter.notifyDataSetChanged();
    }

    private void filterRecipes(String query) {
        ArrayList<Recipe> filteredList = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            if (recipe.getName().toLowerCase().contains(query.toLowerCase())
                    || recipe.getIngredients().toLowerCase().contains(query.toLowerCase())
                    || recipe.getCategory().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(recipe);
            }
        }

        adapter.updateList(filteredList);
    }
}
