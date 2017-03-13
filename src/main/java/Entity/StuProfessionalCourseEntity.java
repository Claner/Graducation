package Entity;

import javax.persistence.*;

/**
 * Created by Clanner on 2017/3/10.
 */
@Entity
@Table(name = "stu_professional_course", schema = "school")
public class StuProfessionalCourseEntity {
    private int id;
    private int stuId;
    private int arrangeId;

    public StuProfessionalCourseEntity() {
    }

    public StuProfessionalCourseEntity(int stuId, int arrangeId) {
        this.stuId = stuId;
        this.arrangeId = arrangeId;
    }

    public StuProfessionalCourseEntity(int arrangeId) {
        this.arrangeId = arrangeId;
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
    @Column(name = "stu_id")
    public int getStuId() {
        return stuId;
    }

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    @Basic
    @Column(name = "arrange_id")
    public int getArrangeId() {
        return arrangeId;
    }

    public void setArrangeId(int arrangeId) {
        this.arrangeId = arrangeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StuProfessionalCourseEntity that = (StuProfessionalCourseEntity) o;

        if (id != that.id) return false;
        if (stuId != that.stuId) return false;
        if (arrangeId != that.arrangeId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + stuId;
        result = 31 * result + arrangeId;
        return result;
    }
}
