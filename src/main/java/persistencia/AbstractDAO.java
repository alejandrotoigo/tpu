package persistencia;

import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public abstract class AbstractDAO {
    Session session = null;
    Transaction transaction = null;

    public void beginTransaction() {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
    }

    public void commit() {
        transaction.commit();
        session.close();
    }

    public void rollback() {
        if(session != null) session.close();
        if(transaction != null) transaction.rollback();
    }
}
