package Dao;

import Entity.AvatarEntity;
import Util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Clanner on 2017/4/9.
 */
@Repository
public class AvatarDao {

    public boolean saveAvatar(int user_id, MultipartFile file) {
        String[] s = file.getOriginalFilename().split("\\.");
        String path = s[0];
        if (isSave(user_id)) {
            //更新
            Session session = HibernateUtil.getSession();
            String hql = "update AvatarEntity as avatar set avatar.avatar=:path where avatar.userId=:user_id";
            Query query = session.createQuery(hql);
            query.setInteger("user_id", user_id);
            query.setString("path", user_id + "-" + path);
            int i = query.executeUpdate();
            HibernateUtil.close();
            return i > 0 ? true : false;
        } else {
            //保存
            Session session = HibernateUtil.getSession();
            session.save(new AvatarEntity(user_id, path));
            HibernateUtil.commit();
            HibernateUtil.close();
            return true;
        }
    }

    public boolean isSave(int user_id) {
        Session session = HibernateUtil.getSession();
        String hql = "select new AvatarEntity(avatar.userId) from AvatarEntity as avatar where avatar.userId=:user_id";
        Query query = session.createQuery(hql);
        query.setInteger("user_id", user_id);
        AvatarEntity avatarEntity = (AvatarEntity) query.uniqueResult();
        HibernateUtil.close();
        return avatarEntity == null ? false : true;
    }
}
