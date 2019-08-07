package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Model.Food;
import sample.Model.FoodData;
import sample.Model.Source;

import java.io.IOException;

public class FridgeController {

    @FXML private TableView<Food> food;

    @FXML private Button addButton;
    @FXML private Button searchButton;

    public void initialize()
    {
        Source.getInstance().initializeFood();
        food.setItems(FoodData.getInstance().getFood());
    }

    @FXML
    private void loadWindow(MouseEvent event)
    {
        if(event.getSource().equals(addButton) || event.getSource().equals(searchButton) || food.getSelectionModel().getSelectedItem()!=null) {
            Stage window = new Stage();
            window.initStyle(StageStyle.TRANSPARENT);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLscenes/fridgeOption.fxml"));

            try {
                Parent root = fxmlLoader.load();

                FridgeOptionController fridgeOptionController = fxmlLoader.getController();
                fridgeOptionController.setSelectedButtonName(((Button) event.getSource()).getId());
                fridgeOptionController.setProducts(food);

                window.setScene(new Scene(root, 400, 400));
                window.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void updateFridge()
    {
        FoodData.getInstance().setFood(Source.getInstance().loadFood());
    }
}
