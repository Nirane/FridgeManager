package sample.Controllers;

import javafx.scene.control.TableView;
import sample.UsableClasses.Food;

public class OptionController {

    protected TableView<Food> products;

    public void setProducts(TableView<Food> products) {
        this.products = products;
    }
}
