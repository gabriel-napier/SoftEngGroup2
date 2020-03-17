package com.napier.sem;

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
         * Connection to the database, throw relevant exception in case of no success
        */

         Connection con = app.connect("jdbc:mysql://127.0.0.1:33060/world?" + "user=root&password=example");

        /**
         * END OF DATABASE CONNECTION CODE
         */

        assertEquals(con.isValid(5), true);
    }

}