package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class CalendarController {

    @FXML private VBox content;

    public void initialize()
    {
       // Calendar calendar = Calendar.getInstance();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefHeight(90);
        scrollPane.setPrefWidth(135);
        //content.getChildren().add(scrollPane);
    }
}
