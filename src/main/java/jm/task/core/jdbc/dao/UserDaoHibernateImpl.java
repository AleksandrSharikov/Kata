package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        SessionFactory sf = Util.getSessionFactory();
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS USERS " +
                    "(" +
                    "ID INT AUTO_INCREMENT," +
                    " name VARCHAR(50)," +
                    " LastName VARCHAR(50)," +
                    " AGE INT," +
                    " primary key (ID)" +
                    ")").executeUpdate();
            tx.commit();
        }catch (Exception e) {
            tx.rollback();
        }finally {
            session.close();
        }

    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
        session.createSQLQuery("DROP TABLE USERS").executeUpdate();
        tx.commit();
        }catch (Exception e) {
            tx.rollback();
        }finally {
            session.close();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
        session.persist(new User(name,lastName,age));
        tx.commit();
        }catch (Exception e) {
            tx.rollback();
        }finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        User user;
        try {
        if((user = session.get(User.class, id)) != null) {
            session.remove(user);
        }
        tx.commit();
        }catch (Exception e) {
            tx.rollback();
        }finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        return (ArrayList<User>) Util.getSessionFactory().openSession().createQuery("From User").list();
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction  tx = session.beginTransaction();
        try {
        session.createQuery("DELETE User").executeUpdate();
        tx.commit();
        }catch (Exception e) {
            tx.rollback();
        }finally {
            session.close();
        }
    }
}
