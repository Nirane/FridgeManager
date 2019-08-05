package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class RecipesData {

    private static RecipesData recipesData = new RecipesData();
    private ObservableList<Recipe> recipes;

    public RecipesData()
    {
        recipes = FXCollections.observableArrayList();
        List<Recipe> list = Source.getInstance().loadRecipes();
        for (Recipe recipe : list) {
            addRecipe(recipe);
        }
    }

    public static RecipesData getInstance()
    {
        return recipesData;
    }

    public ObservableList<Recipe> getRecipes()
    {
        return recipes;
    }

    public void setRecipes(List<Recipe> list)
    {
        recipes.clear();
        for (Recipe recipe : list) {
            addRecipe(recipe);
        }
    }

    public void addRecipe(Recipe recipe)
    {
        recipes.add(recipe);
    }

    public void removeRecipe(Recipe recipe)
    {
        recipes.remove(recipe);
    }
}
