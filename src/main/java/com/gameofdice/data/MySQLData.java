package com.gameofdice.data;

import com.gameofdice.games.Game;
import com.gameofdice.users.User;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class MySQLData implements Persistence {

    private static final MySQLConnection SQL_CONNECTION = new MySQLConnection();
    private static final MySQLData INSTACE = new MySQLData();

    public static MySQLData GetINSTACE() {
        return INSTACE;
    }

    private MySQLData() {
    }

    @Override
    public void saveObj(Object object) {
        SQL_CONNECTION.persistObj(object);
    }

    @Override
    public void updateObject(Object object) {
        SQL_CONNECTION.updateObject(object);
    }
    
    @Override
    public User getUser(String userName) {
        return SQL_CONNECTION.getUser(userName);
    }

    @Override
    public List<Game> getLastGames(String userName, int nrOfGames) {
        return SQL_CONNECTION.getLastGames(userName, nrOfGames);
    }


    private static class MySQLConnection {

        private static final SessionFactory FACTORY = BuildFactory();

        private static SessionFactory BuildFactory() {
            try {
                return new Configuration().configure().buildSessionFactory();
            } catch (HibernateException ex) {
                System.err.println("Failed to create sessionFactory object." + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }

        public void persistObj(Object object) {
            Session session = FACTORY.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(object);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw new RuntimeException(e);
            } finally {
                session.close();
            }
        }

        public void updateObject(Object object) {
            Session session = FACTORY.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.saveOrUpdate(session.merge(object));
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw new RuntimeException(e);
            } finally {
                session.close();
            }
        }
        
        public User getUser(String userName) {
            Session session = FACTORY.openSession();
            Transaction tx = null;
            User user = null;
            try {
                tx = session.beginTransaction();
                String hql = "from User u where u.userName = :userName";
                user = (User) session.createQuery(hql).setParameter("userName", userName).uniqueResult();
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw new RuntimeException(e);
            } finally {
                session.close();
            }
            return user;
        }

        public List<Game> getLastGames(String userName, int nrOfGames) {
            Session session = FACTORY.openSession();
            Transaction tx = null;
            List<Game> games = null;
            try {
                tx = session.beginTransaction();
                String hql = "from Game g where g.player.userName = :userName order by g.id desc";
                games = (List<Game>) session.createQuery(hql).
                        setParameter("userName", userName).
                        setMaxResults(nrOfGames).
                        list();
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw new RuntimeException(e);
            } finally {
                session.close();
            }
            return games;
        }
    }
}
