package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Statement st = Util.getStatement();
        try {
            st.execute("CREATE TABLE IF NOT EXISTS USERS " +
                    "(" +
                    "ID INT AUTO_INCREMENT," +
                    " name VARCHAR(50)," +
                    " LastName VARCHAR(50)," +
                    " AGE INT," +
                    " primary key (ID)" +
                    ")");
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        Statement st = Util.getStatement();
        try {
            st.execute("DROP TABLE IF EXISTS USERS; ");
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        Statement st = Util.getStatement();
        try {
            st.executeUpdate("INSERT INTO USERS (NAME,LASTNAME,AGE) " +
                    "VALUES ('" + name + "','" + lastName + "','" + age + "')");
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        Statement st = Util.getStatement();
        try {
            st.executeUpdate("DELETE FROM USERS WHERE id='" + id + "'");
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        Statement st = Util.getStatement();
        {

            ResultSet resultSet;
            try {
                resultSet = st.executeQuery("SELECT * FROM USERS");

                while (resultSet.next()) {

                    long id = resultSet.getLong("ID");
                    String name = resultSet.getString("NAME");
                    String lastName = resultSet.getString("LASTNAME");
                    byte age = resultSet.getByte("AGE");

                    User user = new User();
                    user.setId(id);
                    user.setName(name);
                    user.setLastName(lastName);
                    user.setAge(age);

                    userList.add(user);
                }
               st.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return userList;
        }
    }

    public void cleanUsersTable() {
        Statement st = Util.getStatement();
        try {
            st.executeUpdate("TRUNCATE USERS");
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
