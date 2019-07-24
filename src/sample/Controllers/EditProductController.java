package sample.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.UsableClasses.Food;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class EditProductController extends OptionController {

    @FXML private TextField nameField;
    @FXML private TextField typeField;
    @FXML private Spinner<Double> weightField;
    @FXML private DatePicker dateField;
    @FXML private TextField ownerField;
    private Food selectedProduct;

    public void initialize()
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                selectedProduct = products.getSelectionModel().getSelectedItem();
                nameField.setText(selectedProduct.getName());
                typeField.setText(selectedProduct.getType());
                weightField.getValueFactory().setValue(selectedProduct.getWeight());
                dateField.setValue(LocalDate.parse(selectedProduct.getDate()));
                ownerField.setText(selectedProduct.getOwner());
            }
        });
    }

    @FXML
    private void update(MouseEvent event)
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
            Statement statement = connection.createStatement();
            statement.execute("UPDATE products " +
                    "SET nazwa = '" + nameField.getText() +
                    "', typ = '" + typeField.getText() +
                    "', waga = '" + weightField.getValue() +
                    "', data = '" + dateField.getValue() +
                    "', właściciel = '" + ownerField.getText() +"'" +
                    "WHERE nazwa = '" + selectedProduct.getName() +
                    "' AND typ = '" + selectedProduct.getType() +
                    "' AND waga = '" + selectedProduct.getWeight() +
                    "' AND data = '" +selectedProduct.getDate() +
                    "' AND właściciel = '" + selectedProduct.getOwner() +"'");
            statement.close();
            connection.close();

            products.getSelectionModel().getSelectedItem().setName(nameField.getText());
            products.getSelectionModel().getSelectedItem().setType(typeField.getText());
            products.getSelectionModel().getSelectedItem().setWeight(weightField.getValue());
            products.getSelectionModel().getSelectedItem().setDate(dateField.getValue().toString());
            products.getSelectionModel().getSelectedItem().setOwner(ownerField.getText());

            exit(event);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void exit(MouseEvent event)
    {
        Stage addProductStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        addProductStage.close();
    }
}
