package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String name = "root";
    private static final String password = "root";
    private static final String URL = "jdbc:mysql://localhost/test";
    private static final String driver = "com.mysql.cj.jdbc.Driver";

    public static Statement getStatement() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            Connection connection = DriverManager.getConnection(URL, name, password);
            return connection.createStatement();
            //statement.executeUpdate("CREATE TABLE IF NOT EXISTS test2 (id int primary key auto_increment, name VARCHAR(10) NOT NULL); ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
