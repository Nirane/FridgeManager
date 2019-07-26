package sample.UsableClasses;

import javafx.beans.property.SimpleStringProperty;

public class Recipe {
    private SimpleStringProperty recipe;
    private SimpleStringProperty type;
    private SimpleStringProperty ingredients;
    private SimpleStringProperty date;
    private SimpleStringProperty time;
    private String stages;

    public Recipe(String recipe, String type, String ingredients, String date, String time, String stages) {
        this.recipe = new SimpleStringProperty(recipe);
        this.type = new SimpleStringProperty(type);
        this.ingredients = new SimpleStringProperty(ingredients);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.stages = stages;
    }

    public String getRecipe() {
        return recipe.get();
    }

    public SimpleStringProperty recipeProperty() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe.set(recipe);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getIngredients() {
        return ingredients.get();
    }

    public SimpleStringProperty ingredientsProperty() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients.set(ingredients);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getStages() {
        return stages;
    }

    public void setStages(String stages) {
        this.stages = stages;
    }
}
