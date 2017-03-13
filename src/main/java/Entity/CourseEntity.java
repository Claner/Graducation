package Entity;

import javax.persistence.*;

/**
 * Created by Clanner on 2017/3/7.
 */
@Entity
@Table(name = "course", schema = "school")
public class CourseEntity {
    private int id;
    private String courseName;
    private int courseCredit;
    private int courseHour;

    public CourseEntity() {
    }

    public CourseEntity(String courseName) {
        this.courseName = courseName;
    }

    public CourseEntity(String courseName, int courseCredit, int courseHour) {
        this.courseName = courseName;
        this.courseCredit = courseCredit;
        this.courseHour = courseHour;
    }

    public CourseEntity(int id, String courseName, int courseCredit, int courseHour) {
        this.id = id;
        this.courseName = courseName;
        this.courseCredit = courseCredit;
        this.courseHour = courseHour;
    }

    public CourseEntity(int id) {
        this.id = id;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "course_name")
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Basic
    @Column(name = "course_credit")
    public int getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(int courseCredit) {
        this.courseCredit = courseCredit;
    }

    @Basic
    @Column(name = "course_hour")
    public int getCourseHour() {
        return courseHour;
    }

    public void setCourseHour(int courseGrade) {
        this.courseHour = courseGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseEntity that = (CourseEntity) o;

        if (id != that.id) return false;
        if (courseCredit != that.courseCredit) return false;
        if (courseHour != that.courseHour) return false;
        if (courseName != null ? !courseName.equals(that.courseName) : that.courseName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (courseName != null ? courseName.hashCode() : 0);
        result = 31 * result + courseCredit;
        result = 31 * result + courseHour;
        return result;
    }
}
