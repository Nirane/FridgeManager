package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    private ImageView fridge;
    @FXML
    private ImageView pan;
    @FXML
    private ImageView calendar;
    @FXML
    private HBox mainMenu;

    @FXML
    public void mainMenuClick(MouseEvent event)
    {
        if(event.getSource().equals(fridge)){
            try {
                Parent root = FXMLLoader.load(getClass().getResource("FXMLscenes/fridge.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root,400,400));

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
