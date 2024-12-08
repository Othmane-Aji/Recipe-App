package com.example.miniproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsRecette extends AppCompatActivity {
    private TextView recipeNameText, ingredientsText, stepsText;
    private Button deleteButton, editButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_recette);

        recipeNameText = findViewById(R.id.recipeName);
        ingredientsText = findViewById(R.id.ingredients);
        stepsText = findViewById(R.id.steps);
        deleteButton = findViewById(R.id.btnDelete);
        editButton = findViewById(R.id.btnEdit);

        sharedPreferences = getSharedPreferences("RecipeApp", MODE_PRIVATE);

        Intent intent = getIntent();
        String recipeName = intent.getStringExtra("name");
        String ingredients = intent.getStringExtra("ingredients");
        String steps = intent.getStringExtra("steps");

        recipeNameText.setText(recipeName);
        ingredientsText.setText(ingredients);
        stepsText.setText(steps);

        editButton.setOnClickListener(v -> {
            Intent editIntent = new Intent(DetailsRecette.this, ModifierRecette.class);
            editIntent.putExtra("name", recipeName);
            editIntent.putExtra("ingredients", ingredients);
            editIntent.putExtra("steps", steps);
            startActivity(editIntent);
        });

        deleteButton.setOnClickListener(v -> confirmDelete(recipeName));
    }

    private void confirmDelete(final String recipeName) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmer la suppression")
                .setMessage("Êtes-vous sûr de vouloir supprimer cette recette ?")
                .setPositiveButton("Oui", (dialog, which) -> deleteRecipe(recipeName))
                .setNegativeButton("Non", null)
                .show();
    }

    private void deleteRecipe(String recipeName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(recipeName);
        editor.apply();

        Toast.makeText(this, "Recette supprimée avec succès", Toast.LENGTH_SHORT).show();
        finish();
    }
}
