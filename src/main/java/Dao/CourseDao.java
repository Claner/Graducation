package Dao;

import Entity.CourseEntity;
import Util.Constant;
import Util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Clanner on 2017/3/7.
 */
@Repository
public class CourseDao {

    /**
     * 添加课程
     */
    public int saveCourse(String courseName, int courseCredit, int courseHour) {
        if (isCourseExist(courseName)) return 0;
        Session session = HibernateUtil.getSession();
        CourseEntity courseEntity = new CourseEntity(courseName, courseCredit, courseHour);
        session.save(courseEntity);
        HibernateUtil.commit();
        int i = courseEntity.getId();
        HibernateUtil.close();
        return i == 0 ? Constant.SAVE_FAILURE : Constant.SAVE_SUCCESS;
    }

    /**
     * 判断是否添加课程
     */
    public boolean isCourseExist(String courseName) {
        Session session = HibernateUtil.getSession();
        String hql = "select new CourseEntity(courseEntity.id) from CourseEntity as courseEntity where courseEntity.courseName=:courseName";
        Query query = session.createQuery(hql);
        query.setString("courseName", courseName);
        CourseEntity courseEntity = (CourseEntity) query.uniqueResult();
        HibernateUtil.close();
        return courseEntity == null ? false : true;
    }

    /**
     * 判断是否添加课程(通过id)
     */
    public boolean isCourseExist(int id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new CourseEntity(courseEntity.id) from CourseEntity as courseEntity where courseEntity.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        CourseEntity courseEntity = (CourseEntity) query.uniqueResult();
        HibernateUtil.close();
        return courseEntity == null ? false : true;
    }

    /**
     * 删除课程
     */
    public int deleteCourse(int id) {
        if (isCourseExist(id)) {
            Session session = HibernateUtil.getSession();
            session.delete(new CourseEntity(id));
            HibernateUtil.commit();
            HibernateUtil.close();
            return Constant.DELETE_SUCCESS;
        } else {
            return Constant.DELETE_FAILURE;
        }
    }

    /**
     * 获取所有课程
     */
    public List<CourseEntity> getAllCourse(int pageNo, int pageSize) {
        Session session = HibernateUtil.getSession();
        String hql = "select new CourseEntity(courseEntity.id,courseEntity.courseName,courseEntity.courseCredit,courseEntity.courseHour) from CourseEntity as courseEntity";
        Query query = session.createQuery(hql);
        List<CourseEntity> list = query.setFirstResult((pageNo - 1) * pageSize)
                .setMaxResults(pageSize).list();
        HibernateUtil.close();
        return list;
    }

    /**
     * 修改课程信息
     */
    public int updateCourseInfo(int id, String courseName, int courseCredit, int courseHour) {
        if (!isCourseExist(id)) return 0;
        if (isCourseExist(courseName)) return Constant.HAS_EXIST;
        Session session = HibernateUtil.getSession();
        String hql = "update CourseEntity as courseEntity set courseEntity.courseName=:courseName,courseEntity.courseCredit=:courseCredit,courseEntity.courseHour=:courseHour where courseEntity.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        query.setString("courseName", courseName);
        query.setInteger("courseCredit", courseCredit);
        query.setInteger("courseHour", courseHour);
        int i = query.executeUpdate();
        HibernateUtil.close();
        return i > 0 ? Constant.UPDATE_SUCCESS : Constant.UPDATE_FAILURE;
    }

    /**
     * 获取课程名称
     */
    public String getCourseName(int id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new CourseEntity(courseEntity.courseName) from CourseEntity as courseEntity where courseEntity.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        CourseEntity courseEntity = (CourseEntity) query.uniqueResult();
        HibernateUtil.close();
        if (courseEntity != null) {
            return courseEntity.getCourseName();
        } else {
            return null;
        }
    }

    /**
     * 获取所有课程数量
     * @return
     */
    public long getCount() {
        Session session = HibernateUtil.getSession();
        String hql = "select count(*) from CourseEntity";
        Query query = session.createQuery(hql);
        long i = ((Long) query.uniqueResult()).intValue();
        HibernateUtil.close();
        return i;
    }
}
