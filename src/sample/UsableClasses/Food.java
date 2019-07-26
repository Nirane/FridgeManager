package sample.UsableClasses;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Food {

    private SimpleStringProperty name;
    private SimpleStringProperty type;
    private SimpleDoubleProperty weight;
    private SimpleStringProperty owner;
    private SimpleStringProperty date;

    public Food(String name, String type, double weight, String date, String owner) {
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type);
        this.weight = new SimpleDoubleProperty(weight);
        this.date = new SimpleStringProperty(date);
        this.owner = new SimpleStringProperty(owner);
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

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public double getWeight() {
        return weight.get();
    }

    public SimpleDoubleProperty weightProperty() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight.set(weight);
    }

    public String getOwner() {
        return owner.get();
    }

    public SimpleStringProperty ownerProperty() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner.set(owner);
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