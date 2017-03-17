package Dao;

import Entity.ProfessionalCourseEntity;
import Util.Constant;
import Util.HibernateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clanner on 2017/3/10.
 */
@Repository
public class ProfessionalCourseDao {
    /**
     * 管理员排课
     */
    public int saveProfessionalCourse(int grade_id, int professional_id, int course_id, String time) {
        if (professional_id == 0 && isArranged(grade_id, course_id, time)) return Constant.HAS_EXIST;
        if (!isMatch(time)) return Constant.PARAMS_NOT_MATCH;
        if (isConflict(grade_id, professional_id, time)) return Constant.COURSE_CONFLICT;
        Session session = HibernateUtil.getSession();
        session.save(new ProfessionalCourseEntity(grade_id, professional_id, course_id, time));
        HibernateUtil.commit();
        HibernateUtil.close();
        return Constant.SAVE_SUCCESS;
    }

    /**
     * 管理员批量录入排课信息
     */
    @Transactional
    public int saveMoreProfessionalCourse(int grade_id, int professional_id, String courses_id, String times) {
        JSONArray courseArray = JSON.parseArray(courses_id);
        JSONArray timeArray = JSON.parseArray(times);
        Integer[] cArr = new Integer[courseArray.size()];
        String[] tArr = new String[timeArray.size()];
//        String[] cArr = courses_id.trim().split(",");
//        String[] tArr = times.trim().split(",");
        if (cArr.length != tArr.length) return Constant.PARAMS_NOT_MATCH;
        int size = cArr.length;
        for (int i = 0; i < size; i++) {
            cArr[i] = (Integer) courseArray.get(i);
            tArr[i] = (String) timeArray.get(i);
        }
        for (int i = 0; i < size; i++) {
            if (!isMatch(tArr[i])) return Constant.PARAMS_NOT_MATCH;
            if (professional_id == 0 && isArranged(grade_id, cArr[i], tArr[i]))
                return Constant.HAS_EXIST;
            if (isConflict(grade_id, professional_id, tArr[i])) return Constant.COURSE_CONFLICT;
        }
        for (int i = 0; i < size; i++) {
            Session session = HibernateUtil.getSession();
            session.save(new ProfessionalCourseEntity(grade_id, professional_id, cArr[i], tArr[i]));
            HibernateUtil.commit();
            HibernateUtil.close();
        }
        return Constant.SAVE_SUCCESS;
    }


    /**
     * 判断时间参数是否合法
     */
    public boolean isMatch(String time) {
//        String[] strings = time.split(",");
//        for (String s : strings) {
        System.out.println(time);
        String[] s1 = time.trim().split("-");
        System.out.println(s1[0]);
        System.out.println(s1[1]);
        Integer week = Integer.parseInt(s1[0]);
        Integer when = Integer.parseInt(s1[1].trim());
        if (week > 7 || week < 1
                || when < 1 || when > 5) {
            return false;
        }
//        }
        return true;
    }

    /**
     * 获取所有排课信息
     */
    public List<ProfessionalCourseEntity> getAllProfessionalCourse(int pageNo, int pageSize) {
        Session session = HibernateUtil.getSession();
        String hql = "select new ProfessionalCourseEntity(pce.id,pce.gradeId,pce.professionalId,pce.courseId,pce.time) from ProfessionalCourseEntity as pce";
        Query query = session.createQuery(hql);
        if (pageNo == 0 && pageSize == 0) {
            List<ProfessionalCourseEntity> list = query.list();
            HibernateUtil.close();
            return list;
        } else {
            List<ProfessionalCourseEntity> list = query.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
            HibernateUtil.close();
            return list;
        }
    }

