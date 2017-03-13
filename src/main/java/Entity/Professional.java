package Entity;

/**
 * Created by Clanner on 2017/3/7.
 */
public class Professional {
    private int id;
    private String academy;
    private String proName;
    private String year;

    public Professional(int id, String academy, String proName, String year) {
        this.id = id;
        this.academy = academy;
        this.proName = proName;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAcademyId() {
        return academy;
    }

    public void setAcademyId(String academy) {
        this.academy = academy;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
