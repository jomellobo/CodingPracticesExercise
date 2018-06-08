package com.orangeandbronze.coding_practices.leave.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private static String url = "dbc:hsqldb:leaves";    
    private static String username = "sa";   
    private static String password = "";
    
    public static Connection getConnection() {
        Connection con;
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                throw new DataAccessException("Failed to create the database connection."); 
            }
        return con;
    }
}
