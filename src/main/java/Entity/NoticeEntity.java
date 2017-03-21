package Entity;

import javax.persistence.*;

/**
 * Created by Clanner on 2017/3/19.
 */
@Entity
@Table(name = "notice", schema = "school")
public class NoticeEntity {
    private int id;
    private String title;
    private String publish;
    private String time;
    private String content;

    public NoticeEntity() {
    }

    public NoticeEntity(int id) {
        this.id = id;
    }

    public NoticeEntity(String content) {
        this.content = content;
    }

    public NoticeEntity(String title, String publish, String time, String content) {
        this.title = title;
        this.publish = publish;
        this.time = time;
        this.content = content;
    }

    public NoticeEntity(int id, String title, String publish, String time) {
        this.id = id;
        this.title = title;
        this.publish = publish;
        this.time = time;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "publish")
    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    @Basic
    @Column(name = "time")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoticeEntity that = (NoticeEntity) o;

        if (id != that.id) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (publish != null ? !publish.equals(that.publish) : that.publish != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (publish != null ? publish.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
