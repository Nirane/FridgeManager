/*
package sample.Model;

import sample.UsableClasses.Recipe;

import java.sql.*;
import java.util.List;

public class SourceRecipes extends Source {



    */
/*public List<Recipe> queryWholeTable()
    {

    }*//*


    public boolean add(String name, String type)
    {
        try{


            statement.execute("INSERT INTO recipes('nazwa', 'typ', 'składniki', 'data', 'czas','etapy') " +
                    "VALUES('" + name + "', " +
                    "'" + type + "', " +
                    "'" + ingredients + "', " +
                    "'" + date + "', " +
                    "'" + time + "'," +
                    "'" + stages + "')");

        } catch (SQLException e) {
            System.out.println("Insertion failed! " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean open()
    {
        super.open();
    }

    @Override
    public void close()
    {



    }
}
*/
