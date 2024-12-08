package com.example.miniproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class AjouterRecette extends AppCompatActivity {
    private EditText editRecipeName, editIngredients, editSteps;
    private ImageView recipeImageView;
    private String imageUri;
    private SharedPreferences sharedPreferences;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_recette);

        // Initialisation des vues
        editRecipeName = findViewById(R.id.editRecipeName);
        editIngredients = findViewById(R.id.editIngredients);
        editSteps = findViewById(R.id.editSteps);
        recipeImageView = findViewById(R.id.recipeImage);
        MaterialButton btnSave = findViewById(R.id.btnSave);
        MaterialButton btnPickImage = findViewById(R.id.btnPickImage);

        sharedPreferences = getSharedPreferences("RecipeApp", MODE_PRIVATE);

        // Vérifier si nous sommes dans un mode de modification
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("name")) {
            // Pré-remplir les champs avec les informations existantes
            editRecipeName.setText(intent.getStringExtra("name"));
            editIngredients.setText(intent.getStringExtra("ingredients"));
            editSteps.setText(intent.getStringExtra("steps"));
            imageUri = intent.getStringExtra("imageUri");
            if (imageUri != null && !imageUri.isEmpty()) {
                recipeImageView.setImageURI(Uri.parse(imageUri));
            }
        }

        // Ouvrir la galerie pour choisir une image
        btnPickImage.setOnClickListener(v -> openImagePicker());


        btnSave.setOnClickListener(v -> saveRecipe());
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData().toString();
            recipeImageView.setImageURI(Uri.parse(imageUri));
        }
    }

    private void saveRecipe() {
        String name = editRecipeName.getText().toString();
        String ingredients = editIngredients.getText().toString();
        String steps = editSteps.getText().toString();

        if (name.isEmpty() || ingredients.isEmpty() || steps.isEmpty()) {
            Toast.makeText(this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(name, ingredients + "||" + steps + "||" + (imageUri != null ? imageUri : ""));
            editor.apply();
            Toast.makeText(this, "Recette ajoutée avec succès", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
