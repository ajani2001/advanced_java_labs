package org.ajani2001.lab2;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        var connection = DriverManager.getConnection("jdbc:postgresql://localhost:5435/postgres", "admin", "admin");
        System.out.println(connection);
    }
}
