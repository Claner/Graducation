package Dao;

import Entity.AcademyEntity;
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
public class AcademyDao {
    /**
     * 录入学院
     */
    public int saveAcademy(String name) {
        if (isAcademyExist(name)) return 0;
        Session session = HibernateUtil.getSession();
        AcademyEntity academyEntity = new AcademyEntity(name);
        session.save(academyEntity);
        HibernateUtil.commit();
        if (academyEntity.getId() != 0) {
            return Constant.SAVE_SUCCESS;
        } else {
            return Constant.SAVE_FAILURE;
        }
    }

    /**
     * 判断学院是否已经存在
     */
    public boolean isAcademyExist(String name) {
        Session session = HibernateUtil.getSession();
        String hql = "select new AcademyEntity(academyEntity.id) from AcademyEntity as academyEntity where academyEntity.academyName=:academyName";
        Query query = session.createQuery(hql);
        query.setString("academyName", name);
        AcademyEntity academyEntity = (AcademyEntity) query.uniqueResult();
        HibernateUtil.close();
        return academyEntity == null ? false : true;
    }

    /**
     * 判断学院是否已经存在
     */
    public boolean isAcademyExist(int id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new AcademyEntity(academyEntity.id) from AcademyEntity as academyEntity where academyEntity.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        AcademyEntity academyEntity = (AcademyEntity) query.uniqueResult();
        HibernateUtil.close();
        return academyEntity == null ? false : true;
    }

    /**
     * 获取学院名字
     */
    public String getAcademyName(int id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new AcademyEntity(academyEntity.academyName) from AcademyEntity as academyEntity where academyEntity.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        AcademyEntity academyEntity = (AcademyEntity) query.uniqueResult();
        HibernateUtil.close();
        if (academyEntity != null) {
            return academyEntity.getAcademyName();
        } else {
            return null;
        }
    }

    /**
     * 删除学院信息
     */
    @Transactional
    public int deleteAcademy(int id) {
        if (isAcademyExist(id)) {
            Session session = HibernateUtil.getSession();
            String hql2 = "delete from AcademyEntity as academy where academy.id=:id";
            String hql = "delete from ProfessionalEntity as pro where pro.academyId=:id";
            Query query = session.createQuery(hql);
            Query query2 = session.createQuery(hql2);
            query.setInteger("id", id);
            query2.setInteger("id", id);
            int i1 = query.executeUpdate();
            int i2 = query2.executeUpdate();
            HibernateUtil.close();
            if (i1 > 0 && i2 > 0) {
                return Constant.DELETE_SUCCESS;
            } else {
                return Constant.DELETE_FAILURE;
            }
        } else {
            return Constant.DELETE_FAILURE;
        }
    }

    /**
     * 删除学院信息
     */
    public int deleteAcademyWhitoutProfessional(int id) {
        if (!isAcademyExist(id)) return Constant.DELETE_FAILURE;
        Session session = HibernateUtil.getSession();
        session.delete(new AcademyEntity(id));
        HibernateUtil.commit();
        HibernateUtil.close();
        return Constant.DELETE_SUCCESS;
    }

    /**
     * 获取所有学院信息
     */
    public List<AcademyEntity> getAllAcademy(int pageNo, int pageSize) {
        Session session = HibernateUtil.getSession();
        String hql = "select new AcademyEntity(academyEntity.id,academyEntity.academyName) from AcademyEntity as academyEntity";
        Query query = session.createQuery(hql);
        if (pageNo == 0 && pageSize == 0) {
            List<AcademyEntity> list = query.list();
            HibernateUtil.close();
            return list;
        } else {
            List<AcademyEntity> list = query.setFirstResult((pageNo - 1) * pageSize)
                    .setMaxResults(pageSize).list();
            HibernateUtil.close();
            return list;
        }
    }

    /**
     * 修改学院信息
     */
    public int updateAcademyInfo(int id, String name) {
        if (!isAcademyExist(id)) return 0;
        if (isAcademyExist(name)) return Constant.HAS_EXIST;
        Session session = HibernateUtil.getSession();
        String hql = "update AcademyEntity as academyEntity set academyEntity.academyName=:name where academyEntity.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        query.setString("name", name);
        int i = query.executeUpdate();
        HibernateUtil.close();
        return i > 0 ? Constant.UPDATE_SUCCESS : Constant.UPDATE_FAILURE;
    }

    /**
     * 获取所有学院数量
     */
    public long getCount() {
        Session session = HibernateUtil.getSession();
        String hql = "select count(*) from AcademyEntity";
        Query query = session.createQuery(hql);
        long i = ((Long) query.uniqueResult()).intValue();
        HibernateUtil.close();
        return i;
    }
}
