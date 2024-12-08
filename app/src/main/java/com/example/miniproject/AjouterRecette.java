package com.example.miniproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class AjouterRecette extends AppCompatActivity {
    private EditText editRecipeName, editIngredients, editSteps;
    private RadioGroup radioGroupCategory;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_recette);

        // Initialisation des vues
        editRecipeName = findViewById(R.id.editRecipeName);
        editIngredients = findViewById(R.id.editIngredients);
        editSteps = findViewById(R.id.editSteps);
        radioGroupCategory = findViewById(R.id.radioGroupCategory);
        MaterialButton btnSave = findViewById(R.id.btnSave);

        // Initialisation de SharedPreferences
        sharedPreferences = getSharedPreferences("RecipeApp", MODE_PRIVATE);

        // Gestion du bouton "Sauvegarder"
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editRecipeName.getText().toString();
                String ingredients = editIngredients.getText().toString();
                String steps = editSteps.getText().toString();

                // Récupérer la catégorie sélectionnée
                int selectedCategoryId = radioGroupCategory.getCheckedRadioButtonId();
                if (selectedCategoryId == -1) {
                    Toast.makeText(AjouterRecette.this, "Veuillez sélectionner une catégorie", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton selectedRadioButton = findViewById(selectedCategoryId);
                String category = selectedRadioButton.getText().toString();

                // Validation des champs
                if (name.isEmpty() || ingredients.isEmpty() || steps.isEmpty()) {
                    Toast.makeText(AjouterRecette.this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
                } else {
                    saveRecipe(name, ingredients, steps, category);
                    Toast.makeText(AjouterRecette.this, "Recette ajoutée avec succès", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void saveRecipe(String name, String ingredients, String steps, String category) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name, category + "||" + ingredients + "||" + steps);
        editor.apply();
    }
}