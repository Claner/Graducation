package Dao;

import Entity.AdminEntity;
import Entity.UserEntity;
import Util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Clanner on 2017/3/7.
 */
@Repository
public class SuperAdminDao {
    /**
     * 删除管理员
     */
    @Transactional
    public boolean deleteAdmin(int id) {
        try {
            Session session = HibernateUtil.getSession();
            session.delete(new UserEntity(id));
            session.delete(new AdminEntity(id));
            HibernateUtil.commit();
            HibernateUtil.close();
            return true;
        } catch (Exception e) {
            HibernateUtil.close();
            return false;
        }
    }
}
