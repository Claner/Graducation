package Dao;

import Entity.NoticeEntity;
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
public class NoticeDao {

    /**
     * 保存校内通知
     */
    public int saveNotice(String title, String publish, String time, String content) {
        if (isAddNoticeByTitle(title)) {
            return Constant.SAVE_FAILURE;
        } else {
            Session session = HibernateUtil.getSession();
            session.save(new NoticeEntity(title, publish, time, content));
            HibernateUtil.commit();
            HibernateUtil.close();
            return Constant.SAVE_SUCCESS;
        }
    }

    /**
     * 判断是否添加过该校内通知
     */
    public boolean isAddNoticeByTitle(String title) {
        Session session = HibernateUtil.getSession();
        String hql = "select new NoticeEntity(noticeEntity.id) from NoticeEntity as noticeEntity where noticeEntity.title=:title";
        Query query = session.createQuery(hql);
        query.setString("title", title);
        NoticeEntity noticeEntity = (NoticeEntity) query.uniqueResult();
        HibernateUtil.close();
        return noticeEntity == null ? false : true;
    }

    public boolean isAddNoticeById(int id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new NoticeEntity(noticeEntity.id) from NoticeEntity as noticeEntity where noticeEntity.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        NoticeEntity noticeEntity = (NoticeEntity) query.uniqueResult();
        HibernateUtil.close();
        return noticeEntity == null ? false : true;
    }

    public int deleteNotice(int id) {
        if (isAddNoticeById(id)) {
            Session session = HibernateUtil.getSession();
            session.delete(new NoticeEntity(id));
            HibernateUtil.commit();
            HibernateUtil.close();
            return Constant.DELETE_SUCCESS;
        } else {
            return Constant.DELETE_FAILURE;
        }
    }

    public List<NoticeEntity> getNotice(int pageNo, int pageSize) {
        Session session = HibernateUtil.getSession();
        String hql = "select new NoticeEntity(notice.id,notice.title,notice.publish,notice.time) from NoticeEntity as notice";
        Query query = session.createQuery(hql);
        if (pageNo == 0 && pageSize == 0) {
            List<NoticeEntity> list = query.list();
            HibernateUtil.close();
            return list;
        } else {
            List<NoticeEntity> list = query.setFirstResult((pageNo - 1) * pageSize)
                    .setMaxResults(pageSize).list();
            HibernateUtil.close();
            return list;
        }
    }

    /**
     * 详情
     */
    public String getNoticeDetails(int id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new NoticeEntity(notice.content) from NoticeEntity as notice where notice.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id", id);
        NoticeEntity noticeEntity = (NoticeEntity) query.uniqueResult();
        HibernateUtil.close();
        if (noticeEntity != null) {
            return noticeEntity.getContent();
        }
        return null;
    }

    /**
     * 修改校内通知
     */
    public int updateNotice(int id, String title, String publish, String time) {
        if (!isAddNoticeById(id)) return Constant.NOT_EXIST;
        if (isAddNoticeByTitle(title)) return Constant.HAS_EXIST;
        Session session = HibernateUtil.getSession();
        String hql = "update NoticeEntity as notice set notice.title=:title, notice.publish=:publish, notice.time=:time where notice.id=:id";
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
     * 修改校内通知内容
     */
    public int updateNoticeDetails(int id,String content){
        Session session = HibernateUtil.getSession();
        String hql = "update NoticeEntity as notice set notice.content=:content where notice.id=:id";
        Query query = session.createQuery(hql);
        query.setInteger("id",id);
        query.setString("content",content);
        int i = query.executeUpdate();
        HibernateUtil.close();
        return i > 0 ? Constant.UPDATE_SUCCESS : Constant.UPDATE_FAILURE;
    }
}
