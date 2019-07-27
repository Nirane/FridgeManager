package sample.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.UsableClasses.Dish;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class HistoryController {

    @FXML private TableView<Dish> dishes;
    @FXML private TableColumn<Dish, String> tableName;
    @FXML private TableColumn<Dish, String> tableDate;

    @FXML private PieChart foodWeek;
    @FXML private PieChart foodMonth;
    @FXML private PieChart dishWeek;
    @FXML private PieChart dishMonth;

    public void initialize()
    {
        initializeSQLTableDishHistory();
        initializaTableDishes();
        fillCharts();
    }
// ten sam typ danych zrobic dla posilku
    private void initializaTableDishes()
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
            Statement statement = connection.createStatement();

            statement.execute("SELECT * FROM dishHistory ORDER BY data DESC");
            ResultSet results = statement.getResultSet();

            tableName.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));
            tableDate.setCellValueFactory(new PropertyValueFactory<Dish, String>("date"));

            while(results.next())
            {
                String name = results.getString("danie");
                String date = results.getString("data");
                dishes.getItems().add(new Dish(name,date));
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeSQLTableDishHistory()
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
            Statement statement = connection.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS " +
                    "dishHistory(danie TEXT NOT NULL, " +
                    "data DATE NOT NULL)");

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void fillCharts()
    {
        fillProductWeekChart();
        fillProductMonthChart();
        fillDishChart(dishWeek);
        fillDishChart(dishMonth);
        /*fillDishWeekChart();
        fillDishMonthChart();*/
    }

    private void fillDishChart(PieChart chart)
    {
        FilteredList weekly = dishes.getItems().filtered(p -> {

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());

            if(chart.getId().equals("dishWeek")) cal.add(Calendar.DATE, -6);
            else if(chart.getId().equals("dishMonth")) cal.add(Calendar.MONTH, -1);

            Date dateBefore = cal.getTime();
            DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Date dishDoneOn = new Date();
            try {
                dishDoneOn = format.parse(p.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return dishDoneOn.after(dateBefore);
        });

        HashMap<String, Integer> dataSet = new HashMap<>();
        for (Object o : weekly) {
            Dish dish = ((Dish) o);

            if (dataSet.containsKey(dish.getName())) dataSet.replace(dish.getName(), dataSet.get(dish.getName()) + 1);
            else dataSet.put(dish.getName(), 1);
        }
        dataSet.forEach((k,v) -> chart.getData().add(new PieChart.Data(k,v)));
    }

    private void fillDishWeekChart()
    {

    }

    private void fillDishMonthChart()
    {
        FilteredList weekly = dishes.getItems().filtered(p -> {

            Calendar cal = Calendar.getInstance();
            Date today = new Date();
            cal.setTime(today);
            cal.add(Calendar.MONTH, -1);
            Date dateBefore = cal.getTime();
            DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Date dishDoneOn = new Date();
            try {
                dishDoneOn = format.parse(p.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return dishDoneOn.after(dateBefore);
        });

        for (Object o : weekly) {
            Dish dish = ((Dish) o);
            dishMonth.getData().add(new PieChart.Data(dish.getName(), 1));
        }
    }

    private void fillProductWeekChart()
    {


        foodWeek.getData().add(new PieChart.Data("jogurt",6));
        foodWeek.getData().add(new PieChart.Data("serek wiejski",3));
        foodWeek.getData().add(new PieChart.Data("twaróg",4));
        foodWeek.getData().add(new PieChart.Data("mleko",4));
        foodWeek.getData().add(new PieChart.Data("szynka",4));
        foodWeek.getData().add(new PieChart.Data("śmietana",4));

    }

    private void fillProductMonthChart()
    {

        foodMonth.getData().add(new PieChart.Data("jogurt",6));
        foodMonth.getData().add(new PieChart.Data("serek wiejski",3));
        foodMonth.getData().add(new PieChart.Data("twaróg",4));
        foodMonth.getData().add(new PieChart.Data("mleko",4));
        foodMonth.getData().add(new PieChart.Data("szynka",4));
        foodMonth.getData().add(new PieChart.Data("śmietana",4));
    }
}
