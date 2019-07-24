package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.UsableClasses.Food;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;

public class FridgeController {

    @FXML private TableView<Food> products;
    @FXML private TableColumn<Food,String> tableName;
    @FXML private TableColumn<Food,String> tableType;
    @FXML private TableColumn<Food,Double> tableWeight;
    @FXML private TableColumn<Food,String> tableDate;
    @FXML private TableColumn<Food,String> tableOwner;

    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button editButton;

    public void initialize()
    {
        updateFridge();
    }

    @FXML
    public void optionChoose(MouseEvent event)
    {
        if(event.getSource().equals(addButton))
        {
            loadWindow("FXMLscenes/addProduct.fxml","Dodaj produkt",400,300);
        }
        else if (event.getSource().equals(deleteButton) && products.getSelectionModel().getSelectedItem()!=null)
        {
            loadWindow("FXMLscenes/deleteProduct.fxml","Usuń produkt",400,600);
        }
        else if (event.getSource().equals(editButton) && products.getSelectionModel().getSelectedItem()!=null)
        {
            loadWindow("FXMLscenes/editProduct.fxml","Edycja produktu",400,300);
        }
    }

    @FXML
    private void loadWindow(String path, String title, int height, int width)
    {
        Stage window = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));

        try {
            Parent root = fxmlLoader.load();

            OptionController optionController = fxmlLoader.getController();
            optionController.setProducts(products);

            window.setTitle(title);
            window.setScene(new Scene(root,width,height));
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
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

            tableName.setCellValueFactory(new PropertyValueFactory<Food, String>("name"));
            tableType.setCellValueFactory(new PropertyValueFactory<Food, String>("type"));
            tableWeight.setCellValueFactory(new PropertyValueFactory<Food, Double>("weight"));
            tableDate.setCellValueFactory(new PropertyValueFactory<Food, String>("date"));
            tableOwner.setCellValueFactory(new PropertyValueFactory<Food, String>("Owner"));

            while(resultSet.next())
            {
                String name = resultSet.getString("nazwa");
                String type = resultSet.getString("typ");
                double weight = resultSet.getDouble("waga");
                //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = resultSet.getString("data");
                //Date toDate = new Date(date);
                //sdf.format(resultSet.getDate(4));
                //String date = resultSet.getDate("data").toString();
                //Date date = resultSet.getString("data").;
                String owner = resultSet.getString("właściciel");
                products.getItems().add(new Food(name,type,weight,date,owner));
                products.refresh();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backToMainMenu(MouseEvent event)
    {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.hide();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLscenes/sample.fxml"));
            window.setTitle("Menedżer lodówki");
            window.setScene(new Scene(root,1200,600));
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
