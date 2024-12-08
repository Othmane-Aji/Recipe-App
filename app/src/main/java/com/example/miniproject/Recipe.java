package com.example.miniproject;


public class Recipe {
    private String name;
    private String category;
    private String ingredients;
    private String steps;

    public Recipe(String name, String category, String ingredients, String steps) {
        this.name = name;
        this.category = category;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getSteps() {
        return steps;
    }
}
