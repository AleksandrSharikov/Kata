package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

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

    private static SessionFactory sf;
    private Util() {}

    public static SessionFactory getSessionFactory() {
        Configuration conf = new Configuration()
                .setProperty("hibernate.connection.url", URL)
                        .setProperty("hibernate.connection.driver", driver)
                            .setProperty("hibernate.connection.username", name)
                                .setProperty("hibernate.connection.password", password)
                                    .setProperty("hibernate.hbm2ddl.auto","update");

        conf.addAnnotatedClass(User.class);
        StandardServiceRegistryBuilder builder
                = new StandardServiceRegistryBuilder().applySettings(conf.getProperties());
        sf = conf.buildSessionFactory(builder.build());

        return sf;
    }

    public static Statement getStatement() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            Connection connection = DriverManager.getConnection(URL, name, password);
            return connection.createStatement();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
