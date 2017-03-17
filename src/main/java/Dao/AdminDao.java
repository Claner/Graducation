package Dao;

import Entity.AdminEntity;
import Entity.StudentEntity;
import Entity.UserEntity;
import Util.Constant;
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
public class AdminDao {
    /**
     * 获取管理员信息
     */
    public AdminEntity getAdminInfo(int id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new AdminEntity(adminEntity.id,adminEntity.name,adminEntity.sex,adminEntity.phone,adminEntity.address) from AdminEntity as adminEntity where adminEntity.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        AdminEntity adminEntity = (AdminEntity) query.uniqueResult();
        HibernateUtil.close();
        return adminEntity;
    }

    /**
     * 修改管理员信息
     */
    public int updateAdminInfo(int admin_id, String name, String sex, String phone, String address) {
        Session session = HibernateUtil.getSession();
        String hql = "update AdminEntity as adminEntity set adminEntity.name=:name,adminEntity.sex=:sex,adminEntity.phone=:phone,adminEntity.address=:address where adminEntity.id=:admin_id";
        Query query = session.createQuery(hql);
        query.setInteger("admin_id", admin_id);
        query.setString("name", name);
        query.setString("sex", sex);
        query.setString("phone", phone);
        query.setString("address", address);
        int i = query.executeUpdate();
        HibernateUtil.close();
        return i > 0 ? Constant.UPDATE_SUCCESS : Constant.UPDATE_FAILURE;
    }

    /**
     * 删除学生
     */
    @Transactional
    public boolean deleteStudent(int stu_id) {
        Session session = HibernateUtil.getSession();
        session.delete(new StudentEntity(stu_id));
        session.delete(new UserEntity(stu_id));
        HibernateUtil.commit();
        HibernateUtil.close();
        return true;
    }

    /**
     * 添加管理员
     */
    public boolean saveAdmin(int admin_id, String name, String sex, String phone, String address) {
        Session session = HibernateUtil.getSession();
        session.save(new AdminEntity(admin_id, name, sex, phone, address));
        HibernateUtil.commit();
        HibernateUtil.close();
        return true;
    }

    /**
     * 获取所有管理员
     */
    public List<AdminEntity> getAllAdmin(int pageNo, int pageSize) {
        Session session = HibernateUtil.getSession();
        String hql = "select new AdminEntity(adminEntity.id,adminEntity.name,adminEntity.sex,adminEntity.phone,adminEntity.address) from AdminEntity as adminEntity";
        Query query = session.createQuery(hql);
        List<AdminEntity> list = query.setFirstResult((pageNo - 1) * pageSize)
                .setMaxResults(pageSize)
                .list();
        HibernateUtil.close();
        return list;
    }

    /**
     * 获取所有管理员数量
     */
    public long getCount() {
        Session session = HibernateUtil.getSession();
        String hql = "select count(*) from AdminEntity ";
        Query query = session.createQuery(hql);
        long i = ((Long) query.uniqueResult()).intValue();
        HibernateUtil.close();
        return i;
    }
}
