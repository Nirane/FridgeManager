package sample.UsableClasses;

import javafx.beans.property.SimpleStringProperty;

public class Dish {

    private SimpleStringProperty name;
    private SimpleStringProperty date;

    public Dish(String name, String date) {
        this.name = new SimpleStringProperty(name);
        this.date = new SimpleStringProperty(date);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }
}
