package Util;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Created by Clanner on 2016/12/19.
 */
public class HibernateUtil {
    private static final Configuration configuration = new Configuration().configure();
    private static final SessionFactory sessionFactory = configuration.buildSessionFactory();
    private static Session session = null;
    private static Transaction transaction = null;

    public static Session getSession() {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        return session;
    }

    /**
     * 提交
     */
    public static void commit() {
        transaction.commit();
    }

    /**
     * 事务回滚
     */
    public static void rollBack() {
        transaction.rollback();
    }

    /**
     * 关闭session
     */
    public static void close() {
        if (session != null) session.close();
    }

    /**
     * 获取count(传入数据库名)
     */
    public long getCount(String name) {
        Session session = getSession();
        String hql = "select count(*) from " + name;
        Query query = session.createQuery(hql);
        long i = ((Long) query.uniqueResult()).intValue();
        HibernateUtil.close();
        return i;
    }
}
