package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.Model.Dish;
import sample.Model.Source;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HistoryController {

    @FXML private TableView<Dish> dishes;
    @FXML private TableColumn<Dish, String> tableDishName;
    @FXML private TableColumn<Dish, String> tableDishDate;

    @FXML private TableView<Dish> food;
    @FXML private TableColumn<Dish, String> tableFoodName;
    @FXML private TableColumn<Dish, Double> tableFoodWeight;
    @FXML private TableColumn<Dish, String> tableFoodDate;

    @FXML private PieChart foodWeek;
    @FXML private PieChart foodMonth;
    @FXML private PieChart dishWeek;
    @FXML private PieChart dishMonth;

    @FXML
    private TabPane tabPane;

    private double dishWeekValuesSummedUp = 0;
    private double dishMonthValuesSummedUp = 0;
    private double foodWeekValuesSummedUp = 0;
    private double foodMonthValuesSummedUp = 0;

    @FXML
    private Label chartLabel;

    private ObservableList<Dish> dishesList = FXCollections.observableArrayList();
    private ObservableList<Dish> foodList = FXCollections.observableArrayList();

    public void initialize() {
        tableDishName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableDishDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        tableFoodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableFoodWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        tableFoodDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        Source.getInstance().initializeHistoryRecipes();
        Source.getInstance().initializeHistoryFood();

        List<Dish> dlist = Source.getInstance().loadHistoryRecipes();
        dishesList.addAll(dlist);
        dishes.setItems(dishesList);

        List<Dish> flist = Source.getInstance().loadHistoryFood();
        foodList.addAll(flist);
        food.setItems(foodList);

        fillCharts();
    }

    private void fillCharts() {
        fillStats(foodWeek, food);
        fillStats(foodMonth, food);
        fillStats(dishWeek, dishes);
        fillStats(dishMonth, dishes);
    }

    private void fillStats(PieChart chart, TableView<Dish> table) {
        FilteredList weekly = table.getItems().filtered(p -> {

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());

            if (chart.getId().equals("dishWeek") || chart.getId().equals("foodWeek")) cal.add(Calendar.DATE, -6);
            else if (chart.getId().equals("dishMonth") || chart.getId().equals("foodMonth"))
                cal.add(Calendar.MONTH, -1);

            Date dateBefore = cal.getTime();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date dishDoneOn = new Date();
            try {
                dishDoneOn = format.parse(p.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return dishDoneOn.after(dateBefore);
        });

        HashMap<String, Double> dataSet = new HashMap<>();
        for (Object o : weekly) {
            Dish dish = ((Dish) o);

            if (dataSet.containsKey(dish.getName())) dataSet.replace(dish.getName(), dataSet.get(dish.getName()) + dish.getWeight());
            else dataSet.put(dish.getName(), dish.getWeight());
        }

        dataSet.forEach((k, v) -> {
            chart.getData().add(new PieChart.Data(k, v));
            switch (chart.getId()) {
                case "dishWeek":
                    dishWeekValuesSummedUp += v;
                    break;
                case "dishMonth":
                    dishMonthValuesSummedUp += v;
                    break;
                case "foodWeek":
                    foodWeekValuesSummedUp += v;
                    break;
                case "foodMonth":
                    foodMonthValuesSummedUp += v;
                    break;
            }
        });

        chartLabel.setStyle("-fx-font-size: 24;");
        chartLabel.setPrefSize(100,40);
        chartLabel.setVisible(true);
        chartLabel.toFront();

        for (final PieChart.Data d: chart.getData()) {
            d.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
                chartLabel.setLayoutX(event.getSceneX()- 780);
                chartLabel.setLayoutY( event.getSceneY() - 70);

                switch (chart.getId()) {
                    case "foodWeek":
                        chartLabel.setText(String.format("%.2f%s %.2f %s", d.getPieValue() / foodWeekValuesSummedUp * 100,"%", d.getPieValue(), "kg"));
                        break;
                    case "foodMonth":
                        chartLabel.setText(String.format("%.2f%s %.2f %s", d.getPieValue() / foodMonthValuesSummedUp * 100,"%", d.getPieValue(), "kg"));
                        break;
                    case "dishWeek":
                        chartLabel.setText(String.format("%.2f", d.getPieValue() / dishWeekValuesSummedUp * 100));
                        break;
                    case "dishMonth":
                        chartLabel.setText(String.format("%.2f", d.getPieValue() / dishMonthValuesSummedUp * 100) + "%");
                        break;
                }

                chartLabel.setStyle("-fx-background-color: #F9D342;" +
                        " -fx-text-fill: #292826;" +
                        " -fx-border-color: black;" +
                        " -fx-border-width: 2;");
            });
        }
    }
}