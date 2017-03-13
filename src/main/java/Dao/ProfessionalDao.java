package Dao;

import Entity.Professional;
import Entity.ProfessionalEntity;
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
public class ProfessionalDao {
    /**
     * 录入专业信息
     */
    public int saveProfessional(int academy_id, String name, String year) {
        if (isProfessionalExist(name)) return 0;
        Session session = HibernateUtil.getSession();
        ProfessionalEntity professionalEntity = new ProfessionalEntity(academy_id, name, year);
        session.save(professionalEntity);
        HibernateUtil.commit();
        if (professionalEntity.getId() != 0) {
            return Constant.SAVE_SUCCESS;
        } else {
            return Constant.SAVE_FAILURE;
        }
    }

    /**
     * 判断专业是否存在
     */
    public boolean isProfessionalExist(String name) {
        Session session = HibernateUtil.getSession();
        String hql = "select new ProfessionalEntity(professionalEntity.id) from ProfessionalEntity as professionalEntity where professionalEntity.proName=:name";
        Query query = session.createQuery(hql);
        query.setString("name", name);
        ProfessionalEntity professionalEntity = (ProfessionalEntity) query.uniqueResult();
        HibernateUtil.close();
        return professionalEntity == null ? false : true;
    }

    /**
     * 判断专业是否存在(用过id)
     */
    public boolean isProfessionalExist(int id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new ProfessionalEntity(professionalEntity.id) from ProfessionalEntity as professionalEntity where professionalEntity.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        ProfessionalEntity professionalEntity = (ProfessionalEntity) query.uniqueResult();
        HibernateUtil.close();
        return professionalEntity == null ? false : true;
    }

    /**
     * 获取专业名字
     */
    public String getProfessionalName(int id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new ProfessionalEntity(professionalEntity.proName) from ProfessionalEntity as professionalEntity where professionalEntity.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        ProfessionalEntity professionalEntity = (ProfessionalEntity) query.uniqueResult();
        if (professionalEntity != null) {
            return professionalEntity.getProName();
        } else {
            return null;
        }
    }

    /**
     * 删除专业
     */
    public int deleteProfessional(int id) {
        if (isProfessionalExist(id)) {
            Session session = HibernateUtil.getSession();
            session.delete(new ProfessionalEntity(id));
            HibernateUtil.commit();
            HibernateUtil.close();
            return Constant.DELETE_SUCCESS;
        } else {
            return Constant.DELETE_FAILURE;
        }
    }

    /**
     * 获取所有专业
     */
    public List<ProfessionalEntity> getAllProfessional(int pageNo, int pageSize) {
        Session session = HibernateUtil.getSession();
        String hql = "select new ProfessionalEntity(professional.id,professional.academyId,professional.proName,professional.year) from ProfessionalEntity as professional";
        Query query = session.createQuery(hql);
        List<ProfessionalEntity> list = query.setFirstResult((pageNo - 1) * pageSize)
                .setMaxResults(pageSize).list();
        HibernateUtil.close();
        return list;
    }

    /**
     * 获取学院相应专业
     */
    public List<ProfessionalEntity> getTargetProfessional(int academy_id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new ProfessionalEntity(professional.id,professional.academyId,professional.proName,professional.year) from ProfessionalEntity as professional where professional.academyId=:academy_id";
        Query query = session.createQuery(hql);
        query.setInteger("academy_id", academy_id);
        List<ProfessionalEntity> list = query.list();
        HibernateUtil.close();
        return list;
    }

    /**
     * 修改学院信息
     */
    public int updateProfessionalInfo(int id, int academy_id, String pro_name, String year) {
        if (!isProfessionalExist(id)) return 0;
        if (isProfessionalExist(pro_name)) return Constant.HAS_EXIST;
        Session session = HibernateUtil.getSession();
        String hql = "update ProfessionalEntity as professionalEntity set professionalEntity.academyId=:academyId,professionalEntity.proName=:proName,professionalEntity.year=:year where professionalEntity.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        query.setInteger("academyId", academy_id);
        query.setString("proName", pro_name);
        query.setString("year", year);
        int i = query.executeUpdate();
        HibernateUtil.close();
        return i > 0 ? Constant.UPDATE_SUCCESS : Constant.UPDATE_FAILURE;
    }

    /**
     * 获取所有专业数量
     *
     * @return
     */
    public long getCount() {
        Session session = HibernateUtil.getSession();
        String hql = "select count(*) from ProfessionalEntity ";
        Query query = session.createQuery(hql);
        long i = ((Long) query.uniqueResult()).intValue();
        HibernateUtil.close();
        return i;
    }

    /**
     * 判断该学院是否有专业
     */
    public boolean isHasProfessional(int academy_id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new ProfessionalEntity(professionalEntity.id) from ProfessionalEntity as professionalEntity where professionalEntity.academyId=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", academy_id);
        List<ProfessionalEntity> list = query.list();
        HibernateUtil.close();
        if (list != null && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
