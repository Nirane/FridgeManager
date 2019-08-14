package sample.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Source{
    //calendar
    //cook and eat

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

    //Food Strings
    private static final String TABLE_FOOD = "food";
    private static final String COLUMN_FOOD_ID = "id";
    private static final String COLUMN_FOOD_NAME = "nazwa";
    private static final String COLUMN_FOOD_TYPE = "typ";
    private static final String COLUMN_FOOD_WEIGHT = "waga";
    private static final String COLUMN_FOOD_DATE = "data";
    private static final String COLUMN_FOOD_OWNER = "właściciel";
    private static final int INDEX_FOOD_ID = 1;
    private static final int INDEX_FOOD_NAME = 2;
    private static final int INDEX_FOOD_TYPE = 3;
    private static final int INDEX_FOOD_WEIGHT = 4;
    private static final int INDEX_FOOD_DATE = 5;
    private static final int INDEX_FOOD_OWNER = 6;

    private static final String INITIALIZE_FOOD = "CREATE TABLE IF NOT EXISTS " + TABLE_FOOD + "(" +
            COLUMN_FOOD_ID + " INTEGER NOT NULL PRIMARY KEY, " + COLUMN_FOOD_NAME + " TEXT NOT NULL, "
            + COLUMN_FOOD_TYPE + " TEXT, " + COLUMN_FOOD_WEIGHT + " DECIMAL, " + COLUMN_FOOD_DATE + " DATE, " +
            COLUMN_FOOD_OWNER + " TEXT)";

    private static final String INSERT_FOOD = "INSERT INTO " + TABLE_FOOD + "('" + COLUMN_FOOD_NAME + "', '" +
            COLUMN_FOOD_TYPE + "', '" + COLUMN_FOOD_WEIGHT + "', '" + COLUMN_FOOD_DATE + "', '" +
            COLUMN_FOOD_OWNER + "')" + " VALUES(?,?,?,?,?)";

    private static final String DELETE_FOOD = "DELETE FROM " + TABLE_FOOD + " WHERE " +
            COLUMN_FOOD_NAME + " = ? AND " +  COLUMN_FOOD_TYPE + " = ? AND " + COLUMN_FOOD_WEIGHT + " = ? AND " +
            COLUMN_FOOD_DATE + " = ? AND " + COLUMN_FOOD_OWNER + " = ?";

    private static final String UPDATE_FOOD = "UPDATE " + TABLE_FOOD + " SET " + COLUMN_FOOD_NAME + " = ?, " +
            COLUMN_FOOD_TYPE + " = ?, " + COLUMN_FOOD_WEIGHT + " = ?, " + COLUMN_FOOD_DATE + " = ?, " +
            COLUMN_FOOD_OWNER + " = ? WHERE " + COLUMN_FOOD_ID + " = ?";

    private static final String SEARCH_FOOD = "SELECT * FROM "+ TABLE_FOOD + " WHERE " +
            COLUMN_FOOD_NAME + " LIKE ? AND " + COLUMN_FOOD_TYPE + " LIKE ? AND " + COLUMN_FOOD_DATE + " LIKE ? AND " +
            COLUMN_FOOD_OWNER + " LIKE ?";

    private static final String LOAD_FOOD = "SELECT * FROM " + TABLE_FOOD + " ORDER BY " + COLUMN_FOOD_NAME;

    //Recipes Strings
    private static final String TABLE_RECIPES = "recipes";
    private static final String COLUMN_RECIPES_ID ="id";
    private static final String COLUMN_RECIPES_NAME = "nazwa";
    private static final String COLUMN_RECIPES_TYPE = "typ";
    private static final String COLUMN_RECIPES_INGREDIENTS = "składniki";
    private static final String COLUMN_RECIPES_DATE = "data";
    private static final String COLUMN_RECIPES_TIME = "czas";
    private static final String COLUMN_RECIPES_STAGES = "etapy";
    private static final int INDEX_RECIPES_ID = 1;
    private static final int INDEX_RECIPES_NAME = 2;
    private static final int INDEX_RECIPES_TYPE = 3;
    private static final int INDEX_RECIPES_INGREDIENTS = 4;
    private static final int INDEX_RECIPES_DATE = 5;
    private static final int INDEX_RECIPES_TIME = 6;
    private static final int INDEX_RECIPES_STAGES = 7;

    private static final String INITIALIZE_RECIPES = "CREATE TABLE IF NOT EXISTS " + TABLE_RECIPES + "(" +
            COLUMN_RECIPES_ID + " INTEGER NOT NULL PRIMARY KEY, " + COLUMN_RECIPES_NAME + " TEXT NOT NULL, " +
            COLUMN_RECIPES_TYPE + " TEXT, " + COLUMN_RECIPES_INGREDIENTS + " TEXT NOT NULL, " +
            COLUMN_RECIPES_DATE + " DATE, " + COLUMN_RECIPES_TIME + " TEXT, " + COLUMN_RECIPES_STAGES + " TEXT)";

    private static final String INSERT_RECIPES = "INSERT INTO " + TABLE_RECIPES + "('" + COLUMN_RECIPES_NAME + "', '" +
            COLUMN_RECIPES_TYPE + "', '" + COLUMN_RECIPES_INGREDIENTS + "', '" + COLUMN_RECIPES_DATE + "', '" +
            COLUMN_RECIPES_TIME + "', '" + COLUMN_RECIPES_STAGES + "') " + "VALUES(?,?,?,?,?,?)";

    private static final String DELETE_RECIPES = "DELETE FROM " + TABLE_RECIPES + " WHERE " + COLUMN_RECIPES_NAME +
            " = ? AND " + COLUMN_RECIPES_TYPE + " = ? AND " + COLUMN_RECIPES_INGREDIENTS + " = ? AND " +
            COLUMN_RECIPES_DATE + " = ? AND " + COLUMN_RECIPES_TIME + " = ? AND " +
            COLUMN_RECIPES_STAGES + " = ?";

    private static final String UPDATE_RECIPES = "UPDATE " + TABLE_RECIPES + " SET " + COLUMN_RECIPES_NAME +
            " = ?, " + COLUMN_RECIPES_TYPE + " = ?, " + COLUMN_RECIPES_INGREDIENTS + " = ?, " + COLUMN_RECIPES_DATE +
            " = ?, " + COLUMN_RECIPES_TIME + " = ?, " + COLUMN_RECIPES_STAGES + " = ? WHERE " + COLUMN_RECIPES_NAME +
            " = ? AND " + COLUMN_RECIPES_TYPE + " = ? AND " + COLUMN_RECIPES_INGREDIENTS + " = ? AND " + COLUMN_RECIPES_DATE +
            " = ? AND " + COLUMN_RECIPES_TIME + " = ? AND " + COLUMN_RECIPES_STAGES + " = ? ";

    private static final String SEARCH_RECIPES = "SELECT * FROM " + TABLE_RECIPES +" WHERE " +
            COLUMN_RECIPES_NAME + " LIKE ? AND " + COLUMN_RECIPES_TYPE + " LIKE ? AND " +
            COLUMN_RECIPES_INGREDIENTS + " LIKE ? AND " + COLUMN_RECIPES_DATE + " LIKE ? AND " +
            COLUMN_RECIPES_TIME + " LIKE ? AND " + COLUMN_RECIPES_STAGES + " LIKE ?";

    private static final String LOAD_RECIPES = "SELECT * FROM " + TABLE_RECIPES + " ORDER BY " + COLUMN_RECIPES_NAME;

    private static final String UPDATE_RECIPE_DATE = "UPDATE " + TABLE_RECIPES + " SET " + COLUMN_RECIPES_DATE +
            " = ? WHERE " + COLUMN_RECIPES_ID + " = ?";

    //History Strings
    //Recipes strings
    private static final String TABLE_HISTORY_RECIPES = "recipesHistory";
    private static final String COLUMN_HISTORY_RECIPES_NAME = "nazwa";
    private static final String COLUMN_HISTORY_RECIPES_DATE = "data";
    private static final int INDEX_HISTORY_RECIPES_NAME = 1;
    private static final int INDEX_HISTORY_RECIPES_DATE = 2;

    private static final String INITIALIZE_HISTORY_RECIPES = "CREATE TABLE IF NOT EXISTS " + TABLE_HISTORY_RECIPES +
            "("+COLUMN_HISTORY_RECIPES_NAME + " TEXT NOT NULL, " + COLUMN_HISTORY_RECIPES_DATE + " DATE NOT NULL)";

    private static final String LOAD_HISTORY_RECIPES = "SELECT * FROM " + TABLE_HISTORY_RECIPES + " ORDER BY " +
            COLUMN_HISTORY_RECIPES_NAME + " DESC";

    private static final String INSERT_HISTORY_RECIPE = "INSERT INTO " + TABLE_HISTORY_RECIPES + "('"
            + COLUMN_HISTORY_RECIPES_NAME + "', '" + COLUMN_HISTORY_RECIPES_DATE + "')" + " VALUES(?,?)";
    //Food
    private static final String TABLE_HISTORY_FOOD = "foodHistory";
    private static final String COLUMN_HISTORY_FOOD_NAME = "nazwa";
    private static final String COLUMN_HISTORY_FOOD_WEIGHT = "waga";
    private static final String COLUMN_HISTORY_FOOD_DATE = "data";
    private static final int INDEX_HISTORY_FOOD_NAME = 1;
    private static final int INDEX_HISTORY_FOOD_WEIGHT = 2;
    private static final int INDEX_HISTORY_FOOD_DATE = 3;

    private static final String INITIALIZE_HISTORY_FOOD = "CREATE TABLE IF NOT EXISTS " + TABLE_HISTORY_FOOD +
            "("+COLUMN_HISTORY_FOOD_NAME + " TEXT NOT NULL, "+ COLUMN_HISTORY_FOOD_WEIGHT + " DECIMAL NOT NULL, " + COLUMN_HISTORY_FOOD_DATE + " DATE NOT NULL)";

    private static final String LOAD_HISTORY_FOOD = "SELECT * FROM " + TABLE_HISTORY_FOOD + " ORDER BY " +
            COLUMN_HISTORY_FOOD_NAME + " DESC";

    //Calendar Strings

    private boolean open()
    {
        try{
           connection = DriverManager.getConnection(CONNECTION_STRING);
           return true;
        }catch(SQLException e) {
            System.out.println("Couldn't connect to the database, " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void close()
    {
        try{
            if(connection!=null) connection.close();

        }catch (SQLException e) {
            System.out.println("Couldn't close the database");
            e.printStackTrace();
        }
    }

    //Fridge section
    public void initializeFood()
    {
        if(open())
        {
            try {
                PreparedStatement initializeFoodStmt = connection.prepareStatement(INITIALIZE_FOOD);
                initializeFoodStmt.execute();
                initializeFoodStmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
    }

    public List<Food> loadFood()
    {
        List<Food> foodList = new ArrayList<>();
        if(open())
        {
            try {
                PreparedStatement loadFoodStmt = connection.prepareStatement(LOAD_FOOD);
                ResultSet resultSet = loadFoodStmt.executeQuery();

                while(resultSet.next())
                {
                    int id = resultSet.getInt(INDEX_FOOD_ID);
                    String name = resultSet.getString(INDEX_FOOD_NAME);
                    String type = resultSet.getString(INDEX_FOOD_TYPE);
                    double weight = resultSet.getDouble(INDEX_FOOD_WEIGHT);
                    String date = resultSet.getString(INDEX_FOOD_DATE);
                    String owner = resultSet.getString(INDEX_FOOD_OWNER);
                    foodList.add(new Food(id, name,type,weight,date,owner));
                }

                resultSet.close();
                loadFoodStmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
        return foodList;
    }

    public int addFood(String name, String type, double weight, String date, String owner)
    {
        int id = 0;
        if(open())
        {
            try {
                PreparedStatement insertFoodStmt = connection.prepareStatement(INSERT_FOOD, Statement.RETURN_GENERATED_KEYS);
                insertFoodStmt.setString(1,name);
                insertFoodStmt.setString(2,type);
                insertFoodStmt.setDouble(3,weight);
                insertFoodStmt.setString(4,date);
                insertFoodStmt.setString(5,owner);
                insertFoodStmt.execute();

                ResultSet resultSet = insertFoodStmt.getGeneratedKeys();
                id = resultSet.getInt(1);

                insertFoodStmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
        return id;
    }

    public void deleteFood(String name, String type, double weight, String date, String owner) {
        if (open())
        {
            try {
                PreparedStatement deleteFoodStmt = connection.prepareStatement(DELETE_FOOD);
                deleteFoodStmt.setString(1,name);
                deleteFoodStmt.setString(2,type);
                deleteFoodStmt.setDouble(3,weight);
                deleteFoodStmt.setString(4,date);
                deleteFoodStmt.setString(5,owner);
                deleteFoodStmt.execute();
                deleteFoodStmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
    }

    public void editFood(int id, String name, String type, double weight, String date, String owner)
    {
        if(open())
        {
            try {
                PreparedStatement editFoodStmt = connection.prepareStatement(UPDATE_FOOD);
                editFoodStmt.setString(1, name);
                editFoodStmt.setString(2, type);
                editFoodStmt.setDouble(3, weight);
                editFoodStmt.setString(4, date);
                editFoodStmt.setString(5, owner);
                editFoodStmt.setInt(6, id);
                editFoodStmt.executeUpdate();
                editFoodStmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
    }

    public List<Food> searchFood(String name, String type, double weight, String date, String owner)
    {
        List<Food> resultList = new ArrayList<>();

        if(open())
        {
            try {
                PreparedStatement searchFoodStmt = connection.prepareStatement(SEARCH_FOOD);
                searchFoodStmt.setString(1,"%" + name + "%");
                searchFoodStmt.setString(2,"%" + type + "%");
                searchFoodStmt.setString(3,"%" + date + "%");
                searchFoodStmt.setString(4,"%" + owner + "%");
                ResultSet results = searchFoodStmt.executeQuery();

                while(results.next())
                {
                    int idQ = results.getInt(COLUMN_FOOD_ID);
                    String nameQ = results.getString(COLUMN_FOOD_NAME);
                    String typeQ = results.getString(COLUMN_FOOD_TYPE);
                    double weightQ = results.getDouble(COLUMN_FOOD_WEIGHT);
                    String dateQ = results.getString(COLUMN_FOOD_DATE);
                    String ownerQ = results.getString(COLUMN_FOOD_OWNER);
                    resultList.add(new Food(idQ,nameQ,typeQ,weightQ,dateQ,ownerQ));
                }
                results.close();
                searchFoodStmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }

        return resultList;
    }

    //Recipes section
    public void initializeRecipes()
    {
        if(open())
        {
            try {
                PreparedStatement initializeRecipesStmt = connection.prepareStatement(INITIALIZE_RECIPES);
                initializeRecipesStmt.execute();
                initializeRecipesStmt.close();

            } catch (SQLException e) {
                System.out.println("Couldn't create a table" + e.getMessage());
                e.printStackTrace();
            } finally {
                close();
            }
        }
    }

    public List<Recipe> loadRecipes()
    {
        List<Recipe> recipes_list = new ArrayList<>();

        if(open())
        {
            try {
                PreparedStatement loadRecipesStmt = connection.prepareStatement(LOAD_RECIPES);
                ResultSet results = loadRecipesStmt.executeQuery();

                while(results.next())
                {
                    int id = results.getInt(INDEX_RECIPES_ID);
                    String name = results.getString(INDEX_RECIPES_NAME);
                    String type = results.getString(INDEX_RECIPES_TYPE);
                    String ingredients = results.getString(INDEX_RECIPES_INGREDIENTS);
                    String date = results.getString(INDEX_RECIPES_DATE);
                    String time = results.getString(INDEX_RECIPES_TIME);
                    String stages = results.getString(INDEX_RECIPES_STAGES);

                    recipes_list.add(new Recipe(id, name,type,ingredients, date, time, stages));
                }

                results.close();
                loadRecipesStmt.close();

            } catch (SQLException e) {
                System.out.println("Couldn't load recipes from database " + e.getMessage());
                e.printStackTrace();
                //popup window with information about error
            } finally {
                close();
            }
        }
        return  recipes_list;
    }

    public int addRecipe(String name, String type, String ingredients, String date, String time, String stages)
    {
        int id = 0;
        if(open())
        {
            try {

                PreparedStatement insertRecipesStmt = connection.prepareStatement(INSERT_RECIPES,Statement.RETURN_GENERATED_KEYS);
                insertRecipesStmt.setString(1,name);
                insertRecipesStmt.setString(2,type);
                insertRecipesStmt.setString(3,ingredients);
                insertRecipesStmt.setString(4,date);
                insertRecipesStmt.setString(5,time);
                insertRecipesStmt.setString(6,stages);
                insertRecipesStmt.execute();

                ResultSet generatedKeys = insertRecipesStmt.getGeneratedKeys();
                id = generatedKeys.getInt(INDEX_RECIPES_ID);

                insertRecipesStmt.close();

            } catch (SQLException e) {
                System.out.println("Couldn't add a recipe to database." + e.getMessage());
                e.printStackTrace();
            } finally {
                close();
            }
        }
        return id;
    }

    public void deleteRecipe(String name, String type, String ingredients, String date, String time, String stages)
    {
        if(open())
        {
            try {
                PreparedStatement deleteRecipesStmt = connection.prepareStatement(DELETE_RECIPES);
                deleteRecipesStmt.setString(1, name);
                deleteRecipesStmt.setString(2, type);
                deleteRecipesStmt.setString(3, ingredients);
                deleteRecipesStmt.setString(4, date);
                deleteRecipesStmt.setString(5, time);
                deleteRecipesStmt.setString(6, stages);
                deleteRecipesStmt.execute();
                deleteRecipesStmt.close();

            } catch (SQLException e) {
                System.out.println("Couldn't delete recipe " + e.getMessage());
                e.printStackTrace();
            } finally {
                close();
            }
        }
    }

    public void editRecipe(Recipe selectedRecipe, String name, String type, String ingredients, String date, String time, String stages)
    {
        if(open())
        {
            try {
                PreparedStatement updateRecipesStmt = connection.prepareStatement(UPDATE_RECIPES);
                updateRecipesStmt.setString(1, name);
                updateRecipesStmt.setString(2, type);
                updateRecipesStmt.setString(3, ingredients);
                updateRecipesStmt.setString(4, date);
                updateRecipesStmt.setString(5, time);
                updateRecipesStmt.setString(6, stages);
                updateRecipesStmt.setString(7, selectedRecipe.getName());
                updateRecipesStmt.setString(8, selectedRecipe.getType());
                updateRecipesStmt.setString(9, selectedRecipe.getIngredients());
                updateRecipesStmt.setString(10, selectedRecipe.getDate());
                updateRecipesStmt.setString(11, selectedRecipe.getTime());
                updateRecipesStmt.setString(12, selectedRecipe.getStages());
                updateRecipesStmt.executeUpdate();
                updateRecipesStmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
    }

    public List<Recipe> searchRecipe(String name, String type, String ingredients, String date, String time, String stages)
    {
        List<Recipe> resultList = new ArrayList<>();

        if(open())
        {
            try {

                PreparedStatement searchRecipesStmt = connection.prepareStatement(SEARCH_RECIPES);
                searchRecipesStmt.setString(1,"%" + name + "%");
                searchRecipesStmt.setString(2,"%" + type + "%");
                searchRecipesStmt.setString(3,"%" + ingredients + "%");
                searchRecipesStmt.setString(4,"%" + date + "%");
                searchRecipesStmt.setString(5,"%" + time + "%");
                searchRecipesStmt.setString(6,"%" + stages + "%");

                ResultSet results = searchRecipesStmt.executeQuery();

                while (results.next()) {
                    int idQ = results.getInt(INDEX_RECIPES_ID);
                    String nameQ = results.getString(INDEX_RECIPES_NAME);
                    String typeQ = results.getString(INDEX_RECIPES_TYPE);
                    String ingredientsQ = results.getString(INDEX_RECIPES_INGREDIENTS);
                    String dateQ = results.getString(INDEX_RECIPES_DATE);
                    String timeQ = results.getString(INDEX_RECIPES_TIME);
                    String stagesQ = results.getString(INDEX_RECIPES_STAGES);

                    resultList.add(new Recipe(idQ, nameQ, typeQ, ingredientsQ, dateQ, timeQ, stagesQ));
                }

                results.close();
                searchRecipesStmt.close();

            } catch (SQLException e) {
                System.out.println("Couldn't search for recipe! " + e.getMessage());
                e.printStackTrace();
            } finally {
                close();
            }
        }
        return resultList;
    }

    public void updateRecipeDate(String date, int id)
    {
        if(open())
        {
            try {
                PreparedStatement updateRecipeDateStmt = connection.prepareStatement(UPDATE_RECIPE_DATE);
                updateRecipeDateStmt.setString(1,date);
                updateRecipeDateStmt.setInt(2,id);
                updateRecipeDateStmt.executeUpdate();
                updateRecipeDateStmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
    }

    //History section
    public void initializeHistoryRecipes()
    {
        if(open())
        {
            try {
                PreparedStatement initializeHistoryStmt = connection.prepareStatement(INITIALIZE_HISTORY_RECIPES);
                initializeHistoryStmt.execute();
                initializeHistoryStmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
    }

    public List<Dish> loadHistoryRecipes()
    {
        List<Dish> resultList = new ArrayList<>();

        if(open())
        {
            try {
                PreparedStatement loadHistoryRecipesStmt = connection.prepareStatement(LOAD_HISTORY_RECIPES);
                ResultSet results = loadHistoryRecipesStmt.executeQuery();

                while(results.next())
                {
                    String name = results.getString(INDEX_HISTORY_RECIPES_NAME);
                    String date = results.getString(INDEX_HISTORY_RECIPES_DATE);
                    resultList.add(new Dish(name,1,date));
                }

                results.close();
                loadHistoryRecipesStmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }

        return resultList;
    }

    public void addHistoryRecipe(String name, String date)
    {
        if(open())
        {
            try {
                PreparedStatement insertHistoryRecipeStmt = connection.prepareStatement(INSERT_HISTORY_RECIPE);
                insertHistoryRecipeStmt.setString(1,name);
                insertHistoryRecipeStmt.setString(2,date);
                insertHistoryRecipeStmt.execute();
                insertHistoryRecipeStmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
    }

    public void initializeHistoryFood()
    {
        if(open())
        {
            try {
                PreparedStatement initializeHistoryStmt = connection.prepareStatement(INITIALIZE_HISTORY_FOOD);
                initializeHistoryStmt.execute();
                initializeHistoryStmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
    }

    public List<Dish> loadHistoryFood()
    {
        List<Dish> resultList = new ArrayList<>();

        if(open())
        {
            try {
                PreparedStatement loadHistoryRecipesStmt = connection.prepareStatement(LOAD_HISTORY_FOOD);
                ResultSet results = loadHistoryRecipesStmt.executeQuery();

                while(results.next())
                {
                    String name = results.getString(INDEX_HISTORY_FOOD_NAME);
                    double weight = results.getDouble(INDEX_HISTORY_FOOD_WEIGHT);
                    String date = results.getString(INDEX_HISTORY_FOOD_DATE);
                    resultList.add(new Dish(name,weight,date));
                }

                results.close();
                loadHistoryRecipesStmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }

        return resultList;
    }

    //Calendar section
    public List<?> loadFoodForCombobox()
    {
        return null;
    }

    public List<Recipe> loadRecipesForCombobox()
    {
        return null;
    }

    public void initializeCalendarRecipes()
    {

    }

    public List<?> loadCalendarRecipes()
    {
        return null;
    }

    public void addCalendarRecipe()
    {

    }

    public void deleteCalendarRecipe()
    {

    }

    public void initializeCalendarFood()
    {

    }

    public List<?> loadCalendarFood()
    {
        return null;
    }

    public void addCalendarFood()
    {

    }

    public void deleteCalendarFood()
    {

    }
}
