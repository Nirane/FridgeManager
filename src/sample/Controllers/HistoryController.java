package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Model.Dish;
import sample.Model.Source;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HistoryController {

    @FXML private TableView<Dish> dishes;
    @FXML private TableView<Dish> food;

    @FXML private PieChart foodWeek;
    @FXML private PieChart foodMonth;
    @FXML private PieChart dishWeek;
    @FXML private PieChart dishMonth;

    private double dishWeekValuesSummedUp = 0;
    private double dishMonthValuesSummedUp = 0;
    private double foodWeekValuesSummedUp = 0;
    private double foodMonthValuesSummedUp = 0;

    @FXML private AnchorPane foodPane;
    @FXML private AnchorPane dishPane;
    @FXML private Label chartLabel;

    private ObservableList<Dish> dishesList = FXCollections.observableArrayList();
    private ObservableList<Dish> foodList = FXCollections.observableArrayList();

    public void initialize() {

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

    void addToDishHistory(Dish dish)
    {
        dishesList.add(dish);
        dishWeek.getData().clear();
        dishMonth.getData().clear();
        dishWeekValuesSummedUp = 0;
        dishMonthValuesSummedUp = 0;
        fillStats(dishWeek, dishes);
        fillStats(dishMonth, dishes);
    }

    private void fillCharts() {
        fillStats(foodWeek, food);
        fillStats(foodMonth, food);
        fillStats(dishWeek, dishes);
        fillStats(dishMonth, dishes);
    }

    private void fillStats(PieChart chart, TableView<Dish> table) {
        FilteredList filteredList = table.getItems().filtered(p -> {

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());

            if (chart.getId().equals("dishWeek") || chart.getId().equals("foodWeek"))
                cal.add(Calendar.DATE, -6);
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
        for (Object o : filteredList) {
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

        chartLabel.toFront();

        for (final PieChart.Data d: chart.getData()) {
            d.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
                chartLabel.setLayoutX(event.getSceneX()- 780);
                chartLabel.setLayoutY(event.getSceneY() - 70);
                chartLabel.setVisible(true);

                switch (chart.getId()) {
                    case "foodWeek":
                        if(!foodPane.getChildren().contains(chartLabel)) foodPane.getChildren().add(chartLabel);
                        chartLabel.setText(String.format("%.2f%s %.2f %s", d.getPieValue() / foodWeekValuesSummedUp * 100,"%", d.getPieValue(), "kg"));
                        break;
                    case "foodMonth":
                        if(!foodPane.getChildren().contains(chartLabel)) foodPane.getChildren().add(chartLabel);
                        chartLabel.setText(String.format("%.2f%s %.2f %s", d.getPieValue() / foodMonthValuesSummedUp * 100,"%", d.getPieValue(), "kg"));
                        break;
                    case "dishWeek":
                        if(!dishPane.getChildren().contains(chartLabel)) dishPane.getChildren().add(chartLabel);
                        chartLabel.setText(String.format("%.2f%s %.0f %s", d.getPieValue() / dishWeekValuesSummedUp * 100,"%", d.getPieValue(), "razy"));
                        break;
                    case "dishMonth":
                        if(!dishPane.getChildren().contains(chartLabel)) dishPane.getChildren().add(chartLabel);
                        chartLabel.setText(String.format("%.2f%s %.0f %s", d.getPieValue() / dishMonthValuesSummedUp * 100,"%", d.getPieValue(), "razy"));
                        break;
                }
            });
        }
    }

    private void removeOutdated()
    {
        //removing data from history that is older than a month
    }

    @FXML
    void makeChartLabelNotVisible()
    {
        chartLabel.setVisible(false);
    }
}