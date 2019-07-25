package sample.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    @FXML private Button fridge;
    @FXML private Button recipes;
    @FXML private HBox content;

    private Node selectedNode;
    private Button selectedButton;
    private Node recipeNode;
    private Node fridgeNode;

    private double xOffset;
    private double yOffset;

    public void initialize()
    {

        try {
            recipeNode = FXMLLoader.load(getClass().getResource("FXMLscenes/recipes.fxml"));
            fridgeNode = FXMLLoader.load(getClass().getResource("FXMLscenes/fridge.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void mainMenuClick(MouseEvent event)
    {
        if(selectedButton != null) selectedButton.setStyle("-fx-background-color:  #1d1d26;");
        selectedButton = (Button) event.getSource();
        selectedButton.setStyle("-fx-background-color: white;");
        if(selectedNode != null) content.getChildren().remove(selectedNode);
        if(event.getSource().equals(recipes)) selectedNode = recipeNode;
        else if(event.getSource().equals(fridge)) selectedNode = fridgeNode;
        content.getChildren().add(selectedNode);
    }

    @FXML
    private void exit(MouseEvent event)
    {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimize(MouseEvent event)
    {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void pressed(MouseEvent event)
    {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void drag(MouseEvent event)
    {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }


}
