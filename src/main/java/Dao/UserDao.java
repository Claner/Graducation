package Dao;

import Entity.AdminEntity;
import Entity.StudentEntity;
import Entity.UserEntity;
import Util.Constant;
import Util.GeneralUtils;
import Util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Clanner on 2017/3/6.
 */
@Repository
public class UserDao {

    /**
     *录入管理员
     */
    public int saveAdmin(String account,String password){
        Session session = HibernateUtil.getSession();
        UserEntity userEntity = new UserEntity(account,GeneralUtils.EnCode(password),"2");
        session.save(userEntity);
        HibernateUtil.commit();
        int id = userEntity.getId();
        HibernateUtil.close();
        return id;
    }

    /**
     * 录入学生（仅管理员可调用）
     * 默认密码是123456
     */
    public int saveUser(String account) {
        Session session = HibernateUtil.getSession();
        UserEntity userEntity = new UserEntity(account, GeneralUtils.EnCode("123456"), "1");
        session.save(userEntity);
        HibernateUtil.commit();
        int id = userEntity.getId();
        HibernateUtil.close();
        return id;
    }

    /**
     * 登陆
     */
    public UserEntity login(String account, String pasword) {
        Session session = HibernateUtil.getSession();
        String hql = "select new UserEntity(userEntity.id,userEntity.account,userEntity.role) from UserEntity as userEntity where userEntity.account=:account  and userEntity.password=:password";
        Query query = session.createQuery(hql);
        query.setString("account", account);
        query.setString("password", pasword);
        UserEntity userEntity = (UserEntity) query.uniqueResult();
        HibernateUtil.close();
        return userEntity;
    }

    /**
     * 判断用户是否已经注册
     */
    public boolean isUserExist(String account) {
        Session session = HibernateUtil.getSession();
        String hql = "select new UserEntity(userEntity.id) from UserEntity as userEntity where userEntity.account=:account";
        Query query = session.createQuery(hql);
        query.setString("account", account);
        UserEntity userEntity = (UserEntity) query.uniqueResult();
        HibernateUtil.close();
        return userEntity == null ? false : true;
    }

    /**
     * 修改密码
     */
    public int modifyPassword(int user_id, String prePassword, String newPassword) {
        if (isPrePasswordCorrect(user_id, prePassword)) {
            Session session = HibernateUtil.getSession();
            String hql = "update UserEntity as userEntity set userEntity.password=:newPassword where userEntity.id=:user_id";
            Query query = session.createQuery(hql);
            query.setString("newPassword", GeneralUtils.EnCode(newPassword));
            query.setInteger("user_id", user_id);
            int i = query.executeUpdate();
            HibernateUtil.close();
            return i > 0 ? Constant.UPDATE_SUCCESS : Constant.UPDATE_FAILURE;
        } else {
            return Constant.PASSWORD_WRONG;
        }
    }

    /**
     * 获取用户密码
     */
    private boolean isPrePasswordCorrect(int user_id, String prePassword) {
        Session session = HibernateUtil.getSession();
        String hql = "select new UserEntity(userEntity.password) from UserEntity as userEntity where userEntity.id=:user_id";
        Query query = session.createQuery(hql);
        query.setInteger("user_id", user_id);
        UserEntity userEntity = (UserEntity) query.uniqueResult();
        HibernateUtil.close();
        if (GeneralUtils.EnCode(prePassword).equals(userEntity.getPassword())) {
            return true;
        }
        return false;
    }

    /**
     * 初始化其他表
     */
    @Transactional
    private boolean init(int id, String role) {
        Session session = HibernateUtil.getSession();
        int which = Integer.parseInt(role);
        switch (which) {
            case 1:
                session.save(new StudentEntity(id));
                HibernateUtil.commit();
                HibernateUtil.close();
                return true;
            case 2:
                session.save(new AdminEntity(id, "管理员", "m", "13692190638", "江门"));
                HibernateUtil.commit();
                HibernateUtil.close();
                return true;
            default:
                return false;
        }
    }
}
