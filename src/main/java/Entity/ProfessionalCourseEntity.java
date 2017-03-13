package Entity;

import javax.persistence.*;

/**
 * Created by Clanner on 2017/3/10.
 */
@Entity
@Table(name = "professional_course", schema = "school")
public class ProfessionalCourseEntity {
    private int id;
    private int gradeId;
    private int professionalId;
    private int courseId;
    private String time;

    public ProfessionalCourseEntity() {
    }

    public ProfessionalCourseEntity(String time) {
        this.time = time;
    }

    public ProfessionalCourseEntity(int id) {
        this.id = id;
    }

    public ProfessionalCourseEntity(int id, int professionalId) {
        this.id = id;
        this.professionalId = professionalId;
    }

    public ProfessionalCourseEntity(int gradeId, int professionalId, int courseId, String time) {
        this.gradeId = gradeId;
        this.professionalId = professionalId;
        this.courseId = courseId;
        this.time = time;
    }

    public ProfessionalCourseEntity(int id, int gradeId, int professionalId, int courseId, String time) {
        this.id = id;
        this.gradeId = gradeId;
        this.professionalId = professionalId;
        this.courseId = courseId;
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
    @Column(name = "grade_id")
    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    @Basic
    @Column(name = "professional_id")
    public int getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(int professionalId) {
        this.professionalId = professionalId;
    }

    @Basic
    @Column(name = "course_id")
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "time")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfessionalCourseEntity that = (ProfessionalCourseEntity) o;

        if (id != that.id) return false;
        if (gradeId != that.gradeId) return false;
        if (professionalId != that.professionalId) return false;
        if (courseId != that.courseId) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + gradeId;
        result = 31 * result + professionalId;
        result = 31 * result + courseId;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
