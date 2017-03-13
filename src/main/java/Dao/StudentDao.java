package Dao;

import Entity.StudentEntity;
import Util.Constant;
import Util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Clanner on 2017/3/7.
 */
@Repository
public class StudentDao {

    /**
     * 保存学生信息
     */
    public boolean saveStudent(int stu_id, int academy_id, int professional_id, int grade, String name,
                               String sex, String phone, String address) {
        Session session = HibernateUtil.getSession();
        session.save(new StudentEntity(stu_id, academy_id, professional_id, grade, name, sex, phone, address));
        HibernateUtil.commit();
        HibernateUtil.close();
        return true;
    }

    /**
     * 查询学生信息
     */
    public StudentEntity selectStudent(int stu_id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new StudentEntity(studentEntity.stuId,studentEntity.academyId,studentEntity.professionalId,studentEntity.gradeId,studentEntity.name,studentEntity.sex,studentEntity.phone,studentEntity.address) from StudentEntity as studentEntity where studentEntity.stuId=:stu_id";
        Query query = session.createQuery(hql);
        query.setInteger("stu_id", stu_id);
        StudentEntity studentEntity = (StudentEntity) query.uniqueResult();
        HibernateUtil.close();
        return studentEntity;
    }

    /**
     * 修改学生信息
     */
    public int updateStudent(int stu_id, String name, String sex, String phone, String address) {
        Session session = HibernateUtil.getSession();
        String hql = "update StudentEntity as studentEntity set studentEntity.name=:name,studentEntity.sex=:sex,studentEntity.phone=:phone,studentEntity.address=:address where studentEntity.stuId=:stu_id";
        Query query = session.createQuery(hql);
        query.setInteger("stu_id", stu_id);
        query.setString("name", name);
        query.setString("sex", sex);
        query.setString("phone", phone);
        query.setString("address", address);
        int i = query.executeUpdate();
        HibernateUtil.close();
        return i > 0 ? Constant.UPDATE_SUCCESS : Constant.UPDATE_FAILURE;
    }

    /**
     * 判断学生是否存在
     */
    public boolean isStudentExist(int stu_id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new StudentEntity(studentEntity.stuId) from StudentEntity as studentEntity where studentEntity.stuId=:stu_id";
        Query query = session.createQuery(hql);
        query.setInteger("stu_id", stu_id);
        StudentEntity studentEntity = (StudentEntity) query.uniqueResult();
        if (studentEntity != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 修改学生专业，学院，和年级
     */
    public int updateStudentAcademyOrProfessionalOrGrade(int stu_id, int academy_id, int professional_id, int grade) {
        Session session = HibernateUtil.getSession();
        String hql = "update StudentEntity as studentEntity set studentEntity.academyId=:academy_id,studentEntity.professionalId=:professional_id,studentEntity.gradeId=:grade where studentEntity.stuId=:stu_id";
        Query query = session.createQuery(hql);
        query.setInteger("stu_id", stu_id);
        query.setInteger("academy_id", academy_id);
        query.setInteger("professional_id", professional_id);
        query.setInteger("grade", grade);
        int i = query.executeUpdate();
        HibernateUtil.close();
        return i > 0 ? Constant.UPDATE_SUCCESS : Constant.UPDATE_FAILURE;
    }

    /**
     * 获取学生信息表中的数量
     */
    public long getCount() {
        Session session = HibernateUtil.getSession();
        String hql = "select count(*) from StudentEntity as studentEntity";
        Query query = session.createQuery(hql);
        long i = ((Long) query.uniqueResult()).intValue();
        HibernateUtil.close();
        return i;
    }

    /**
     * 获取所有学生信息
     */
    public List<StudentEntity> getAllStudentInfo(int pageNo, int pageSize) {
        Session session = HibernateUtil.getSession();
        String hql = "select new StudentEntity(studentEntity.stuId,studentEntity.academyId,studentEntity.professionalId,studentEntity.gradeId,studentEntity.name,studentEntity.sex,studentEntity.phone,studentEntity.address) from StudentEntity  as studentEntity";
        Query query = session.createQuery(hql);

        if (pageNo == 0 && pageSize == 0) {
            List<StudentEntity> list = query.list();
            HibernateUtil.close();
            return list;
        } else {
            List<StudentEntity> list = query.setFirstResult((pageNo - 1) * pageSize)
                    .setMaxResults(pageSize).list();
            HibernateUtil.close();
            return list;
        }
    }

    /**
     * 查成绩
     */
    public int loginSystem(String account,String password,String code){
        try {
            Document document = Jsoup.connect("http://jwc.wyu.edu.cn/student/logon.asp")
                    .timeout(3000)
                    .userAgent("test")
                    .cookie("auth", "token")
                    .data("UserCode", account)
                    .data("UserPwd", password)
                    .data("Validate", code)
                    .post();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
