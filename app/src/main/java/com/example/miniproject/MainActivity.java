package com.example.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des vues
        TextView welcomeMessage = findViewById(R.id.welcomeMessage);
        MaterialButton btnAddRecipe = findViewById(R.id.btnAddRecipe);
        MaterialButton btnViewRecipes = findViewById(R.id.btnViewRecipes);

        // Message de bienvenue
        welcomeMessage.setText("Découvrez vos nouvelles recettes favorites !");


        btnAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AjouterRecette.class));
            }
        });

        btnViewRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ConsulterRecette.class));
            }
        });
    }
}