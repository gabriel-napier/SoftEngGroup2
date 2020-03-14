package SoftEngAPP;

/**
 *
 * Software Engineering Methods Assessment
 * Edinburgh Napier University 2020
 * Graduate Apprenticeship SofDev class GROUP 2
 *
 * Callum Lackie
 * Fraser MacLean
 * Gabor Nemeth
 * Gabriel Pinchuck
 *
 */


import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * more comments!
 */
public class App
{

    public static void main(String[] args) throws IOException, SQLException {

        /**
         * loading JDBC DRIVER
         */
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

        /**
         * Connection to the database, throw relevant exception in case of no success
         */
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
        /**
         * END OF DATABASE CONNECTION CODE
         */

        /**
         * this list is used to store SQL queries
         */
        List<SQLquery> queries = updateQueryList();

        /**
         * this piece of code is used to run all available SQL queries
         */
        for (SQLquery query : queries) {
            System.out.println(query.name);
            System.out.println(query.sql);
            System.out.println(getQueryResult(query, con));
        }

        /**
         * at the end of main, close database connection
         */
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

    /**
     * getQueryResult runs the selected SQL query on the database
     * @param query is an SQLquery object
     * @param con is the database connection
     * @return output is the queries result in CSV format
     * @throws SQLException
     */
    public static String getQueryResult(SQLquery query, Connection con) throws SQLException {

        //load selected database query and run it against database

        Statement stmt = con.createStatement();
        ResultSet result = stmt.executeQuery(query.sql);
        String output = "";

        /**
        * build output string
        */
        while (result.next()) {
            ResultSetMetaData rsmd = result.getMetaData();
            int columnsnumber = rsmd.getColumnCount();
            for (int i = 1; i < columnsnumber + 1; i++) {
                String name = rsmd.getColumnName(i);
                if (i == columnsnumber) {

                    /**
                     * add new line when last column is reached otherwise just add "," for CSV format
                     */
                    output = output + result.getString(name) + "\n";
                } else {
                    output = output + result.getString(name) + ",";
                }
            }
        }
        /**
         * return output string
         */
        return output;
    }

    /**
     * SQLquery class
     * used to create SQL query objects that can later be referred to for listing queries and running
     * the desired database query by user
     */
    public static class SQLquery {

        public String name;
        public String sql;

        public SQLquery(String newname, String newsql) {
            this.name = newname;
            this.sql = newsql;
        }

    }

    /**
     * updateQueryList loads the available SQL query strings from .xyz files in the /mydata/data docker volume folder
     * into a List so that they may be called on demand
     */
    public static List<SQLquery> updateQueryList() throws IOException {

        List<String> querynames = new ArrayList<String>();
        List<SQLquery> queries = new ArrayList<SQLquery>();
        File curDir = new File(".");
        String[] fileNames = curDir.list();

        for (int i = 0; i <= fileNames.length - 1; i++) {

            /**
             * SELECT ONLY .XYZ FILES FROM FOLDER
             */
            System.out.println(fileNames[i]);
            if (fileNames[i].contains(".xyz")) {

                querynames.add(fileNames[i]);

            }

        }

        /**
         * create SQLquery objects with appropriate name and query string
         */
        for ( String querypath : querynames ) {

            String content = new String(Files.readAllBytes(Paths.get(querypath)));
            SQLquery newquery = new SQLquery(querypath.replace(".xyz",""),content);
            queries.add(newquery);

        }

        /**
         * return query list
         */
        return queries;

    }



}