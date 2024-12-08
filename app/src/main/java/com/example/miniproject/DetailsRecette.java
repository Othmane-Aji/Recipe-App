package com.example.miniproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsRecette extends AppCompatActivity {
    private TextView recipeNameText, ingredientsText, stepsText;
    private ImageView recipeImageView;
    private Button btnEdit, btnDelete;
    private SharedPreferences sharedPreferences;
    private String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_recette);

        // Initialisation des vues
        recipeImageView = findViewById(R.id.recipeImageView);
        recipeNameText = findViewById(R.id.recipeName);
        ingredientsText = findViewById(R.id.recipeIngredients);
        stepsText = findViewById(R.id.recipeSteps);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        // Initialisation de SharedPreferences
        sharedPreferences = getSharedPreferences("RecipeApp", MODE_PRIVATE);

        // Récupérer les données passées par l'Intent
        Intent intent = getIntent();
        recipeName = intent.getStringExtra("name");
        String ingredients = intent.getStringExtra("ingredients");
        String steps = intent.getStringExtra("steps");
        String imageUri = intent.getStringExtra("imageUri");

        // Afficher les informations de la recette
        recipeNameText.setText(recipeName);
        ingredientsText.setText(ingredients);
        stepsText.setText(steps);

        // Vérifier si l'URI de l'image est valide et l'afficher
        if (imageUri != null && !imageUri.isEmpty()) {
            recipeImageView.setImageURI(Uri.parse(imageUri));
        } else {
            // Si aucune image n'est fournie, vous pouvez afficher une image par défaut
            recipeImageView.setImageResource(R.drawable.placeholder_image); // Image par défaut
        }

        btnEdit.setOnClickListener(v -> {

            Intent editIntent = new Intent(DetailsRecette.this, AjouterRecette.class);
            editIntent.putExtra("name", recipeName);
            editIntent.putExtra("ingredients", ingredients);
            editIntent.putExtra("steps", steps);
            editIntent.putExtra("imageUri", imageUri);
            startActivity(editIntent);
        });


        btnDelete.setOnClickListener(v -> {
            deleteRecipe(recipeName);
        });
    }

    private void deleteRecipe(String recipeName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(recipeName);
        editor.apply();
        Toast.makeText(this, "Recette supprimée avec succès", Toast.LENGTH_SHORT).show();
        finish();
    }
}
