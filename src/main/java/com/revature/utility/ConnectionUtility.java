package com.revature.utility;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {

    public static Connection getConnection(DataSource dataSource) throws SQLException {

        DriverManager.registerDriver(new Driver());

        return DriverManager.getConnection(
                dataSource.getUrl(),
                dataSource.getUsername(),
                dataSource.getPassword()
        );
    }
}