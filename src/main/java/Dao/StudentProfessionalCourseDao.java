package Dao;

import Entity.StuProfessionalCourseEntity;
import Util.Constant;
import Util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clanner on 2017/3/11.
 */
@Repository
public class StudentProfessionalCourseDao {
    /**
     * 获取学生所有上课时间
     */
    public List<StuProfessionalCourseEntity> getAllStuProfessionalCourse(int stu_id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new StuProfessionalCourseEntity(spce.arrangeId) from StuProfessionalCourseEntity as spce where spce.stuId=:stu_id";
        Query query = session.createQuery(hql);
        query.setInteger("stu_id", stu_id);
        List<StuProfessionalCourseEntity> list = query.list();
        HibernateUtil.close();
        return list;
    }

    /**
     * 学生选课
     */
    public int selectCourse(int stu_id, int arrange_id) {
        if (isSelectedCourse(stu_id, arrange_id)) {
            return Constant.SAVE_FAILURE;
        } else {
            Session session = HibernateUtil.getSession();
            session.save(new StuProfessionalCourseEntity(stu_id, arrange_id));
            HibernateUtil.commit();
            HibernateUtil.close();
            return Constant.SAVE_SUCCESS;
        }
    }

    /**
     * 判断学生是否已选该公选课
     */
    public boolean isSelectedCourse(int stu_id, int arrange_id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new StuProfessionalCourseEntity(spce.id,spce.arrangeId) from StuProfessionalCourseEntity as spce where spce.stuId=:stu_id and spce.arrangeId=:arrange_id";
        Query query = session.createQuery(hql);
        query.setInteger("stu_id", stu_id);
        query.setInteger("arrange_id", arrange_id);
        StuProfessionalCourseEntity stuProfessionalCourseEntity = (StuProfessionalCourseEntity) query.uniqueResult();
        HibernateUtil.close();
        return stuProfessionalCourseEntity == null ? false : true;
    }

    /**
     * 获取学生公选课课表
     */
    public List<Integer> getStuProfessionalCourse(int stu_id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new StuProfessionalCourseEntity(spce.arrangeId) from StuProfessionalCourseEntity as spce where spce.stuId=:stu_id";
        Query query = session.createQuery(hql);
        query.setInteger("stu_id", stu_id);
        List<StuProfessionalCourseEntity> list = query.list();
        HibernateUtil.close();
        if (list != null && list.size() > 0) {
            List<Integer> integerList = new ArrayList<Integer>();
            for (StuProfessionalCourseEntity s : list) {
                integerList.add(s.getArrangeId());
            }
            return integerList;
        }
        return null;
    }

    /**
     * 学生退课
     */
    public int deleteCourse(int stu_id, int arrange_id) {
        if (isSelectedCourse(stu_id, arrange_id)) {
            Session session = HibernateUtil.getSession();
            String hql = "delete StuProfessionalCourseEntity as spce where spce.stuId=:stu_id and spce.arrangeId=:arrange_id";
            Query query = session.createQuery(hql);
            query.setInteger("stu_id", stu_id);
            query.setInteger("arrange_id", arrange_id);
            int i = query.executeUpdate();
            HibernateUtil.close();
            return i > 0 ? Constant.DELETE_SUCCESS : Constant.DELETE_FAILURE;
        } else {
            return Constant.NOT_EXIST;
        }
    }


}
