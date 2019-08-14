package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import sample.Model.Food;
import sample.Model.FoodData;
import sample.Model.Recipe;
import sample.Model.RecipesData;

import java.time.LocalDate;

public class CalendarController {

    @FXML private DatePicker datePicker;

    @FXML private ListView<Recipe> recipeListView;
    @FXML private ComboBox<Recipe> recipeCombobox;
    @FXML private TextArea recipeIngredients;

    @FXML private ListView<Food> foodListView;
    @FXML private ComboBox<Food> foodCombobox;

    public void initialize()
    {
        datePicker.setValue(LocalDate.now());
        setComboboxes();
    }

    private void setComboboxes()
    {
        recipeCombobox.setItems(RecipesData.getInstance().getRecipes());
        Callback<ListView<Recipe>, ListCell<Recipe>> recipeCellFactory = new Callback<>() {
            @Override
            public ListCell<Recipe> call(ListView<Recipe> recipeListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Recipe recipe, boolean empty) {
                        super.updateItem(recipe, empty);
                        if (empty) setText(null);
                        else {
                            setText(recipe.getName());
                        }
                    }
                };
            }
        };
        recipeCombobox.setButtonCell(recipeCellFactory.call(null));
        recipeCombobox.setCellFactory(recipeCellFactory);

        foodCombobox.setItems(FoodData.getInstance().getFood());
        Callback<ListView<Food>, ListCell<Food>> foodCellFactory = new Callback<>() {
            @Override
            public ListCell<Food> call(ListView<Food> foodListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Food food, boolean empty) {
                        super.updateItem(food, empty);
                        if (empty) setText(null);
                        else {
                            setText(food.getName());
                        }
                    }
                };
            }
        };
        foodCombobox.setButtonCell(foodCellFactory.call(null));
        foodCombobox.setCellFactory(foodCellFactory);
    }

    private void onDateChange()
    {
        //load entries according to selected date
    }

    private void addFood()
    {
        //rearrange datas
    }

    private void deleteFood()
    {

    }

    private void addRecipe()
    {

    }

    private void deleteRecipe()
    {

    }

    private void fillIngredientsDescription()
    {

    }
}
