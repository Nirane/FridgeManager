package sample.Controllers;

public class Controllers {

    private static Controllers controllers = new Controllers();
    private HistoryController historyController;

    private Controllers()
    {

    }

    public static Controllers getInstance()
    {
        return controllers;
    }

    public HistoryController getHistoryController()
    {
        return historyController;
    }

    public void setHistoryController(HistoryController historyController)
    {
        this.historyController = historyController;
    }
}
