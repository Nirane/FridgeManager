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
import sample.UsableClasses.Food;

import java.io.IOException;
import java.sql.*;

public class FridgeController {

    @FXML private TableView<Food> products;
    @FXML private TableColumn<Food,String> tableName;
    @FXML private TableColumn<Food,String> tableType;
    @FXML private TableColumn<Food,Double> tableWeight;
    @FXML private TableColumn<Food,String> tableOwner;

    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button editButton;

    public void initialize()
    {
        updateFridge();
    }

    @FXML
    public void swapToAddProduct()
    {
        Stage addProductWindow = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLscenes/addProduct.fxml"));
            Parent root = loader.load();
            AddProductController addProductController = loader.getController();
            addProductController.setProducts(products);
            addProductWindow.setTitle("Dodaj produkt");
            addProductWindow.setScene(new Scene(root,300,400));
            addProductWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void optionChoose(MouseEvent event)
    {
        String path;
        String title;

        if(products.getSelectionModel().getSelectedItem()!=null)
        {
            if(event.getSource().equals(addButton))
            {
                path = "FXMLscenes/addProduct.fxml";
                title = "Dodaj produkt";
            }
            else if(event.getSource().equals(deleteButton))
            {
                path = "FXMLscenes/deleteProduct.fxml";
                title = "Usuń produkt";
            }
            else
            {
                path = "FXMLscenes/editProduct.fxml";
                title = "Edycja produktu";
            }

            loadWindow(path,title);
        }
    }

    @FXML
    private void loadWindow(String path, String title)
    {
        Stage window = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));

        try {
            Parent root = fxmlLoader.load();

            DeleteProductController deleteProductController = fxmlLoader.getController();
            deleteProductController.setProduct(products.getSelectionModel().getSelectedItem());
            deleteProductController.setProducts(products);

            window.setTitle(title);
            window.setScene(new Scene(root,400,300));
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
            tableOwner.setCellValueFactory(new PropertyValueFactory<Food, String>("Owner"));

            while(resultSet.next())
            {
                String name = resultSet.getString("nazwa");
                String type = resultSet.getString("typ");
                double weight = resultSet.getDouble("waga");
                //Date date = resultSet.getDate("data");
                //Date date = resultSet.getString("data").;
                String owner = resultSet.getString("właściciel");
                products.getItems().add(new Food(name,type,weight,owner));
                products.refresh();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
