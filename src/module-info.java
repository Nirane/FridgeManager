module FridgeManager {

    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires javafx.base;

    exports sample.Controllers;
    exports sample.UsableClasses;

    opens sample.UsableClasses;
    opens sample.Controllers;
    opens sample;
}