import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import com.napier.sem.App;
import java.sql.*;

import java.sql.DriverManager;

class MyTest
{

    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();



    }

    @Test
    void unitTest() throws SQLException {
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
                con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:33060/world?" + "user=root&password=example");
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


        app.getQueryResult(new App.SQLquery("01","select * from world.city limit 1"),con);


        assertEquals(con.isValid(5), true);
    }

}