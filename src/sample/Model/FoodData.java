package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class FoodData {

    private static FoodData foodData = new FoodData();
    private ObservableList<Food> foodList;

    private FoodData()
    {
        foodList = FXCollections.observableArrayList();
        List<Food> foodSource = Source.getInstance().loadFood();
        for(Food food : foodSource)
        {
            addFood(food);
        }
    }

    public static FoodData getInstance()
    {
        return foodData;
    }

    public ObservableList<Food> getFood()
    {
        return foodList;
    }

    public void setFood(List<Food> foodSource)
    {
        foodList.clear();
        for(Food food: foodSource)
        {
            addFood(food);
        }
    }

    public void addFood(Food food)
    {
        foodList.add(food);
    }

    public void removeFood(Food food)
    {
        foodList.remove(food);
    }
}
