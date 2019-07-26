package sample.UsableClasses;

import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class FillableTextArea extends TextArea {

    private TableView<Recipe> recipes;

    public FillableTextArea(TableView<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void fillStagesDescription(MouseEvent event)
    {
        if(recipes.getSelectionModel().getSelectedItem()!=null)
        {
            String toSplit = recipes.getSelectionModel().getSelectedItem().getStages();
            String[] splitted = toSplit.split("[.]");

            StringBuilder builder = new StringBuilder();
            for (int i=0;i<splitted.length;i++) {
                String string = i+1 +". " +splitted[i] +"\n \n";
                builder.append(string);
            }
            this.setText(builder.toString());
        }
    }
}
