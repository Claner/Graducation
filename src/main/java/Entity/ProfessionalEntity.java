package Entity;

import javax.persistence.*;

/**
 * Created by Clanner on 2017/3/7.
 */
@Entity
@Table(name = "professional", schema = "school")
public class ProfessionalEntity {
    private int id;
    private int academyId;
    private String proName;
    private String year;

    public ProfessionalEntity() {
    }

    public ProfessionalEntity(int id) {
        this.id = id;
    }

    public ProfessionalEntity(String proName) {
        this.proName = proName;
    }

    public ProfessionalEntity(int academyId, String proName, String year) {
        this.academyId = academyId;
        this.proName = proName;
        this.year = year;
    }

    public ProfessionalEntity(int id, int academyId, String proName, String year) {
        this.id = id;
        this.academyId = academyId;
        this.proName = proName;
        this.year = year;
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
    @Column(name = "academy_id")
    public int getAcademyId() {
        return academyId;
    }

    public void setAcademyId(int academyId) {
        this.academyId = academyId;
    }

    @Basic
    @Column(name = "pro_name")
    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    @Basic
    @Column(name = "year")
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfessionalEntity that = (ProfessionalEntity) o;

        if (id != that.id) return false;
        if (academyId != that.academyId) return false;
        if (proName != null ? !proName.equals(that.proName) : that.proName != null) return false;
        if (year != null ? !year.equals(that.year) : that.year != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + academyId;
        result = 31 * result + (proName != null ? proName.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        return result;
    }
}
