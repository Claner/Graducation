package Dao;

import Entity.BriefEntity;
import Util.Constant;
import Util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Clanner on 2017/3/19.
 */
@Repository
public class BriefDao {
    /**
     * 保存校内简讯到数据库
     */
    public int saveBrief(String title, String publish, String time, String content) {
        if (isAddBriefByTitle(title)) {
            return Constant.SAVE_FAILURE;
        } else {
            Session session = HibernateUtil.getSession();
            session.save(new BriefEntity(title, publish, time, content));
            HibernateUtil.commit();
            HibernateUtil.close();
            return Constant.SAVE_SUCCESS;
        }
    }

    /**
     * 判断是否已添加
     */
    public boolean isAddBriefByTitle(String title) {
        Session session = HibernateUtil.getSession();
        String hql = "select new BriefEntity(briefEntity.id) from BriefEntity as briefEntity where briefEntity.title=:title";
        Query query = session.createQuery(hql);
        query.setString("title", title);
        BriefEntity briefEntity = (BriefEntity) query.uniqueResult();
        HibernateUtil.close();
        return briefEntity == null ? false : true;
    }

    public boolean isAddBriefById(int id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new BriefEntity(briefEntity.id) from BriefEntity as briefEntity where briefEntity.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        BriefEntity briefEntity = (BriefEntity) query.uniqueResult();
        HibernateUtil.close();
        return briefEntity == null ? false : true;
    }

    /**
     * 删除校内简讯到数据库
     */
    public int deleteBrief(int id) {
        if (isAddBriefById(id)) {
            Session session = HibernateUtil.getSession();
            session.delete(new BriefEntity(id));
            HibernateUtil.commit();
            HibernateUtil.close();
            return Constant.DELETE_SUCCESS;
        } else {
            return Constant.DELETE_FAILURE;
        }
    }

    /**
     * 获取校内简讯
     */
    public List<BriefEntity> getBrief(int pageNo, int pageSize) {
        Session session = HibernateUtil.getSession();
        String hql = "select new BriefEntity(brief.id,brief.title,brief.publish,brief.time) from BriefEntity as brief";
        Query query = session.createQuery(hql);
        if (pageNo == 0 && pageSize == 0) {
            List<BriefEntity> list = query.list();
            HibernateUtil.close();
            return list;
        }
        List<BriefEntity> list = query.setFirstResult((pageNo - 1) * pageSize)
                .setMaxResults(pageSize).list();
        HibernateUtil.close();
        return list;
    }

    /**
     * 获取校内简讯详情
     */
    public String getBriefDetails(int id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new BriefEntity(brief.content) from BriefEntity as brief where brief.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        BriefEntity briefEntity = (BriefEntity) query.uniqueResult();
        HibernateUtil.close();
        if (briefEntity != null) {
            return briefEntity.getContent();
        }
        return null;
    }

    /**
     * 修改校内简讯
     */
    public int updateBrief(int id, String title, String publish, String time) {
        if (!isAddBriefById(id)) return Constant.NOT_EXIST;
        if (isAddBriefByTitle(title)) return Constant.HAS_EXIST;
        Session session = HibernateUtil.getSession();
        String hql = "update BriefEntity as brief set brief.title=:title,brief.publish=:publish,brief.time=:time where brief.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        query.setString("title", title);
        query.setString("publish", publish);
        query.setString("time", time);
        int i = query.executeUpdate();
        HibernateUtil.close();
        return i > 0 ? Constant.UPDATE_SUCCESS : Constant.UPDATE_FAILURE;
    }

    /**
     * 修改校内简讯内容
     */
    public int updateBriefDetails(int id, String content) {
        Session session = HibernateUtil.getSession();
        String hql = "update BriefEntity as brief set brief.content=:content where brief.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        query.setString("content", content);
        int i = query.executeUpdate();
        HibernateUtil.close();
        return i > 0 ? Constant.UPDATE_SUCCESS : Constant.UPDATE_FAILURE;
    }

    /**
     * 获取数量
     */
    public long getCount(){
        Session session = HibernateUtil.getSession();
        String hql = "select count(*) from BriefEntity";
        Query query = session.createQuery(hql);
        long i = ((Long) query.uniqueResult()).intValue();
        HibernateUtil.close();
        return i;
    }
}
