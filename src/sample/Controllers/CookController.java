package sample.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Model.Dish;
import sample.Model.Recipe;
import sample.Model.Source;

import java.time.LocalDate;
import java.util.LinkedList;

public class CookController extends ToolbarController {

    @FXML private VBox content;

    private Recipe selectedRecipe;
    private int numberOfSteps;
    private LinkedList<CheckBox> checkBoxes;

    private Stage window;

    public void setSelectedRecipe(Recipe recipe)
    {
        this.selectedRecipe = recipe;
    }

    public void initialize()
    {
        checkBoxes= new LinkedList<>();
        numberOfSteps = 0;

        Platform.runLater(() -> {
            window = (Stage) content.getScene().getWindow();
            String toSplit = selectedRecipe.getStages();
            String[] splitted = toSplit.split("[.]");

            Label name = new Label("nazwa: "+selectedRecipe.getName());
            name.wrapTextProperty().setValue(true);
            name.setStyle(" -fx-background-color: #292826;" +
                    "    -fx-text-fill: #F9D342; -fx-border-color: #F9D342; -fx-border-width: 1;");
            content.getChildren().add(name);

            Label type = new Label("typ: "+selectedRecipe.getType());
            type.wrapTextProperty().setValue(true);
            type.setStyle(" -fx-background-color: #292826;" +
                    "    -fx-text-fill: #F9D342; -fx-border-color: #F9D342; -fx-border-width: 1;");
            content.getChildren().add(type);

            Label ingredients = new Label("składniki: " + selectedRecipe.getIngredients());
            ingredients.wrapTextProperty().setValue(true);
            ingredients.setStyle(" -fx-background-color: #292826;" +
                    "    -fx-text-fill: #F9D342; -fx-border-color: #F9D342; -fx-border-width: 1;");
            content.getChildren().add(ingredients);

            Label time = new Label("czas: "+selectedRecipe.getTime());
            time.wrapTextProperty().setValue(true);
            time.setStyle(" -fx-background-color: #292826; -fx-min-width: 300;" +
                    "  -fx-text-fill: #F9D342; -fx-border-color: #F9D342; -fx-border-width: 1;");
            content.getChildren().add(time);

            for(String split : splitted)
            {
                HBox hBox = new HBox();
                hBox.spacingProperty().setValue(10);

                hBox.setStyle("-fx-border-width: 1; -fx-border-color: black; -fx-border-style: solid;" +
                        " -fx-min-height: 35; -fx-margin: 0 50 0 50; -fx-padding: 2 0 0 0;" +
                        " -fx-background-color: #F9D342;");
                hBox.setAlignment(Pos.TOP_CENTER);
                //hBox.getStyleClass().add("");
                CheckBox checkBox = new CheckBox();

                hBox.getChildren().add(checkBox);
                Label label = new Label(split.replace("\n",""));
                label.wrapTextProperty().setValue(true);
                hBox.getChildren().add(label);

                checkBoxes.add(checkBox);
                content.getChildren().add(hBox);
                numberOfSteps++;
            }

            for(int i=1;i<checkBoxes.size();i++)
            {
                checkBoxes.get(i).setDisable(true);
            }

            for(int i=0;i<checkBoxes.size();i++)
            {
                final int it = i;
                checkBoxes.get(i).setOnMouseClicked((e) -> {
                    checkBoxes.get(it).setDisable(true);
                    if(it+1<checkBoxes.size())checkBoxes.get(it+1).setDisable(false);
                    else if(it+1 == checkBoxes.size())
                    {
                        content.getChildren().add(new Label("Kliknij ugotuj aby dokończyć gotowanie! :)"));
                    }
                });
            }
        });

    }

    @FXML
    private void cook()
    {
        int howMany = 0;
        for(CheckBox checkBox : checkBoxes)
        {
            if(checkBox.isSelected()) howMany++;
        }

        if(howMany==numberOfSteps)
        {
            String today = (LocalDate.now().toString());
            Source.getInstance().updateRecipeDate(today, selectedRecipe.getId());
            selectedRecipe.setDate(today);
            Source.getInstance().addHistoryRecipe(selectedRecipe.getName(),selectedRecipe.getDate());
            Controllers.getInstance().getHistoryController().addToDishHistory(new Dish(selectedRecipe.getName(),1, today));
            window.close();
        }
    }
}
