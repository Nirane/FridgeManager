package sample.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Model.Food;
import sample.Model.FoodData;
import sample.Model.Source;

import java.sql.*;
import java.time.LocalDate;

public class FridgeOptionController extends ToolbarController {

    @FXML private TextField nameField;
    @FXML private TextField typeField;
    @FXML private Spinner<Double> weightField;
    @FXML private DatePicker dateField;
    @FXML private TextField ownerField;

    @FXML private Label windowName;
    @FXML private Button endActionButton;

    @FXML private Label weightLabel;
    @FXML private VBox content;

    @FXML private TableView<Food> products;

    private Stage window;
    private String selectedButtonName;

    private String name;
    private String type;
    private double weight;
    private String date;
    private String owner;

    private Food selectedFood;

    void setProducts(TableView<Food> products)
    {
        this.products = products;
    }

    void setSelectedButtonName(String selectedButtonName)
    {
        this.selectedButtonName = selectedButtonName;
    }

    public void initialize()
    {
        Platform.runLater(() -> {
            window = (Stage) nameField.getScene().getWindow();
            selectedFood = products.getSelectionModel().getSelectedItem();

            if(selectedButtonName.equals("addButton"))
            {
                window.setTitle("Dodaj produkt");
                windowName.setText("Dodaj produkt");
                endActionButton.setText("Dodaj");
                endActionButton.setOnMouseClicked(e -> {
                    setOptionData();
                    addProduct();
                });
            }
            else if(selectedButtonName.equals("searchButton"))
            {
                window.setTitle("Wyszukaj produkt");
                windowName.setText("Wyszukaj produkt");
                endActionButton.setText("Szukaj");
                //No need of searching exact weight (who could do that?)
                content.getChildren().remove(weightLabel);
                content.getChildren().remove(weightField);

                endActionButton.setOnMouseClicked(e -> {
                    setOptionData();
                    searchProduct();
                });
            }
            else
            {
                nameField.setText(products.getSelectionModel().getSelectedItem().getName());
                typeField.setText(products.getSelectionModel().getSelectedItem().getType());
                weightField.getValueFactory().setValue(products.getSelectionModel().getSelectedItem().getWeight());
                dateField.setValue(LocalDate.parse(products.getSelectionModel().getSelectedItem().getDate()));
                ownerField.setText(products.getSelectionModel().getSelectedItem().getOwner());

                if(selectedButtonName.equals("editButton"))
                {
                    window.setTitle("Edytuj produkt");
                    windowName.setText("Edytuj produkt");
                    endActionButton.setText("Zapisz");
                    endActionButton.setOnMouseClicked(e -> {
                        setOptionData();
                        editProduct();
                    });
                }
                else
                {
                    nameField.setDisable(true);
                    typeField.setDisable(true);
                    weightField.setDisable(true);
                    dateField.setDisable(true);
                    ownerField.setDisable(true);

                    window.setTitle("Usuń produkt");
                    windowName.setText("Usuń produkt");
                    endActionButton.setText("Usuń");
                    endActionButton.setOnMouseClicked(e -> {
                        setOptionData();
                        deleteProduct();
                    });
                }
            }
        });
    }

    private void setOptionData()
    {
        this.name = nameField.getText();
        this.type = typeField.getText();
        if(weightField.getValue()!=null) weight = weightField.getValue();
        this.date = (dateField.getValue()!=null) ? Date.valueOf(dateField.getValue()).toString() : "";
        this.owner = ownerField.getText();
    }

    @FXML
    private void addProduct()
    {
        int id = Source.getInstance().addFood(name, type, weight, date, owner);
        FoodData.getInstance().addFood(new Food(id, name,type,weight,date,owner));
        window.close();
    }

    @FXML
    private void searchProduct()
    {
        FoodData.getInstance().setFood(Source.getInstance().searchFood(name,type,weight,date,owner));
        window.close();
    }

    @FXML
    private void editProduct()
    {
        Source.getInstance().editFood(selectedFood.getId(), name, type, weight, date, owner);
        selectedFood.setName(name);
        selectedFood.setType(type);
        selectedFood.setWeight(weight);
        selectedFood.setDate(date);
        selectedFood.setOwner(owner);
        window.close();
    }

    @FXML
    private void deleteProduct()
    {
        Source.getInstance().deleteFood(name,type,weight,date,owner);
        FoodData.getInstance().removeFood(selectedFood);
        window.close();
    }
}