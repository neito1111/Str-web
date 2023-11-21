package org.example.start;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBWorker {
    private static final String HOST = "jdbc:postgresql://localhost:5432/strweblab4";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "123456";

    @Getter
    private static Connection connection = null;

    public static void connect() throws SQLException{
        try {
            connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
            if(!connection.isClosed()) System.out.println("Connection with database is accepted");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void closeConnection()  {
        try {
            connection.close();
            if(connection.isClosed()) System.out.println("Connection is closed");
        } catch (SQLException e) {
            System.out.println("Problem with closing connection");
        }

    }

}
