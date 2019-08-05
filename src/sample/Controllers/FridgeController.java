package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Model.Food;

import java.io.IOException;
import java.sql.*;

public class FridgeController {

    @FXML private TableView<Food> products;
    @FXML private TableColumn<Food,String> tableName;
    @FXML private TableColumn<Food,String> tableType;
    @FXML private TableColumn<Food,Double> tableWeight;
    @FXML private TableColumn<Food,String> tableDate;
    @FXML private TableColumn<Food,String> tableOwner;

    @FXML private Button addButton;
    @FXML private Button searchButton;

    public void initialize()
    {
        updateFridge();
    }

    @FXML
    private void loadWindow(MouseEvent event)
    {
        if(event.getSource().equals(addButton) || event.getSource().equals(searchButton) || products.getSelectionModel().getSelectedItem()!=null) {
            Stage window = new Stage();
            window.initStyle(StageStyle.TRANSPARENT);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLscenes/fridgeOption.fxml"));

            try {
                Parent root = fxmlLoader.load();

                FridgeOptionController fridgeOptionController = fxmlLoader.getController();
                fridgeOptionController.setSelectedButtonName(((Button) event.getSource()).getId());
                fridgeOptionController.setProducts(products);

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
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
            Statement statement = connection.createStatement();
            statement.execute("SELECT * FROM products ORDER BY nazwa");
            ResultSet resultSet = statement.getResultSet();

            tableName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tableType.setCellValueFactory(new PropertyValueFactory<>("type"));
            tableWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
            tableDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            tableOwner.setCellValueFactory(new PropertyValueFactory<>("Owner"));

            products.getItems().clear();

            while(resultSet.next())
            {
                String name = resultSet.getString("nazwa");
                String type = resultSet.getString("typ");
                double weight = resultSet.getDouble("waga");
                String date = resultSet.getString("data");
                String owner = resultSet.getString("właściciel");
                products.getItems().add(new Food(name,type,weight,date,owner));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
