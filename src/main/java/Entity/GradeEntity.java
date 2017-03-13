package Entity;

import javax.persistence.*;

/**
 * Created by Clanner on 2017/3/11.
 */
@Entity
@Table(name = "grade", schema = "school")
public class GradeEntity {
    private int id;
    private String gradeName;

    public GradeEntity() {
    }

    public GradeEntity(String gradeName) {
        this.gradeName = gradeName;
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
    @Column(name = "grade_name")
    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GradeEntity that = (GradeEntity) o;

        if (id != that.id) return false;
        if (gradeName != null ? !gradeName.equals(that.gradeName) : that.gradeName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (gradeName != null ? gradeName.hashCode() : 0);
        return result;
    }
}
