package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }

    @Test
    void testGet() throws SQLException
    {
        /**
         * Connection to the database, throw relevant exception in case of no success
         */

        Connection con = app.connect("jdbc:mysql://127.0.0.1:33060/world?" + "user=root&password=example");

        /**
         * END OF DATABASE CONNECTION CODE
         */
        String test = app.getQueryResult(new App.SQLquery("01","select * from world.city limit 1"),con);

        assertEquals(test,"1,Kabul,AFG,Kabol,1780000" + "\n");
    }

}
