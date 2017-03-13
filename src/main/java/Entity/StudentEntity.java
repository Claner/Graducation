package Entity;

import javax.persistence.*;

/**
 * Created by Clanner on 2017/3/10.
 */
@Entity
@Table(name = "student", schema = "school")
public class StudentEntity {
    private int stuId;
    private int academyId;
    private int professionalId;
    private int gradeId;
    private String name;
    private String sex;
    private String phone;
    private String address;

    public StudentEntity() {
    }

    public StudentEntity(int stuId) {
        this.stuId = stuId;
    }

    public StudentEntity(int stuId, int academyId, int professionalId, int gradeId, String name, String sex, String phone, String address) {
        this.stuId = stuId;
        this.academyId = academyId;
        this.professionalId = professionalId;
        this.gradeId = gradeId;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.address = address;
    }

    @Id
    @Column(name = "stu_id")
    public int getStuId() {
        return stuId;
    }

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    @Basic
    @Column(name = "academy_id")
    public int getAcademyId() {
        return academyId;
    }

    public void setAcademyId(int academyId) {
        this.academyId = academyId;
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
    @Column(name = "grade_id")
    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int grade) {
        this.gradeId = grade;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentEntity that = (StudentEntity) o;

        if (stuId != that.stuId) return false;
        if (academyId != that.academyId) return false;
        if (professionalId != that.professionalId) return false;
        if (gradeId != that.gradeId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (sex != null ? !sex.equals(that.sex) : that.sex != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stuId;
        result = 31 * result + academyId;
        result = 31 * result + professionalId;
        result = 31 * result + gradeId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
