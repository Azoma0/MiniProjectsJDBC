package by.azoma.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String URL_KEY = "jdbc:postgresql://localhost:5432/student-management-system";
    private static final String USER_KEY = "postgres";
    private static final String PASSWORD_KEY = "azoma";

    public static Connection open(){
        try {
            return DriverManager.getConnection(URL_KEY, USER_KEY, PASSWORD_KEY);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private ConnectionManager(){

    }
}
