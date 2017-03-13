package Entity;

import java.util.List;

/**
 * Created by Clanner on 2017/3/13.
 */
public class GradeStudentScore {
    private int grade;

    private List<StudentScore> list;

    public GradeStudentScore(int grade, List<StudentScore> list) {
        this.grade = grade;
        this.list = list;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public List<StudentScore> getList() {

        return list;
    }

    public void setList(List<StudentScore> list) {
        this.list = list;
    }
}
