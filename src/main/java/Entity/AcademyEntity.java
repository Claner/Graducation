package Entity;

import javax.persistence.*;

/**
 * Created by Clanner on 2017/3/6.
 */
@Entity
@Table(name = "academy", schema = "school")
public class AcademyEntity {
    private int id;
    private String academyName;

    public AcademyEntity() {
    }

    public AcademyEntity(int id) {
        this.id = id;
    }

    public AcademyEntity(String academyName) {
        this.academyName = academyName;
    }

    public AcademyEntity(int id, String academyName) {
        this.id = id;
        this.academyName = academyName;
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
    @Column(name = "academy_name")
    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AcademyEntity that = (AcademyEntity) o;

        if (id != that.id) return false;
        if (academyName != null ? !academyName.equals(that.academyName) : that.academyName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (academyName != null ? academyName.hashCode() : 0);
        return result;
    }
}
