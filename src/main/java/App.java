package SoftEngAPP;

import com.mysql.cj.PreparedQuery;
import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * more comments!
 */
public class App
{

    public static void main(String[] args) throws IOException, SQLException {
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
                //con.setCatalog("world");
                // Wait a bit
                Thread.sleep(1000);
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
        List<String> querynames = new ArrayList<String>();
        List<SQLquery> queries = new ArrayList<SQLquery>();
        File curDir = new File(".");
        String[] fileNames = curDir.list();
        for (int i = 0; i <= fileNames.length - 1; i++) {
            System.out.println(fileNames[i]);
            if (fileNames[i].contains(".sql")) {
                querynames.add(fileNames[i]);
            }

        }

        for ( String querypath : querynames ) {

            String content = new String(Files.readAllBytes(Paths.get(querypath)));
            SQLquery newquery = new SQLquery(querypath,content);
            queries.add(newquery);
        }

        for (SQLquery query : queries) {
            System.out.println(query.name);
            System.out.println(query.sql);

            Statement statement = con.createStatement();

            ResultSet asd = statement.executeQuery(query.sql);
            while (asd.next()) {
                String output = "";
                output = output + asd.getInt("ID") + "\t";
                output = output + asd.getString("Name") + "\t";
                output = output + asd.getString("CountryCode") + "\t";
                output = output + asd.getString("District") + "\t";
                output = output + asd.getInt("Population") + "\n";
                System.out.println(output);
            }

        }


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


    public static class SQLquery {

        public String name;
        public String sql;

        public SQLquery(String newname, String newsql) {
            this.name = newname;
            this.sql = newsql;
        }


    }

}