    /**
     * 获取指定年级和专业的排课信息
     */
    public List<ProfessionalCourseEntity> getTargetProfessionalCourse(int grade_id, int professional_id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new ProfessionalCourseEntity(pce.id,pce.gradeId,pce.professionalId,pce.courseId,pce.time) from ProfessionalCourseEntity as pce where pce.gradeId=:grade_id and pce.professionalId=:professional_id";
        Query query = session.createQuery(hql);
        query.setInteger("grade_id", grade_id);
        query.setInteger("professional_id", professional_id);
        List<ProfessionalCourseEntity> list = query.list();
        HibernateUtil.close();
        return list;
    }

    /**
     * 获取所有课程信息的数量
     */
    public long getCount() {
        Session session = HibernateUtil.getSession();
        String hql = "select count(*) from ProfessionalCourseEntity";
        Query query = session.createQuery(hql);
        long i = ((Long) query.uniqueResult()).intValue();
        HibernateUtil.close();
        return i;
    }

    /**
     * 修改排课信息
     */
    public int updateProfessionalCourse(int id, int grade_id, int professional_id, int course_id, String time) {
        if (!isMatch(time)) return Constant.PARAMS_NOT_MATCH;
        if (!isExist(id)) return Constant.NOT_EXIST;
        if (professional_id == 0 && isArranged(grade_id, course_id, time)) return Constant.HAS_EXIST;
        if (isConflict(grade_id, professional_id, time)) return Constant.COURSE_CONFLICT;
        Session session = HibernateUtil.getSession();
        String hql = "update ProfessionalCourseEntity as pce set pce.gradeId=:grade_id,pce.professionalId=:professional_id,pce.courseId=:course_id,pce.time=:time where pce.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        query.setInteger("grade_id", grade_id);
        query.setInteger("professional_id", professional_id);
        query.setInteger("course_id", course_id);
        query.setString("time", time);
        int i = query.executeUpdate();
        HibernateUtil.close();
        return i > 0 ? Constant.UPDATE_SUCCESS : Constant.UPDATE_FAILURE;
    }

    /**
     * 删除排课信息
     */
    public int deleteProfessionalCourse(int id) {
        if (!isExist(id)) return Constant.DELETE_FAILURE;
        Session session = HibernateUtil.getSession();
        session.delete(new ProfessionalCourseEntity(id));
        HibernateUtil.commit();
        HibernateUtil.close();
        return Constant.DELETE_SUCCESS;
    }

    /**
     * 判断排课信息是否存在
     */
    public boolean isExist(int id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new ProfessionalCourseEntity(pce.id) from ProfessionalCourseEntity as pce where pce.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        ProfessionalCourseEntity professionalCourseEntity = (ProfessionalCourseEntity) query.uniqueResult();
        HibernateUtil.close();
        return professionalCourseEntity == null ? false : true;
    }

