package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

import java.sql.*;

//parametryzacja okien w lodówce
//walidacja
//transition w main menu
//czy na pewno wszystko jest tam gdzie powinno?

public class Controller {

    @FXML private ImageView fridge;
    @FXML private ImageView pan;
    @FXML private ImageView calendar;

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
    public void mainMenuClick(MouseEvent event)
    {
        if(event.getSource().equals(fridge)){
            try {
                //Parent root = FXMLLoader.load(getClass().getResource())
                Parent root = FXMLLoader.load(getClass().getResource("FXMLscenes/fridge.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root,720,400);
                stage.setScene(scene);

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
