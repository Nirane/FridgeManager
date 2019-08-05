package sample.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Model.Food;

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

    @FXML private TableView<Food> products;

    private Stage window;
    private String selectedButtonName;

    private String name;
    private String type;
    private double weight;
    private Date date;
    private String owner;

    private Food selectedProduct;

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
            selectedProduct = products.getSelectionModel().getSelectedItem();

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
        if(weightField.getValue()!=null) this.weight = weightField.getValue();
        if(dateField.getValue()!=null) this.date = Date.valueOf(dateField.getValue());
        this.owner = ownerField.getText();
    }

    @FXML
    private void addProduct()
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO products VALUES('" + name+ "', '"+ type + "', " + weight + ", '" + date + "', '"+ owner +"'" + ")");
            products.getItems().add(new Food(name,type,weight,date.toString(),owner));
            statement.close();
            connection.close();
            window.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void searchProduct()
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM products WHERE ";

            System.out.println(weight);

            if(!name.trim().isEmpty()) sql += "nazwa LIKE '%" + name + "%'";
            else sql += "nazwa LIKE '%'";
            if(!type.trim().isEmpty()) sql += " AND typ LIKE '%" + type + "%'";
            else sql += " AND typ LIKE '%'";
            if(weight!=0.0) sql += " AND waga LIKE '%" + weight + "%'";
            else sql += " AND waga LIKE '%'";
            if(date!=null) sql += " AND data LIKE '%" + date + "%'";
            else sql += " AND data LIKE '%'";
            if(!owner.trim().isEmpty()) sql += " AND właściciel LIKE '%" + owner + "%'";
            else sql += " AND właściciel LIKE '%'";

            statement.execute(sql);
            products.getItems().clear();

            ResultSet results = statement.getResultSet();
            while (results.next())
            {
                String nameQ = results.getString("nazwa");
                String typeQ = results.getString("typ");
                double weightQ = results.getDouble("waga");
                String dateQ = results.getString("data");
                String ownerQ = results.getString("właściciel");
                products.getItems().add(new Food(nameQ,typeQ, weightQ, dateQ, ownerQ));
            }

            statement.close();
            connection.close();
            window.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void editProduct()
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
            Statement statement = connection.createStatement();
            statement.execute("UPDATE products " +
                    "SET nazwa = '" + name +
                    "', typ = '" + type +
                    "', waga = '" + weight +
                    "', data = '" + date +
                    "', właściciel = '" + owner +"'" +
                    "WHERE nazwa = '" + selectedProduct.getName() +
                    "' AND typ = '" + selectedProduct.getType() +
                    "' AND waga = '" + selectedProduct.getWeight() +
                    "' AND data = '" +selectedProduct.getDate() +
                    "' AND właściciel = '" + selectedProduct.getOwner() +"'");
            statement.close();
            connection.close();

            selectedProduct.setName(name);
            selectedProduct.setType(type);
            selectedProduct.setWeight(weight);
            selectedProduct.setDate(date.toString());
            selectedProduct.setOwner(owner);
            window.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteProduct()
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:baza.db");

            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM products WHERE " +
                    "nazwa = '" + name +
                    "' AND typ = '" + type +
                    "' AND waga = '" + weight +
                    "' AND data = '" + date +
                    "' AND właściciel = '" + owner +"'");

            products.getItems().remove(selectedProduct);

            statement.close();
            connection.close();
            window.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
