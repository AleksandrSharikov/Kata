package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
       /* Session session = Util.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS USERS " +
                "(" +
                "ID INT AUTO_INCREMENT," +
                " name VARCHAR(50)," +
                " LastName VARCHAR(50)," +
                " AGE INT," +
                " primary key (ID)" +
                ")").executeUpdate();
        tx.commit();
        session.close();*/

    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.createSQLQuery("DROP TABLE USERS").executeUpdate();
        tx.commit();
        session.close();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(new User(name,lastName,age));
        tx.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        if(session.get(User.class, id) != null) {
            session.remove(session.get(User.class, id));
        }
        tx.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {

        return (ArrayList<User>)  Util.getSessionFactory().openSession().createQuery("From User").list();
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction  tx = session.beginTransaction();
        session.createQuery("DELETE User").executeUpdate();
        tx.commit();
        session.close();
    }
}
