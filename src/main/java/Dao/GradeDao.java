package Dao;

import Entity.GradeEntity;
import Util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * Created by Clanner on 2017/3/11.
 */
@Repository
public class GradeDao {
    /**
     * 获取年级
     */
    public String getGrade(int id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new GradeEntity(gradeEntity.gradeName) from GradeEntity as gradeEntity where gradeEntity.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        GradeEntity gradeEntity = (GradeEntity) query.uniqueResult();
        HibernateUtil.close();
        if (gradeEntity == null) return null;
        return gradeEntity.getGradeName();
    }
}
