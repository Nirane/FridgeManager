package sample.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Model.RecipesData;
import sample.Model.Source;
import sample.Model.Recipe;

import java.sql.Date;
import java.time.LocalDate;

public class RecipesOptionController extends ToolbarController {

    @FXML private TextField nameField;
    @FXML private TextField typeField;
    @FXML private TextArea ingredientsField;
    @FXML private DatePicker dateField;
    @FXML private ComboBox<String> timeField;
    @FXML private TextArea stagesField;
    @FXML private Button endActionButton;
    @FXML private Label windowName;

    private Stage window;

    private Recipe selectedRecipe;
    private String selectedButtonName;
    private TableView<Recipe> recipes;
    private TextArea stagesDescription;
    private RecipesController recipesController;

    private String name;
    private String type;
    private String ingredients;
    private String date;
    private String time;
    private String stages;

    void setRecipesController(RecipesController recipesController)
    {
        this.recipesController = recipesController;
    }

    void setSelectedButtonName(String selectedButtonName) {
        this.selectedButtonName = selectedButtonName;
    }

    void setSelectedRecipe(Recipe selectedRecipe)
    {
        this.selectedRecipe = selectedRecipe;
    }

    void setRecipes(TableView<Recipe> recipes)
    {
        this.recipes = recipes;
    }

    void setStagesDescription(TextArea stagesDescription)
    {
        this.stagesDescription = stagesDescription;
    }

    public void initialize()
    {
        Platform.runLater(() -> {
            window = (Stage) nameField.getScene().getWindow();

            if(selectedButtonName.equals("addButton"))
            {
                window.setTitle("Dodaj przepis");
                windowName.setText("Dodaj przepis");
                endActionButton.setText("Dodaj");
                endActionButton.setOnMouseClicked(event -> {
                    setOptionData();
                    addRecipe();
                });
            }
            else if(selectedButtonName.equals("searchButton"))
            {
                window.setTitle("Wyszukaj przepis");
                windowName.setText("Wyszukaj przepis");
                endActionButton.setText("Szukaj");
                endActionButton.setOnMouseClicked(event -> {
                    setOptionData();
                    searchRecipe();
                });
            }
            else
            {
                nameField.setText(selectedRecipe.getName());
                typeField.setText(selectedRecipe.getType());
                ingredientsField.setText(selectedRecipe.getIngredients());
                dateField.setValue(LocalDate.parse(selectedRecipe.getDate()));
                timeField.setValue(selectedRecipe.getTime());
                stagesField.setText(selectedRecipe.getStages());

                if(selectedButtonName.equals("editButton"))
                {
                    window.setTitle("Edytuj przepis");
                    windowName.setText("Edytuj przepis");
                    endActionButton.setText("Zapisz");
                    endActionButton.setOnMouseClicked(event -> {
                        setOptionData();
                        editRecipe();
                    });
                }
                else if(selectedButtonName.equals("deleteButton"))
                {
                    nameField.setDisable(true);
                    typeField.setDisable(true);
                    ingredientsField.setDisable(true);
                    dateField.setDisable(true);
                    timeField.setDisable(true);
                    stagesField.setDisable(true);

                    window.setTitle("Usuń przepis");
                    windowName.setText("Usuń przepis");
                    endActionButton.setText("Usuń");
                    endActionButton.setOnMouseClicked(event -> {
                        setOptionData();
                        deleteRecipe();
                    });
                }
            }

        });

    }

    private void setOptionData()
    {
        name = nameField.getText();
        type = typeField.getText();
        ingredients = ingredientsField.getText();
        date = (dateField.getValue()!=null) ? Date.valueOf(dateField.getValue()).toString() : "";
        time = (timeField.getValue()!=null) ? timeField.getValue() : "";
        stages = stagesField.getText();
    }

    private void addRecipe()
    {
        int generatedId = Source.getInstance().addRecipe(name,type,ingredients,date,time,stages);
        RecipesData.getInstance().addRecipe(new Recipe(generatedId,name,type,ingredients,date,time,stages));
        window.close();
    }

    private void editRecipe()
    {
        Source.getInstance().editRecipe(selectedRecipe, name,type,ingredients,date,time,stages);
        selectedRecipe.setName(name);
        selectedRecipe.setType(type);
        selectedRecipe.setIngredients(ingredients);
        selectedRecipe.setDate(date);
        selectedRecipe.setTime(time);
        selectedRecipe.setStages(stages);
        recipesController.fillStagesDescription();
        window.close();
    }

    private void deleteRecipe()
    {
        Source.getInstance().deleteRecipe(name,type,ingredients,date,time,stages);
        RecipesData.getInstance().removeRecipe(selectedRecipe);
        window.close();
    }

    private void searchRecipe()
    {
        RecipesData.getInstance().setRecipes(Source.getInstance().searchRecipe(name,type,ingredients,date,time,stages));
        recipesController.fillStagesDescription();
        window.close();
    }
}
