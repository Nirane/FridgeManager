package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.UsableClasses.Food;

import java.sql.*;

public class AddProductController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField typeField;
    @FXML
    private Spinner<Double> weightField;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField ownerField;

    private TableView products;

    public void setProducts(TableView products)
    {
        this.products = products;
    }

    @FXML
    public void save(MouseEvent event)
    {
        String name = nameField.getText();
        String type = typeField.getText();
        double weight = weightField.getValue();
        Date date = Date.valueOf(dateField.getValue());
        String owner = ownerField.getText();

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO products VALUES('" + name+ "', '"+ type + "', " + weight + ", '" + date + "', '"+ owner +"'" + ")");
            products.getItems().add(new Food(name,type,weight,owner));
            statement.close();
            connection.close();
            cancel(event);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void cancel(MouseEvent event)
    {
        final Stage addProductStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        addProductStage.close();
    }
}
