package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.UsableClasses.Recipe;

import java.io.IOException;
import java.sql.*;

public class RecipesController extends ToolbarController {

    @FXML private TableView<Recipe> recipes;
    @FXML private TableColumn<Recipe, String> tableRecipe;
    @FXML private TableColumn<Recipe, String> tableType;
    @FXML private TableColumn<Recipe, String> tableIngredients;
    @FXML private TableColumn<Recipe, String> tableDate;
    @FXML private TableColumn<Recipe, String> tableTime;

    @FXML private TextArea stagesDescription;
    @FXML private Button addButton;
    @FXML private Button searchButton;

    public void initialize()
    {
        initializeTable();
        updateRecipes();
    }

    private void initializeTable()
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS " +
                    "recipes(nazwa TEXT NOT NULL, " +
                    "typ TEXT, " +
                    "składniki TEXT NOT NULL, " +
                    "data DATE, " +
                    "czas TEXT," +
                    "etapy TEXT )");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadWindow(MouseEvent event)
    {
        if(event.getSource().equals(addButton) || event.getSource().equals(searchButton) || recipes.getSelectionModel().getSelectedItem()!=null)
        {
            Stage window = new Stage();
            window.initStyle(StageStyle.TRANSPARENT);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLscenes/recipeOption.fxml"));

            try {
                Parent root = fxmlLoader.load();

                RecipesOptionController recipesOptionController = fxmlLoader.getController();
                recipesOptionController.setSelectedButtonName(((Button) event.getSource()).getId());
                recipesOptionController.setSelectedRecipe(recipes.getSelectionModel().getSelectedItem());
                recipesOptionController.setRecipes(recipes);
                recipesOptionController.setStagesDescription(stagesDescription);
                recipesOptionController.setRecipesController(this);

                window.setScene(new Scene(root,400,600));
                window.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void updateRecipes()
    {
        recipes.getItems().clear();

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
            Statement statement = connection.createStatement();
            statement.execute("SELECT * FROM recipes");
            ResultSet results = statement.getResultSet();

            tableRecipe.setCellValueFactory(new PropertyValueFactory<>("recipe"));
            tableType.setCellValueFactory(new PropertyValueFactory<>("type"));
            tableIngredients.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
            tableDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            tableTime.setCellValueFactory(new PropertyValueFactory<>("time"));

            while(results.next())
            {
                String name = results.getString("nazwa");
                String type = results.getString("typ");
                String ingredients = results.getString("składniki");
                String date = results.getString("data");
                String time = results.getString("czas");
                String stages = results.getString("etapy");
                recipes.getItems().add(new Recipe(name,type,ingredients,date,time, stages));
            }
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void fillStagesDescription()
    {
        if(recipes.getSelectionModel().getSelectedItem()!=null)
        {
            String toSplit = recipes.getSelectionModel().getSelectedItem().getStages();
            String[] splitted = toSplit.split("[.]");

            StringBuilder builder = new StringBuilder();
            for (int i=0;i<splitted.length;i++) {
                splitted[i] = splitted[i].replace("\n","");
                String string =  i+1 + ". " +splitted[i] +"\n";
                builder.append(string);
            }
            stagesDescription.setText(builder.toString());
        }
    }
}
