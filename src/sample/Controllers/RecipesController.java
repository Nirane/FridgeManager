package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Model.Recipe;
import sample.Model.RecipesData;
import sample.Model.Source;

import java.io.IOException;

public class RecipesController extends ToolbarController {

    @FXML private TableView<Recipe> recipes;

    @FXML private TextArea stagesDescription;
    @FXML private Button addButton;
    @FXML private Button searchButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;
    @FXML private Button cookButton;

    public void initialize()
    {
        Source.getInstance().initializeRecipes();
        recipes.setItems(RecipesData.getInstance().getRecipes());
    }

    @FXML
    private void loadWindow(MouseEvent event)
    {
        if(event.getSource().equals(addButton) || event.getSource().equals(searchButton) ||
                ((event.getSource().equals(editButton)||(event.getSource().equals(deleteButton)))
                        &&(recipes.getSelectionModel().getSelectedItem()!=null)))
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
        else if(event.getSource().equals(cookButton) && recipes.getSelectionModel().getSelectedItem()!=null)
        {
            Stage window = new Stage();
            window.initStyle(StageStyle.TRANSPARENT);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLscenes/cook.fxml"));

            try {
                Parent root = fxmlLoader.load();

                CookController cookController = fxmlLoader.getController();
                cookController.setSelectedRecipe(recipes.getSelectionModel().getSelectedItem());

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
        RecipesData.getInstance().setRecipes(Source.getInstance().loadRecipes());
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
        else stagesDescription.setText("");
    }
}
