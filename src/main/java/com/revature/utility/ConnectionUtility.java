package com.revature.utility;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {

    public static Connection getConnection() throws SQLException {

        DriverManager.registerDriver(new Driver());

        //String url = System.getenv("DB_URL");
        //String user = System.getenv("DB_USER");
        //String pass = System.getenv("DB_PASS");
	String url = "jdbc:postgresql://34.122.221.182:5432/projectone";
	String user = "postgres";
	String pass = "password";

        return DriverManager.getConnection(url, user, pass);
    }
}
