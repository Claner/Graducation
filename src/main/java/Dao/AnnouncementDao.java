package Dao;

import Entity.AnnouncementEntity;
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
public class AnnouncementDao {
    /**
     * 保存公告到数据库
     */
    public int saveAnnouncement(String title, String publish, String time, String content) {
        if (isAddAnnouncementByTitle(title)) {
            return Constant.SAVE_FAILURE;
        } else {
            Session session = HibernateUtil.getSession();
            session.save(new AnnouncementEntity(title, publish, time, content));
            HibernateUtil.commit();
            HibernateUtil.close();
            return Constant.SAVE_SUCCESS;
        }
    }

    public boolean isAddAnnouncementByTitle(String title) {
        Session session = HibernateUtil.getSession();
        String hql = "select new AnnouncementEntity(announcement.id) from AnnouncementEntity as announcement where announcement.title=:title";
        Query query = session.createQuery(hql);
        query.setString("title", title);
        AnnouncementEntity announcementEntity = (AnnouncementEntity) query.uniqueResult();
        return announcementEntity == null ? false : true;
    }

    public boolean isAddAnnouncementById(int id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new AnnouncementEntity(announcement.id) from AnnouncementEntity as announcement where announcement.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        AnnouncementEntity announcementEntity = (AnnouncementEntity) query.uniqueResult();
        return announcementEntity == null ? false : true;
    }

    /**
     * 删除保存公告
     */
    public int deleteAnnouncement(int id) {
        if (isAddAnnouncementById(id)) {
            Session session = HibernateUtil.getSession();
            session.delete(new AnnouncementEntity(id));
            HibernateUtil.commit();
            HibernateUtil.close();
            return Constant.DELETE_SUCCESS;
        } else {
            return Constant.DELETE_FAILURE;
        }
    }

    /**
     * 获取公告
     */
    public List<AnnouncementEntity> getAnnouncement(int pageNo, int pageSize) {
        Session session = HibernateUtil.getSession();
        String hql = "select new AnnouncementEntity(announcement.id,announcement.title,announcement.publish,announcement.time) from AnnouncementEntity as announcement";
        Query query = session.createQuery(hql);
        if (pageNo == 0 && pageSize == 0) {
            List<AnnouncementEntity> list = query.list();
            HibernateUtil.close();
            return list;
        } else {
            List<AnnouncementEntity> list = query.setFirstResult((pageNo - 1) * pageSize)
                    .setMaxResults(pageSize).list();
            HibernateUtil.close();
            return list;
        }
    }

    /**
     * 获取公告详情
     */
    public String getAnnouncementDetails(int id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new AnnouncementEntity(announcement.content) from AnnouncementEntity as announcement where announcement.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        AnnouncementEntity announcementEntity = (AnnouncementEntity) query.uniqueResult();
        HibernateUtil.close();
        if (announcementEntity != null) {
            return announcementEntity.getContent();
        }
        return null;
    }

    /**
     * 修改公示公告
     */
    public int updateAnnouncement(int id, String title, String publish, String time) {
        if (!isAddAnnouncementById(id)) return Constant.NOT_EXIST;
        if (isAddAnnouncementByTitle(title)) return Constant.HAS_EXIST;
        Session session = HibernateUtil.getSession();
        String hql = "update AnnouncementEntity as announcement set announcement.title=:title,announcement.publish=:publish,announcement.time=:time where announcement.id=:id";
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
     * 修改公示公告内容
     */
    public int updateAnnouncementDetails(int id, String content) {
        Session session = HibernateUtil.getSession();
        String hql = "update AnnouncementEntity as announcement set announcement.content=:content where announcement.id=:id";
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
    public long getCount() {
        Session session = HibernateUtil.getSession();
        String hql = "select count(*) from AnnouncementEntity";
        Query query = session.createQuery(hql);
        long i = ((Long) query.uniqueResult()).intValue();
        HibernateUtil.close();
        return i;
    }
}