    /**
     * 判断该年级该专业的课程时间是否冲突
     */
    public boolean isConflict(int grade_id, int professional_id, String time) {
        if (professional_id == 0) return false;
        List<String> list = getAllTime(grade_id, professional_id);
        if (list == null || list.size() == 0) return false;
        for (String s : list) {
            if (s.equals(time)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取该年级该专业上课的所有时间
     */
    public List<String> getAllTime(int grade_id, int professional_id) {
        System.out.println("");
        Session session = HibernateUtil.getSession();
        String hql = "select new ProfessionalCourseEntity(pce.time) from ProfessionalCourseEntity as pce where pce.gradeId=:grade_id and pce.professionalId=:professional_id";
        Query query = session.createQuery(hql);
        query.setInteger("grade_id", grade_id);
        query.setInteger("professional_id", professional_id);
        List<ProfessionalCourseEntity> list = query.list();
        HibernateUtil.close();
        if (list != null && list.size() > 0) {
            List<String> timeList = new ArrayList<String>();
            for (ProfessionalCourseEntity professionalCourseEntity : list) {
                timeList.add(professionalCourseEntity.getTime());
            }
            return timeList;
        } else {
            return null;
        }
    }

    /**
     * 获取目标年级的专业课表
     */
    public List<ProfessionalCourseEntity> getTargetCourse(int grade_id, int professional_id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new ProfessionalCourseEntity(pce.id,pce.gradeId,pce.professionalId,pce.courseId,pce.time) from ProfessionalCourseEntity as pce where pce.gradeId=:grade_id and pce.professionalId=:professional_id";
        Query query = session.createQuery(hql);
        query.setInteger("grade_id", grade_id);
        query.setInteger("professional_id", professional_id);
        List<ProfessionalCourseEntity> list = query.list();
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    /**
     * 判断课程是否已经存在
     */
    public boolean isArranged(int grade_id, int course_id, String time) {
        Session session = HibernateUtil.getSession();
        String hql = "select new ProfessionalCourseEntity(pce.id) from ProfessionalCourseEntity as pce where pce.gradeId=:grade_id and pce.courseId=:course_id and pce.time=:time";
        Query query = session.createQuery(hql);
        query.setInteger("grade_id", grade_id);
        query.setInteger("course_id", course_id);
        query.setString("time", time);
        ProfessionalCourseEntity professionalCourseEntity = (ProfessionalCourseEntity) query.uniqueResult();
        HibernateUtil.close();
        return professionalCourseEntity == null ? false : true;
    }

    /**
     * 根据id和grade_id获取时间
     */
    public String getTimeById(int id, int grade_id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new ProfessionalCourseEntity(pce.time) from ProfessionalCourseEntity pce where pce.id=:id and pce.gradeId=:grade_id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        query.setInteger("grade_id", grade_id);
        ProfessionalCourseEntity professionalCourseEntity = (ProfessionalCourseEntity) query.uniqueResult();
        HibernateUtil.close();
        if (professionalCourseEntity != null) {
            return professionalCourseEntity.getTime();
        }
        return null;
    }

    /**
     * 学生获取选课课表
     */
    public List<ProfessionalCourseEntity> getSelectableCourse(int grade_id, List<String> timeList) {
        System.out.println("getSelectableCourse");
        Session session = HibernateUtil.getSession();
        String hql = "select new ProfessionalCourseEntity(pce.id,pce.gradeId,pce.professionalId,pce.courseId,pce.time) from ProfessionalCourseEntity as pce where pce.gradeId=:grade_id and pce.professionalId=:professional_id";
        Query query = session.createQuery(hql);
        query.setInteger("grade_id", grade_id);
        query.setInteger("professional_id", 0);
        List<ProfessionalCourseEntity> list = query.list();
        if (list != null && list.size() > 0) {

            for (ProfessionalCourseEntity professionalCourseEntity : list) {
                System.out.println(professionalCourseEntity.getTime());
            }

            for (int i = 0; i < list.size(); i++) {
                for (String s : timeList) {
                    if (list.get(i).getTime().equals(s)) {
                        list.remove(i);
                        i--;
                        break;
                    }
                }
            }
            return list;
        } else {
            System.out.println("没有可选课程");
        }
        return null;
    }

    /**
     * 判断是否是公选课
     */
    public boolean isPublic(int id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new ProfessionalCourseEntity(pce.id,pce.professionalId) from ProfessionalCourseEntity as pce where pce.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        ProfessionalCourseEntity professionalCourseEntity = (ProfessionalCourseEntity) query.uniqueResult();
        HibernateUtil.close();
        if (professionalCourseEntity.getProfessionalId() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据id获取排课信息
     */
    public ProfessionalCourseEntity getProfessionalCourseById(int id, int grade_id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new ProfessionalCourseEntity(pce.id,pce.gradeId,pce.professionalId,pce.courseId,pce.time) from ProfessionalCourseEntity as pce where pce.id=:id and pce.gradeId=:grade_id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        query.setInteger("grade_id", grade_id);
        ProfessionalCourseEntity professionalCourseEntity = (ProfessionalCourseEntity) query.uniqueResult();
        HibernateUtil.close();
        return professionalCourseEntity;
    }
}
