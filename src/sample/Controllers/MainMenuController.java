package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MainMenuController extends ToolbarController {

    @FXML private Button welcome;
    @FXML private Button fridge;
    @FXML private Button recipes;
    @FXML private Button history;
    @FXML private Button calendar;
    @FXML private Button creators;
    @FXML private HBox content;

    private Node selectedNode;
    private Button selectedButton;

    private Node welcomeNode;
    private Node fridgeNode;
    private Node recipeNode;
    private Node historyNode;
    private Node calendarNode;
    private Node creatorsNode;

    public void initialize()
    {
        try {
            welcomeNode = FXMLLoader.load(getClass().getResource("FXMLscenes/welcome.fxml"));
            recipeNode = FXMLLoader.load(getClass().getResource("FXMLscenes/recipes.fxml"));
            fridgeNode = FXMLLoader.load(getClass().getResource("FXMLscenes/fridge.fxml"));

            FXMLLoader historyLoader = new FXMLLoader(getClass().getResource("FXMLscenes/history.fxml"));
            historyNode = historyLoader.load();
            Controllers.getInstance().setHistoryController(historyLoader.getController());

            calendarNode = FXMLLoader.load(getClass().getResource("FXMLscenes/calendar.fxml"));
            creatorsNode = FXMLLoader.load(getClass().getResource("FXMLscenes/creators.fxml"));
            selectedButton = welcome;
            selectedButton.getStyleClass().add(2,"menuButtonSelected");
            selectedNode = welcomeNode;
            content.getChildren().add(selectedNode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void mainMenuClick(MouseEvent event)
    {
        if(selectedButton != null) selectedButton.getStyleClass().remove(2);
        selectedButton = (Button) event.getSource();
        selectedButton.getStyleClass().add(2,"menuButtonSelected");

        if(selectedNode != null) content.getChildren().remove(selectedNode);

        if(event.getSource().equals(recipes)) selectedNode = recipeNode;
        else if(event.getSource().equals(fridge)) selectedNode = fridgeNode;
        else if(event.getSource().equals(welcome)) selectedNode = welcomeNode;
        else if(event.getSource().equals(history)) selectedNode = historyNode;
        else if(event.getSource().equals(calendar)) selectedNode = calendarNode;
        else if(event.getSource().equals(creators)) selectedNode = creatorsNode;

        content.getChildren().add(selectedNode);

        if(selectedNode != historyNode) Controllers.getInstance().getHistoryController().makeChartLabelNotVisible();
    }
}
