package sample.Model;

import java.sql.*;

public class Source{

    private static final String DB_NAME = "baza.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;

    private Connection connection;

    private static Source instance = new Source();

    private Source()
    {

    }

    public static Source getInstance()
    {
        return instance;
    }

    //Handling Recipes
    public static final String TABLE_RECIPES = "recipes";
    public static final String COLUMN_RECIPES_NAME = "nazwa";
    public static final String COLUMN_RECIPES_TYPE = "typ";
    public static final String COLUMN_RECIPES_INGREDIENTS = "składniki";
    public static final String COLUMN_RECIPES_DATE = "data";
    public static final String COLUMN_RECIPES_TIME = "czas";
    public static final String COLUMN_RECIPES_STAGES = "etapy";
    public static final int INDEX_RECIPES_NAME = 1;
    public static final int INDEX_RECIPES_TYPE = 2;
    public static final int INDEX_RECIPES_INGREDIENTS = 3;
    public static final int INDEX_RECIPES_DATE = 4;
    public static final int INDEX_RECIPES_TIME = 5;
    public static final int INDEX_RECIPES_STAGES = 6;
    //
    /*Statement statement = connection.createStatement();
            statement.execute("INSERT INTO recipes('nazwa', 'typ', 'składniki', 'data', 'czas','etapy') " +
                    "VALUES('" + name + "', " +
            "'" + type + "', " +
            "'" + ingredients + "', " +
            "'" + date + "', " +
            "'" + time + "'," +
            "'" + stages + "')");
            statement.close();*/

    public static final String INITIALIZE_RECIPES = "";

    public static final String INSERT_RECIPES = "INSERT INTO " + TABLE_RECIPES + "('" + COLUMN_RECIPES_NAME + "', '" +
            COLUMN_RECIPES_TYPE + "', '" + COLUMN_RECIPES_INGREDIENTS + "', '" + COLUMN_RECIPES_DATE + "', '" +
            COLUMN_RECIPES_TIME + "', '" + COLUMN_RECIPES_STAGES + "') " + "VALUES(?,?,?,?,?,?)";

    public static final String DELETE_RECIPES = "";

    public static final String UPDATE_RECIPES = "";

    public static final String SEARCH_RECIPES = "";

    public static final String LOAD_RECIPES = "";
    //PreparedStatements for Recipes
    private PreparedStatement INITIALIZE_RECIPES_STMT;
    private PreparedStatement INSERT_RECIPES_STMT;
    private PreparedStatement DELETE_RECIPES_STMT;
    private PreparedStatement UPDATE_RECIPES_STMT;
    private PreparedStatement SEARCH_RECIPES_STMT;
    private PreparedStatement LOAD_RECIPES_STMT;

    public boolean open()
    {
        try{
           connection = DriverManager.getConnection(CONNECTION_STRING);
           return true;
        }catch(SQLException e)
        {
            System.out.println("Couldn't connect to the database, " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void close()
    {
        try{
            if(connection!=null) connection.close();

        }catch (SQLException e)
        {
            System.out.println("Couldn't close the database");
            e.printStackTrace();
        }
    }

    public void addRecipe(String name, String type, String ingredients, Date date, String time, String stages)
    {
        try {
            INSERT_RECIPES_STMT = connection.prepareStatement(INSERT_RECIPES);
            INSERT_RECIPES_STMT.setString(1,name);
            INSERT_RECIPES_STMT.setString(2,type);
            INSERT_RECIPES_STMT.setString(3,ingredients);
            INSERT_RECIPES_STMT.setDate(4,date);
            INSERT_RECIPES_STMT.setString(5,time);
            INSERT_RECIPES_STMT.setString(6,stages);
            INSERT_RECIPES_STMT.execute();

        } catch (SQLException e) {
            System.out.println("Couldn't add a recipe to database." + e.getMessage());
            e.printStackTrace();
        }
    }

     /*try {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS " +
                "recipes(nazwa TEXT NOT NULL, " +
                "typ TEXT, " +
                "składniki TEXT NOT NULL, " +
                "data DATE, " +
                "czas TEXT," +
                "etapy TEXT )");
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }*/

    public void initializeRecipes()
    {
        try {
            INITIALIZE_RECIPES_STMT = connection.prepareStatement(INITIALIZE_RECIPES);

        } catch (SQLException e) {
            System.out.println("Couldn't create a table" + e.getMessage());
            e.printStackTrace();
        }
    }
}
