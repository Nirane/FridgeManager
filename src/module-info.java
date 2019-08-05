module FridgeManager {

    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires javafx.base;

    exports sample.Controllers;
    exports sample.Model;

    opens sample.Model;
    opens sample.Controllers;
    opens sample;
}