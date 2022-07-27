package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.getConnection();


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sqlCommand = "CREATE TABLE IF NOT EXISTS User " + " (name VARCHAR(255), lastName VARCHAR(255), age TINYINT)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommand);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String dropUsersTableSql = "DROP TABLE IF EXISTS User";
        try (Statement statement = connection.createStatement()) {
            statement.execute(dropUsersTableSql);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUserSql = "INSERT INTO User (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(saveUserSql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + "добавлен в базу данных");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String removeUserByIdSql = "DELETE fROM User WHERE ID = ";
        try (Statement statement = connection.createStatement()) {
            statement.execute(removeUserByIdSql + id);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM User";
        //Statement statement = null;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User temporUser = new User(resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getByte("age"));
                userList.add(temporUser);
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
//        finally {
//            if(statement != null) {
//                statement.close();
//            }
//            if(connection != null) {
//                connection.close();
//            }
//        }
        return userList;
    }

    public void cleanUsersTable() {
        String cleanUsersTableSql = "TRUNCATE TABLE User";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(cleanUsersTableSql);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
