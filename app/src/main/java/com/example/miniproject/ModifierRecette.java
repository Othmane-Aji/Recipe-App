package com.example.miniproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ModifierRecette extends AppCompatActivity {
    private EditText nameEditText, ingredientsEditText, stepsEditText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_recette);

        nameEditText = findViewById(R.id.editRecipeName);
        ingredientsEditText = findViewById(R.id.editIngredients);
        stepsEditText = findViewById(R.id.editSteps);

        sharedPreferences = getSharedPreferences("RecipeApp", MODE_PRIVATE);

        String oldName = getIntent().getStringExtra("name");
        String ingredients = getIntent().getStringExtra("ingredients");
        String steps = getIntent().getStringExtra("steps");

        nameEditText.setText(oldName);
        ingredientsEditText.setText(ingredients);
        stepsEditText.setText(steps);

        findViewById(R.id.btnSave).setOnClickListener(v -> saveRecipeChanges(oldName));
    }

    private void saveRecipeChanges(String oldName) {
        String newName = nameEditText.getText().toString();
        String ingredients = ingredientsEditText.getText().toString();
        String steps = stepsEditText.getText().toString();

        if (newName.isEmpty() || ingredients.isEmpty() || steps.isEmpty()) {
            Toast.makeText(this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(oldName);
        editor.putString(newName, ingredients + "||" + steps);
        editor.apply();

        Toast.makeText(this, "Recette mise à jour avec succès", Toast.LENGTH_SHORT).show();
        finish();
    }
}
