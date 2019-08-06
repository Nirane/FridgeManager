package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import sample.Model.Food;
import sample.Model.Recipe;

import java.time.LocalDate;

public class CalendarController {

    @FXML private DatePicker datePicker;

    @FXML private ListView<Recipe> recipeListView;
    @FXML private ComboBox<Recipe> recipeCombobox;
    @FXML private TextArea recipeIngredients;

    @FXML private ListView<Food> foodListView;
    @FXML private ComboBox<String> foodCombobox;

    public void initialize()
    {
        datePicker.setValue(LocalDate.now());
    }

    private void setComboboxes()
    {

    }

    private void onDateChange()
    {

    }

    private void addFood()
    {

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
