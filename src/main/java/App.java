package SoftEngAPP;

import java.io.FileReader;
import java.lang.System.*;
import java.sql.*;
import java.io.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import jdk.nashorn.internal.parser.JSONParser;

/**
 * more comments!
 */
public class App
{
    public static void main(String[] args)
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
       // Catch and console log error if error found
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        // Connection to the database
        Connection con = null;
        int retries = 100;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://mysql1:3306/world?" + "user=root&password=example");
                System.out.println("Successfully connected");
                // Wait a bit
                Thread.sleep(10000);
                // Exit for loop
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
                System.out.println("SQLException: " + sqle.getMessage());
                System.out.println("SQLState: " + sqle.getSQLState());
                System.out.println("VendorError: " + sqle.getErrorCode());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }

        File curDir = new File(".");
        String[] fileNames = curDir.list();
        //String jsonFile = "./test01.json";
        for (int i = 0; i <= fileNames.length - 1; i++) {
            System.out.println(fileNames[i]);
            System.out.println(curDir.getPath().toString());
        }
        Gson gson = new Gson();

        try (Reader reader = new FileReader("./test01.json")) {

            SQLquery test123 = gson.fromJson(reader, SQLquery.class);

            System.out.println(test123.name);
            System.out.println(test123.sql);
            System.out.println(reader.toString());
        } catch ( IOException e) {
            e.printStackTrace();
        }





        /*/
        try {

        SQLquery test123 = gson.fromJson(new FileReader(jsonFile), SQLquery.class);

        System.out.println(test123.sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
        /*/



        // Attempt to close database connection
        if (con != null)
        {
            try
            {
                // Close database connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }
    public class SQLquery {

        public String name = "pp big";
        public String sql = "pp smol";

    }

    public void getJson(String fileName) {

        Gson gson = new Gson();
        SQLquery test123 = null;
        try {
            test123 = gson.fromJson(new FileReader(fileName), SQLquery.class);
        } catch (FileNotFoundException e) {
            System.out.println(test123.sql);
        }

    }


}