package sample.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.UsableClasses.Food;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteProductController extends OptionController {

    @FXML private Label labelName;
    @FXML private Label labelType;
    @FXML private Label labelWeight;
    @FXML private Label labelDate;
    @FXML private Label labelOwner;
    private Food selectedProduct;

    public void initialize() {

        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                selectedProduct = products.getSelectionModel().getSelectedItem();

                if(selectedProduct!=null)
                {
                    labelName.setText(selectedProduct.getName());
                    labelType.setText(selectedProduct.getType());
                    labelWeight.setText(selectedProduct.getWeight() +" kg");
                    labelDate.setText(selectedProduct.getDate());
                    labelOwner.setText(selectedProduct.getOwner());
                }
            }
        });
    }

    @FXML
    private void deleteProduct(MouseEvent event)
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:baza.db");

            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM products WHERE " +
                    "nazwa = '" + selectedProduct.getName() +
                    "' AND typ = '" + selectedProduct.getType() +
                    "' AND waga = '" + selectedProduct.getWeight() +
                    "' AND data = '" +selectedProduct.getDate() +
                    "' AND właściciel = '" + selectedProduct.getOwner() +"'");
            products.getItems().remove(selectedProduct);

            statement.close();
            connection.close();

            closeWindow(event);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeWindow(MouseEvent event)
    {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
