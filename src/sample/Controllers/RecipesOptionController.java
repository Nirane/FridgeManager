package sample.Controllers;

import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.UsableClasses.Recipe;

import java.sql.*;
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
    private Date date;
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
                    addRecipe(name,type,ingredients,date,time,stages);
                });
            }
            else if(selectedButtonName.equals("searchButton"))
            {
                window.setTitle("Wyszukaj przepis");
                windowName.setText("Wyszukaj przepis");
                endActionButton.setText("Szukaj");
                endActionButton.setOnMouseClicked(event -> {
                    setOptionData();
                    searchRecipe(name,type,ingredients,date,time,stages);
                });
            }
            else
            {
                nameField.setText(selectedRecipe.getRecipe());
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
                        editRecipe(name,type,ingredients,date,time,stages);
                    });
                }
                else if(selectedButtonName.equals("deleteButton"))
                {
                    nameField.setEditable(false);
                    typeField.setEditable(false);
                    ingredientsField.setEditable(false);
                    dateField.setDisable(true);
                    timeField.setDisable(true);
                    stagesField.setEditable(false);

                    window.setTitle("Usuń przepis");
                    windowName.setText("Usuń przepis");
                    endActionButton.setText("Usuń");
                    endActionButton.setOnMouseClicked(event -> {
                        setOptionData();
                        deleteRecipe(name,type,ingredients,date,time,stages);
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
        if(dateField.getValue()!=null) date = Date.valueOf(dateField.getValue());
        if(timeField.getValue()!=null) time = timeField.getValue();
        stages = stagesField.getText();
    }

    @FXML
    private void addRecipe(String name, String type, String ingredients, Date date, String time, String stages)
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO recipes('nazwa', 'typ', 'składniki', 'data', 'czas','etapy') " +
                    "VALUES('" + name + "', " +
                    "'" + type + "', " +
                    "'" + ingredients + "', " +
                    "'" + date + "', " +
                    "'" + time + "'," +
                    "'" + stages + "')");
            statement.close();
            connection.close();

            recipes.getItems().add(new Recipe(name,type,ingredients,date.toString(),time,stages));
            window.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editRecipe(String name, String type, String ingredients, Date date, String time, String stages)
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
            Statement statement = connection.createStatement();
            statement.execute("UPDATE recipes " +
                    "SET nazwa = '" + name +
                    "', typ = '" + type +
                    "', składniki = '" + ingredients +
                    "', data = '" + date +
                    "', czas = '" + time  +
                    "', etapy = '" + stages +"' " +
                    "WHERE nazwa = '" + selectedRecipe.getRecipe() +
                    "' AND typ = '" + selectedRecipe.getType() +
                    "' AND składniki = '" + selectedRecipe.getIngredients() +
                    "' AND data = '" +selectedRecipe.getDate() +
                    "' AND czas = '" +selectedRecipe.getTime() +
                    "' AND etapy = '" + selectedRecipe.getStages() +"'");
            statement.close();
            connection.close();

            recipes.getSelectionModel().getSelectedItem().setRecipe(name);
            recipes.getSelectionModel().getSelectedItem().setType(type);
            recipes.getSelectionModel().getSelectedItem().setIngredients(ingredients);
            recipes.getSelectionModel().getSelectedItem().setDate(date.toString());
            recipes.getSelectionModel().getSelectedItem().setTime(time);
            recipes.getSelectionModel().getSelectedItem().setStages(stages);
            recipesController.fillStagesDescription();
            window.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteRecipe(String name, String type, String ingredients, Date date, String time, String stages)
    {
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM recipes " +
                    "WHERE nazwa = '" + name +
                    "' AND typ = '" + type +
                    "' AND składniki = '" + ingredients +
                    "' AND data = '" + date +
                    "' AND czas = '" + time +
                    "' AND etapy = '" + stages + "'");
            statement.close();
            connection.close();
            recipes.getItems().remove(selectedRecipe);
            stagesDescription.setText("");
            window.close();
            
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchRecipe(String name, String type, String ingredients, Date date, String time, String stages)
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM recipes WHERE ";

            if(!name.trim().isEmpty()) sql += "nazwa LIKE '%" + name + "%'";
            else sql += "nazwa LIKE '%'";
            if(!type.trim().isEmpty()) sql += " AND typ LIKE '%" + type + "%'";
            else sql += " AND typ LIKE '%'";
            if(!ingredients.trim().isEmpty()) sql += " AND składniki LIKE '%" + ingredients + "%'";
            else sql += " AND składniki LIKE '%'";
            if(date!=null) sql += " AND data LIKE '%" + date + "%'";
            else sql += " AND data LIKE '%'";
            if(time!=null) sql += " AND czas LIKE '%" + time + "%'";
            else sql += " AND czas LIKE '%'";
            if(!stages.trim().isEmpty()) sql += " AND etapy LIKE '%" + stages + "%'";
            else sql += " AND etapy LIKE '%'";

            statement.execute(sql);
            recipes.getItems().clear();
            ResultSet results = statement.getResultSet();
            while (results.next())
            {
                String nameQ = results.getString("nazwa");
                String typeQ = results.getString("typ");
                String ingredientsQ = results.getString("składniki");
                String dateQ = results.getString("data");
                String timeQ = results.getString("czas");
                String stagesQ = results.getString("etapy");
                recipes.getItems().add(new Recipe(nameQ,typeQ,ingredientsQ,dateQ,timeQ, stagesQ));
            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
