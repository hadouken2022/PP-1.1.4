package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final static String URL = "jdbc:mysql://localhost:3306/Product";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "Mark41747";
    private final static String DIALECT = "org.hibernate.dialect.MySQL8Dialect";
    private static SessionFactory sessionFactory;

    static {
        try {
            Properties properties = new Properties();
            properties.setProperty("hibernate.connection.driver_class", DRIVER);
            properties.setProperty("hibernate.connection.url", URL);
            properties.setProperty("hibernate.connection.username", USERNAME);
            properties.setProperty("hibernate.connection.password", PASSWORD);
            properties.setProperty("hibernate.dialect", DIALECT);

            Configuration configuration = new Configuration();
            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }
}