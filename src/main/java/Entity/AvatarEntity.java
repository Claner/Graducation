package Entity;

import javax.persistence.*;

/**
 * Created by Clanner on 2017/4/9.
 */
@Entity
@Table(name = "avatar", schema = "school")
public class AvatarEntity {
    private int userId;
    private String avatar;

    public AvatarEntity() {
    }

    public AvatarEntity(int userId) {
        this.userId = userId;
    }

    public AvatarEntity(int userId, String avatar) {
        this.userId = userId;
        this.avatar = avatar;
    }

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "avatar")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AvatarEntity that = (AvatarEntity) o;

        if (userId != that.userId) return false;
        if (avatar != null ? !avatar.equals(that.avatar) : that.avatar != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        return result;
    }
}
