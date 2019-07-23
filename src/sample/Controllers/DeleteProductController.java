package sample.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import sample.UsableClasses.Food;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteProductController {

    @FXML private Label assurement;
    private TableView products;
    private Food product;

    public void setProduct(Food product)
    {
        this.product = product;
    }

    public void setProducts(TableView products)
    {
        this.products = products;
    }

    public void initialize() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(product!=null)
                    assurement.setText("Czy jesteś pewny, że chcesz usunać: " + product.getName() + " typu " + product.getType() + " o wadze " + product.getWeight() + " kg właściciela " + product.getOwner());
            }
        });
    }

    @FXML
    private void deleteProduct()
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM products WHERE nazwa = '" + product.getName() + "'");
            products.getItems().remove(product);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
