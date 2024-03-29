package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource("Controllers/FXMLscenes/mainMenu.fxml"));
        primaryStage.setTitle("Fridge Manager");
        primaryStage.setScene(new Scene(root, 1350, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
