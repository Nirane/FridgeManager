package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class Controller {
    //mainWindow
    @FXML
    private ImageView fridge;
    @FXML
    private ImageView pan;
    @FXML
    private ImageView calendar;
    @FXML
    private HBox mainMenu;

    //fridge
    @FXML
    private ListView products;

    //addProductWindow
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

    public void initialize()
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS products(nazwa TEXT NOT NULL, typ TEXT, waga DECIMAL, data DATE, właściciel TEXT)");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void swapToAddProduct()
    {
        Stage addProductWindow = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLscenes/addProduct.fxml"));
            addProductWindow.setTitle("Dodaj produkt");
            addProductWindow.setScene(new Scene(root,300,400));
            addProductWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void save()
    {
        String name = nameField.getText();
        String type = typeField.getText();
        double weight = weightField.getValue();
        Date date = Date.valueOf(dateField.getValue());
        String owner = ownerField.getText();
        System.out.println(date);


        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO products VALUES('" + name+ "', '"+ type + "', " + weight + ", '" + date + "', '"+ owner +"'" + ")");
            statement.close();
            connection.close();
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

    @FXML
    public void mainMenuClick(MouseEvent event)
    {
        /*try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Spectrum\\Desktop\\Java virtual\\testbd\\test.db");
            Statement statement = conn.createStatement();
            statement.execute("INSERT INTO contacts VALUES('Beata',123412231,'heh@gmail.com')");
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }*/


        if(event.getSource().equals(fridge)){
            try {
                Parent root = FXMLLoader.load(getClass().getResource("FXMLscenes/fridge.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root,720,400));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void hoverOver(MouseEvent event)
    {
        if(event.getSource().equals(fridge)) fridge.opacityProperty().setValue(1);
        else if(event.getSource().equals(pan)) pan.opacityProperty().setValue(1);
        else if(event.getSource().equals(calendar)) calendar.opacityProperty().setValue(1);
    }

    @FXML
    public void hoverOut(MouseEvent event)
    {
        if(event.getSource().equals(fridge)) fridge.opacityProperty().setValue(0.65);
        else if(event.getSource().equals(pan)) pan.opacityProperty().setValue(0.65);
        else if(event.getSource().equals(calendar)) calendar.opacityProperty().setValue(0.65);
    }
}
