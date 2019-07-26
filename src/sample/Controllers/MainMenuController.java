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

public class MainMenuController extends ToolbarController {

    @FXML private Button fridge;
    @FXML private Button recipes;
    @FXML private HBox content;

    private Node selectedNode;
    private Button selectedButton;
    private Node recipeNode;
    private Node fridgeNode;

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
}
