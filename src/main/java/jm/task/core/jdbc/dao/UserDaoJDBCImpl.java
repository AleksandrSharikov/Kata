package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try(Connection con = Util.getConnection()) {
            Statement st = con.createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS USERS " +
                    "(" +
                    "ID INT AUTO_INCREMENT," +
                    " name VARCHAR(50)," +
                    " LastName VARCHAR(50)," +
                    " AGE INT," +
                    " primary key (ID)" +
                    ")");
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {

        try(Connection con = Util.getConnection()) {
            Statement st = con.createStatement();
            st.execute("DROP TABLE IF EXISTS USERS; ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String saveStatement = "INSERT INTO USERS (NAME,LASTNAME,AGE) VALUES ( ?, ?, ?)";
        try(Connection con = Util.getConnection()) {
            PreparedStatement st = con.prepareStatement(saveStatement);
            st.setString(1,name);
            st.setString(2,lastName);
            st.setByte(3,age);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        String removeById = "DELETE FROM USERS WHERE id= ?";
        try(Connection con = Util.getConnection()) {
            PreparedStatement st = con.prepareStatement(removeById);
            st.setLong(1,id);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try(Connection con = Util.getConnection()) {
            Statement st = con.createStatement();

            ResultSet resultSet;
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

    public void cleanUsersTable() {
        try(Connection con = Util.getConnection()) {
            Statement st = con.createStatement();
            st.executeUpdate("TRUNCATE USERS");
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
